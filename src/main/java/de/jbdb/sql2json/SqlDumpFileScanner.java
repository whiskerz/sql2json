package de.jbdb.sql2json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

	private StringBuffer currentInsert;;

	public List<String> scanFile(String filePath) {
		Consumer<String> scan = (String line) -> scanLine(line);

		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

			stream.forEach(scan);

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
			}
		}

	}

}
