package shtykh.topic;

import shtykh.topic.util.Table;
import shtykh.topic.util.Util;

import java.util.Date;

/**
 * Created by shtykh on 03/04/15.
 */
public class Topic {
	private String name;
	private Date last;

	public Topic(String name, Date last) {
		this.name = name;
		this.last = last;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		Table table = new Table();
		table.addRow("Name", "Last");
		table.addRow(name, String.valueOf(last));
		return Util.htmlPage("Topic", table.html());
	}

	public Date getLast() {
		return last;
	}
}
