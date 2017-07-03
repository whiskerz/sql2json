package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTINSERT;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class Sql2JSONServiceTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void simpleInsert() throws Exception {

		Sql2JSONService classUnderTest = new Sql2JSONService();

		final File tempFile = tempFolder.newFile("tempFile.sql");
		FileUtils.writeStringToFile(tempFile, String.join("\n", TESTINSERT), Charset.defaultCharset());

		String resultJson = classUnderTest.convertInsertFilesToJson("tmpDirectory");

		assertThat(resultJson).isNullOrEmpty();
		// assertThat(resultJson).isEqualTo(TESTJSON);
	}
}
