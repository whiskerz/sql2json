package de.jbdb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SqlDumpFileScanner {
	public enum State {
		NO_INSERT

	}

	private State state = State.NO_INSERT;
	private ArrayList<String> insertList = new ArrayList<String>();;

	public List<String> scanFile(String filePath) {
		Consumer<String> scan = (String line) -> scanLine(line);

		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

			stream.forEach(scan);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return insertList;
	}

	private Consumer<? super String> scanLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
