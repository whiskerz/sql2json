package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTINSERT;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_COLUMN;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_TABLE;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE1;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE2;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.jbdb.sql2json.step1.input.modell.ColumnName;
import de.jbdb.sql2json.step1.input.modell.ColumnValue;
import de.jbdb.sql2json.step1.input.modell.InsertStatement;
import de.jbdb.sql2json.step1.input.modell.Row;
import de.jbdb.sql2json.step1.input.modell.TableName;

public class ScanResultTest {

	private ScanResult classUnderTest;

	@Before
	public void before() {
		classUnderTest = new ScanResult();
	}

	@Test
	public void newScanResultHasEmptyErrorMessages() throws Exception {
		assertThat(classUnderTest.getErrorMessages()).isNotNull();
		assertThat(classUnderTest.getErrorMessages()).isEmpty();
	}

	@Test
	public void havingNoResults_WillReturnStatusNone() throws Exception {
		assertThat(classUnderTest.getResultStatus()).isEqualTo(ScanResultStatus.FAIL);
	}

	@Test
	public void havingResultsAndNoErrors_WillReturnStatusFull() {
		classUnderTest.add(new InsertStatement("INSERT INTO TEST('TEST') VALUES 'TEST'"));

		assertThat(classUnderTest.getResultStatus()).isEqualTo(ScanResultStatus.FULL);
	}

	@Test
	public void havingResultsAndErrors_WillReturnPartial() throws Exception {
		classUnderTest.add(new InsertStatement("INSERT INTO TEST('TEST') VALUES 'TEST'"));
		classUnderTest.addError("Generic Error");

		assertThat(classUnderTest.getResultStatus()).isEqualTo(ScanResultStatus.PARTIAL);
	}

	@Test
	public void addingSameTableWillMerge() throws Exception {
		InsertStatement insert = new InsertStatement(String.join(" ", TESTINSERT));

		classUnderTest.add(insert);
		classUnderTest.add(insert);

		Map<TableName, InsertStatement> resultMap = classUnderTest.getAllResults();

		assertThat(resultMap).isNotNull();
		assertThat(resultMap).isNotEmpty();
		assertThat(resultMap).hasSize(1);

		Set<TableName> tableNameSet = resultMap.keySet();
		assertThat(tableNameSet).isNotNull();
		assertThat(tableNameSet).isNotEmpty();
		assertThat(tableNameSet).hasSize(1);
		assertThat(tableNameSet.stream().findFirst().get()).isEqualTo(new TableName(TEST_TABLE));

		InsertStatement insertStatement = resultMap.values().stream().findFirst().get();
		assertThat(insertStatement).isNotNull();
		assertThat(insertStatement.getTableName()).isEqualTo(new TableName(TEST_TABLE));
		assertThat(insertStatement.getColumnNames()).isNotNull();
		assertThat(insertStatement.getColumnNames()).hasSize(2);
		assertThat(insertStatement.getColumnNames().get(0)).isEqualTo(new ColumnName(TEST_COLUMN));
		assertThat(insertStatement.getColumnNames().get(1)).isEqualTo(new ColumnName(TEST_COLUMN));

		List<Row> rows = insertStatement.getValueRows();
		assertThat(rows).describedAs("Rows").isNotNull();
		assertThat(rows).describedAs("Rows").isNotEmpty();
		assertThat(rows).hasSize(2);

		List<ColumnValue> values1 = rows.get(0).getValues();
		assertThat(values1).describedAs("Values").isNotNull();
		assertThat(values1).describedAs("Values").isNotEmpty();
		assertThat(values1).hasSize(2);
		assertThat(values1).contains(new ColumnValue(new ColumnName(TEST_COLUMN), TEST_VALUE1),
				new ColumnValue(new ColumnName(TEST_COLUMN), TEST_VALUE2));

		List<ColumnValue> values2 = rows.get(0).getValues();
		assertThat(values2).describedAs("Values").isNotNull();
		assertThat(values2).describedAs("Values").isNotEmpty();
		assertThat(values2).hasSize(2);
		assertThat(values2).contains(new ColumnValue(new ColumnName(TEST_COLUMN), TEST_VALUE1),
				new ColumnValue(new ColumnName(TEST_COLUMN), TEST_VALUE2));
	}

}
