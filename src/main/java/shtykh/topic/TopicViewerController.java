package shtykh.topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shtykh.topic.util.Table;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static shtykh.topic.util.Util.href;
import static shtykh.topic.util.Util.htmlPage;

@Controller
@EnableAutoConfiguration
public class TopicViewerController {
	private static final String ERROR_PAGE_REF = "errorpage";
	private static final String TOPIC_PAGE_REF = "topic";

	private Map<String, Topic> topics;
	private TopicManager topicManager;

	public TopicViewerController() {
		topicManager = new TopicMock(); // TODO injection
		topics = topicManager.get();
	}

	@RequestMapping("/")
	@ResponseBody
	String home() {
		topics = topicManager.refresh(topics);
		Table table = new Table("Name", "Link");
		for (Topic topic : topics.values()) {
			String href;
			try {
				String cleanName = URLEncoder.encode(topic.getName(), "UTF-8");
				href = href(TOPIC_PAGE_REF + "?name=" + cleanName);
			} catch (Exception e) {
				href = errorHref(e);
			}
			table.addRow(topic.getName(), href);
		}
		String body = topics.isEmpty() ? "is empty" : table.html();
		return htmlPage("Topics", "Topics:", body);
	}

	@RequestMapping(TOPIC_PAGE_REF)
	@ResponseBody
	public String topicPage(
			@RequestParam(value = "name") String name) {
		Topic topic = topics.get(name);
		return topic.toString();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(TopicViewerController.class, args);
	}
	
	@RequestMapping(ERROR_PAGE_REF)
	@ResponseBody
	public String errorPage(
			@RequestParam(value = "msg") String msg) {
		return htmlPage("Error", msg);
	}

	private static String errorHref(Exception ex) {
		String message = null;
		try {
			message = URLEncoder.encode(ex.getMessage(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			message = "UTF8_IS_NOT_SUPPORTED";
		}
		return href(ERROR_PAGE_REF + "?msg=" + message, "Error! (see error page)");
	}
}
