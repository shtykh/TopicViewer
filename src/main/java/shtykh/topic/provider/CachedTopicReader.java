package shtykh.topic.provider;

import shtykh.topic.Topic;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shtykh on 09/04/15.
 */
public class CachedTopicReader extends TopicReader {
	private Map<String, Topic> cache;

	@Override
	public void init() throws Exception {
		super.init();
		cache = new ConcurrentHashMap<>();
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
			if (needToCache(topic, lastTimestampDir.getName())) {
				cache.put(topicName, readTopic(topicName, lastTimestampDir));
			}
			return cache.get(topicName);
		}
	}

	private boolean needToCache(Topic topic, String timestamp) {
		return topic == null || !topic.getTimeStamp().equals(timestamp);
	}

}
