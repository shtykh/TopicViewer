package shtykh.topic;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shtykh on 06/04/15.
 */
public class TopicReader implements Provider<Topic> {
	private final File rootDir;
	
	private File getTopicDir(String topicName) {
		return new File(rootDir.getAbsolutePath().concat("/" + topicName + "/history/"));
	}
	
	private File getTheLastFile(File directory) {
		if (!directory.isDirectory()) {
			return null;
		}
		File[] timestampFiles = directory.listFiles();
		if (timestampFiles == null || timestampFiles.length <= 0) {
			return null;
		} else {
			Arrays.sort(timestampFiles, Comparator.<File>reverseOrder());
			return timestampFiles[0];
		}
	}
	
	private Map<String, Topic> topics;

	public TopicReader(String rootDirPath) {
		this.rootDir = new File(rootDirPath);
		if (!rootDir.isDirectory()) {
			throw new RuntimeException(rootDirPath + " is not a directory");
		}
		topics = new ConcurrentHashMap<>();
	}


	@Override
	public Set<String> keySet() {
		return topics.keySet();
	}

	@Override
	public Topic get(Object key) throws IOException {
		Topic topic = topics.get(key);
		File topicDir = getTopicDir((String) key);
		File lastFile = getTheLastFile(topicDir);
		if (topic != null && !topic.getTimeStamp().equals(lastFile.getName())) {
			refreshTopic((String) key, lastFile);
		}
		return topic;
	}

	@Override
	public void refresh() throws IOException {
		for (File topicNameFile : rootDir.listFiles()) {
			if (topicNameFile.isDirectory()) {
				String topicName = topicNameFile.getName();
				if (!keySet().contains(topicName)) {
					File history = new File(topicNameFile.getAbsolutePath().concat("/history"));
					if (history.isDirectory()) {
						File timestampFile = getTheLastFile(history);
						Topic topic = topics.get(topicName);
						String maxTimestamp = timestampFile.getName();
						if (topic == null || !maxTimestamp.equals(topic.getTimeStamp())) {
							refreshTopic(topicName, timestampFile);
						}
					}
				}
			}
		}
	}

	private void refreshTopic(String topicName, File timestampFile) throws IOException {
		File file = new File(timestampFile.getAbsolutePath().concat("/offsets.csv"));
		topics.put(topicName, readFromFile(topicName, timestampFile.getName(), file));
	}

	private Topic readFromFile(String name, String timestamp, File file) throws IOException {
		Topic topic = new Topic(name, timestamp);
		CSVParser reader = new CSVParser(new FileReader(file), CSVFormat.DEFAULT);
		reader.forEach((record)->topic.addPartition(
											Integer.decode(record.get(0)),
											Long.   decode(record.get(1))));
		topics.put(name, topic);
		return topic;
	}

	@Override
	public boolean isEmpty() {
		return topics.isEmpty();
	}
}
