package de.jbdb.sql2json.step1.input;

import static org.fest.assertions.Assertions.assertThat;

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
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SqlDumpFileScannerTest {

	private static final String TEST_TABLE = "testTable";
	private static final String TEST_COLUMN = "testColumn";
	private static final String TEST_VALUE1 = "testValue1";
	private static final String TEST_VALUE2 = "testValue2";
	private static final String[] TESTINSERT = { "INSERT INTO " + TEST_TABLE + " (" + TEST_COLUMN + ") VALUES ",
			"(" + TEST_VALUE1 + ", " + TEST_VALUE2 + ");" };

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	private SqlDumpFileScanner classUnderTest;

	@Before
	public void before() {
		FileHandler fileHandler = new FileHandler();
		classUnderTest = new SqlDumpFileScanner(fileHandler);
	}

	@Test
	public void testCreation_ServiceMayNotBeNull() throws Exception {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("service for file handling is required");

		new SqlDumpFileScanner(null);
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
		assertThat(tableNameSet.toArray(new TableName[1])[0]).isEqualTo(TEST_TABLE);

		InsertStatement insert = resultMap.values().stream().findFirst().get();
		assertThat(insert).isNotNull();
		assertThat(insert.getTableName()).isEqualTo(TEST_TABLE);
		assertThat(insert.getColumnNames()).isNotNull();
		assertThat(insert.getColumnNames()).hasSize(1);
		assertThat(insert.getColumnNames().get(0)).isEqualTo(TEST_COLUMN);

		List<ValueRow> rowValues = insert.getValueRows();
		assertThat(rowValues).isNotNull();
		assertThat(rowValues).isNotEmpty();
		assertThat(rowValues).hasSize(2);
		assertThat(rowValues).contains(new ValueRow(TEST_VALUE1), new ValueRow(TEST_VALUE2));
	}
}
