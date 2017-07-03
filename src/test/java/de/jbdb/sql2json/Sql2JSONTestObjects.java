package de.jbdb.sql2json;

import net.sf.jsqlparser.expression.StringValue;

public interface Sql2JSONTestObjects {

	/**
	 * STRING_VALUE needs to be a String beginning and ending with "'" since the jsqlparser lib expects Strings to use
	 * this SQL style
	 */
	StringValue STRING_VALUE_01 = new StringValue("'testValue01'");
	StringValue STRING_VALUE_02 = new StringValue("'testValue02'");

	String TEST_TABLE = "testTable";
	String TEST_COLUMN = "testColumn";
	String TEST_VALUE1 = "testValue1";
	String TEST_VALUE2 = "testValue2";
	String[] TESTINSERT = { "INSERT INTO " + TEST_TABLE + " (" + TEST_COLUMN + ") VALUES ",
			"(" + TEST_VALUE1 + ", " + TEST_VALUE2 + ");" };

	String TESTJSON = "{\"" + TEST_TABLE + "\":[" //
			+ "{\"" + TEST_COLUMN + "\":\"" + TEST_VALUE1 + "\"}," //
			+ "{\"" + TEST_COLUMN + "\":\"" + TEST_VALUE2 + "\"}" //
			+ "]}"; //
}
