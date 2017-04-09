package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_TABLE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TableNameTest {

	@Test
	public void constructingWithBackticks_BackticksAreRemoved() throws Exception {

		TableName tableName = new TableName("`" + TEST_TABLE + "`");

		assertThat(tableName.get()).isEqualTo(TEST_TABLE);
	}

	@Test
	public void constructingWithWhitespace_WhitespaceIsRemoved() throws Exception {

		TableName tableName = new TableName(" " + TEST_TABLE + " ");

		assertThat(tableName.get()).isEqualTo(TEST_TABLE);
	}

	@Test
	public void equalsAndSameUseGivenTableName() throws Exception {
		TableName tableNameOne = new TableName("One");
		TableName tableNameTwo = new TableName("One");

		assertThat(tableNameOne.equals(tableNameTwo)).isTrue();
		assertThat(tableNameOne.hashCode()).isEqualTo(tableNameTwo.hashCode());
	}

	@Test
	public void toStringWillGiveTableName() throws Exception {

		assertThat(new TableName(TEST_TABLE).toString()).isEqualTo(TEST_TABLE);
	}
}
