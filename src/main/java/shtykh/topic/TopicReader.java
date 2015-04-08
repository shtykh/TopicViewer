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
	public static String ROOT_DIR;
	private File rootDir;
	private Map<String, Topic> cache;
	private Set<String> keySet;

	@Override
	public void init() throws Exception {
		cache = new ConcurrentHashMap<>();
		keySet = new HashSet<>();
		this.rootDir = new File(ROOT_DIR);
		if (!rootDir.isDirectory()) {
			throw new Exception(ROOT_DIR + " is not a directory");
		}
	}
	

	private File getTopicDir(String topicName) {
		return new File(rootDir.getAbsolutePath().concat("/" + topicName + "/history/"));
	}

	private File getTheLastTimestampDir(String topicName) {
		File directory = getTopicDir(topicName);
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
	public Topic get(Object key) throws Exception {
		String topicName = (String) key;
		File lastTimestampDir = getTheLastTimestampDir(topicName);
		if (lastTimestampDir == null) {
			cache.remove(topicName);
			return null;
		} else {
			Topic topic = cache.get(topicName);
			if (topic == null || !topic.getTimeStamp().equals(lastTimestampDir.getName())) {
				refreshTopic(topicName, lastTimestampDir);
			}
			return cache.get(topicName);
		}
	}

	private void refreshTopic(String topicName, File timestampFile) throws TopicReaderException {
		File file = new File(timestampFile.getAbsolutePath().concat("/offsets.csv"));
		cache.put(topicName, readFromFile(topicName, timestampFile.getName(), file));
	}

	private Topic readFromFile(String name, String timestamp, File file) throws TopicReaderException {
		Topic topic = new Topic(name, timestamp);
		try {
			CSVParser reader = new CSVParser(new FileReader(file), CSVFormat.DEFAULT);
			reader.forEach((record)->topic.addPartition(
						Integer.decode(record.get(0)),
						Long.   decode(record.get(1))));
			cache.put(name, topic);
			return topic;
		} catch (IOException e) {
			throw new TopicReaderException("IOException in file: " + file.getAbsolutePath(), e);
		} catch (NumberFormatException e) {
			throw new TopicReaderException("Invalid csv file: " + file.getAbsolutePath(), e);
		}
	}
}
