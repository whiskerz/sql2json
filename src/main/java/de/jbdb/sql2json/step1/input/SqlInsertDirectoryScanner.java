package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.ConvenientIllegalArgumentException.throwIllegalArgument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import de.jbdb.sql2json.step1.input.modell.InsertStatement;

public class SqlInsertDirectoryScanner {
	private enum State {
		NO_INSERT, INSERT
	}

	// MESSAGES
	private static final String DIRECTORY_PATH_NULL_EMPTY = "A given path was null or empty. This is a parameter error. Calling class gave illegal arguments. Please check caller.";
	private static final String DIRECTORY_DOESNT_EXIST = "A given path leads to a non existant directory. This is a parameter error. Calling class gave illegal arguments. Please check caller.";
	private static final String IOEXCEPTION_READING_FILELIST = "An IOException occured while trying to read the file list: ";
	private static final String IOEXCEPTION_READING_FILE = "An IOException occured while trying to read a single file: ";

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
	@Autowired(required = true)
	public SqlInsertDirectoryScanner(FileHandler fileHandler) {
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
			scanResult.addError(DIRECTORY_PATH_NULL_EMPTY);
			return;
		}

		Path directory = fileHandler.get(directoryPath);
		if (Files.notExists(directory)) {
			scanResult.addError(DIRECTORY_DOESNT_EXIST);
			return;
		}

		try {
			fileHandler.files(directory).filter(this::exists).filter(this::isSqlFile).forEach(this::scanFile);
		} catch (IOException e) {
			scanResult.addError(IOEXCEPTION_READING_FILELIST + e.getMessage());
		}
	}

	private void scanFile(Path filePath) {
		try (Stream<String> stream = fileHandler.lines(filePath)) {

			stream.forEach(this::scanLine);

		} catch (IOException e) {
			scanResult.addError(IOEXCEPTION_READING_FILE + e.getMessage());
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
		return !Files.isDirectory(filePath) && filePath.toString().endsWith(SQL_EXTENSION);
	}

}
