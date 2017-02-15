package de.jbdb.sql2json;

public class IllegalVisitorArgumentException extends IllegalArgumentException {

	private static final long serialVersionUID = 5381716642803330491L;

	public static void throwIllegalArgument() {
		throwIllegalArgument("Only accepting INSERT statements.");
	}

	public static void throwIllegalArgument(String message) {
		throw new IllegalArgumentException(message);
	}

}
