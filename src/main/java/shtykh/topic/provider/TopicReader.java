package shtykh.topic.provider;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import shtykh.topic.Topic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by shtykh on 06/04/15.
 */
public class TopicReader implements Provider<Topic> {
	public static String ROOT_DIR;
	private File rootDir;
	private Set<String> keySet;

	@Override
	public void init() throws Exception {
		keySet = new HashSet<>();
		this.rootDir = new File(ROOT_DIR);
		if (!rootDir.isDirectory()) {
			throw new Exception(ROOT_DIR + " is not a directory");
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
		return readTopic(topicName, lastTimestampDir);
	}

	protected File getTheLastTimestampDir(String topicName) {
		File[] timestampFiles = getTopicDir(topicName).listFiles();
		if (timestampFiles == null) {
			return null;
		} else {
			Arrays.sort(timestampFiles, Comparator.<File>reverseOrder());
			return timestampFiles[0];
		}
	}

	protected Topic readTopic(String topicName, File timestampFile) throws TopicReaderException {
		File file = new File(timestampFile.getAbsolutePath().concat("/offsets.csv"));
		return readFromFile(topicName, timestampFile.getName(), file);
	}

	private File getTopicDir(String topicName) {
		return new File(rootDir.getAbsolutePath().concat("/" + topicName + "/history/"));
	}

	private Topic readFromFile(String name, String timestamp, File file) throws TopicReaderException {
		try {
			CSVParser reader = new CSVParser(new FileReader(file), CSVFormat.DEFAULT);
			PartitionsData data = new PartitionsData();
			Topic topic = new Topic(name, timestamp, data);
			reader.forEach((record)->data.addPartition(
						Integer.decode(record.get(0).trim()),
						Long.   decode(record.get(1).trim())));
			return topic;
		} catch (IOException e) {
			throw new TopicReaderException("IOException in file: " + file.getAbsolutePath(), e);
		} catch (NumberFormatException e) {
			throw new TopicReaderException("Invalid csv file: " + file.getAbsolutePath(), e);
		}
	}
}
