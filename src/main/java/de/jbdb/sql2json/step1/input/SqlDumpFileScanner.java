package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.ConvenientIllegalArgumentException.throwIllegalArgument;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class SqlDumpFileScanner {
	private enum State {
		NO_INSERT, INSERT
	}

	// CONSTANTS
	private static final String INSERT_START = "INSERT";
	private static final String INSERT_END = ";";

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
	public ScanResult scanDirectory(String... directoryPath) {
		if (directoryPath == null || directoryPath.length == 0) {
			throwIllegalArgument("Path array may not be null or empty.");
		}

		Arrays.stream(directoryPath).forEach(this::scanFile);

		return scanResult;
	}

	// PRIVATE METHODS
	private void scanFile(String filePath) {
		try (Stream<String> stream = fileHandler.lines(fileHandler.get(filePath))) {

			stream.forEach(this::scanLine);

		} catch (IOException e) {
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

}
