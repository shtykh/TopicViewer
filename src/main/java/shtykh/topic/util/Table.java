package shtykh.topic.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shtykh on 06/04/15.
 */
public class Table {
	private static final int BORDER = 1;
	private List<List<String>> contents;

	public Table(String... hat) {
		contents = new ArrayList<>();
		if (hat.length != 0) {
			addRow(hat);
		}
	}

	public void addRow(String... strings) {
		contents.add(Arrays.asList(strings));
	}
	
	public String html() {
		StringBuilder sb = new StringBuilder();
		sb.append("<table border=" + BORDER + ">");
		for (int i = 0; i < contents.size(); i++) {
			List<String> row = contents.get(i);
			sb.append("<tr>");
			for (int j = 0; j < contents.get(i).size(); j++) {
				sb.append("<td>");
				sb.append(row.get(j));
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	
	public String toString() {
		return html();
	}
}
