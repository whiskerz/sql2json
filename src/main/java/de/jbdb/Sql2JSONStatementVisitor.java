package de.jbdb;

import static de.jbdb.IllegalVisitorArgumentException.throwIllegalArgument;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import net.sf.jsqlparser.expression.operators.relational.ItemsList;
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

public class Sql2JSONStatementVisitor implements StatementVisitor {

	private JsonObjectBuilder resultBuilder;

	@Override
	public void visit(Insert insert) {
		if (insert == null) {
			throwIllegalArgument("Insert statement parameter may not be null.");
		}

		Table table = insert.getTable();
		if (table == null) {
			throwIllegalArgument("Table is required since the table name defines the top level json name to be used.");
		}

		resultBuilder = Json.createObjectBuilder();
		String tableName = table.getName();

		List<Column> columns = insert.getColumns();
		if (columns == null || columns.isEmpty()) {
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			resultBuilder.add(tableName, arrayBuilder);
			return;
		}

		ItemsList itemsList = insert.getItemsList();
		if (itemsList == null) {
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			resultBuilder.add(tableName, arrayBuilder);
			return;
		}

		Sql2JSONItemsListParser insertParser = new Sql2JSONItemsListParser(columns);
		JsonArrayBuilder jsonArrayBuilder = insertParser.parse(itemsList);
		resultBuilder.add(tableName, jsonArrayBuilder);
	}

	public JsonObject returnResult() {
		if (resultBuilder == null) {
			return null;
		}
		return resultBuilder.build();
	}

	@Override
	public void visit(Select select) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Delete delete) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Update update) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Replace replace) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Drop drop) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Truncate truncate) {
		throwIllegalArgument();
	}

	@Override
	public void visit(CreateIndex createIndex) {
		throwIllegalArgument();
	}

	@Override
	public void visit(CreateTable createTable) {
		throwIllegalArgument();
	}

	@Override
	public void visit(CreateView createView) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Alter alter) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Statements stmts) {
		throwIllegalArgument();
	}

}
