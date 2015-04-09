package shtykh.topic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shtykh.topic.data.Topic;
import shtykh.topic.provider.Provider;
import shtykh.topic.util.HtmlHelper;
import shtykh.topic.util.TableBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import static shtykh.topic.util.HtmlHelper.href;
import static shtykh.topic.util.HtmlHelper.htmlPage;

@Controller
public class TopicViewer {
	private static final Logger log = Logger.getLogger(TopicViewer.class);

	private final static String SCHEME = "http";
	private final static String HOST = "localhost";
	private final static int PORT = 8080;
	
	private static final String errorPageRef = "/errorPageRef";
	private static final String topicStatistics = "/topic/stat";
	private static final String topicPartitionList = "/topic/list";
	private static final String NAME_PARAM = "name";
	private static final String MSG_PARAM = "msg";

	private String initialisationErrorPage = null;

	private HtmlHelper htmlHelper;
	private Provider<String, Topic> provider;

	@Autowired
	public TopicViewer(Provider<String, Topic> provider) {
		this.provider = provider;
		this.htmlHelper = new HtmlHelper(SCHEME, HOST, PORT);
		try {
			provider.init();
		} catch (Exception e) {
			initialisationErrorPage = e.getMessage();
			log.error(initialisationErrorPage);
		}
	}

	@RequestMapping("/")
	@ResponseBody
	String home() {
		if (initialisationErrorPage != null) {
			return initialisationErrorPage;
		}
		TableBuilder tableBuilder = new TableBuilder("Name", "Statistics", "Partitions list");
		String[] keySet = provider.keys();
		for (String topicName : keySet) {
			String hrefStatistics;
			String hrefList;
			try {
				URI uriStatistics = htmlHelper.uriBuilder(topicStatistics)
						.addParameter(NAME_PARAM, topicName)
						.build();
				hrefStatistics = href(uriStatistics);
				URI uriPartitions = htmlHelper.uriBuilder(topicPartitionList)
						.addParameter(NAME_PARAM, topicName)
						.build();
				hrefList = href(uriPartitions);
			} catch (Exception e) {
				String error = errorHref(e);
				hrefStatistics = error;
				hrefList       = error;
			}
			tableBuilder.addRow(topicName, hrefStatistics, hrefList);
		}
		String body = keySet.length == 0 ? "is empty" : tableBuilder.buildHtml();
		return htmlPage("Topics", "Topics:", body);
	}

	@RequestMapping(topicStatistics)
	@ResponseBody
	public String topicPage(
			@RequestParam(value = NAME_PARAM) String name) {
		if (initialisationErrorPage != null) {
			return initialisationErrorPage;
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
			@RequestParam(value = NAME_PARAM) String name) {
		if (initialisationErrorPage != null) {
			return initialisationErrorPage;
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
			@RequestParam(value = MSG_PARAM) String msg) {
		return htmlPage("Error", msg);
	}

	private String errorHref(Exception ex) {
		log.error(ex);
		if (initialisationErrorPage != null) {
			return initialisationErrorPage;
		}
		URI uri = null;
		try {
			uri = htmlHelper.uriBuilder(errorPageRef)
						.addParameter(MSG_PARAM, ex.getMessage())
						.build();
		} catch (URISyntaxException uriException) {
			log.error(uriException);
		}
		return href(uri, "Error! (see error page)");
	}
}
