package de.jbdb.sql2json.step1.input;

public enum ScanResultStatus {

	FULL("The scan was successful at all paths"), 
	PARTIAL("Some paths could be scanned successfully, others not. Query the error messages for more info"), 
	FAIL("All paths failed scanning. Query the error messages for more info");
	
	private String message; 
	
	private ScanResultStatus(String message) {
		this.message = message; 
	}
	
	public String getMessage() {
		return message;
	}
}
