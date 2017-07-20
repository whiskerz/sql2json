package de.jbdb.sql2json.step1.input.modell;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_COLUMN;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_TWO_COLUMNS;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE1;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE2;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.jbdb.sql2json.Sql2JSONTestObjects;

public class RowTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void lessValuesThanColumns() throws Exception {
		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(
				org.hamcrest.CoreMatchers.containsString("number of values does not match number of columns"));

		new Row(new Columns("('TEST_COLUMN1', 'TEST_COLUMN2')"), "('TEST_VALUE1');");
	}

	@Test
	public void moreValuesThanColumns() throws Exception {
		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(
				org.hamcrest.CoreMatchers.containsString("number of values does not match number of columns"));

		new Row(new Columns("('TEST_COLUMN1', 'TEST_COLUMN2')"), "('TEST_VALUE1', 'TEST_VALUE2', 'TEST_VALUE3');");
	}

	@Test
	public void constructorWithNoValues() throws Exception {
		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(org.hamcrest.CoreMatchers.containsString("values are missing"));

		new Row(new Columns("TEST_COLUMN"), " ");
	}

	@Test
	public void oneValueInOneRow() throws Exception {
		Row row = new Row(new Columns(Sql2JSONTestObjects.TEST_COLUMN), "(" + TEST_VALUE1 + ")");

		List<ColumnValue> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(1);

		ColumnValue value = values.get(0);
		assertThat(value.toString()).endsWith(TEST_VALUE1 + "\"");
	}

	@Test
	public void oneValueInOneRow_NoBrackets() throws Exception {
		Row row = new Row(new Columns(Sql2JSONTestObjects.TEST_COLUMN), TEST_VALUE1);

		List<ColumnValue> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(1);

		ColumnValue value = values.get(0);
		assertThat(value.toString()).endsWith(TEST_VALUE1 + "\"");
	}

	@Test
	public void twoValuesInOneRow() throws Exception {
		Row row = new Row(new Columns(Sql2JSONTestObjects.TEST_TWO_COLUMNS),
				"(" + TEST_VALUE1 + "," + TEST_VALUE1 + ")");

		List<ColumnValue> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(2);

		ColumnValue value1 = values.get(0);
		assertThat(value1.toString()).endsWith(TEST_VALUE1 + "\"");

		ColumnValue value2 = values.get(1);
		assertThat(value2).isNotSameAs(value1);
		assertThat(value2.toString()).endsWith(TEST_VALUE1 + "\"");
	}

	@Test
	public void twoValuesInOneRow_OneBracketMissing_Quotes_Whitespace() throws Exception {
		Row row = new Row(new Columns(Sql2JSONTestObjects.TEST_TWO_COLUMNS),
				"'" + TEST_VALUE1 + "'     ,	'" + TEST_VALUE1 + "  '      )");

		List<ColumnValue> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(2);

		ColumnValue value1 = values.get(0);
		assertThat(value1.toString()).endsWith(TEST_VALUE1 + "\"");

		ColumnValue value2 = values.get(1);
		assertThat(value2).isNotSameAs(value1);
		assertThat(value2.toString()).endsWith(TEST_VALUE1 + "\"");
	}

	@Test
	public void gettingTwoUnquotedStrings_WithIndividualBrackets() throws Exception {
		Row row = new Row(new Columns(Sql2JSONTestObjects.TEST_TWO_COLUMNS), "(testValue1),(testValue2)");

		List<ColumnValue> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(2);

		ColumnValue value1 = values.get(0);
		assertThat(value1.toString()).endsWith("testValue1)\"");

		ColumnValue value2 = values.get(1);
		assertThat(value2).isNotSameAs(value1);
		assertThat(value2.toString()).endsWith("(testValue2\"");
	}

	@Test
	public void gettingTwoQuotedStringWithInnerComma() throws Exception {
		Row row = new Row(new Columns(Sql2JSONTestObjects.TEST_TWO_COLUMNS),
				"('" + TEST_VALUE1 + "," + TEST_VALUE1 + "','" + TEST_VALUE1 + "," + TEST_VALUE1 + "'");

		List<ColumnValue> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(2);

		ColumnValue value1 = values.get(0);
		assertThat(value1.toString()).endsWith("\"" + TEST_VALUE1 + "," + TEST_VALUE1 + "\"");

		ColumnValue value2 = values.get(1);
		assertThat(value2).isNotSameAs(value1);
		assertThat(value2.toString()).endsWith("\"" + TEST_VALUE1 + "," + TEST_VALUE1 + "\"");
	}

	@Test
	public void gettingLongAndMixedValues_WithInnerCommaAndWithout_WithQuotesAndWithout() throws Exception {
		Row row = new Row(new Columns(Sql2JSONTestObjects.TEST_FOUR_COLUMNS), "'" + TEST_VALUE1 + "," + TEST_VALUE1
				+ "'," + TEST_VALUE1 + "," + TEST_VALUE1 + ",'" + TEST_VALUE1 + "'");

		List<ColumnValue> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(4);

		ColumnValue value1 = values.get(0);
		assertThat(value1.toString()).endsWith(TEST_VALUE1 + "," + TEST_VALUE1 + "\"");

		ColumnValue value2 = values.get(1);
		assertThat(value2.toString()).endsWith(TEST_VALUE1 + "\"");

		ColumnValue value3 = values.get(2);
		assertThat(value3.toString()).endsWith(TEST_VALUE1 + "\"");

		ColumnValue value4 = values.get(3);
		assertThat(value4.toString()).endsWith(TEST_VALUE1 + "\"");
	}

	@Test
	public void missingClosingQuote() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(CoreMatchers.containsString("don't know how to interpret its value"));

		new Row(new Columns(Sql2JSONTestObjects.TEST_COLUMN), "'" + TEST_VALUE1);
	}

	@Test
	public void oneValue_withBrackets_withQuotes_withWhiteSpace() throws Exception {
		Row row = new Row(new Columns(Sql2JSONTestObjects.TEST_COLUMN),
				"       (       	'" + TEST_VALUE1 + "'	)		");

		List<ColumnValue> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(1);

		ColumnValue value1 = values.get(0);
		assertThat(value1.toString()).endsWith(TEST_VALUE1 + "\"");
	}

	@Test
	public void twoValues_withBrackes_withoutQuotes_withSemicolon() throws Exception {
		Row row = new Row(new Columns(TEST_TWO_COLUMNS), "  (" + TEST_VALUE1 + ", " + TEST_VALUE2 + ");");

		List<ColumnValue> values = row.getValues();
		assertThat(values).describedAs("row values").isNotNull();
		assertThat(values).describedAs("row values").isNotEmpty();
		assertThat(values).describedAs("row values").hasSize(2);
		assertThat(values).contains(new ColumnValue(new ColumnName(TEST_COLUMN), TEST_VALUE1),
				new ColumnValue(new ColumnName(TEST_COLUMN), TEST_VALUE2));
	}

	@Test
	public void toJSON_OneValue() throws Exception {

		Row classUnderTest = new Row(new Columns(TEST_COLUMN), "('TEST_VALUE1')");

		String jsonString = classUnderTest.toJSON();

		assertThat(jsonString).isEqualTo("{\"" + TEST_COLUMN + "\":\"TEST_VALUE1\"}");
	}

	@Test
	public void toJSON_TwoValues() throws Exception {

		Row classUnderTest = new Row(new Columns(TEST_TWO_COLUMNS), "('TEST_VALUE1', 'TEST_VALUE2')");

		String jsonString = classUnderTest.toJSON();

		assertThat(jsonString)
				.isEqualTo("{\"" + TEST_COLUMN + "\":\"TEST_VALUE1\",\"" + TEST_COLUMN + "\":\"TEST_VALUE2\"}");
	}

	@Test
	public void bug001_twoSingleQuotesMeanOneEscapedSingleQuote() throws Exception {
		Columns columns = new Columns("cb_nick, cb_time, cb_text");
		String values = "'Marc', 1089900323, 'Meine Herrn, bis man sich durch alle Seiten durchgewurschtelt hat, wo etwas neues steht, hat man schon fast wieder vergessen, was man zu beginn gelesen hat.\r\nKann man irgendwie ''informiert'' werden, ob etwas gekommen ist, was einen auch wirklich angeht??'";

		Row classUnderTest = new Row(columns, values);

		String jsonString = classUnderTest.toJSON();

		assertThat(jsonString).isEqualTo(
				"{\"cb_nick\":\"Marc\",\"cb_time\":\"1089900323\",\"cb_text\":\"Meine Herrn, bis man sich durch alle Seiten durchgewurschtelt hat, wo etwas neues steht, hat man schon fast wieder vergessen, was man zu beginn gelesen hat.\r\nKann man irgendwie 'informiert' werden, ob etwas gekommen ist, was einen auch wirklich angeht??\"}");
	}

	@Test
	public void bug002_emptyValues() throws Exception {
		Row classUnderTest = new Row(new Columns(TEST_TWO_COLUMNS), "('TEST_VALUE1', '')");

		String jsonString = classUnderTest.toJSON();

		assertThat(jsonString).isEqualTo("{\"" + TEST_COLUMN + "\":\"TEST_VALUE1\",\"" + TEST_COLUMN + "\":\"\"}");
	}

	@Test
	public void bug003_threeStartingSingleQuotes() throws Exception {
		Columns columns = new Columns("cb_nick, cb_time, cb_text");
		String values = "'Gero', 1376546676, '''Brütal Legend'', ist das eine türkische Entwicklung? ;-)'";

		Row classUnderTest = new Row(columns, values);
		String jsonString = classUnderTest.toJSON();

		assertThat(jsonString).isEqualTo(
				"{\"cb_nick\":\"Gero\",\"cb_time\":\"1376546676\",\"cb_text\":\"'Brütal Legend', ist das eine türkische Entwicklung? ;-)\"}");
	}

	@Test
	public void bug004_UnescapedSpecialCharSimple() throws Exception {
		Columns columns = new Columns("`post_text`");
		String values = "('a" + (char) 0x1e + "b')";

		Row classUnderTest = new Row(columns, values);
		String jsonString = classUnderTest.toJSON();

		assertThat(jsonString).isEqualTo("{\"post_text\":\"a\\u001eb\"}");
	}

	@Test
	public void bug004_UnescapedSpecialChar() throws Exception {
		Columns columns = new Columns(
				"`post_id`, `topic_id`, `post_datum`, `post_autor`, `post_text`, `post_ip`, `post_Edit`");
		String values = "(12127, 370, 1396472394, 170, '[topic_titel]Mana Static[/topic_titel][Quote=Street Magic errata v 1.4.1.][b]p. 173 Mana Static[/b]\r\nAdd the following sentence between the  first and second sentence: \"Background count rises at a rate of 1 per Combat Turn up to the Force of the spell.\"[/Quote]\r\n\r\nDieser Spruch hat mir einfach keine Ruhe gelassen und da musste ich mal ein bisschen recherchieren. So wird dieser Killerspruch ein bisschen moderater.', '178.7.146.203', 0)";

		Row classUnderTest = new Row(columns, values);
		String jsonString = classUnderTest.toJSON();

		assertThat(jsonString).isEqualTo(
				"{\"post_id\":\"12127\",\"topic_id\":\"370\",\"post_datum\":\"1396472394\",\"post_autor\":\"170\",\"post_text\":\"[topic_titel]Mana Static[/topic_titel][Quote=Street Magic errata v 1.4.1.][b]p. 173 Mana Static[/b]\r\nAdd the following sentence between the \\u001e first and second sentence: \\\"Background count rises at a rate of 1 per Combat Turn up to the Force of the spell.\\\"[/Quote]\r\n\r\nDieser Spruch hat mir einfach keine Ruhe gelassen und da musste ich mal ein bisschen recherchieren. So wird dieser Killerspruch ein bisschen moderater.\",\"post_ip\":\"178.7.146.203\",\"post_Edit\":\"0\"}");
	}

	@Test
	public void testToJSON_EscapeBackslash() throws Exception {
		Columns columns = new Columns(TEST_COLUMN);
		String values = "'\\'";

		Row classUnderTest = new Row(columns, values);
		String jsonString = classUnderTest.toJSON();

		assertThat(jsonString).isEqualTo("{\"" + TEST_COLUMN + "\":\"\\\\\"" + "}");
	}

	@Test
	public void testToJSON_EscapeDoubleBackslashAndQuote() throws Exception {
		Columns columns = new Columns(TEST_COLUMN);
		String values = "'\\\\\"'";

		Row classUnderTest = new Row(columns, values);
		String jsonString = classUnderTest.toJSON();

		assertThat(jsonString).isEqualTo("{\"" + TEST_COLUMN + "\":\"\\\\\\\\\\\"" + "\"}");
	}
}
