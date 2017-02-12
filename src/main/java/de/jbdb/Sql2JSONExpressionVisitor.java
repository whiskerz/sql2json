package de.jbdb;

import static de.jbdb.IllegalVisitorArgumentException.throwIllegalArgument;

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

	@Override
	public void visit(NullValue nullValue) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Function function) {
		throwIllegalArgument();
	}

	@Override
	public void visit(SignedExpression signedExpression) {
		throwIllegalArgument();
	}

	@Override
	public void visit(JdbcParameter jdbcParameter) {
		throwIllegalArgument();
	}

	@Override
	public void visit(JdbcNamedParameter jdbcNamedParameter) {
		throwIllegalArgument();
	}

	@Override
	public void visit(DoubleValue doubleValue) {
		throwIllegalArgument();
	}

	@Override
	public void visit(LongValue longValue) {
		throwIllegalArgument();
	}

	@Override
	public void visit(DateValue dateValue) {
		throwIllegalArgument();
	}

	@Override
	public void visit(TimeValue timeValue) {
		throwIllegalArgument();
	}

	@Override
	public void visit(TimestampValue timestampValue) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Parenthesis parenthesis) {
		throwIllegalArgument();
	}

	@Override
	public void visit(StringValue stringValue) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Addition addition) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Division division) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Multiplication multiplication) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Subtraction subtraction) {
		throwIllegalArgument();
	}

	@Override
	public void visit(AndExpression andExpression) {
		throwIllegalArgument();
	}

	@Override
	public void visit(OrExpression orExpression) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Between between) {
		throwIllegalArgument();
	}

	@Override
	public void visit(EqualsTo equalsTo) {
		throwIllegalArgument();
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		throwIllegalArgument();
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		throwIllegalArgument();
	}

	@Override
	public void visit(InExpression inExpression) {
		throwIllegalArgument();
	}

	@Override
	public void visit(IsNullExpression isNullExpression) {
		throwIllegalArgument();
	}

	@Override
	public void visit(LikeExpression likeExpression) {
		throwIllegalArgument();
	}

	@Override
	public void visit(MinorThan minorThan) {
		throwIllegalArgument();
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		throwIllegalArgument();
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Column tableColumn) {
		throwIllegalArgument();
	}

	@Override
	public void visit(SubSelect subSelect) {
		throwIllegalArgument();
	}

	@Override
	public void visit(CaseExpression caseExpression) {
		throwIllegalArgument();
	}

	@Override
	public void visit(WhenClause whenClause) {
		throwIllegalArgument();
	}

	@Override
	public void visit(ExistsExpression existsExpression) {
		throwIllegalArgument();
	}

	@Override
	public void visit(AllComparisonExpression allComparisonExpression) {
		throwIllegalArgument();
	}

	@Override
	public void visit(AnyComparisonExpression anyComparisonExpression) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Concat concat) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Matches matches) {
		throwIllegalArgument();
	}

	@Override
	public void visit(BitwiseAnd bitwiseAnd) {
		throwIllegalArgument();
	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {
		throwIllegalArgument();
	}

	@Override
	public void visit(BitwiseXor bitwiseXor) {
		throwIllegalArgument();
	}

	@Override
	public void visit(CastExpression cast) {
		throwIllegalArgument();
	}

	@Override
	public void visit(Modulo modulo) {
		throwIllegalArgument();
	}

	@Override
	public void visit(AnalyticExpression aexpr) {
		throwIllegalArgument();
	}

	@Override
	public void visit(ExtractExpression eexpr) {
		throwIllegalArgument();
	}

	@Override
	public void visit(IntervalExpression iexpr) {
		throwIllegalArgument();
	}

	@Override
	public void visit(OracleHierarchicalExpression oexpr) {
		throwIllegalArgument();
	}

	@Override
	public void visit(RegExpMatchOperator rexpr) {
		throwIllegalArgument();
	}

}
