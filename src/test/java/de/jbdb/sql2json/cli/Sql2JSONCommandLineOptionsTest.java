package de.jbdb.sql2json.cli;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.jbdb.sql2json.cli.Sql2JSONCommandLineOptions;
import joptsimple.OptionException;

public class Sql2JSONCommandLineOptionsTest {

	@Test(expected = NullPointerException.class)
	public void testMain_NullArguments() throws Exception {

		Sql2JSONCommandLineOptions.parseFrom(null);
	}

	@Test(expected = OptionException.class)
	public void testMain_EmptyArguments() throws Exception {

		Sql2JSONCommandLineOptions.parseFrom(new String[] {});
	}

	@Test
	public void testMain_BothOptionsSet() throws Exception {
		Sql2JSONCommandLineOptions options = Sql2JSONCommandLineOptions
				.parseFrom(new String[] { "-in", "inputDir", "-out", "outputDir" });

		assertThat(options.getInputDirectory()).isEqualTo("inputDir");
		assertThat(options.getOutputDirectory()).isEqualTo("outputDir");
	}
}
