package de.jbdb.sql2json.step1.input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class FileHandlerService {

	public Path get(String first, String... more) {
		return Paths.get(first, more);
	}

	public Stream<Path> files(Path directoryPath) throws IOException {
		return Files.list(directoryPath);
	}

	public Stream<String> lines(Path path) throws IOException {
		return Files.lines(path);
	}

	public Path write(String path, String text) throws IOException {
		return Files.write(Paths.get(path), text.getBytes());
	}
}
