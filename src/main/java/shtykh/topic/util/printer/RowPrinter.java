package shtykh.topic.util.printer;

import shtykh.topic.util.TableBuilder;

import java.util.Collection;

/**
 * Created by shtykh on 09/04/15.
 */
public interface RowPrinter {
	default TableBuilder getRowTable() throws PrinterException {
		return new TableBuilder().addRows(getRows());
	}

	Collection<String[]> getRows() throws PrinterException;

	boolean isEmpty();
}
