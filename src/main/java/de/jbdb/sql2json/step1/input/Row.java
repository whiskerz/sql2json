package de.jbdb.sql2json.step1.input;

public class Row {

	private String valueRow;

	public Row(String valueRow) {
		this.valueRow = valueRow;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valueRow == null) ? 0 : valueRow.hashCode());
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
		Row other = (Row) obj;
		if (valueRow == null) {
			if (other.valueRow != null)
				return false;
		} else if (!valueRow.equals(other.valueRow))
			return false;
		return true;
	}

}
