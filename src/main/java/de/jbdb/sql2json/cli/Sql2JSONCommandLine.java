package de.jbdb.sql2json.cli;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(Sql2JSONCommandLine.PROD_PROFILE)
@ComponentScan("de.jbdb.sql2json")
@SpringBootApplication
public class Sql2JSONCommandLine {

	public static final String TEST_PROFILE = "test";
	public static final String PROD_PROFILE = "prod";

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Sql2JSONCommandLine.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
