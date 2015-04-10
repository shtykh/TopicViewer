package shtykh.topic.provider;

import shtykh.topic.data.Topic;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shtykh on 09/04/15.
 */
public class CachedTopicReader extends TopicReader {
	private Map<String, Topic> cache;

	public CachedTopicReader() {
		super();
		cache = new ConcurrentHashMap<>();
	}

	@Override
	public Topic get(String topicName) throws ProviderException {
		File lastTimestampDir = getTheLastTimestampDir(topicName);
		if (lastTimestampDir == null) {
			return null;
		} else {
			Topic topic = cache.get(topicName);
			if (needToCache(topic, lastTimestampDir.getName())) {
				cache.put(topicName, readTopic(topicName, lastTimestampDir));
			}
			return cache.get(topicName);
		}
	}

	private boolean needToCache(Topic oldTopicFromCache, String theNewestTimestamp) {
		return oldTopicFromCache == null || !oldTopicFromCache.getTimestamp().equals(theNewestTimestamp);
	}

}
