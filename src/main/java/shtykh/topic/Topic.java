package shtykh.topic;

import shtykh.topic.provider.FieldPrinter;
import shtykh.topic.provider.PartitionsData;
import shtykh.topic.util.TableBuilder;

import static shtykh.topic.util.HtmlHelper.htmlPage;

/**
 * Created by shtykh on 03/04/15.
 */
public class Topic extends FieldPrinter {
	private final String name;
	private final String timeStamp;
	private final PartitionsData partitionsData;

	public Topic(String name, String timeStamp, PartitionsData partitionsData) {
		super(new String[]{"name", "timeStamp", "partitionsData"});
		this.name = name;
		this.timeStamp = timeStamp;
		this.partitionsData = partitionsData;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getPartitionDataPage() {
		String body = partitionsData.isEmpty()
				? "There is no partitions."
				: new TableBuilder("Partition number", "Messages number").
					addRows(partitionsData.getRows()).
					buildHtml();
		return htmlPage("Topic '" + name + "'. Partitions.", body);
	}

	public String getStatisticsPage() throws Exception {
		return htmlPage("Topic '" + name + "'. Statistics.", 
				getRowTable().buildHtml());
	}
}
