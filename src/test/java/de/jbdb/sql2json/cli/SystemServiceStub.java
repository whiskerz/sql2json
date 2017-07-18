package de.jbdb.sql2json.cli;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(Sql2JSONCommandLine.TEST_PROFILE)
class SystemServiceStub extends SystemService {

	@Override
	public void exit(int status) {
		System.out.println("Called exit with status: " + status);
	}

}