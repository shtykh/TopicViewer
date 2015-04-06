package shtykh.topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shtykh.topic.util.Table;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static java.net.URLEncoder.encode;
import static shtykh.topic.util.Util.href;
import static shtykh.topic.util.Util.htmlPage;

@Controller
@EnableAutoConfiguration
public class TopicViewerController {
	private static final String ERROR_PAGE_REF = "errorpage";
	private static final String TOPIC_PAGE_REF = "topic";
	private static String ROOT_DIR;

	private Provider<Topic> topicProvider;

	public TopicViewerController() {
		topicProvider = new TopicReader(ROOT_DIR);
	}

	@RequestMapping("/")
	@ResponseBody
	String home() {
		Table table = new Table("Name", "Link");
		try {
			topicProvider.refresh();
		} catch (IOException e) {
			errorPage(e.getMessage());
		}
		for (String topicName : topicProvider.keySet()) {
			String href;
			try {
				String cleanName = encode(topicName, "UTF-8");
				href = href(TOPIC_PAGE_REF + "?name=" + cleanName);
			} catch (Exception e) {
				href = errorHref(e);
			}
			table.addRow(topicName, href);
		}
		String body = topicProvider.isEmpty() ? "is empty" : table.html();
		return htmlPage("Topics", "Topics:", body);
	}

	@RequestMapping(TOPIC_PAGE_REF)
	@ResponseBody
	public String topicPage(
			@RequestParam(value = "name") String name) throws IOException {
		Topic topic = topicProvider.get(name);
		return topic.toString();
	}

	public static void main(String[] args) throws Exception {
		TopicViewerController.ROOT_DIR = args[0];
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
			message = encode(ex.getMessage(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			message = "UTF8_IS_NOT_SUPPORTED";
		}
		return href(ERROR_PAGE_REF + "?msg=" + message, "Error! (see error page)");
	}
}
