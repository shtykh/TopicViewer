package shtykh.topic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shtykh.topic.provider.Provider;
import shtykh.topic.util.HtmlHelper;
import shtykh.topic.util.TableBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import static java.net.URLEncoder.encode;
import static shtykh.topic.util.HtmlHelper.htmlPage;

@Controller
@EnableAutoConfiguration
public class TopicViewerController {
	private static Logger log = Logger.getLogger(TopicViewerController.class);
	private String INITIALISATION_ERROR_PAGE = null;
	
	private final String errorPageRef = "errorPageRef";
	private final String topicStatistics = "topic/stat";
	private final String topicPartitionList = "topic/list";

	private HtmlHelper htmlHelper;
	private Provider<Topic> provider;

	@Autowired
	public TopicViewerController(Provider<Topic> provider, HtmlHelper htmlHelper) {
		this.provider = provider;
		this.htmlHelper = htmlHelper;
		init();
	}

	public void init() {
		try {
			provider.init();
		} catch (Exception e) {
			INITIALISATION_ERROR_PAGE = e.getMessage();
		}
	}

	@RequestMapping("/")
	@ResponseBody
	String home() {
		if (INITIALISATION_ERROR_PAGE != null) {
			return INITIALISATION_ERROR_PAGE;
		}
		TableBuilder tableBuilder = new TableBuilder("Name", "Statistics", "Partitions list");
		Set<String> keySet = provider.keySet();
		for (String topicName : keySet) {
			String hrefStatistics;
			String hrefList;
			try {
				String cleanName = encode(topicName, "UTF-8");
				hrefStatistics = htmlHelper.href(topicStatistics + "?name=" + cleanName);
				hrefList = htmlHelper.href(topicPartitionList + "?name=" + cleanName);
			} catch (Exception e) {
				String error = errorHref(e);
				hrefStatistics = error;
				hrefList       = error;
			}
			tableBuilder.addRow(topicName, hrefStatistics, hrefList);
		}
		String body = keySet.isEmpty() ? "is empty" : tableBuilder.buildHtml();
		return htmlPage("Topics", "Topics:", body);
	}

	@RequestMapping(topicStatistics)
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
			return topic.getPartitionDataPage();
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
}
