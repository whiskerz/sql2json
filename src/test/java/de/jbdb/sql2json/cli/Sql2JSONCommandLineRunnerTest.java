package de.jbdb.sql2json.cli;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mockito;

import de.jbdb.sql2json.step1.input.FileHandlerService;
import de.jbdb.sql2json.step1.input.Sql2JSONService;

public class Sql2JSONCommandLineRunnerTest {

	@Test
	public void missingParams_printHelpToTerminal() throws Exception {

		SystemService outServiceMock = mock(SystemService.class);
		Sql2JSONCommandLineRunner classUnderTest = new Sql2JSONCommandLineRunner(mock(Sql2JSONService.class),
				outServiceMock, mock(FileHandlerService.class));

		classUnderTest.run(new String[] {});

		verify(outServiceMock).println(Mockito.contains("help"));
		verify(outServiceMock).exit(0);
	}
}
