package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTINSERT;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_COLUMN;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_TABLE;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE1;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE2;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InsertStatementTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void constructorWithNullParameter_ThrowsIllegalArgumentException() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(org.hamcrest.CoreMatchers.containsString("parameter was null"));

		new InsertStatement(null);
	}

	@Test
	public void constructorWithEmptyParameter_ThrowsIllegalArgumentException() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(org.hamcrest.CoreMatchers.containsString("parameter was empty"));

		new InsertStatement("");
	}

	@Test
	public void constructorWithSimpleInsert() throws Exception {

		InsertStatement insertStatement = new InsertStatement(String.join(" ", TESTINSERT));

		assertThat(insertStatement.getTableName()).isEqualTo(new TableName(TEST_TABLE));
		assertThat(insertStatement.getColumnNames()).isNotNull();
		assertThat(insertStatement.getColumnNames()).hasSize(1);
		assertThat(insertStatement.getColumnNames().get(0)).isEqualTo(new ColumnName(TEST_COLUMN));

		List<Row> rows = insertStatement.getValueRows();
		assertThat(rows).describedAs("Rows").isNotNull();
		assertThat(rows).describedAs("Rows").isNotEmpty();
		assertThat(rows).hasSize(1);

		List<Value> values = rows.get(0).getValues();
		assertThat(values).describedAs("Values").isNotNull();
		assertThat(values).describedAs("Values").isNotEmpty();
		assertThat(values).hasSize(2);
		assertThat(values).contains(new Value(TEST_VALUE1), new Value(TEST_VALUE2));
	}

	@Test
	public void insertWithLowerCaseLetters() throws Exception {
		InsertStatement insertStatement = new InsertStatement(
				"insert into " + TEST_TABLE + "(" + TEST_COLUMN + ") values (" + TEST_VALUE1 + ");");

		assertThat(insertStatement.getTableName()).isEqualTo(new TableName(TEST_TABLE));
		assertThat(insertStatement.getColumnNames()).isNotNull();
		assertThat(insertStatement.getColumnNames()).hasSize(1);
		assertThat(insertStatement.getColumnNames().get(0)).isEqualTo(new ColumnName(TEST_COLUMN));

		List<Row> rows = insertStatement.getValueRows();
		assertThat(rows).describedAs("Rows").isNotNull();
		assertThat(rows).describedAs("Rows").isNotEmpty();
		assertThat(rows).hasSize(1);

		List<Value> values = rows.get(0).getValues();
		assertThat(values).describedAs("Values").isNotNull();
		assertThat(values).describedAs("Values").isNotEmpty();
		assertThat(values).hasSize(1);
		assertThat(values).contains(new Value(TEST_VALUE1));
	}

	@Test
	public void constructorWithComplexInsert() throws Exception {
		String tableName = "abforum_posts";

		String columnPostId = "post_id";
		String columnTopicId = "topic_id";
		String columnDotum = "post_datum";
		String columnAutor = "post_autor";
		String columnText = "post_text";
		String columnIp = "post_ip";
		String columnEdit = "post_Edit";

		String postId1 = "5888";
		String topicId1 = "250";
		String postDatum1 = "1089889278";
		String postAutor1 = "153";
		String postText1 = "[topic_titel]Gero - Elf - KiMagier[/topic_titel]Alles was Deinen Charbetrifft, hier rein :). Hier sehe ich häufiger nach als in meinen Mails.[geaendert:1090078601,1]";
		String ip1 = "";
		String edit1 = "0";

		String postId2 = "5893";
		String topicId2 = "252";
		String postDatum2 = "1089891839";
		String postAutor2 = "1";
		String postText2 = "[topic_titel]Neuling[/topic_titel]";
		String ip2 = "";
		String edit2 = "0";

		String postId3 = "5894";
		String topicId3 = "252";
		String postDatum3 = "1089892743";
		String postAutor3 = "141";
		String postText3 = "[topic_titel]Anmeldung von Falk Fuxfell[/topic_titel][b][url=index.php?u=141][i]Falk Fuxfell[/i][/url] hat sich neu im Forum angemeldet und schreibt folgendes über sich:[/b]\r\n            Damit du hier am Forumsleben teilnehmen kannst schreibe bitte etwas über dich.\r\nHhast du schon Vorstellungen mit was für einem Held, welcher Teil Aventuriens, welche Zeit etc. für dich in Frage kommt?\r\nHast du vor, ein Abenteuer zu leiten? Wenn ja, welche Art Abenteuer (wer/was/wann/wo) würdest du vorschlagen?\r\nBist du regelmäßig genug im Internet, um hier teilnehmen zu können? Wer nicht durchschnittlich wenigstens jeden zweiten oder dritten Tag online ist, hält den Abenteuerverlauf zu oft auf. (Ausnahmen sollte vor dem Abenteuereinstieg geklärt werden)\r\nJeder User hat einen Titel unter seinem Namen. Dort sollte der Heldentyp/Profession/Rasse oder eine vergleichbare Information zu deinem Helden stehen. Dies kann aber nur vom Admin geändert werden. Welcher Titel soll bei dir eingetragen werden?\r\nHast du sonst noch etwas zu ergänzen? (Angaben zu dir kannst du nach der Anmeldung in deinem Profil machen, dies ist daher hier nicht unbedingt nötig.";
		String ip3 = "";
		String edit3 = "0";
		String complexInsert = "INSERT INTO `" + tableName + "` (`" + columnPostId + "`, `" + columnTopicId + "`, `"
				+ columnDotum + "`, `" + columnAutor + "`, `" + columnText + "`, `" + columnIp + "`, `" + columnEdit
				+ "`) VALUES" + "(" + postId1 + ", " + topicId1 + ", " + postDatum1 + ", " + postAutor1 + ", '"
				+ postText1 + "', '" + ip1 + "', " + edit1 + ")," + "(" + postId2 + ", " + topicId2 + ", " + postDatum2
				+ ", " + postAutor2 + ", '" + postText2 + "', '" + ip2 + "', " + edit2 + ")," + "(" + postId3 + ", "
				+ topicId3 + ", " + postDatum3 + ", " + postAutor3 + ", '" + postText3 + "', '" + ip3 + "', " + edit3
				+ ");";

		InsertStatement insertStatement = new InsertStatement(complexInsert);
		assertThat(insertStatement.getTableName()).isEqualTo(new TableName(tableName));
		assertThat(insertStatement.getColumnNames()).isNotNull();
		assertThat(insertStatement.getColumnNames()).hasSize(7);
		assertThat(insertStatement.getColumnNames()).contains(new ColumnName(columnPostId),
				new ColumnName(columnTopicId), new ColumnName(columnDotum), new ColumnName(columnAutor),
				new ColumnName(columnText), new ColumnName(columnIp), new ColumnName(columnEdit));

		List<Row> rows = insertStatement.getValueRows();
		assertThat(rows).describedAs("Rows").isNotNull();
		assertThat(rows).describedAs("Rows").isNotEmpty();
		assertThat(rows).hasSize(3);

		assertThat(rows.stream().map(row -> row.getValues().get(0)).collect(Collectors.toList()))
				.containsExactlyInAnyOrder(new Value(postId1), new Value(postId2), new Value(postId3));

		assertThat(rows.stream().map(row -> row.getValues().get(1)).collect(Collectors.toList()))
				.containsExactlyInAnyOrder(new Value(topicId1), new Value(topicId2), new Value(topicId3));

		assertThat(rows.stream().map(row -> row.getValues().get(2)).collect(Collectors.toList()))
				.containsExactlyInAnyOrder(new Value(postDatum1), new Value(postDatum2), new Value(postDatum3));

		assertThat(rows.stream().map(row -> row.getValues().get(3)).collect(Collectors.toList()))
				.containsExactlyInAnyOrder(new Value(postAutor1), new Value(postAutor2), new Value(postAutor3));

		assertThat(rows.stream().map(row -> row.getValues().get(4)).collect(Collectors.toList()))
				.containsExactlyInAnyOrder(new Value(postText1), new Value(postText2), new Value(postText3));

		assertThat(rows.stream().map(row -> row.getValues().get(5)).collect(Collectors.toList()))
				.containsExactlyInAnyOrder(new Value(ip1), new Value(ip2), new Value(ip3));

		assertThat(rows.stream().map(row -> row.getValues().get(6)).collect(Collectors.toList()))
				.containsExactlyInAnyOrder(new Value(edit1), new Value(edit2), new Value(edit3));
	}

}
