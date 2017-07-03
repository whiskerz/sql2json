package de.jbdb.sql2json.cli;

import static java.lang.System.exit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import de.jbdb.sql2json.step1.input.Sql2JSONService;

@Controller
public class Sql2JSONCommandLineRunner implements CommandLineRunner {

	@Autowired
	private Sql2JSONService sql2JSONService;

	@Override
	public void run(String... args) throws Exception {
		Sql2JSONCommandLineOptions commandLineOptions = Sql2JSONCommandLineOptions.parseFrom(args);

		sql2JSONService.convertInsertFilesToJson(commandLineOptions.getInputDirectory());

		// System.out.println("I am here!: run");
		//
		// if (args.length > 0) {
		// System.out.println(helloService.getMessage(args[0].toString()));
		// } else {
		// System.out.println(helloService.getMessage());
		// }

		// Since we run in a thread if we don't exit explicitly we will run
		// forever

		exit(0);
	}

}
