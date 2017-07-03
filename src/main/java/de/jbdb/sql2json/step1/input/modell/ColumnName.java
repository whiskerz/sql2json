package de.jbdb.sql2json.step1.input.modell;

import java.util.Arrays;
import java.util.List;

public class ColumnName {

	private String columnName;
	private List<String> CONTROL_CHARACTERS = Arrays.asList("\"", "'", "`");

	public ColumnName(String columnName) {
		this.columnName = purify(columnName);
	}

	public String get() {
		return columnName;
	}

	private String purify(String columnName) {
		String toPurify = columnName;

		if (nameStartsWithControlCharacter(toPurify)) {
			toPurify = toPurify.substring(1);
		}
		if (nameEndsWithControlChracter(toPurify)) {
			toPurify = toPurify.substring(0, toPurify.length() - 1);
		}

		return toPurify;
	}

	private boolean nameStartsWithControlCharacter(String toPurify) {
		String firstCharacter = toPurify.substring(0, 1);
		return CONTROL_CHARACTERS.contains(firstCharacter);
	}

	private boolean nameEndsWithControlChracter(String toPurify) {
		String lastCharacter = toPurify.substring(toPurify.length() - 1, toPurify.length());
		return CONTROL_CHARACTERS.contains(lastCharacter);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
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
		ColumnName other = (ColumnName) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return columnName;
	}
}
