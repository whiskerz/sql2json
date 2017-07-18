package de.jbdb.sql2json.cli;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("de.jbdb.sql2json")
@SpringBootApplication
public class Sql2JSONCommandLine {

	public static final String TEST_PROFILE = "test";
	public static final String PROD_PROFILE = "prod";

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Sql2JSONCommandLine.class);
		app.setAdditionalProfiles(PROD_PROFILE);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
