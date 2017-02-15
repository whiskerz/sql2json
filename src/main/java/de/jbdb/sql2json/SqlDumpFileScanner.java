package de.jbdb.sql2json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SqlDumpFileScanner {
	public enum State {
		NO_INSERT, INSERT

	}

	private static final String INSERT_START = "INSERT";

	private static final String INSERT_END = ";";

	private State state = State.NO_INSERT;
	private ArrayList<String> insertList = new ArrayList<String>();

	private StringBuffer currentInsert;

	private HashMap<String, Insert> insertMap;

	public Collection<Insert> scanDirectory(String[] directoryPath) {
		Consumer<String> scanFile = (String file) -> scanFile(file);

		Arrays.stream(directoryPath).forEach(scanFile);

		return insertMap.values();
	}

	public List<String> scanFile(String filePath) {
		Consumer<String> scanLine = (String line) -> scanLine(line);

		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

			stream.forEach(scanLine);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return insertList;
	}

	private void scanLine(String line) {
		if (state == State.NO_INSERT) {
			if (line.startsWith(INSERT_START)) {
				state = State.INSERT;
				currentInsert = new StringBuffer();
			}
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
