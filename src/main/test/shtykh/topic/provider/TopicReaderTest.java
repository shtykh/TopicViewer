package shtykh.topic.provider;

import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;
import shtykh.topic.data.Topic;
import shtykh.topic.provider.args.ArgsMock;

public class TopicReaderTest {
	private static final String ROOT_PATH = "testData";
	Provider<String, Topic> topicReader;

	@Before
	public void setUp() throws ProviderException {
		topicReader = new CachedTopicReader();
		new ArgsMock((ArgsReceiver) topicReader);
	}

	@Test
	public void testKeys() throws Exception {
		Assert.assertNotNull(topicReader.keys());
	}

	@Test
	public void testGet() throws Exception {
		Topic topic = topicReader.get("¥ˆ≤µ˚∞§");
		Assert.assertNotNull(topic);
		Assert.assertEquals("8015-04-06-13-02-22", topic.getTimestamp());
	}
}