package shtykh.topic.util.printer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by shtykh on 09/04/15.
 */
public abstract class FieldPrinter implements RowPrinter {
	private final String[] fieldNames;

	public FieldPrinter(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}

	public Collection<String[]> getFieldRows() throws PrinterException {
		Collection<String[]> rows = new ArrayList<>();
		for (String fieldName : fieldNames) {
			Class clazz = getClass();
			Field field;
			Object value;
			try {
				field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				value = field.get(this);
			} catch (Exception e) {
				throw new PrinterException(e);
			}
			if (FieldPrinter.class.isAssignableFrom(value.getClass())) {
				Collection<String[]> rowsOfField = ((FieldPrinter) value).getFieldRows();
				rows.addAll(rowsOfField);
			} else {
				rows.add(new String[]{fieldName, value.toString()});
			}
			field.setAccessible(false);
		}
		return rows;
	}

	@Override
	public Collection<String[]> getRows() throws PrinterException {
		try {
			return getFieldRows();
		} catch (Exception e) {
			throw new PrinterException(e);
		}
	}

	@Override
	public boolean isEmpty() {
		return fieldNames.length == 0;
	}
}
