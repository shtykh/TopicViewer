package shtykh.topic;

import javafx.util.Pair;
import shtykh.topic.util.Table;
import shtykh.topic.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shtykh on 03/04/15.
 */
public class Topic {
	private String name;
	private String timeStamp;
	
	private List<Pair<Integer, Long>> data;
	private long max = Long.MIN_VALUE;
	private long min = Long.MAX_VALUE;
	private double avg = 0;
	private long sum = 0;

	public Topic() {
	}

	public Topic(String name, String timeStamp) {
		this.name = name;
		this.timeStamp = timeStamp;
		data = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		Table table = new Table();
		table.addRow("Name", name);
		table.addRow("Last run", timeStamp);
		table.addRow("Min", String.valueOf(min));
		table.addRow("Max", String.valueOf(max));
		table.addRow("Avg", String.valueOf(avg));
		table.addRow("Sum", String.valueOf(sum));
		return Util.htmlPage("Topic " + name + ". Statistics.", table.html());
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void addPartition(int partitionNumber, long messageNumber) {
		max = Math.max(max, messageNumber);
		min = Math.min(min, messageNumber);
		avg = (avg * data.size() + messageNumber) / (data.size() + 1);
		data.add(new Pair<>(partitionNumber, messageNumber));
		sum = sum + messageNumber;
	}
}
