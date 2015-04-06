package shtykh.topic;

import java.util.HashMap;

/**
 * Created by shtykh on 06/04/15.
 */
public class TopicsMock extends HashMap<String, Topic> implements Provider<Topic> {
	public TopicsMock() {
		super();
		put("now0", new Topic("now0", "2015-04-06-13-02-22"));
		put("long ago", new Topic("long ago", "1215-04-06-13-02-22"));
		put("©∫çΩ", new Topic("©∫çΩ", "8815-04-06-13-02-22"));
	}
}
