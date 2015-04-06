package shtykh.topic;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shtykh on 06/04/15.
 */
public class TopicReader implements Provider<Topic> {
	private final File rootDir;
	private Map<String, Topic> cache;
	private Set<String> keySet;

	public TopicReader(String rootDirPath) {
		this.rootDir = new File(rootDirPath);
		if (!rootDir.isDirectory()) {
			throw new RuntimeException(rootDirPath + " is not a directory");
		}
		cache = new ConcurrentHashMap<>();
		keySet = new HashSet<>();
	}

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

	@Override
	public Set<String> keySet() {
		keySet.clear();
		if(rootDir.listFiles() != null) {
			for (File file : rootDir.listFiles()) {
				keySet.add(file.getName());
			}
		}
		return keySet;
	}

	@Override
	public Topic get(Object key) throws IOException {
		File lastFile = getTheLastFile(getTopicDir((String) key));
		if (lastFile == null) {
			cache.remove(key);
			return null;
		} else {
			Topic topic = cache.get(key);
			if (topic == null || !topic.getTimeStamp().equals(lastFile.getName())) {
				refreshTopic((String) key, lastFile);
			}
			return cache.get(key);
		}
	}

	private void refreshTopic(String topicName, File timestampFile) throws IOException {
		File file = new File(timestampFile.getAbsolutePath().concat("/offsets.csv"));
		cache.put(topicName, readFromFile(topicName, timestampFile.getName(), file));
	}

	private Topic readFromFile(String name, String timestamp, File file) throws IOException {
		Topic topic = new Topic(name, timestamp);
		CSVParser reader = new CSVParser(new FileReader(file), CSVFormat.DEFAULT);
		reader.forEach((record)->topic.addPartition(
											Integer.decode(record.get(0)),
											Long.   decode(record.get(1))));
		cache.put(name, topic);
		return topic;
	}
}
