package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTINSERT;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTJSON;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.StringReader;

import javax.json.Json;
import javax.json.stream.JsonParser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.jbdb.sql2json.step1.input.modell.InsertStatement;

@RunWith(MockitoJUnitRunner.class)
public class Sql2JSONServiceTest {

	@Mock
	private SqlInsertDirectoryScanner directoryScanner;

	@Mock
	private FileHandlerService fileHandler;

	@InjectMocks
	private Sql2JSONService classUnderTest;

	@Test
	public void simpleInsert() throws Exception {

		ScanResult scanResult = new ScanResult();
		scanResult.add(new InsertStatement(TESTINSERT));
		when(directoryScanner.scanDirectories(Mockito.anyString())).thenReturn(scanResult);

		String resultJson = classUnderTest.convertInsertFilesToJson("tmpDirectory");

		assertThat(resultJson).isEqualTo("[" + TESTJSON + "]");
	}

	@Test
	public void twoTableInsert() throws Exception {

		ScanResult scanResult = new ScanResult();
		scanResult.add(new InsertStatement(TESTINSERT));
		scanResult.add(new InsertStatement(TESTINSERT.replace(TEST_TABLE, "TEST_TABLE2")));
		when(directoryScanner.scanDirectories(Mockito.anyString())).thenReturn(scanResult);

		String resultJson = classUnderTest.convertInsertFilesToJson("tmpDirectory");

		JsonParser jsonParser = Json.createParser(new StringReader(resultJson));
		while (jsonParser.hasNext()) {
			jsonParser.next();
		}
		assertThat(resultJson).contains(TESTJSON);
		assertThat(resultJson).contains(TESTJSON.replace(TEST_TABLE, "TEST_TABLE2"));
	}

	@Test
	public void failedInsert() throws Exception {

		ScanResult scanResult = new ScanResult();
		scanResult.addError("Some Error");
		when(directoryScanner.scanDirectories(Mockito.anyString())).thenReturn(scanResult);

		String resultJson = classUnderTest.convertInsertFilesToJson("tmpDirectory");

		assertThat(resultJson).isEmpty();
		verify(fileHandler).write("tmpDirectory" + "/error.log", "[Some Error]");
	}

	@Test
	public void partialInsert() throws Exception {

		ScanResult scanResult = new ScanResult();
		scanResult.add(new InsertStatement(TESTINSERT));
		scanResult.addError("Some Error");
		when(directoryScanner.scanDirectories(Mockito.anyString())).thenReturn(scanResult);

		String resultJson = classUnderTest.convertInsertFilesToJson("tmpDirectory");

		JsonParser jsonParser = Json.createParser(new StringReader(resultJson));
		while (jsonParser.hasNext()) {
			jsonParser.next();
		}
		assertThat(resultJson).contains(TESTJSON);

		verify(fileHandler).write("tmpDirectory" + "/error.log", "[Some Error]");
	}

}
