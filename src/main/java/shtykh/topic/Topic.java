package shtykh.topic;

import javafx.util.Pair;
import shtykh.topic.util.Table;
import shtykh.topic.util.Util;

import java.util.List;

/**
 * Created by shtykh on 03/04/15.
 */
public class Topic {
	private String name;
	private String timeStamp;
	
	private List<Pair<Integer, Long>> data;
	private long summ;
	private long max;
	private long min;
	private long avg;

	public Topic() {
	}

	public Topic(String name, String timeStamp) {
		this.name = name;
		this.timeStamp = timeStamp;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		Table table = new Table();
		table.addRow("Name", "Last");
		table.addRow(name, String.valueOf(timeStamp));
		return Util.htmlPage("Topic", table.html());
	}

	public String getTimeStamp() {
		return timeStamp;
	}
}
