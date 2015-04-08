package shtykh.topic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shtykh.topic.util.HtmlHelper;
import shtykh.topic.util.Table;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import static java.net.URLEncoder.encode;
import static shtykh.topic.util.HtmlHelper.htmlPage;

@EnableAutoConfiguration
@Controller 
public class TopicViewerController {
	private static Logger log = Logger.getLogger(TopicViewerController.class);

	private String INITIALISATION_ERROR_PAGE = null;
	private final String errorPageRef = "errorPageRef";
	private final String topicPageRef = "topicPageRef";
	private final String topicPartitionList = "topicPartitionList";
	
	@Autowired
	private HtmlHelper htmlHelper;
	
	@Autowired
	private TopicReader provider;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		if (INITIALISATION_ERROR_PAGE != null) {
			return INITIALISATION_ERROR_PAGE;
		}
		Table table = new Table("Name", "Statistics", "Partitions list");
		Set<String> keySet = provider.keySet();
		for (String topicName : keySet) {
			String hrefStatistics;
			String hrefList;
			try {
				String cleanName = encode(topicName, "UTF-8");
				hrefStatistics = htmlHelper.href(topicPageRef + "?name=" + cleanName);
				hrefList = htmlHelper.href(topicPartitionList + "?name=" + cleanName);
			} catch (Exception e) {
				String error = errorHref(e);
				hrefStatistics = error;
				hrefList       = error;
			}
			table.addRow(topicName, hrefStatistics, hrefList);
		}
		String body = keySet.isEmpty() ? "is empty" : table.html();
		return htmlPage("Topics", "Topics:", body);
	}

	@RequestMapping(topicPageRef)
	@ResponseBody
	public String topicPage(
			@RequestParam(value = "name") String name) {
		if (INITIALISATION_ERROR_PAGE != null) {
			return INITIALISATION_ERROR_PAGE;
		}
		try {
			Topic topic = provider.get(name);
			return topic.getStatisticsPage();
		} catch (Exception e) {
			return errorPage(e.getMessage());
		}
	}

	@RequestMapping(topicPartitionList)
	@ResponseBody
	public String topicListPage(
			@RequestParam(value = "name") String name) {
		if (INITIALISATION_ERROR_PAGE != null) {
			return INITIALISATION_ERROR_PAGE;
		}
		try {
			Topic topic = provider.get(name);
			return topic.getListPage();
		} catch (Exception e) {
			return errorPage(e.getMessage());
		}
	}

	@RequestMapping(errorPageRef)
	@ResponseBody
	public String errorPage(
			@RequestParam(value = "msg") String msg) {
		return htmlPage("Error", msg);
	}

	private String errorHref(Exception ex) {
		log.error(ex.getMessage());
		if (INITIALISATION_ERROR_PAGE != null) {
			return INITIALISATION_ERROR_PAGE;
		}
		String message;
		try {
			message = encode(ex.getMessage(), "UTF-8");
		} catch (UnsupportedEncodingException unsupportedEncodingEx) {
			log.error(unsupportedEncodingEx.getMessage());
			message = "UTF8_IS_NOT_SUPPORTED";
		}
		return htmlHelper.href(errorPageRef + "?msg=" + message, "Error! (see error page)");
	}

	public static void main(String[] args) throws Exception {
		TopicReader.ROOT_DIR = args[0];
		Object[] classes = new Object[]{
				HtmlHelper.class,
				TopicViewerController.class,
				TopicReader.class};
		ResourceLoader resourceLoader = new FileSystemXmlApplicationContext("/src/main/resources/applicationContext.xml");
		SpringApplication app = new SpringApplication(resourceLoader, classes);
		app.run(args);
	}
}
