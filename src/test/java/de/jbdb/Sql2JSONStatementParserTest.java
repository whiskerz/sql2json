package de.jbdb;

import static org.fest.assertions.Assertions.assertThat;

import javax.json.JsonObject;
import javax.json.JsonValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

public class Sql2JSONStatementParserTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private Sql2JSONStatementParser classUnderTest;
	
	@Before
	public void before() {
		classUnderTest = new Sql2JSONStatementParser();
	}
	
	@Test
	public void testVisitSelect() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Select());
	}

	@Test
	public void testVisitDelete() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Delete());
	}

	@Test
	public void testVisitUpdate() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Update());
	}

	@Test
	public void testVisitReplace() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Replace());
	}

	@Test
	public void testVisitDrop() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Drop());
	}

	@Test
	public void testVisitTruncate() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Truncate());
	}

	@Test
	public void testVisitCreateIndex() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new CreateIndex());
	}

	@Test
	public void testVisitCreateTable() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new CreateTable());
	}

	@Test
	public void testVisitCreateView() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new CreateView());
	}

	@Test
	public void testVisitAlter() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Alter());
	}

	@Test
	public void testVisitStatements() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		classUnderTest.visit(new Statements());
	}

	@Test
	public void testVisitInsertNull() throws Exception {
		classUnderTest.visit((Insert) null);
		JsonObject result = classUnderTest.returnResult();
		
		assertThat(result).isNull();
	}

	@Test
	public void testVisitInsertEmptyInsert() throws Exception {
		classUnderTest.visit(new Insert());
		JsonObject result = classUnderTest.returnResult();
		
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();
	}

	@Test
	public void testVisitInsertTableOnly() throws Exception {
		Insert insert = new Insert();
		insert.setTable(new Table("testTable"));
		
		classUnderTest.visit(insert);
		JsonObject result = classUnderTest.returnResult();
		
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
		
		JsonValue actual = result.get("testTable");
		assertThat(actual).isNotNull();
	}

}
