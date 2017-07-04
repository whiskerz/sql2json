package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTINSERT;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTJSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Sql2JSONServiceIntegrationTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Mock
	private SqlInsertDirectoryScanner directoryScanner;

	@InjectMocks
	private Sql2JSONService classUnderTest;

	@Test
	public void simpleInsert() throws Exception {

		final File tempFile = tempFolder.newFile("tempFile.sql");
		FileUtils.writeStringToFile(tempFile, TESTINSERT, Charset.defaultCharset());

		ScanResult scanResult = new ScanResult();
		// scanResult.add
		when(directoryScanner.scanDirectories(Mockito.anyString())).thenReturn(scanResult);

		String resultJson = classUnderTest.convertInsertFilesToJson("tmpDirectory");

		assertThat(resultJson).isEqualTo(TESTJSON);
	}

	// TODO Multiple tables in directory structure

	// TODO Error Handling
	// scanResult.getResultStatus();

}
