package de.jbdb.sql2json.step1.input.modell;

import java.util.ArrayList;
import java.util.List;

public class Rows {

	private ArrayList<Row> rowList;

	public Rows(Columns columns, String rowAsString) {
		String parameter = rowAsString;

		String[] rowParts = parameter.split("\\s*\\)\\s*(,)\\s*\\(");

		rowList = new ArrayList<Row>(rowParts.length);
		for (String individualRow : rowParts) {
			rowList.add(new Row(columns, individualRow));
		}
	}

	public void addAll(Rows valueRows) {
		this.rowList.addAll(valueRows.rowList);
	}

	public List<Row> asList() {
		return rowList;
	}

	public String toJSON() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (Row row : rowList) {
			builder.append(row.toJSON());
			builder.append(",");
		}
		builder.replace(builder.length() - 1, builder.length(), "]");

		return builder.toString();
	}

	@Override
	public String toString() {
		return "Rows [rowList=" + rowList + "]";
	}

}
