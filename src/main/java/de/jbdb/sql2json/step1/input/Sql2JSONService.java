package de.jbdb.sql2json.step1.input;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.jbdb.sql2json.step1.input.modell.InsertStatement;
import de.jbdb.sql2json.step1.input.modell.TableName;

@Service
public class Sql2JSONService {

	private SqlInsertDirectoryScanner directoryScanner;

	@Autowired(required = true)
	public Sql2JSONService(SqlInsertDirectoryScanner directoryScanner) {
		this.directoryScanner = directoryScanner;
	}

	public String convertInsertFilesToJson(String... rootDirectoryList) {

		ScanResult scanResult = directoryScanner.scanDirectories(rootDirectoryList);

		Map<TableName, InsertStatement> allResults = scanResult.getAllResults();

		return allResults.values().stream().findFirst().get().toJSON();
	}

}
