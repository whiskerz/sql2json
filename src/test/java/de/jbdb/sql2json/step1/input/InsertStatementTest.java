package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTINSERT;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_COLUMN;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_TABLE;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE1;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE2;
import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InsertStatementTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void constructorWithNullParameter_ThrowsIllegalArgumentException() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(org.hamcrest.CoreMatchers.containsString("parameter was null"));

		new InsertStatement(null);
	}

	@Test
	public void constructorWithEmptyParameter_ThrowsIllegalArgumentException() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(org.hamcrest.CoreMatchers.containsString("parameter was empty"));

		new InsertStatement("");
	}

	@Test
	public void constructorWithSimpleInsert() throws Exception {

		InsertStatement insertStatement = new InsertStatement(String.join(" ", TESTINSERT));

		assertThat(insertStatement.getTableName()).isEqualTo(TEST_TABLE);
		assertThat(insertStatement.getColumnNames()).isNotNull();
		assertThat(insertStatement.getColumnNames()).hasSize(1);
		assertThat(insertStatement.getColumnNames().get(0)).isEqualTo(TEST_COLUMN);

		List<ValueRow> rowValues = insertStatement.getValueRows();
		assertThat(rowValues).isNotNull();
		assertThat(rowValues).isNotEmpty();
		assertThat(rowValues).hasSize(2);
		assertThat(rowValues).contains(new ValueRow(TEST_VALUE1), new ValueRow(TEST_VALUE2));
	}
}
