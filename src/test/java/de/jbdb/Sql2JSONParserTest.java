package de.jbdb;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Sql2JSONParserTest {

	private String forumPostSql = "INSERT INTO `abforum_posts` (`post_id`, `topic_id`, `post_datum`, `post_autor`, `post_text`, `post_ip`, `post_Edit`) VALUES"
			+ "(7026, 365, 1102526329, 176, '[topic_titel]Kommunikation[/topic_titel]Na, gottseidank bin ich bei uns an der Uni der Admin - zumindest bei allen Verwaltungsrechnern.\r\nKann überall ran und alles machen :mrgreen:', '84.129.7.246', 0),"
			+ "(5896, 252, 1089893820, 142, '[topic_titel]Anmeldung von Whiskerz[/topic_titel][b][url=index.php?u=142][i]Whiskerz[/i][/url] hat sich neu im Forum angemeldet und schreibt folgendes über sich:[/b]\r\n            Damit du hier am Forumsleben teilnehmen kannst schreibe bitte etwas über dich.\r\nHhast du schon Vorstellungen mit was für einem Held, welcher Teil Aventuriens, welche Zeit etc. für dich in Frage kommt?\r\nHast du vor, ein Abenteuer zu leiten? Wenn ja, welche Art Abenteuer (wer/was/wann/wo) würdest du vorschlagen?\r\nBist du regelmäßig genug im Internet, um hier teilnehmen zu können? Wer nicht durchschnittlich wenigstens jeden zweiten oder dritten Tag online ist, hält den Abenteuerverlauf zu oft auf. (Ausnahmen sollte vor dem Abenteuereinstieg geklärt werden)\r\nJeder User hat einen Titel unter seinem Namen. Dort sollte der Heldentyp/Profession/Rasse oder eine vergleichbare Information zu deinem Helden stehen. Dies kann aber nur vom Admin geändert werden. Welcher Titel soll bei dir eingetragen werden?\r\nHast du sonst noch etwas zu ergänzen? (Angaben zu dir kannst du nach der Anmeldung in deinem Profil machen, dies ist daher hier nicht unbedingt nötig.', '', 0),"
			+ "(5897, 252, 1089894102, 142, '[topic_titel]Re: Anmeldung von Whiskerz[/topic_titel]Hm gehört überarbeitet die Software ^.^ ich werd mich mal melden wenn mir *wirklich* langweilig ist ... interessiert mich ohnehin so ein Forumsteil ... auf was für nem Server läuft das eigentlich? per dyndns oder so? geforwarded auf Deinen stets laufenden Heimrechner oder wie? d.h. verfügbarkeit nur solange Stromrechnung bezahlt ^.^?\r\n\r\nIm übrigen : mach nen \\\"Home\\\" Button irgendwo rechts hin, damit man auch wieder zurück auf die Startseite kommt egal wo man ist (hatte grad nen Fehler und hin in der Luft)\r\n\r\nUnd mach diese Standardnachricht raus, die da immer beim Anmelden gepostet wird, es reicht zu wissen \\\"... ist jetzt da\\\" und nicht dieses ganze blah-sülz was man da hätte schreiben sollen', '', 0),"
			+ "(5898, 252, 1089894179, 142, '[topic_titel]Re: Re: Anmeldung von Whiskerz[/topic_titel]achja und wo ich schon dabei sind : mach nen Link zu \\\"howtos\\\" um die Funktionen des Forums zu erklären, angefangen von \\\"anmelden\\\", \\\"account verwaltung\\\" , \\\"verfassen von nachrichten\\\" usw. - das wäre ganz praktisch ^.^', '', 0)";

	private String postDatum01 = "1102526329";
	private String postDatum02 = "1089893820";
	private String postDatum03 = "1089894102";
	private String postDatum04 = "1089894179";

	private Sql2JSONParser classUnderTest;

	@Before
	public void before() {
		classUnderTest = new Sql2JSONParser();
	}

	@Test
	public void testConvertNull() throws Exception {
		String json = classUnderTest.parseSQL(null);

		assertThat(json).isEmpty();
	}

	@Test
	public void testConvertEmptyString() throws Exception {
		String json = classUnderTest.parseSQL("");

		assertThat(json).isEmpty();
	}

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testConvertNonInsertStatement() throws Exception {
		exception.expect(IllegalArgumentException.class);

		classUnderTest.parseSQL("DROP TABLE test;");
	}

	@Test
	public void testConvertSimpleInsert() throws Exception {

		String json = classUnderTest.parseSQL("INSERT INTO testTable (testAttribute) VALUES ('testValue');");

		assertThat(json).isNotNull();
		assertThat(json).isNotEmpty();
		assertThat(json).isEqualTo("{\"testTable\":[{\"testAttribute\":\"testValue\"}]}");
	}

	@Test
	public void testConvertSimpleInsert_RemoveAccentuatedQuotes() throws Exception {

		String json = classUnderTest.parseSQL("INSERT INTO `testTable` (`testAttribute`) VALUES ('testValue');");

		assertThat(json).isNotNull();
		assertThat(json).isNotEmpty();
		assertThat(json).isEqualTo("{\"testTable\":[{\"testAttribute\":\"testValue\"}]}");
	}

	@Test
	public void testConvertPostInsertToJSON() throws Exception {
		assertThat(classUnderTest).isNotNull();

		String json = classUnderTest.parseSQL(forumPostSql);

		assertThat(json).isNotNull();
		assertThat(json).contains(postDatum01);
		assertThat(json).contains(postDatum02);
		assertThat(json).contains(postDatum03);
		assertThat(json).contains(postDatum04);
	}

}
