package de.jbdb.sql2json.step1.input.modell;

public class TableName {

	private String tableName;

	public TableName(String name) {
		this.tableName = name.trim();
		this.tableName = this.tableName.replaceAll("`", "");
	}

	public String get() {
		return tableName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableName other = (TableName) obj;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return tableName;
	}
}
