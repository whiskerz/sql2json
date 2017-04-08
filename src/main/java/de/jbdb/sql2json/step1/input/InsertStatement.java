package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.ConvenientIllegalArgumentException.throwIllegalArgument;

import java.util.List;

public class InsertStatement {

	private TableName tableName;
	private List<ColumnName> columnNames;
	private List<ValueRow> valueRows;

	public InsertStatement(String statement) {
		if (statement == null) {
			throwIllegalArgument(
					"InsertStatement construction parameter was null! Please check caller so he supplies a valid parameter!");
		}
		if (statement.isEmpty()) {
			throwIllegalArgument(
					"InsertStatement construction parameter was empty! Please check caller so he supplies a valid parameter!");
		}
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

	public List<ValueRow> getValueRows() {
		return valueRows;
	}
}
