package shtykh.topic.data;

import shtykh.topic.util.printer.FieldPrinter;
import shtykh.topic.util.TableBuilder;

import static shtykh.topic.util.HtmlHelper.htmlPage;

/**
 * Created by shtykh on 03/04/15.
 */
public class Topic extends FieldPrinter {
	private final String name;
	private final String timestamp;
	private final PartitionsData partitionsData;

	public Topic(String name, String timestamp, PartitionsData partitionsData) {
		super(new String[]{"name", "timestamp", "partitionsData"});
		this.name = name;
		this.timestamp = timestamp;
		this.partitionsData = partitionsData;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getPartitionDataPage() {
		String body = partitionsData.isEmpty()
				? "There is no partitions."
				: new TableBuilder("Partition number", "Messages number")
					.addRows(partitionsData.getRows())
					.buildHtml();
		return htmlPage("Topic '" + name + "'. Partitions.", body);
	}

	public String getStatisticsPage() throws Exception {
		return htmlPage("Topic '" + name + "'. Statistics.", 
				getRowTable().buildHtml());
	}
}
