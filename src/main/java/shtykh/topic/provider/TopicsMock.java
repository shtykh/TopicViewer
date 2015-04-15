package shtykh.topic.provider;

import shtykh.topic.data.PartitionsData;
import shtykh.topic.data.Topic;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by shtykh on 06/04/15.
 */
public class TopicsMock implements Provider<String, Topic> {
	private Map<String, Topic> map = new HashMap<>();

	public static final String KEY0 = "now0";
	public static final String KEY1 = "long ago";
	public static final String KEY2 = "©∫çΩ";

	public static final String TIMESTAMP0 = "2015-04-06-13-02-22";
	public static final String TIMESTAMP1 = "1215-04-06-13-02-22";
	public static final String TIMESTAMP2 = "8815-04-06-22-02-22";

	public TopicsMock() {
		PartitionsData data = new PartitionsData("TopicMock Partition Data");
		IntStream.range(0, 12)
			.forEach(i -> data.addPartition(i, i * 1234567L));
		map.put(KEY0, new Topic(KEY0, TIMESTAMP0, data));
		map.put(KEY1, new Topic(KEY1, TIMESTAMP1, data));
		map.put(KEY2, new Topic(KEY2, TIMESTAMP2, data));
	}

	@Override
	public String[] keys() {
		return map.keySet()
				.stream()
				.toArray(String[]::new);
	}

	@Override
	public Topic get(String key) throws ProviderException {
		return map.get(key);
	}
}
