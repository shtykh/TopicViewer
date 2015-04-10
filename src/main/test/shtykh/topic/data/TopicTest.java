package shtykh.topic.data;

import org.junit.Before;
import org.junit.Test;
import shtykh.topic.provider.Provider;
import shtykh.topic.provider.TopicsMock;

import static org.junit.Assert.assertEquals;
import static shtykh.topic.provider.TopicsMock.*;

public class TopicTest {
	
	private Provider<String, Topic> topicProvider;
	
	@Before
	public void setUp(){
		topicProvider = new TopicsMock();
	}

	@Test
	public void testGetTimestamp() throws Exception {
		assertEquals(TopicsMock.TIMESTAMP0, topicProvider.get(KEY0).getTimestamp());
	}

	@Test
	public void testGetPartitionDataPage() throws Exception {
		Topic topic1 = topicProvider.get(KEY1);
		String partitionDataPage = topic1.getPartitionDataPage();
		assertEquals(
				"<html>" +
						"<title>Topic 'long ago'. Partitions.</title>" +
						"<body>" +
						"<h1>Topic 'long ago'. Partitions.</h1>" +
							"<table border=1>" +
								"<tr><td>Partition number</td><td>Messages number</td></tr>" +
								"<tr><td>0</td><td>0</td></tr>" +
								"<tr><td>1</td><td>1234567</td></tr>" +
								"<tr><td>2</td><td>2469134</td></tr>" +
								"<tr><td>3</td><td>3703701</td></tr>" +
								"<tr><td>4</td><td>4938268</td></tr>" +
								"<tr><td>5</td><td>6172835</td></tr>" +
								"<tr><td>6</td><td>7407402</td></tr>" +
								"<tr><td>7</td><td>8641969</td></tr>" +
								"<tr><td>8</td><td>9876536</td></tr>" +
								"<tr><td>9</td><td>11111103</td></tr>" +
								"<tr><td>10</td><td>12345670</td></tr>" +
								"<tr><td>11</td><td>13580237</td></tr>" +
							"</table>" +
						"</body>" +
				"</html>", partitionDataPage);
	}

	@Test
	public void testGetStatisticsPage() throws Exception {
		Topic topic2 = topicProvider.get(KEY2);
		String statisticsPage = topic2.getStatisticsPage();
		assertEquals(
				"<html>" +
						"<title>Topic '©∫çΩ'. Statistics.</title>" +
						"<body>" +
							"<h1>Topic '©∫çΩ'. Statistics.</h1>" +
							"<table border=1>" +
								"<tr><td>name</td><td>©∫çΩ</td></tr>" +
								"<tr><td>timestamp</td><td>8815-04-06-22-02-22</td></tr>" +
								"<tr><td>min</td><td>0</td></tr>" +
								"<tr><td>max</td><td>13580237</td></tr>" +
								"<tr><td>avg</td><td>6790118.5</td></tr>" +
								"<tr><td>sum</td><td>81481422</td></tr>" +
							"</table>" +
						"</body>" + 
				"</html>", statisticsPage);
	}
}