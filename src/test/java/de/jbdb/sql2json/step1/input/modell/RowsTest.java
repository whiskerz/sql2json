package de.jbdb.sql2json.step1.input.modell;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_COLUMN;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE1;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.jbdb.sql2json.Sql2JSONTestObjects;

public class RowsTest {

	// TODO
	// empty string = error
	// null = error

	@Test
	public void twoValuesInTwoRowsEach() throws Exception {
		Rows rows = new Rows(new Columns(Sql2JSONTestObjects.TEST_COLUMN),
				"(" + TEST_VALUE1 + "," + TEST_VALUE1 + ")," + "(" + TEST_VALUE1 + "," + TEST_VALUE1 + ")");

		assertRows(rows, 2, 2);
	}

	@Test
	public void twoValuesInTwoRowsEachWithWhitespace() throws Exception {
		Rows rows = new Rows(new Columns(Sql2JSONTestObjects.TEST_COLUMN), "   (   " + TEST_VALUE1 + " ,  "
				+ TEST_VALUE1 + "   )  ,    " + " (  " + TEST_VALUE1 + "   , " + TEST_VALUE1 + "   ) ");

		assertRows(rows, 2, 2);
	}

	@Test
	public void twoValuesWithTrailingBracketAndSemicolon() throws Exception {
		Rows rows = new Rows(new Columns(TEST_COLUMN), "  (testValue1, testValue1);");

		assertRows(rows, 1, 2);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void lessValuesThanColumns() throws Exception {
		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(org.hamcrest.CoreMatchers.containsString("fewer values than columns"));

		new Rows(new Columns("('TEST_COLUMN1', 'TEST_COLUMN2')"), "('TEST_VALUE1');");
	}

	@Test
	public void moreValuesThanColumns() throws Exception {
		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(org.hamcrest.CoreMatchers.containsString("more values than columns"));

		new Rows(new Columns("('TEST_COLUMN1', 'TEST_COLUMN2')"), "('TEST_VALUE1', 'TEST_VALUE2', 'TEST_VALUE3');");
	}

	@Test
	public void addAll() throws Exception {

		Rows rows1 = new Rows(new Columns(TEST_COLUMN), TEST_VALUE1);
		Rows rows2 = new Rows(new Columns(TEST_COLUMN), TEST_VALUE1);

		rows1.addAll(rows2);

		assertRows(rows1, 2, 1);
		assertRows(rows2, 1, 1);
	}

	private void assertRows(Rows rows, int numberOfRows, int numberOfValues) {
		List<Row> rowAsList = rows.asList();
		assertThat(rowAsList).isNotNull();
		assertThat(rowAsList).isNotEmpty();
		assertThat(rowAsList).hasSize(numberOfRows);
		assertThat(rowAsList.get(0)).isEqualTo(createRow(numberOfValues));
	}

	private Row createRow(int numberOfValues) {
		StringBuffer valueString = new StringBuffer();
		valueString.append("(");
		for (int i = 0; i < numberOfValues; i++) {
			valueString.append(TEST_VALUE1);
			valueString.append(",");
		}
		valueString.delete(valueString.length() - 1, valueString.length());

		Row row = new Row(new Columns(TEST_COLUMN), valueString.toString());
		assertThat(row.getValues()).hasSize(numberOfValues);

		return row;
	}
}
