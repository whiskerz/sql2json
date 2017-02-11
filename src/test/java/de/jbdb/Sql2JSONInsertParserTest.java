package de.jbdb;

import org.junit.Before;
import org.junit.Test;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
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
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperatorType;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

public class Sql2JSONInsertParserTest {

	private Sql2JSONInsertParser classUnderTest;
	
	@Before
	public void before() {
		classUnderTest = new Sql2JSONInsertParser();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testVisitNullValue() throws Exception {
		classUnderTest.visit(new NullValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitFunction() throws Exception {
		classUnderTest.visit(new Function());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitSignedExpression() throws Exception {
		classUnderTest.visit(new SignedExpression('+', new NullValue()));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitJdbcParameter() throws Exception {
		classUnderTest.visit(new JdbcParameter());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitJdbcNamedParameter() throws Exception {
		classUnderTest.visit(new JdbcNamedParameter());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitDoubleValue() throws Exception {
		classUnderTest.visit(new DoubleValue("1.0"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitLongValue() throws Exception {
		classUnderTest.visit(new LongValue("1"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitDateValue() throws Exception {
		classUnderTest.visit(new DateValue("2222-2-22"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitTimeValue() throws Exception {
		classUnderTest.visit(new TimeValue("22:22:22"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitTimestampValue() throws Exception {
		classUnderTest.visit(new TimestampValue("2222.2.22 22:22"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitParenthesis() throws Exception {
		classUnderTest.visit(new Parenthesis());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitStringValue() throws Exception {
		classUnderTest.visit(new StringValue("String"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitAddition() throws Exception {
		classUnderTest.visit(new Addition());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitDivision() throws Exception {
		classUnderTest.visit(new Division());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitMultiplication() throws Exception {
		classUnderTest.visit(new Multiplication());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitSubtraction() throws Exception {
		classUnderTest.visit(new Subtraction());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitAndExpression() throws Exception {
		classUnderTest.visit(new AndExpression(new StringValue("String"), new StringValue("String")));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitOrExpression() throws Exception {
		classUnderTest.visit(new OrExpression(new StringValue("String"), new StringValue("String")));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitBetween() throws Exception {
		classUnderTest.visit(new Between());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitEqualsTo() throws Exception {
		classUnderTest.visit(new EqualsTo());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitGreaterThan() throws Exception {
		classUnderTest.visit(new GreaterThan());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitGreaterThanEquals() throws Exception {
		classUnderTest.visit(new GreaterThanEquals());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitInExpression() throws Exception {
		classUnderTest.visit(new InExpression());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitIsNullExpression() throws Exception {
		classUnderTest.visit(new IsNullExpression());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitLikeExpression() throws Exception {
		classUnderTest.visit(new LikeExpression());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitMinorThan() throws Exception {
		classUnderTest.visit(new MinorThan());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitMinorThanEquals() throws Exception {
		classUnderTest.visit(new MinorThanEquals());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitNotEqualsTo() throws Exception {
		classUnderTest.visit(new NotEqualsTo());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitColumn() throws Exception {
		classUnderTest.visit(new Column());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitSubSelect() throws Exception {
		classUnderTest.visit(new SubSelect());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitCaseExpression() throws Exception {
		classUnderTest.visit(new CaseExpression());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitWhenClause() throws Exception {
		classUnderTest.visit(new WhenClause());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitExistsExpression() throws Exception {
		classUnderTest.visit(new ExistsExpression());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitAllComparisonExpression() throws Exception {
		classUnderTest.visit(new AllComparisonExpression(new SubSelect()));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitAnyComparisonExpression() throws Exception {
		classUnderTest.visit(new AnyComparisonExpression(new SubSelect()));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitConcat() throws Exception {
		classUnderTest.visit(new Concat());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitMatches() throws Exception {
		classUnderTest.visit(new Matches());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitBitwiseAnd() throws Exception {
		classUnderTest.visit(new BitwiseAnd());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitBitwiseOr() throws Exception {
		classUnderTest.visit(new BitwiseOr());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitBitwiseXor() throws Exception {
		classUnderTest.visit(new BitwiseXor());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitCastExpression() throws Exception {
		classUnderTest.visit(new CastExpression());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitModulo() throws Exception {
		classUnderTest.visit(new Modulo());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitAnalyticExpression() throws Exception {
		classUnderTest.visit(new AnalyticExpression());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitExtractExpression() throws Exception {
		classUnderTest.visit(new ExtractExpression());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitIntervalExpression() throws Exception {
		classUnderTest.visit(new IntervalExpression());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitOracleHierarchicalExpression() throws Exception {
		classUnderTest.visit(new OracleHierarchicalExpression());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVisitRegExpMatchOperator() throws Exception {
		classUnderTest.visit(new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASESENSITIVE));
	}

}
