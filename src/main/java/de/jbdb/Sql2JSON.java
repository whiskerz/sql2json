package de.jbdb;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

public class Sql2JSON {

	public String parseSQL(String forumPostSql) throws JSQLParserException {
		
		Statement statement = CCJSqlParserUtil.parse(forumPostSql);
		
		SqlValues valueList = new SqlValues(columns);
		valueList.addRow(row);
		
		JSONConverter converter = new JSONConverter(valueList);
		
		return converter.getJSON();
	}

}
