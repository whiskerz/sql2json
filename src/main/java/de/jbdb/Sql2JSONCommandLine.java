package de.jbdb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Sql2JSONCommandLine {

	public enum State {
		NO_INSERT

	}

	private State state = State.NO_INSERT;
	private ArrayList<String> insertList = new ArrayList<String>();;

	public static void main(String[] args) {
		Sql2JSONCommandLineOptions commandLineOptions = Sql2JSONCommandLineOptions.parseFrom(args);

		Sql2JSONCommandLine tempInstance = new Sql2JSONCommandLine();

		Consumer<String> scan = (String line) -> tempInstance.scanLine(line);

		try (Stream<String> stream = Files.lines(Paths.get(commandLineOptions.getInputDirectory()))) {

			stream.forEach(scan);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Consumer<? super String> scanLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
