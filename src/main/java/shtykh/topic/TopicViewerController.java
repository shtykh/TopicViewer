package shtykh.topic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shtykh.topic.provider.Provider;
import shtykh.topic.util.HtmlHelper;
import shtykh.topic.util.TableBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import static shtykh.topic.util.HtmlHelper.href;
import static shtykh.topic.util.HtmlHelper.htmlPage;

@Controller
public class TopicViewerController {
	private static final Logger log = Logger.getLogger(TopicViewerController.class);
	private static final String errorPageRef = "/errorPageRef";
	private static final String topicStatistics = "/topic/stat";
	private static final String topicPartitionList = "/topic/list";
	private static final String NAME_PARAM = "name";
	private static final String MSG_PARAM = "msg";

	private String INITIALISATION_ERROR_PAGE = null;

	private HtmlHelper htmlHelper;
	private Provider<Topic> provider;

	@Autowired
	public TopicViewerController(Provider<Topic> provider, HtmlHelper htmlHelper) {
		this.provider = provider;
		this.htmlHelper = htmlHelper;
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
		String body = keySet.isEmpty() ? "is empty" : tableBuilder.buildHtml();
		return htmlPage("Topics", "Topics:", body);
	}

	@RequestMapping(topicStatistics)
	@ResponseBody
	public String topicPage(
			@RequestParam(value = NAME_PARAM) String name) {
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
			@RequestParam(value = NAME_PARAM) String name) {
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
			@RequestParam(value = MSG_PARAM) String msg) {
		return htmlPage("Error", msg);
	}

	private String errorHref(Exception ex) {
		log.error(ex);
		if (INITIALISATION_ERROR_PAGE != null) {
			return INITIALISATION_ERROR_PAGE;
		}
		URI uri = null;
		try {
			uri = htmlHelper.uriBuilder(errorPageRef)
						.addParameter(MSG_PARAM, ex.getMessage())
						.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return href(uri, "Error! (see error page)");
	}
}
