package de.jbdb.sql2json.step1.input;

import java.util.List;

public class InsertStatement {
	private TableName tableName;
	private List<ColumnName> columnNames;
	private List<ValueRow> valueRows;

	public InsertStatement(String string) {
		// TODO Auto-generated constructor stub
	}

	public TableName getTableName() {
		return tableName;
	}

	public void mergeWith(InsertStatement otherInsert) {
		valueRows.addAll(otherInsert.valueRows);
	}

	public List<ColumnName> getColumnNames() {
		return columnNames;
	}

}
