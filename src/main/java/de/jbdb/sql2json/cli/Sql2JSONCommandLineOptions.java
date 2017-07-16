package de.jbdb.sql2json.cli;

import java.io.IOException;
import java.io.StringWriter;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Sql2JSONCommandLineOptions {

	private static final String HELP_ARGUMENT = "help";
	private static final String OUT_ARGUMENT = "out";
	private static final String IN_ARGUMENT = "in";

	private String inputDirectory;
	private String outputDirectory;
	private Boolean helpRequested;

	private OptionParser parser;

	public static Sql2JSONCommandLineOptions parseFrom(String[] args) {
		Sql2JSONCommandLineOptions options = new Sql2JSONCommandLineOptions();

		try {
			OptionSet optionSet = options.parser.parse(args);
			options.inputDirectory = (String) optionSet.valueOf(IN_ARGUMENT);
			options.outputDirectory = (String) optionSet.valueOf(OUT_ARGUMENT);
			options.helpRequested = optionSet.has(HELP_ARGUMENT);
		} catch (OptionException optionException) {
			options.helpRequested = true;
		}

		return options;
	}

	private Sql2JSONCommandLineOptions() {

		parser = new OptionParser();

		parser.accepts(IN_ARGUMENT).withRequiredArg().ofType(String.class)
				.describedAs("Directory to read sql dump files from").required();
		parser.accepts(OUT_ARGUMENT).withRequiredArg().ofType(String.class)
				.describedAs("Directory to write json files to").required();
		parser.accepts(HELP_ARGUMENT).forHelp();
	}

	public String getInputDirectory() {
		return inputDirectory;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public Boolean isHelpRequested() {
		return helpRequested;
	}

	public String getHelpScreen() {
		StringWriter stringWriter = new StringWriter();
		try {
			parser.printHelpOn(stringWriter);
		} catch (IOException e) {
			e.printStackTrace();
			return "Error writing help screen: " + e.getMessage();
		}
		return stringWriter.toString();
	}

}
