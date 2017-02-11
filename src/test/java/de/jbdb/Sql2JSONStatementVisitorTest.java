package de.jbdb;

import static de.jbdb.matcher.CaseInsensitiveSubstringMatcher.containsIgnoringCase;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
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

public class Sql2JSONStatementVisitorTest {

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	private Sql2JSONStatementVisitor classUnderTest;
	
	@Before
	public void before() {
		classUnderTest = new Sql2JSONStatementVisitor();
	}
	
	@Test
	public void testVisitSelect() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Select());
	}

	@Test
	public void testVisitDelete() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Delete());
	}

	@Test
	public void testVisitUpdate() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Update());
	}

	@Test
	public void testVisitReplace() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Replace());
	}

	@Test
	public void testVisitDrop() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Drop());
	}

	@Test
	public void testVisitTruncate() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Truncate());
	}

	@Test
	public void testVisitCreateIndex() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new CreateIndex());
	}

	@Test
	public void testVisitCreateTable() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new CreateTable());
	}

	@Test
	public void testVisitCreateView() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new CreateView());
	}

	@Test
	public void testVisitAlter() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Alter());
	}

	@Test
	public void testVisitStatements() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Statements());
	}

	@Test
	public void testVisitInsertNull() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(containsIgnoringCase("insert statement parameter may not be null"));
		
		classUnderTest.visit((Insert) null);
	}

	@Test
	public void testVisitInsertEmptyInsert() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(containsIgnoringCase("table is required"));
		
		classUnderTest.visit(new Insert());
	}

	@Test
	public void testVisitInsertTableOnly() throws Exception {
		Insert insert = new Insert();
		insert.setTable(new Table("testTable"));
		
		classUnderTest.visit(insert);
		JsonObject result = classUnderTest.returnResult();
		
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
		
		JsonArray jsonToplevel = result.getJsonArray("testTable");
		assertThat(jsonToplevel).isNotNull();
		assertThat(jsonToplevel).isEmpty();
	}

	@Test
	public void testVisitInsertTableColumnNoValue() throws Exception {
		Insert insert = new Insert();
		insert.setTable(new Table("testTable"));
		insert.setColumns(Arrays.asList(new Column("testColumn")));
		
		classUnderTest.visit(insert);
		JsonObject result = classUnderTest.returnResult();
		
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
		
		JsonArray jsonToplevel = result.getJsonArray("testTable");
		assertThat(jsonToplevel).isNotNull();
		assertThat(jsonToplevel).isEmpty();
	}

	@Test
	public void testVisitInsertTableColumnOneValue() throws Exception {
		Insert insert = new Insert();
		insert.setTable(new Table("testTable"));
		insert.setColumns(Arrays.asList(new Column("testColumn")));
		insert.setItemsList(createItemsList());
		
		classUnderTest.visit(insert);
		JsonObject result = classUnderTest.returnResult();
		
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
		
		JsonArray jsonToplevel = result.getJsonArray("testTable");
		assertThat(jsonToplevel).isNotNull();
		assertThat(jsonToplevel).isNotEmpty();
		assertThat(jsonToplevel).hasSize(1);
		
		JsonObject jsonObject = jsonToplevel.getJsonObject(0);
		assertThat(jsonObject).isNotNull();
		assertThat(jsonObject.getString("grutzlpfrümpft")).isNull();
		assertThat(jsonObject.getJsonString("testColumn")).isNotNull();
		assertThat(jsonObject.getJsonString("testColumn").getString()).isEqualTo("testValue");
	}

	private ItemsList createItemsList() {
		ExpressionList expressions = new ExpressionList();
		expressions.setExpressions(Arrays.asList(new StringValue("testValue")));
		
		return expressions;
	}
}
