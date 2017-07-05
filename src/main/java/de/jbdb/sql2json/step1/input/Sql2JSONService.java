package de.jbdb.sql2json.step1.input;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import de.jbdb.sql2json.step1.input.modell.InsertStatement;
import de.jbdb.sql2json.step1.input.modell.TableName;

@Service
public class Sql2JSONService {

	private SqlInsertDirectoryScanner directoryScanner;
	private FileHandler fileHandler;

	@Autowired(required = true)
	public Sql2JSONService(SqlInsertDirectoryScanner directoryScanner, FileHandler fileHandler) {
		this.directoryScanner = directoryScanner;
		this.fileHandler = fileHandler;
	}

	public String convertInsertFilesToJson(String rootDirectory) {

		ScanResult scanResult = directoryScanner.scanDirectories(rootDirectory);

		if (!StringUtils.isEmpty(scanResult.getErrorMessages())) {
			try {
				fileHandler.write(rootDirectory + "/error.log", scanResult.getErrorMessages());
			} catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
			if (scanResult.getAllResults().isEmpty()) {
				return "";
			}
		}

		Map<TableName, InsertStatement> allResults = scanResult.getAllResults();
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (InsertStatement insertStatement : allResults.values()) {
			builder.append(insertStatement.toJSON());
			builder.append(",");
		}
		builder.replace(builder.length() - 1, builder.length(), "]");

		return builder.toString();
	}

}
