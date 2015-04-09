package shtykh.topic.provider;

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

	public Collection<String[]> getFieldRows() throws Exception {
		Collection<String[]> rows = new ArrayList<>();
		for (String fieldName: fieldNames) {
			Class clazz = getClass();
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			Object value = field.get(this);
			if(FieldPrinter.class.isAssignableFrom(value.getClass())) {
				Collection<String[]> rowsOfField = ((FieldPrinter) value).getFieldRows();
				rows.addAll(rowsOfField);
			} else{
				rows.add(new String[]{fieldName, value.toString()});
			}
			field.setAccessible(false);
		}
		return rows;
	}

	@Override
	public Collection<String[]> getRows() throws Exception{
		return getFieldRows();
	}

	@Override
	public boolean isEmpty() {
		return fieldNames.length == 0;
	}
}
