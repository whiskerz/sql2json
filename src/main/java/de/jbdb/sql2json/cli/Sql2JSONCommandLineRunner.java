package de.jbdb.sql2json.cli;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import de.jbdb.sql2json.step1.input.FileHandlerService;
import de.jbdb.sql2json.step1.input.Sql2JSONService;

@Controller
public class Sql2JSONCommandLineRunner implements CommandLineRunner {

	public static final String RESULT_FILE_NAME = "results.json";

	private Sql2JSONService sql2JSONService;
	private SystemService systemService;
	private FileHandlerService fileHandlerService;

	@Autowired(required = true)
	public Sql2JSONCommandLineRunner(Sql2JSONService sql2JSONService, SystemService systemService,
			FileHandlerService fileHandlerService) {
		this.sql2JSONService = sql2JSONService;
		this.systemService = systemService;
		this.fileHandlerService = fileHandlerService;
	}

	@Override
	public void run(String... args) throws Exception {
		Sql2JSONCommandLineOptions commandLineOptions = Sql2JSONCommandLineOptions.parseFrom(args);

		if (commandLineOptions.isHelpRequested()) {
			systemService.println(commandLineOptions.getHelpScreen());
			systemService.exit(0);
			return;
		}

		String json = sql2JSONService.convertInsertFilesToJson(commandLineOptions.getInputDirectory());

		Path outputPath = fileHandlerService.get(getOutputFileAbsolutePath(commandLineOptions));

		// TODO Error handling such as file not writeable, IOExceptions, file already exists ...
		Files.write(outputPath, json.getBytes());

		// Since we run in a thread if we don't exit explicitly we will run
		// forever
		systemService.exit(0);
	}

	private String getOutputFileAbsolutePath(Sql2JSONCommandLineOptions commandLineOptions) {
		String outputDirectory = commandLineOptions.getOutputDirectory();

		if (!outputDirectory.endsWith(File.separator)) {
			outputDirectory = outputDirectory + File.separator;
		}

		return outputDirectory + RESULT_FILE_NAME;
	}

}
