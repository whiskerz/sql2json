package de.jbdb.sql2json;

import java.util.List;

public class Insert {
	private String tableName;
	private List<String> columnNames;
	private List<InsertValues> insertValues;

	public Insert(String string) {
		// TODO Auto-generated constructor stub
	}

	public String getTableName() {
		return tableName;
	}

	public void mergeWith(Insert otherInsert) {
		insertValues.addAll(otherInsert.insertValues);
	}

}
