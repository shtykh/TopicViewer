package shtykh.topic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import shtykh.topic.provider.CachedTopicReader;
import shtykh.topic.provider.args.ArgsMock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
		TopicViewer.class,
		CachedTopicReader.class,
		MainApplication.class,
		ArgsMock.class})
@WebAppConfiguration
@IntegrationTest("server.port=9000")
public class TopicViewerTest {
	private static final String EXPECTED_HOME_RESPONSE_BODY =
			"<html><title>Topics</title>" +
			"<body><h1>Topics:</h1>" +
			"<table border=1>" +
			"<tr><td>Name</td><td>Statistics</td><td>Partitions list</td></tr>" +
			"<tr><td>empty</td><td><a href=http://localhost:8080/topic/stat?name=empty>http://localhost:8080/topic/stat?name=empty</a></td><td><a href=http://localhost:8080/topic/list?name=empty>http://localhost:8080/topic/list?name=empty</a></td></tr>" +
			"<tr><td>now1</td><td><a href=http://localhost:8080/topic/stat?name=now1>http://localhost:8080/topic/stat?name=now1</a></td><td><a href=http://localhost:8080/topic/list?name=now1>http://localhost:8080/topic/list?name=now1</a></td></tr>" +
			"<tr><td>old one</td><td><a href=http://localhost:8080/topic/stat?name=old+one>http://localhost:8080/topic/stat?name=old+one</a></td><td><a href=http://localhost:8080/topic/list?name=old+one>http://localhost:8080/topic/list?name=old+one</a></td></tr>" +
			"<tr><td>topic with space in csv</td><td><a href=http://localhost:8080/topic/stat?name=topic+with+space+in+csv>http://localhost:8080/topic/stat?name=topic+with+space+in+csv</a></td><td><a href=http://localhost:8080/topic/list?name=topic+with+space+in+csv>http://localhost:8080/topic/list?name=topic+with+space+in+csv</a></td></tr>" +
			"<tr><td>topicNew</td><td><a href=http://localhost:8080/topic/stat?name=topicNew>http://localhost:8080/topic/stat?name=topicNew</a></td><td><a href=http://localhost:8080/topic/list?name=topicNew>http://localhost:8080/topic/list?name=topicNew</a></td></tr><tr><td>¥ˆ≤µ˚∞§</td><td><a href=http://localhost:8080/topic/stat?name=%C2%A5%CB%86%E2%89%A4%C2%B5%CB%9A%E2%88%9E%C2%A7>http://localhost:8080/topic/stat?name=%C2%A5%CB%86%E2%89%A4%C2%B5%CB%9A%E2%88%9E%C2%A7</a></td><td><a href=http://localhost:8080/topic/list?name=%C2%A5%CB%86%E2%89%A4%C2%B5%CB%9A%E2%88%9E%C2%A7>http://localhost:8080/topic/list?name=%C2%A5%CB%86%E2%89%A4%C2%B5%CB%9A%E2%88%9E%C2%A7</a></td></tr>" +
			"</table></body></html>";
	
	private RestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		restTemplate = new TestRestTemplate();
	}

	@Test
	public void testHome() throws Exception {
		ResponseEntity<String> entity =
				restTemplate.getForEntity("http://localhost:9000/", String.class);
		Assert.assertEquals(EXPECTED_HOME_RESPONSE_BODY, entity.getBody());
	}

	@Test
	public void testEmpty() throws Exception {
		ResponseEntity<String> entity =
				restTemplate.getForEntity("http://localhost:9000/topic/stat?name=empty", String.class);
		Assert.assertTrue(entity.getBody().contains("is empty"));
	}
}