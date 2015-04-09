package shtykh.topic.provider;

import shtykh.topic.util.TableBuilder;

import java.util.Collection;

/**
 * Created by shtykh on 09/04/15.
 */
public interface RowPrinter {
	default TableBuilder getRowTable() throws Exception {
		return new TableBuilder().addRows(getRows());
	}
	
	Collection<String[]> getRows() throws Exception;
	boolean isEmpty();
}
