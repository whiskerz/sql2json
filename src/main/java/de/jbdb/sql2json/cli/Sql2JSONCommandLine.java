package de.jbdb.sql2json.cli;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sql2JSONCommandLine {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Sql2JSONCommandLine.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
