package de.jbdb;

import javax.json.Json;
import javax.json.JsonObject;

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
	
	private JsonObject result;

	public void visit(Insert insert) {
		if (insert == null) {
			result = null;
			return;
		}
		
		new Sql2JSONInsertParser();
		
		result = Json.createObjectBuilder().build();
	}

	public JsonObject returnResult() {
		return result;
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
		throw new IllegalArgumentException("Only accepting INSERT statements.");
	}

}
