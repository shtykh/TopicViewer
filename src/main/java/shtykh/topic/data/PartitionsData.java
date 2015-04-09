package shtykh.topic.data;

import shtykh.topic.util.printer.FieldPrinter;
import shtykh.topic.util.printer.RowPrinter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by shtykh on 09/04/15.
 */
public class PartitionsData extends FieldPrinter implements RowPrinter {
	private final List<Partition> data = new ArrayList<>();
	
	private long min = Long.MAX_VALUE;
	private long max = Long.MIN_VALUE;
	private double avg = 0;
	private long sum = 0;

	public PartitionsData() {
		super(new String[]{"min", "max", "avg", "sum"});
	}

	public void addPartition(int partitionNumber, long messageNumber) {
		min = Math.min(min, messageNumber);
		max = Math.max(max, messageNumber);
		avg = (avg * data.size() + messageNumber) / (data.size() + 1);
		sum += messageNumber;
		data.add(new Partition(partitionNumber, messageNumber));
	}
	
	@Override
	public Collection<String[]> getRows(){
		Collection<String[]> rows = new ArrayList<>();
		for (Partition partition : data) {
			rows.add(partition.toStringRow());	
		}
		return rows;
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	static class Partition {
		private final int number;
		private final long messages;

		Partition(int number, long messages) {
			this.number = number;
			this.messages = messages;
		}

		public String[] toStringRow() {
			return new String[]{String.valueOf(number), String.valueOf(messages)};
		}
	}
	
}
