package de.jbdb.sql2json.step1.input;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import de.jbdb.sql2json.step1.input.FileHandler;
import de.jbdb.sql2json.step1.input.SqlDumpFileScanner;

@RunWith(MockitoJUnitRunner.class)
public class SqlDumpFileScannerTest {

	private static final String TESTPATH = "/src/test/path";
	private static final String TEST_TABLE = "testTable";
	private static final String TEST_COLUMN = "testColumn";
	private static final String TEST_VALUE1 = "testValue1";
	private static final String TEST_VALUE2 = "testValue2";
	private static final String TESTINSERT = "INSERT INTO " + TEST_TABLE + " (" + TEST_COLUMN + ") VALUES "
			+ TEST_VALUE1 + ", " + TEST_VALUE2;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	private FileHandler fileHandlerMock;

	private SqlDumpFileScanner classUnderTest;

	@Test
	public void testCreation_ServiceMayNotBeNull() throws Exception {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("service for file handling is required");

		new SqlDumpFileScanner(null);
	}

	@Before
	public void before() {
		classUnderTest = new SqlDumpFileScanner(fileHandlerMock);
	}

	@Test
	public void testScanDirectory_NullArgument() throws Exception {

		expectedException.expect(IllegalArgumentException.class);

		classUnderTest.scanDirectory((String) null);
	}

	@Test
	public void testScanDirectory_EmptyArgument() throws Exception {

		expectedException.expect(IllegalArgumentException.class);

		classUnderTest.scanDirectory(new String[] {});
	}

	@Test
	public void testScanDirectory_HappyPathOneFile() throws Exception {

		Path irrelevantPath = mock(Path.class);
		when(fileHandlerMock.get(TESTPATH)).thenReturn(irrelevantPath);

		Stream<String> fileAsStream = Stream.empty();
		when(fileHandlerMock.lines(Mockito.any(Path.class))).thenReturn(fileAsStream);

		Collection<Insert> insertStatements = classUnderTest.scanDirectory(TESTPATH);

		assertThat(insertStatements).isNotNull();
		assertThat(insertStatements).isNotEmpty();
		assertThat(insertStatements).hasSize(1);

		Insert insert = insertStatements.stream().findFirst().get();
		assertThat(insert).isNotNull();
		assertThat(insert.getTableName()).isEqualTo(TEST_TABLE);
		assertThat(insert.getColumnNames()).isNotNull();
		assertThat(insert.getColumnNames()).hasSize(1);
		assertThat(insert.getColumnNames().get(0)).isEqualTo(TEST_COLUMN);
	}
}
