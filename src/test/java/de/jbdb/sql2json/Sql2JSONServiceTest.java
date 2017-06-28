package de.jbdb.sql2json;

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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.jbdb.sql2json.step1.input.ColumnName;
import de.jbdb.sql2json.step1.input.InsertStatement;
import de.jbdb.sql2json.step1.input.Row;
import de.jbdb.sql2json.step1.input.ScanResult;
import de.jbdb.sql2json.step1.input.TableName;
import de.jbdb.sql2json.step1.input.Value;

public class Sql2JSONServiceTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void simpleInsertHappyPath() throws Exception {

		Sql2JSONService classUnderTest = new Sql2JSONService();

		final File tempFile = tempFolder.newFile("tempFile.sql");
		FileUtils.writeStringToFile(tempFile, String.join("\n", TESTINSERT), Charset.defaultCharset());

		String resultJson = classUnderTest.convert("tmpDirectory");

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
		assertThat(insertStatement.getColumnNames()).hasSize(1);
		assertThat(insertStatement.getColumnNames().get(0)).isEqualTo(new ColumnName(TEST_COLUMN));

		List<Row> rows = insertStatement.getValueRows();
		assertThat(rows).describedAs("Rows").isNotNull();
		assertThat(rows).describedAs("Rows").isNotEmpty();
		assertThat(rows).hasSize(1);

		List<Value> values = rows.get(0).getValues();
		assertThat(values).describedAs("Values").isNotNull();
		assertThat(values).describedAs("Values").isNotEmpty();
		assertThat(values).hasSize(2);
		assertThat(values).contains(new Value(TEST_VALUE1), new Value(TEST_VALUE2));
	}
}
