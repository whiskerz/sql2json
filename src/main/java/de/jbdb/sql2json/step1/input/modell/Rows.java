package de.jbdb.sql2json.step1.input.modell;

import java.util.ArrayList;
import java.util.List;

import de.jbdb.sql2json.Sql2JSONTestObjects;

public class Rows {

	private ArrayList<Row> rowList;

	public Rows createRows(String rowAsString) {
		return new Rows(new Columns(Sql2JSONTestObjects.TEST_COLUMN), rowAsString);
	}

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

	@Override
	public String toString() {
		return "Rows [rowList=" + rowList + "]";
	}

}
