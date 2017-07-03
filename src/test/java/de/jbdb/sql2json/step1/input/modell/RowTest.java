package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE1;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE2;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RowTest {

	// create row with brackest, w/o brackets, with one value, with multiple values

	@Test
	public void oneValueInOneRow() throws Exception {
		Row row = new Row("(" + TEST_VALUE1 + ")");

		List<Value> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(1);

		Value value = values.get(0);
		assertThat(value.toString()).isEqualTo(TEST_VALUE1);
	}

	@Test
	public void oneValueInOneRow_NoBrackets() throws Exception {
		Row row = new Row(TEST_VALUE1);

		List<Value> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(1);

		Value value = values.get(0);
		assertThat(value.toString()).isEqualTo(TEST_VALUE1);
	}

	@Test
	public void twoValuesInOneRow() throws Exception {
		Row row = new Row("(" + TEST_VALUE1 + "," + TEST_VALUE1 + ")");

		List<Value> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(2);

		Value value1 = values.get(0);
		assertThat(value1.toString()).isEqualTo(TEST_VALUE1);

		Value value2 = values.get(1);
		assertThat(value2).isNotSameAs(value1);
		assertThat(value2.toString()).isEqualTo(TEST_VALUE1);
	}

	@Test
	public void twoValuesInOneRow_OneBracketMissing_Quotes_Whitespace() throws Exception {
		Row row = new Row("'" + TEST_VALUE1 + "'     ,	'" + TEST_VALUE1 + "  '      )");

		List<Value> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(2);

		Value value1 = values.get(0);
		assertThat(value1.toString()).isEqualTo(TEST_VALUE1);

		Value value2 = values.get(1);
		assertThat(value2).isNotSameAs(value1);
		assertThat(value2.toString()).isEqualTo(TEST_VALUE1);
	}

	@Test
	public void gettingTwoUnquotedStrings_WithIndividualBrackets() throws Exception {
		Row row = new Row("(testValue1),(testValue2)");

		List<Value> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(2);

		Value value1 = values.get(0);
		assertThat(value1.toString()).isEqualTo("testValue1)");

		Value value2 = values.get(1);
		assertThat(value2).isNotSameAs(value1);
		assertThat(value2.toString()).isEqualTo("(testValue2");
	}

	@Test
	public void gettingTwoQuotedStringWithInnerComma() throws Exception {
		Row row = new Row("('" + TEST_VALUE1 + "," + TEST_VALUE1 + "','" + TEST_VALUE1 + "," + TEST_VALUE1 + "'");

		List<Value> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(2);

		Value value1 = values.get(0);
		assertThat(value1.toString()).isEqualTo(TEST_VALUE1 + "," + TEST_VALUE1);

		Value value2 = values.get(1);
		assertThat(value2).isNotSameAs(value1);
		assertThat(value2.toString()).isEqualTo(TEST_VALUE1 + "," + TEST_VALUE1);
	}

	@Test
	public void gettingLongAndMixedValues_WithInnerCommaAndWithout_WithQuotesAndWithout() throws Exception {
		Row row = new Row("'" + TEST_VALUE1 + "," + TEST_VALUE1 + "'," + TEST_VALUE1 + "," + TEST_VALUE1 + ",'"
				+ TEST_VALUE1 + "'");

		List<Value> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(4);

		Value value1 = values.get(0);
		assertThat(value1.toString()).isEqualTo(TEST_VALUE1 + "," + TEST_VALUE1);

		Value value2 = values.get(1);
		assertThat(value2.toString()).isEqualTo(TEST_VALUE1);

		Value value3 = values.get(2);
		assertThat(value3.toString()).isEqualTo(TEST_VALUE1);

		Value value4 = values.get(3);
		assertThat(value4.toString()).isEqualTo(TEST_VALUE1);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void missingClosingQuote() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(CoreMatchers.containsString("don't know how to interpret its value"));

		new Row("'" + TEST_VALUE1);
	}

	@Test
	public void oneValue_withBrackets_withQuotes_withWhiteSpace() throws Exception {
		Row row = new Row("       (       	'" + TEST_VALUE1 + "'	)		");

		List<Value> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(1);

		Value value1 = values.get(0);
		assertThat(value1.toString()).isEqualTo(TEST_VALUE1);
	}

	@Test
	public void twoValues_withBrackes_withoutQuotes_withSemicolon() throws Exception {
		Row row = new Row("  (" + TEST_VALUE1 + ", " + TEST_VALUE2 + ");");

		List<Value> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(2);
		assertThat(values).contains(new Value(TEST_VALUE1), new Value(TEST_VALUE2));
	}
}
