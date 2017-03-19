package de.jbdb.sql2json.step2.insertparsing;

import static de.jbdb.sql2json.ConvenientIllegalArgumentException.throwIllegalArgument;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

public class Sql2JSONItemsListParser implements ItemsListVisitor {

	@NotNull
	@Size(min = 1)
	private List<Column> columns;
	private JsonArrayBuilder jsonArrayBuilder;

	public Sql2JSONItemsListParser(List<Column> columns) {
		if (columns == null || columns.isEmpty()) {
			throwIllegalArgument(
					"Columns are required to parse. If you do not have columns, return an empty JSON instead.");
		}

		for (Column column : columns) {
			String columnName = column.getColumnName();
			if (columnName == null || columnName.isEmpty()) {
				throwIllegalArgument(
						"Column names are mandatory on columns. If you do not have named columns, return an empty JSON instead.");
			}
		}

		this.columns = columns;
	}

	/**
	 * @param itemsList
	 * @throws IllegalArgumentException
	 *             If itemsList is null
	 */
	public synchronized JsonArrayBuilder parse(ItemsList itemsList) {
		if (itemsList == null) {
			throwIllegalArgument(
					"Seriously? You call a parse interface with a null value? Won't handle that gracefully, make sure you have something relevant!");
		}

		jsonArrayBuilder = Json.createArrayBuilder();

		itemsList.accept(this);

		return jsonArrayBuilder;
	}

	@Override
	public void visit(ExpressionList expressionList) {
		List<Expression> expressions = expressionList.getExpressions();

		if (expressions == null) {
			throwIllegalArgument(
					"Expressions are null but shouldn't be, something went wrong with earlier steps I fear.");
		}

		if (expressions.size() != columns.size()) {
			throwIllegalArgument("Expression list has " + expressions.size() + " but there are " + columns.size()
					+ " columns declared. Mismatch cannot be resolved.");
		}

		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		Sql2JSONExpressionVisitor expressionVisitor = new Sql2JSONExpressionVisitor(jsonObjectBuilder);

		for (int i = 0; i < expressions.size(); i++) {
			expressionVisitor.setColumnName(columns.get(i).getColumnName());
			expressions.get(i).accept(expressionVisitor);
		}

		jsonArrayBuilder.add(jsonObjectBuilder);
	}

	@Override
	public void visit(MultiExpressionList multiExprList) {

		if (multiExprList == null) {
			throwIllegalArgument(
					"MultiExpressions are null but shouldn't be, something went wrong with earlier steps I fear.");
		}

		List<ExpressionList> expressionListList = multiExprList.getExprList();

		if (expressionListList == null || expressionListList.isEmpty()) {
			throwIllegalArgument(
					"ExpressionList should neither be null nor empty. Well perhaps empty, edit this block if they should.");
		}

		for (ExpressionList expressionList : expressionListList) {
			expressionList.accept(this);
		}
	}

	@Override
	public void visit(SubSelect subSelect) {
		throwIllegalArgument("We do not support nor endorse sub selects.");
	}

}
