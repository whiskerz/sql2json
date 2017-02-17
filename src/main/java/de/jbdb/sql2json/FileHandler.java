package de.jbdb.sql2json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileHandler {

	public Stream<String> lines(Path path) throws IOException {
		return Files.lines(path);
	}

	public Path get(String first, String... more) {
		return Paths.get(first, more);
	}

}
