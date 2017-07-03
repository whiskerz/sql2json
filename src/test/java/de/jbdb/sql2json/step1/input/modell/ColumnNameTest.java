package de.jbdb.sql2json.step1.input.modell;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_COLUMN;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.jbdb.sql2json.step1.input.modell.ColumnName;

public class ColumnNameTest {

	@Test
	public void constructionWithSingleQuotes() throws Exception {
		ColumnName classUnderTest = new ColumnName("'" + TEST_COLUMN + "'");

		assertThat(classUnderTest.get()).isEqualTo(TEST_COLUMN);
	}

	@Test
	public void constructionWithDoubleQuotes() throws Exception {
		ColumnName classUnderTest = new ColumnName("\"" + TEST_COLUMN + "\"");

		assertThat(classUnderTest.get()).isEqualTo(TEST_COLUMN);
	}

	@Test
	public void constructionWithBackticks() throws Exception {
		ColumnName classUnderTest = new ColumnName("`" + TEST_COLUMN + "`");

		assertThat(classUnderTest.get()).isEqualTo(TEST_COLUMN);
	}

	@Test
	public void equalsAndHashcodeUseGivenName() throws Exception {
		ColumnName columnNameOne = new ColumnName("One");
		ColumnName columnNameTwo = new ColumnName("One");

		assertThat(columnNameOne.equals(columnNameTwo)).isTrue();
		assertThat(columnNameOne.hashCode()).isEqualTo(columnNameTwo.hashCode());
	}

	@Test
	public void toStringWillGiveTheName() throws Exception {

		assertThat(new ColumnName(TEST_COLUMN).toString()).isEqualTo(TEST_COLUMN);
	}
}
