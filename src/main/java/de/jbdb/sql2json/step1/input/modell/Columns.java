package de.jbdb.sql2json.step1.input.modell;

import java.util.ArrayList;
import java.util.List;

public class Columns {

	private List<ColumnName> names;

	public Columns(String columnString) {
		names = new ArrayList<>();

		String columnNames = columnString.trim();
		if (columnNames.endsWith(")")) {
			columnNames = columnNames.substring(0, columnNames.length() - 1);
		}

		String[] columnNamesSplitted = columnNames.split(",");

		for (String columnName : columnNamesSplitted) {
			names.add(new ColumnName(columnName.trim()));
		}

	}

	public List<ColumnName> getNames() {
		return new ArrayList<>(names);
	}

	@Override
	public String toString() {
		return "Columns [names=" + names + "]";
	}

}
