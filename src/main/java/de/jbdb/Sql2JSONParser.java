package de.jbdb;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

public class Sql2JSONParser {

	public String parseSQL(String sqlStatement) throws JSQLParserException {

		if (sqlStatement == null) {
			return "";
		}

		if (sqlStatement == "") {
			return "";
		}

		String parseableStatemant = removeAccentuatedQuotationMarks(sqlStatement);

		Statement statement = CCJSqlParserUtil.parse(parseableStatemant);

		Sql2JSONStatementVisitor statementVisitor = new Sql2JSONStatementVisitor();

		statement.accept(statementVisitor);

		return new JSONObject2StringConverter().convertJSONObject(statementVisitor.returnResult());
	}

	private String removeAccentuatedQuotationMarks(String sqlStatement) {
		return sqlStatement.replace("`", "");
	}

}
