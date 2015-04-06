package shtykh.topic;

import java.util.Map;

/**
 * Created by shtykh on 06/04/15.
 */
public interface TopicManager {
	Map<String, Topic> get();
	Map<String, Topic> refresh(Map<String, Topic> topics);

}
