package shtykh.topic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by shtykh on 06/04/15.
 */
public class TopicsMock implements Provider<Topic> {
	private Map<String, Topic> topics;

	public TopicsMock() {
		this.topics = init();
	}

	private Map<String, Topic> init() {
		HashMap<String, Topic> topics = new HashMap<>();
		topics.put("now0", new Topic("now0", "2015-04-06-13-02-22"));
		topics.put("long ago", new Topic("long ago", "1215-04-06-13-02-22"));
		topics.put("©∫çΩ", new Topic("©∫çΩ", "8815-04-06-13-02-22"));
		return topics;
	}

	@Override
	public void refresh() {}

	@Override
	public boolean isEmpty() {
		return topics.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return topics.keySet();
	}

	@Override
	public Topic get(Object key) {
		return topics.get(key);
	}
}
