package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTINSERT;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTJSON;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.jbdb.sql2json.cli.Sql2JSONCommandLine;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Sql2JSONCommandLine.class)
@ActiveProfiles(Sql2JSONCommandLine.TEST_PROFILE)
public class Sql2JSONServiceIntegrationTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Autowired
	private Sql2JSONService classUnderTest;

	@Test
	public void simpleSpringSetupTest() throws Exception {

		assertThat(classUnderTest).isNotNull();
	}

	@Test
	public void simpleInsert_HappyPath() throws Exception {

		final File tempFile1 = tempFolder.newFile("tempFile1.sql");
		FileUtils.writeStringToFile(tempFile1, TESTINSERT, Charset.defaultCharset());

		String resultJson = classUnderTest.convertInsertFilesToJson(tempFolder.getRoot().getAbsolutePath());

		assertThat(resultJson).isEqualTo("[\n" + TESTJSON + "\n]");
	}
}
