package de.jbdb.sql2json.step1.input.modell;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Row {

	private enum State {
		OUTSIDE, INSIDE
	}

	private List<ColumnValue> valueList;
	private final Columns columns;
	private final String values;

	public Row(Columns columns, String values) {
		this.columns = columns;
		this.values = values;

		String parameter = values.trim();

		assertThat(parameter).as("The value string is empty, all values are missing.").isNotEmpty();

		if (parameter.startsWith("(")) {
			parameter = parameter.substring(1).trim();
		}

		if (parameter.endsWith(";")) {
			parameter = parameter.substring(0, parameter.length() - 1).trim();
		}

		if (parameter.endsWith(")")) {
			parameter = parameter.substring(0, parameter.length() - 1).trim();
		}

		// Using stack because it is easier to understand later on instead of working with indexes
		Stack<ColumnName> columnsStack = new Stack<>();
		List<ColumnName> names = columns.getNames();
		Collections.reverse(names);
		for (ColumnName columnName : names) {
			columnsStack.push(columnName);
		}

		valueList = new ArrayList<>();

		State state = State.OUTSIDE;
		StringBuilder collector = null;
		// The first solution did not allow for good handling of the quoted single quote (''). Using a simple state
		// machine instead.
		while (!parameter.isEmpty()) {
			switch (state) {
			case OUTSIDE:
				if (parameter.startsWith("'''")) {
					state = State.INSIDE;
					collector = new StringBuilder();
					parameter = parameter.substring(1);
					continue;
				}
				if (parameter.startsWith("''")) {
					valueList.add(new ColumnValue(exceptionSafePop(columnsStack), ""));
					parameter = parameter.substring(2);
					continue;
				}
				if (parameter.startsWith("'")) {
					state = State.INSIDE;
					collector = new StringBuilder();
					parameter = parameter.substring(1);
					continue;
				}
				if (parameter.startsWith(",")) {
					parameter = parameter.substring(1).trim();
					continue;
				}
				int nextComma = parameter.indexOf(",");
				nextComma = nextComma >= 0 ? nextComma : parameter.length();
				String valueString = parameter.substring(0, nextComma).trim();
				valueList.add(new ColumnValue(exceptionSafePop(columnsStack), valueString));
				parameter = parameter.substring(nextComma);
				break;
			case INSIDE:
				if (parameter.startsWith("''")) {
					collector.append("'");
					parameter = parameter.substring(2);
					continue;
				}
				if (parameter.startsWith("'")) {
					state = State.OUTSIDE;
					valueList.add(new ColumnValue(exceptionSafePop(columnsStack), collector.toString()));
					collector = null;
					parameter = parameter.substring(1).trim();
					continue;
				}
				int closingQuoteIndex = parameter.indexOf("'", 1);
				if (closingQuoteIndex < 0) {
					throw new IllegalArgumentException(
							"Could not parse input. It seems to be malformed and is missing a closing quote, I don't know how to interpret its values without that. Here is your input: "
									+ values);
				}
				collector.append(parameter.charAt(0));
				parameter = parameter.substring(1);
				break;
			}
		}

		assertThat(valueList)
				.as("It appears that the number of values does not match number of columns.\nValues: %d\nColumns: %d",
						valueList.size(), columns.getNames().size())
				.hasSize(columns.getNames().size());
	}

	private ColumnName exceptionSafePop(Stack<ColumnName> columnsStack) {
		try {
			return columnsStack.pop();
		} catch (EmptyStackException emptyStackException) {
			String message = String.format(
					"It appears that the number of values does not match number of columns. Stack was empty.\nColumns:%s\nValues:%s",
					columns.toString(), values);
			throw new AssertionError(message, emptyStackException);
		}
	}

	public List<ColumnValue> getValues() {
		return valueList;
	}

	public String toJSON() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		for (ColumnValue value : valueList) {
			builder.append("\"");
			builder.append(value.getColumnName());
			builder.append("\"");
			builder.append(":");
			builder.append("\"");
			builder.append(value.getJSONEscapedColumnValue());
			builder.append("\"");
			builder.append(",");
		}
		builder.replace(builder.length() - 1, builder.length(), "}");
		return builder.toString();
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
