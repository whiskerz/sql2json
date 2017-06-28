package de.jbdb.sql2json.step1.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanResult {

	private List<String> errorMessages = new ArrayList<>();

	private Map<TableName, InsertStatement> resultMap = new HashMap<>();

	public ScanResultStatus getResultStatus() {
		if (resultMap.isEmpty()) {
			return ScanResultStatus.FAIL;
		}
		if (!errorMessages.isEmpty()) {
			return ScanResultStatus.PARTIAL;
		}
		return ScanResultStatus.FULL;
	}

	public String getErrorMessages() {
		if (errorMessages.isEmpty()) {
			return "";
		}
		return errorMessages.toString();
	}

	public Map<TableName, InsertStatement> getAllResults() {
		return resultMap;
	}

	public void add(InsertStatement insert) {
		// if (resultMap.containsKey(insert.getTableName())) {
		// InsertStatement existingInsert = resultMap.get(insert.getTableName());
		// existingInsert.mergeWith(insert);
		// } else {
		resultMap.put(insert.getTableName(), insert);
		// }
	}

	public void addError(String errorMessage) {
		this.errorMessages.add(errorMessage);
	}

}
