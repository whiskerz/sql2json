package de.jbdb.sql2json.step1.input;

import java.util.ArrayList;
import java.util.List;

public class Rows {

	private ArrayList<Row> rowList;

	public Rows(String rowAsString) {
		String parameter = rowAsString;

		String[] rowParts = parameter.split("\\s*\\)\\s*(,)\\s*\\(");

		rowList = new ArrayList<Row>(rowParts.length);
		for (String individualRow : rowParts) {
			rowList.add(new Row(individualRow));
		}
	}

	public void addAll(Rows valueRows) {
		this.rowList.addAll(valueRows.rowList);
	}

	public List<Row> asList() {
		return rowList;
	}

	@Override
	public String toString() {
		return "Rows [rowList=" + rowList + "]";
	}

}
