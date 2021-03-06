package de.jbdb.sql2json.cli;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(Sql2JSONCommandLine.PROD_PROFILE)
public class SystemService {

	public void println(String string) {
		System.out.println(string);
	}

	public void exit(int status) {
		System.exit(status);
	}

}
