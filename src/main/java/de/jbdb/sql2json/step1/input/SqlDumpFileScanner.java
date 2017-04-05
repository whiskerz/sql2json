package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.ConvenientIllegalArgumentException.throwIllegalArgument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class SqlDumpFileScanner {
	private enum State {
		NO_INSERT, INSERT
	}

	// CONSTANTS
	private static final String INSERT_START = "INSERT";
	private static final String INSERT_END = ";";
	private static final String SQL_EXTENSION = ".sql";

	// SERVICES
	private FileHandler fileHandler;

	// ATTRIBUTES
	private State state = State.NO_INSERT;
	private StringBuffer currentInsert;
	private ScanResult scanResult;

	// CONSTRUCTOR
	public SqlDumpFileScanner(FileHandler fileHandler) {
		if (fileHandler == null) {
			throwIllegalArgument("A service for file handling is required for this scanner.");
		}

		this.fileHandler = fileHandler;
	}

	// PUBLIC API
	public ScanResult scanDirectories(String... directoryPath) {
		if (directoryPath == null || directoryPath.length == 0) {
			throwIllegalArgument("Path array may not be null or empty.");
		}

		scanResult = new ScanResult();
		Arrays.stream(directoryPath).forEach(this::scanDirectory);

		return scanResult;
	}

	// PRIVATE METHODS
	private void scanDirectory(String directoryPath) {
		if (directoryPath == null || directoryPath.isEmpty()) {
			scanResult.setResultStatus(ScanResultStatus.PARTIAL);
			return;
		}
		
		Path directory = fileHandler.get(directoryPath);
		if (Files.notExists(directory)) {
			scanResult.setResultStatus(ScanResultStatus.PARTIAL);
			return;
		}
		
		try {
			fileHandler.files(directory).filter(this::exists).filter(this::isSqlFile).forEach(this::scanFile);;
		} catch (IOException e) {
			scanResult.setResultStatus(ScanResultStatus.PARTIAL);
			// TODO: Real logging
			e.printStackTrace();
		}
	}
	
	private void scanFile(Path filePath) {
		try (Stream<String> stream = fileHandler.lines(filePath)) {

			stream.forEach(this::scanLine);

		} catch (IOException e) {
			// TODO: Real logging
			e.printStackTrace();
		}
	}

	private void scanLine(String line) {
		if (State.NO_INSERT == state && line.startsWith(INSERT_START)) {
			state = State.INSERT;
			currentInsert = new StringBuffer();
		}

		if (State.INSERT == state) {
			currentInsert.append(line);

			if (line.endsWith(INSERT_END)) {
				state = State.NO_INSERT;
				scanResult.add(new InsertStatement(currentInsert.toString()));
			}
		}
	}
	
	private boolean exists(Path filePath) {
		return Files.exists(filePath);
	}
	
	private boolean isSqlFile(Path filePath) {
		return !Files.isDirectory(filePath) && filePath.endsWith(SQL_EXTENSION);
	}

}
