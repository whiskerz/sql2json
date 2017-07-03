package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.ConvenientIllegalArgumentException.throwIllegalArgument;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class InsertStatement {

	private TableName tableName;
	private Columns columns;
	private Rows valueRows;

	public InsertStatement(String statement) {
		if (statement == null) {
			throwIllegalArgument(
					"InsertStatement construction parameter was null! Please check caller so he supplies a valid parameter!");
		}
		if (statement.isEmpty()) {
			throwIllegalArgument(
					"InsertStatement construction parameter was empty! Please check caller so he supplies a valid parameter!");
		}

		String[] tableAndValues = statement.split("(?i)VALUES");
		assertThat(tableAndValues).describedAs("Now thats a strange statement with multiple Values: %s", statement)
				.hasSize(2);

		setTableAndColumnsFrom(tableAndValues[0]);
		setValueRowsFrom(tableAndValues[1]);
	}

	public TableName getTableName() {
		return tableName;
	}

	public void mergeWith(InsertStatement otherInsert) {
		valueRows.addAll(otherInsert.valueRows);
	}

	public List<ColumnName> getColumnNames() {
		return columns.getNames();
	}

	public List<Row> getValueRows() {
		return valueRows.asList();
	}

	private void setTableAndColumnsFrom(String insertIntoTableWithColumns) {
		String tableAndColumns = insertIntoTableWithColumns.replaceFirst("(?i)INSERT\\hINTO\\h", "");
		String[] tableAndColumnsSplit = tableAndColumns.split("\\(");
		assertThat(tableAndColumnsSplit).as("Now thats a strange statement with multiple brackets behind the table: %s",
				insertIntoTableWithColumns).hasSize(2);

		tableName = new TableName(tableAndColumnsSplit[0]);
		columns = new Columns(tableAndColumnsSplit[1]);
	}

	private void setValueRowsFrom(String valueRowsString) {
		valueRows = new Rows(valueRowsString);
	}
}
