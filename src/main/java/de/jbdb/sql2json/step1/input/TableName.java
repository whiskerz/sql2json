package de.jbdb.sql2json.step1.input;

public class TableName {

	private String name;

	public TableName(String name) {
		this.name = name.trim();
		this.name = this.name.replaceAll("`", "");
	}

	public String get() {
		return name;
	}

}
