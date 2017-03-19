package de.jbdb.sql2json.step3.output;


import static org.fest.assertions.Assertions.assertThat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.junit.Before;
import org.junit.Test;

import de.jbdb.sql2json.step3.output.JSONObject2StringConverter;

public class JSONObject2StringConverterTest {

	private JSONObject2StringConverter classUnderTest;

	@Before
	public void before() {
		classUnderTest = new JSONObject2StringConverter();
	}
	
	@Test
	public void testConvertNull() {
		
		String jsonString = classUnderTest.convertJSONObject(null);
		
		assertThat(jsonString).isNotNull();
		assertThat(jsonString).isEmpty();
	}

	@Test
	public void testConvertEmptyObject() throws Exception {
		JsonObject jsonObject = Json.createObjectBuilder().build();
		
		String jsonString = classUnderTest.convertJSONObject(jsonObject);
		
		assertThat(jsonString).isNotNull();
		assertThat(jsonString).isNotEmpty();
		assertThat(jsonString).isEqualTo("{}");
	}
	
	@Test
	public void testConvertSimpleObject() throws Exception {
		JsonObject jsonObject = Json.createObjectBuilder().add("attribute", "value").build();
		
		String jsonString = classUnderTest.convertJSONObject(jsonObject);
		
		assertThat(jsonString).isNotNull();
		assertThat(jsonString).isNotEmpty();
		assertThat(jsonString).isEqualTo("{\"attribute\":\"value\"}");
	}
	
	@Test
	public void testConvertNestedObject() throws Exception {
		JsonObjectBuilder innerObject = Json.createObjectBuilder().add("attribute", "value");
		JsonObject jsonObject = Json.createObjectBuilder().add("innerObject", innerObject).build();

		String jsonString = classUnderTest.convertJSONObject(jsonObject);
		
		assertThat(jsonString).isNotNull();
		assertThat(jsonString).isNotEmpty();
		assertThat(jsonString).isEqualTo("{\"innerObject\":{\"attribute\":\"value\"}}");
	}
}
