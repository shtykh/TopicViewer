package shtykh.topic.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by shtykh on 06/04/15.
 */
public class TableBuilder {
	private static final int BORDER = 1;
	private List<String[]> contents;

	public TableBuilder(String... hat) {
		contents = new ArrayList<>();
		if (hat.length != 0) {
			addRow(hat);
		}
	}

	public TableBuilder addRow(String... strings) {
		contents.add(strings);
		return this;
	}
	
	public TableBuilder addRows(Collection<String[]> rows) {
		contents.addAll(rows);
		return this;
	}
	
	public String buildHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<table border=" + BORDER + ">");
		for (int i = 0; i < contents.size(); i++) {
			String[] row = contents.get(i);
			sb.append("<tr>");
			for (int j = 0; j < contents.get(i).length; j++) {
				sb.append("<td>");
				sb.append(row[j]);
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
}
