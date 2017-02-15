package de.jbdb.sql2json;

import static de.jbdb.sql2json.IllegalVisitorArgumentException.throwIllegalArgument;

import javax.json.JsonObjectBuilder;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

public class Sql2JSONExpressionVisitor implements ExpressionVisitor {

	private JsonObjectBuilder jsonObjectBuilder;

	private String columnName;

	public Sql2JSONExpressionVisitor(JsonObjectBuilder jsonObjectBuilder) {
		if (jsonObjectBuilder == null) {
			throwIllegalArgument("Need the object builder to put results somewhere. Please supply one.");
		}
		this.jsonObjectBuilder = jsonObjectBuilder;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public void visit(NullValue nullValue) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(Function function) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(SignedExpression signedExpression) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(JdbcParameter jdbcParameter) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(JdbcNamedParameter jdbcNamedParameter) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(DoubleValue doubleValue) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(LongValue longValue) {
		checkColumnName();

		jsonObjectBuilder.add(columnName, longValue.getStringValue());
	}

	@Override
	public void visit(DateValue dateValue) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(TimeValue timeValue) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(TimestampValue timestampValue) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(Parenthesis parenthesis) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(StringValue stringValue) {
		checkColumnName();

		jsonObjectBuilder.add(columnName, stringValue.getValue());
		columnName = null;
	}

	private void checkColumnName() {
		if (columnName == null) {
			throwIllegalArgument(
					"You need to set the column name before calling the accept method on your expression. Sorry visitors are kinda stupid that way or I have not found a better way to deal with them.");
		}
	}

	@Override
	public void visit(Addition addition) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(Division division) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(Multiplication multiplication) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(Subtraction subtraction) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(AndExpression andExpression) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(OrExpression orExpression) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(Between between) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(EqualsTo equalsTo) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(InExpression inExpression) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(IsNullExpression isNullExpression) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(LikeExpression likeExpression) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(MinorThan minorThan) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(Column tableColumn) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(SubSelect subSelect) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(CaseExpression caseExpression) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(WhenClause whenClause) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(ExistsExpression existsExpression) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(AllComparisonExpression allComparisonExpression) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(AnyComparisonExpression anyComparisonExpression) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(Concat concat) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(Matches matches) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(BitwiseAnd bitwiseAnd) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(BitwiseXor bitwiseXor) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(CastExpression cast) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(Modulo modulo) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(AnalyticExpression aexpr) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(ExtractExpression eexpr) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(IntervalExpression iexpr) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(OracleHierarchicalExpression oexpr) {
		throwIllegalArgument("Only accepting String so far.");
	}

	@Override
	public void visit(RegExpMatchOperator rexpr) {
		throwIllegalArgument("Only accepting String so far.");
	}

}
