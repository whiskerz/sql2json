package de.jbdb.sql2json;

import static de.jbdb.sql2json.ConvenientIllegalArgumentException.throwIllegalArgument;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
	private HashMap<String, Insert> insertMap;

	public SqlDumpFileScanner(FileHandler fileHandler) {
		if (fileHandler == null) {
			throwIllegalArgument("A service for file handling is required for this scanner.");
		}

		this.fileHandler = fileHandler;
	}

	public Collection<Insert> scanDirectory(String... directoryPath) {
		if (directoryPath == null || directoryPath.length == 0) {
			throwIllegalArgument("Path array may not be null or empty.");
		}

		Arrays.stream(directoryPath).forEach(this::scanFile);

		return insertMap.values();
	}

	private void scanFile(String filePath) {
		try (Stream<String> stream = fileHandler.lines(fileHandler.get(filePath))) {

			stream.forEach(this::scanLine);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void scanLine(String line) {
		if (state == State.NO_INSERT && line.startsWith(INSERT_START)) {
			state = State.INSERT;
			currentInsert = new StringBuffer();
		}

		if (state == State.INSERT) {
			currentInsert.append(line);

			if (line.endsWith(INSERT_END)) {
				state = State.NO_INSERT;
				Insert insert = new Insert(currentInsert.toString());
				if (insertMap.containsKey(insert.getTableName())) {
					Insert existingInsert = insertMap.get(insert.getTableName());
					existingInsert.mergeWith(insert);
				} else {
					insertMap.put(insert.getTableName(), insert);
				}
			}
		}
	}

}
