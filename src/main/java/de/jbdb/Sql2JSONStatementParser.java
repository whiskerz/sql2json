package de.jbdb;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

public class Sql2JSONStatementParser implements StatementVisitor {
	
	private JsonObjectBuilder resultBuilder;

	public void visit(Insert insert) {
		if (insert == null) {
			throwIllegalArgument("Insert statement parameter may not be null.");
		}
		
		resultBuilder = Json.createObjectBuilder();
		
		Table table = insert.getTable();
		if (table == null) {
			throwIllegalArgument("Table is required since the table name defines the top level json name to be used.");
		}
		
		if (table != null) {
			String tableName = table.getName();
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			resultBuilder.add(tableName, arrayBuilder);
		}
		
		List<Column> columns = insert.getColumns();
		if (columns != null && isNotEmpty(columns)) {
			
		}
	}

	private boolean isNotEmpty(List<Column> columns) {
		return !columns.isEmpty();
	}

	public JsonObject returnResult() {
		if (resultBuilder == null) {
			return null;
		}
		return resultBuilder.build();
	}
	
	public void visit(Select select) {
		throwIllegalArgument();
	}

	public void visit(Delete delete) {
		throwIllegalArgument();
	}

	public void visit(Update update) {
		throwIllegalArgument();
	}

	public void visit(Replace replace) {
		throwIllegalArgument();
	}

	public void visit(Drop drop) {
		throwIllegalArgument();
	}

	public void visit(Truncate truncate) {
		throwIllegalArgument();
	}

	public void visit(CreateIndex createIndex) {
		throwIllegalArgument();
	}

	public void visit(CreateTable createTable) {
		throwIllegalArgument();
	}

	public void visit(CreateView createView) {
		throwIllegalArgument();
	}

	public void visit(Alter alter) {
		throwIllegalArgument();
	}

	public void visit(Statements stmts) {
		throwIllegalArgument();
	}

	private void throwIllegalArgument() {
		throwIllegalArgument("Only accepting INSERT statements.");
	}

	private void throwIllegalArgument(String message) {
		throw new IllegalArgumentException(message);
	}

}
