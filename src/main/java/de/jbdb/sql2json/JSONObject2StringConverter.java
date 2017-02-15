package de.jbdb.sql2json;

import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

public class JSONObject2StringConverter {

	public String convertJSONObject(JsonObject jsonObject) {
		if (jsonObject == null) {
			return "";
		}
		
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.writeObject(jsonObject);
		}
		
		return stringWriter.toString();
	}

}
