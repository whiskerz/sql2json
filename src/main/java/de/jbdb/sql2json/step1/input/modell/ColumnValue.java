package de.jbdb.sql2json.step1.input.modell;

public class ColumnValue {

	private ColumnName columnName;
	private Value value;

	public ColumnValue(ColumnName columnName, String valueString) {
		this.columnName = columnName;
		this.value = new Value(valueString);
	}

	@Override
	public String toString() {
		return "\"" + columnName.toString() + "\":\"" + value.toString() + "\"";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ColumnValue other = (ColumnValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
