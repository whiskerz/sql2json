package de.jbdb.sql2json;

import net.sf.jsqlparser.expression.StringValue;

public interface Sql2JSONTestObjects {

	/**
	 * STRING_VALUE needs to be a String beginning and ending with "'" since the jsqlparser lib expects Strings to use
	 * this SQL style
	 */
	StringValue STRING_VALUE_01 = new StringValue("'testValue01'");
	StringValue STRING_VALUE_02 = new StringValue("'testValue02'");

}
