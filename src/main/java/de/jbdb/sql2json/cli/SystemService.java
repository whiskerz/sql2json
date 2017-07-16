package de.jbdb.sql2json.cli;

import org.springframework.stereotype.Service;

@Service
public class SystemService {

	public void println(String string) {
		System.out.println(string);
	}

	public void exit(int status) {
		System.exit(status);
	}

}
