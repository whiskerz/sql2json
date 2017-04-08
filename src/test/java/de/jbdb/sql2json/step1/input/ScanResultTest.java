package de.jbdb.sql2json.step1.input;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

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
}
