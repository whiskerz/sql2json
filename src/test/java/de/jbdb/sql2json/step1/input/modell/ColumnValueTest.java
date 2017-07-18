package de.jbdb.sql2json.step1.input.modell;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_COLUMN;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE1;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ColumnValueTest {

	@Test
	public void testCreateValue_Whitespace_Quotes() throws Exception {
		ColumnValue value = new ColumnValue(new ColumnName(TEST_COLUMN), "	'" + TEST_VALUE1 + "  '      ");

		assertThat(value.toString()).isEqualTo("\"" + TEST_COLUMN + "\":\"" + "'" + TEST_VALUE1 + "  '" + "\"");
	}

	@Test
	public void testEquals_HashCode() throws Exception {
		ColumnValue value1 = new ColumnValue(new ColumnName(TEST_COLUMN + "1"), "	'" + TEST_VALUE1 + "  '      ");
		ColumnValue value2 = new ColumnValue(new ColumnName(TEST_COLUMN + "2"), "	'" + TEST_VALUE1 + "  '      ");

		assertThat(value1).isEqualTo(value2);
		assertThat(value1.hashCode()).isEqualTo(value2.hashCode());
	}

	@Test
	public void testToJSON_EscapeTab() throws Exception {
		String values = "a\tb";

		ColumnValue classUnderTest = new ColumnValue(new ColumnName(TEST_COLUMN), values);
		String jsonEscapedValue = classUnderTest.getJSONEscapedColumnValue();

		assertThat(jsonEscapedValue).isEqualTo("a\\tb");
	}

	@Test
	public void testToJSON_RecordSeparator() throws Exception {
		String values = "a" + 0x1e + "b";

		ColumnValue classUnderTest = new ColumnValue(new ColumnName(TEST_COLUMN), values);
		String jsonEscapedValue = classUnderTest.getJSONEscapedColumnValue();

		assertThat(jsonEscapedValue).isEqualTo("a\\u001eb");
	}

	@Test
	public void testToJSON_EscapeBackslash() throws Exception {
		String values = "\\";

		ColumnValue classUnderTest = new ColumnValue(new ColumnName(TEST_COLUMN), values);
		String jsonEscapedValue = classUnderTest.getJSONEscapedColumnValue();

		assertThat(jsonEscapedValue).isEqualTo("\\\\");
	}

	@Test
	public void testToJSON_EscapeDoubleBackslashAndQuote() throws Exception {
		String values = "\\\\\"";

		ColumnValue classUnderTest = new ColumnValue(new ColumnName(TEST_COLUMN), values);
		String jsonEscapedValue = classUnderTest.getJSONEscapedColumnValue();

		assertThat(jsonEscapedValue).isEqualTo("\\\\\\\\\\\"");
	}
}
