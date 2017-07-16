package de.jbdb.sql2json.cli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Sql2JSONCommandLineOptionsTest {

	@Test(expected = NullPointerException.class)
	public void testMain_NullArguments() throws Exception {

		Sql2JSONCommandLineOptions.parseFrom(null);
	}

	@Test
	public void testMain_BothOptionsSet() throws Exception {
		Sql2JSONCommandLineOptions options = Sql2JSONCommandLineOptions
				.parseFrom(new String[] { "-in", "inputDir", "-out", "outputDir" });

		assertThat(options.getInputDirectory()).isEqualTo("inputDir");
		assertThat(options.getOutputDirectory()).isEqualTo("outputDir");
	}

	@Test
	public void requestingHelpMeansNoExceptionHelpflagTrue() throws Exception {

		Sql2JSONCommandLineOptions options = Sql2JSONCommandLineOptions.parseFrom(new String[] { "-help" });

		assertThat(options.isHelpRequested()).isEqualTo(true);
		assertThat(options.getInputDirectory()).isNull();
		assertThat(options.getOutputDirectory()).isNull();
	}

	@Test
	public void noParametersMeansNoExceptionHelpflagTrue() throws Exception {

		Sql2JSONCommandLineOptions options = Sql2JSONCommandLineOptions.parseFrom(new String[] {});

		assertThat(options.isHelpRequested()).isEqualTo(true);
		assertThat(options.getInputDirectory()).isNull();
		assertThat(options.getOutputDirectory()).isNull();
	}
}
