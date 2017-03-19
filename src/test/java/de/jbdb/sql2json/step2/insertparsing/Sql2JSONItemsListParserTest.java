package de.jbdb.sql2json.step2.insertparsing;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.json.JsonArrayBuilder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.jbdb.sql2json.Sql2JSONTestObjects;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

public class Sql2JSONItemsListParserTest implements Sql2JSONTestObjects {

	private static final String COLNAME_1 = "testColumn1";
	private static final String COLNAME_2 = "testColumn2";

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private Sql2JSONItemsListParser classUnderTest;

	@Test
	public void testSql2JSONItemsListParser_NullColumns() {
		expectedException.expect(IllegalArgumentException.class);

		new Sql2JSONItemsListParser(null);
	}

	@Test
	public void testSql2JSONItemsListParser_EmptyColumns() {
		expectedException.expect(IllegalArgumentException.class);

		new Sql2JSONItemsListParser(Collections.emptyList());
	}

	@Test
	public void testSql2JSONItemsListParser_NoColumName() throws Exception {
		expectedException.expect(IllegalArgumentException.class);

		new Sql2JSONItemsListParser(Arrays.asList(new Column()));
	}

	@Before
	public void before() {
		Column column = new Column(COLNAME_1);
		classUnderTest = new Sql2JSONItemsListParser(Arrays.asList(column));
	}

	@Test
	public void testParse_NullItemList() {
		expectedException.expect(IllegalArgumentException.class);

		classUnderTest.parse(null);
	}

	@Test
	public void testParse_EmptyItemList() {
		expectedException.expect(IllegalArgumentException.class);

		classUnderTest.parse(new ExpressionList());
	}

	@Test
	public void testParse_SingleItemItemList() {
		ExpressionList expressionList = new ExpressionList();
		expressionList.setExpressions(Arrays.asList(STRING_VALUE_01));

		JsonArrayBuilder result = classUnderTest.parse(expressionList);

		assertThat(result.build().toString())
				.isEqualTo("[{\"" + COLNAME_1 + "\":\"" + STRING_VALUE_01.getValue() + "\"}]");
	}

	@Test
	public void testParse_MultiItemItemList() throws Exception {
		ExpressionList expressionList = new ExpressionList();
		expressionList.setExpressions(Arrays.asList(STRING_VALUE_01, STRING_VALUE_02));

		classUnderTest = new Sql2JSONItemsListParser(Arrays.asList(new Column(COLNAME_1), new Column(COLNAME_2)));

		JsonArrayBuilder result = classUnderTest.parse(expressionList);

		assertThat(result.build().toString()).isEqualTo("[{\"" + COLNAME_1 + "\":\"" + STRING_VALUE_01.getValue()
				+ "\",\"" + COLNAME_2 + "\":\"" + STRING_VALUE_02.getValue() + "\"}]");
	}

	@Test
	public void testVisitExpressionList() throws Exception {
		testParse_MultiItemItemList();
	}

	@Test
	public void testVisitExpressionList_ExpressionListListIsNull() throws Exception {
		expectedException.expect(IllegalArgumentException.class);

		ExpressionList expressionList = new ExpressionList();
		expressionList.setExpressions(null);

		classUnderTest.visit(expressionList);
	}

	@Test
	public void testVisitExpressionList_TwoItemsButOneColumn() throws Exception {
		expectedException.expect(IllegalArgumentException.class);

		ExpressionList expressionList = new ExpressionList();
		expressionList.setExpressions(Arrays.asList(STRING_VALUE_01, STRING_VALUE_01));

		classUnderTest.visit(expressionList);
	}

	@Test
	public void testParse_MultiExpressionList() {
		ExpressionList expressionList = new ExpressionList();
		expressionList.setExpressions(Arrays.asList(STRING_VALUE_01, STRING_VALUE_02));

		MultiExpressionList multiExpressionList = new MultiExpressionList();
		multiExpressionList.addExpressionList(expressionList);
		multiExpressionList.addExpressionList(expressionList);

		classUnderTest = new Sql2JSONItemsListParser(Arrays.asList(new Column(COLNAME_1), new Column(COLNAME_2)));

		JsonArrayBuilder result = classUnderTest.parse(multiExpressionList);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");

		// JSON Object 1
		stringBuilder.append("{\"");
		stringBuilder.append(COLNAME_1);
		stringBuilder.append("\":\"");
		stringBuilder.append(STRING_VALUE_01.getValue());
		stringBuilder.append("\",\"");
		stringBuilder.append(COLNAME_2);
		stringBuilder.append("\":\"");
		stringBuilder.append(STRING_VALUE_02.getValue());
		stringBuilder.append("\"}");

		stringBuilder.append(",");

		// JSON Object 2
		stringBuilder.append("{\"");
		stringBuilder.append(COLNAME_1);
		stringBuilder.append("\":\"");
		stringBuilder.append(STRING_VALUE_01.getValue());
		stringBuilder.append("\",\"");
		stringBuilder.append(COLNAME_2);
		stringBuilder.append("\":\"");
		stringBuilder.append(STRING_VALUE_02.getValue());
		stringBuilder.append("\"}");

		stringBuilder.append("]");
		assertThat(result.build().toString()).isEqualTo(stringBuilder.toString());
	}

	@Test
	public void testVisitMultiExpressionList() throws Exception {
		testParse_MultiExpressionList();
	}

	@Test
	public void testVisitMultiExpressionList_ListIsNull() throws Exception {
		expectedException.expect(IllegalArgumentException.class);

		classUnderTest.parse(null);
	}

	@Test
	public void testVisitMultiExpressionList_ExpressionListNull() throws Exception {
		expectedException.expect(IllegalArgumentException.class);

		MultiExpressionList multiExpressionList = new MultiExpressionList();
		multiExpressionList.addExpressionList((List<Expression>) null);

		classUnderTest.parse(multiExpressionList);
	}

	@Test
	public void testVisitMultiExpressionList_ExpressionListEmpty() throws Exception {
		expectedException.expect(IllegalArgumentException.class);

		MultiExpressionList multiExpressionList = new MultiExpressionList();
		multiExpressionList.addExpressionList(Collections.emptyList());

		classUnderTest.parse(multiExpressionList);
	}

	@Test
	public void testVisitSubSelect() {
		expectedException.expect(IllegalArgumentException.class);

		classUnderTest.visit(new SubSelect());
	}

}
