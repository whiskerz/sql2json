package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE1;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class RowsTest {

	@Test
	public void oneValueInOneRow() throws Exception {
		Rows rows = new Rows(TEST_VALUE1);

		assertRows(rows, 1);
	}

	@Test
	public void twoValuesInOneRow() throws Exception {
		// TODO implement but move to Row ValueObjectTest
	}

	@Test
	public void twoValuesInTwoRowsEach() throws Exception {
		// TODO implement
	}

	@Test
	public void twoValuesInTwoRowsEachWithWhitespace() throws Exception {
		// TODO implement
	}

	private void assertRows(Rows rows, int numberOfRows) {
		List<Row> rowAsList = rows.asList();
		assertThat(rowAsList).isNotNull();
		assertThat(rowAsList).isNotEmpty();
		assertThat(rowAsList).hasSize(numberOfRows);
		assertThat(rowAsList.get(0)).isEqualTo(new Row(TEST_VALUE1));
	}
}
