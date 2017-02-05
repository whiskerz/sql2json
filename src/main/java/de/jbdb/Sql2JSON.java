package de.jbdb;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

public class Sql2JSON {

	public String parseSQL(String sqlStatement) throws JSQLParserException {
		
		if (sqlStatement == null) {
			return "";
		}
		
		if (sqlStatement == "") {
			return "";
		}
		
		Statement statement = CCJSqlParserUtil.parse(sqlStatement);
		
		Sql2JSONStatementParser statementVisitor = new Sql2JSONStatementParser();
		
		statement.accept(statementVisitor);
		
		return new JSONObject2StringConverter().convertJSONObject(statementVisitor.returnResult());		
	}

}
