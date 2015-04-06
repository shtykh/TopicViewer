package shtykh.topic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shtykh on 06/04/15.
 */
public class TopicMock implements TopicManager {
	private static Map<String, Topic> topics;

	public TopicMock() {
		topics = new HashMap<String, Topic>();
		topics.put("now0", new Topic("now0", new Date()));
		topics.put("now1", new Topic("now1", new Date()));
		topics.put("long ago", new Topic("long ago", new Date(23434L)));
	}

	@Override
	public Map<String, Topic> get() {
		return topics;
	}

	@Override
	public Map<String, Topic> refresh(Map<String, Topic> topics) {
		return topics;
	}
}
