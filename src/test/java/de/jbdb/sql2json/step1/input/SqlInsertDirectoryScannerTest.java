package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTINSERT;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_COLUMN;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_TABLE;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE1;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE2;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.jbdb.sql2json.step1.input.modell.ColumnName;
import de.jbdb.sql2json.step1.input.modell.ColumnValue;
import de.jbdb.sql2json.step1.input.modell.InsertStatement;
import de.jbdb.sql2json.step1.input.modell.Row;
import de.jbdb.sql2json.step1.input.modell.TableName;

@RunWith(MockitoJUnitRunner.class)
public class SqlInsertDirectoryScannerTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	private SqlInsertDirectoryScanner classUnderTest;

	@Before
	public void before() {
		FileHandler fileHandler = new FileHandler();
		classUnderTest = new SqlInsertDirectoryScanner(fileHandler);
	}

	@Test
	public void testCreation_ServiceMayNotBeNull() throws Exception {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("service for file handling is required");

		new SqlInsertDirectoryScanner(null);
	}

	@Test
	public void testScanDirectories_NullArgument() throws Exception {

		expectedException.expect(IllegalArgumentException.class);

		String[] testNull = null;

		classUnderTest.scanDirectories(testNull);
	}

	@Test
	public void testScanDirectories_EmptyArgument() throws Exception {

		expectedException.expect(IllegalArgumentException.class);

		classUnderTest.scanDirectories(new String[] {});
	}

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void testScanDirectories_OneNullArgument() throws Exception {
		final File tempFile = tempFolder.newFile("tempFile.sql");
		FileUtils.writeStringToFile(tempFile, String.join("\n", TESTINSERT), Charset.defaultCharset());

		ScanResult directoryScan = classUnderTest
				.scanDirectories(new String[] { tempFolder.getRoot().getAbsolutePath(), null });

		assertThat(directoryScan.getResultStatus()).isEqualTo(ScanResultStatus.PARTIAL);
		assertThat(directoryScan.getErrorMessages()).containsIgnoringCase("path was null or empty");
	}

	@Test
	public void testScanDirectories_HappyPathOneFile() throws Exception {
		final File tempFile = tempFolder.newFile("tempFile.sql");
		FileUtils.writeStringToFile(tempFile, String.join("\n", TESTINSERT), Charset.defaultCharset());

		ScanResult scanResult = classUnderTest.scanDirectories(tempFolder.getRoot().getAbsolutePath());

		Map<TableName, InsertStatement> resultMap = scanResult.getAllResults();

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
		assertThat(rows).hasSize(1);

		List<ColumnValue> values = rows.get(0).getValues();
		assertThat(values).describedAs("Values").isNotNull();
		assertThat(values).describedAs("Values").isNotEmpty();
		assertThat(values).hasSize(2);
		assertThat(values).contains(new ColumnValue(new ColumnName(TEST_COLUMN), TEST_VALUE1),
				new ColumnValue(new ColumnName(TEST_COLUMN), TEST_VALUE2));
	}

	@Test
	public void testScanDirectories_TwoIdenticalFiles_ExpectOneInsertButDuplicateValues() throws Exception {
		final File tempFile1 = tempFolder.newFile("tempFile1.sql");
		FileUtils.writeStringToFile(tempFile1, String.join("\n", TESTINSERT), Charset.defaultCharset());
		final File tempFile2 = tempFolder.newFile("tempFile2.sql");
		FileUtils.writeStringToFile(tempFile2, String.join("\n", TESTINSERT), Charset.defaultCharset());

		ScanResult scanResult = classUnderTest.scanDirectories(tempFolder.getRoot().getAbsolutePath());

		Map<TableName, InsertStatement> resultMap = scanResult.getAllResults();

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

		List<ColumnValue> values2 = rows.get(1).getValues();
		assertThat(values2).describedAs("Values").isNotNull();
		assertThat(values2).describedAs("Values").isNotEmpty();
		assertThat(values2).hasSize(2);
		assertThat(values2).contains(new ColumnValue(new ColumnName(TEST_COLUMN), TEST_VALUE1),
				new ColumnValue(new ColumnName(TEST_COLUMN), TEST_VALUE2));
	}
}
