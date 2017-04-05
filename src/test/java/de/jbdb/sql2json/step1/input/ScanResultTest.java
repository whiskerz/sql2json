package de.jbdb.sql2json.step1.input;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class ScanResultTest {

	@Test
	public void newScanResultHasStatusFull() {
		ScanResult classUnderTest = new ScanResult();
		
		assertThat(classUnderTest.getResultStatus()).isEqualTo(ScanResultStatus.FULL);
	}

}
