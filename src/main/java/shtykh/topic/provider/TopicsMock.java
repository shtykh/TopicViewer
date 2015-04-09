package shtykh.topic.provider;

import shtykh.topic.Topic;

import java.util.HashMap;

/**
 * Created by shtykh on 06/04/15.
 */
public class TopicsMock extends HashMap<String, Topic> implements Provider<Topic> {
	public TopicsMock() {
		super();
		PartitionsData data = new PartitionsData();
		for (int i = 0; i < 12; i++) {
			data.addPartition(i, i * 1234567L);
		}
		put("now0", new Topic("now0", "2015-04-06-13-02-22", data));
		put("long ago", new Topic("long ago", "1215-04-06-13-02-22", data));
		put("©∫çΩ", new Topic("©∫çΩ", "8815-04-06-13-02-22", data));
	}

	@Override
	public void init() throws Exception {
	}
}
