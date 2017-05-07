package de.jbdb.sql2json.step1.input;

import java.util.ArrayList;
import java.util.List;

public class Row {

	private List<Value> valueList;

	public Row(String values) {
		String parameter = values.trim();

		if (parameter.startsWith("(")) {
			parameter = parameter.substring(1).trim();
		}

		if (parameter.endsWith(")")) {
			parameter = parameter.substring(0, parameter.length() - 1).trim();
		}

		valueList = new ArrayList<>();

		// TODO yuk
		while (!parameter.isEmpty()) {
			if (parameter.startsWith("'")) {
				int closingQuoteIndex = parameter.indexOf("'", 1);
				if (closingQuoteIndex < 0) {
					throw new IllegalArgumentException(
							"Could not parse input. It seems to be malformed and is missing a closing quote, I don't know how to interpret its values without that. Here is your input: "
									+ values);
				}
				valueList.add(new Value(parameter.substring(1, closingQuoteIndex)));
				parameter = parameter.substring(closingQuoteIndex + 1).trim();
				continue;
			}
			if (parameter.startsWith(",")) {
				parameter = parameter.substring(1).trim();
				continue;
			}

			int nextComma = parameter.indexOf(",");
			nextComma = nextComma >= 0 ? nextComma : parameter.length();
			valueList.add(new Value(parameter.substring(0, nextComma).trim()));
			parameter = parameter.substring(nextComma);
		}
	}

	public List<Value> getValues() {
		return valueList;
	}

	@Override
	public String toString() {
		return "Row [valueList=" + valueList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valueList == null) ? 0 : valueList.hashCode());
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
		if (valueList == null) {
			if (other.valueList != null)
				return false;
		} else if (!valueList.equals(other.valueList))
			return false;
		return true;
	}

}
