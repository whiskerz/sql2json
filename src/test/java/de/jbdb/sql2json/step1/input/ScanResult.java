package de.jbdb.sql2json.step1.input;

import java.util.List;
import java.util.Map;

public class ScanResult {

	private ScanResultStatus resultStatus;
	private List<String> errorMessages;
	
	private Map<TableName, InsertStatement> resultMap;

	public ScanResultStatus getResultStatus() {
		return resultStatus;
	}

	public String getErrorMessages() {
		return errorMessages.toString();
	}

	public Map<TableName, InsertStatement> getAllResults() {
		return resultMap;
	}

	public void add(InsertStatement insert) {
		if (resultMap.containsKey(insert.getTableName())) {
			InsertStatement existingInsert = resultMap.get(insert.getTableName());
			existingInsert.mergeWith(insert);
		} else {
			resultMap.put(insert.getTableName(), insert);
		}
	}

}
