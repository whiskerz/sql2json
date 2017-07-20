package de.jbdb.sql2json.step1.input;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTINSERT;
import static de.jbdb.sql2json.Sql2JSONTestObjects.TESTJSON;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.jbdb.sql2json.cli.Sql2JSONCommandLine;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Sql2JSONCommandLine.class)
@ActiveProfiles(Sql2JSONCommandLine.TEST_PROFILE)
public class Sql2JSONServiceIntegrationTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Autowired
	private Sql2JSONService classUnderTest;

	@Test
	public void simpleSpringSetupTest() throws Exception {

		assertThat(classUnderTest).isNotNull();
	}

	@Test
	public void simpleInsert_HappyPath() throws Exception {

		final File tempFile1 = tempFolder.newFile("tempFile1.sql");
		FileUtils.writeStringToFile(tempFile1, TESTINSERT, Charset.defaultCharset());

		String resultJson = classUnderTest.convertInsertFilesToJson(tempFolder.getRoot().getAbsolutePath());

		assertThat(resultJson).isEqualTo("[\n" + TESTJSON + "\n]");
	}

	@Test
	public void testBug004_UnescapedSpecialCharacter() throws Exception {
		URL testUrl = this.getClass().getResource("/bug004UnescapedSpecialChar");
		File testDir = new File(testUrl.getFile());

		String resultJson = classUnderTest.convertInsertFilesToJson(testDir.getAbsolutePath());

		assertThat(resultJson).isEqualTo("[\n" //
				+ "{\"abforum_posts\":[\n" //
				+ "{\"post_id\":\"12127\",\"topic_id\":\"370\",\"post_datum\":\"1396472394\",\"post_autor\":\"170\",\"post_text\":\"[topic_titel]Mana Static[/topic_titel][Quote=Street Magic errata v 1.4.1.][b]p. 173 Mana Static[/b]\\\\r\\\\nAdd the following sentence between the \\u001e first and second sentence: \\\"Background count rises at a rate of 1 per Combat Turn up to the Force of the spell.\\\"[/Quote]\\\\r\\\\n\\\\r\\\\nDieser Spruch hat mir einfach keine Ruhe gelassen und da musste ich mal ein bisschen recherchieren. So wird dieser Killerspruch ein bisschen moderater.\",\"post_ip\":\"178.7.146.203\",\"post_Edit\":\"0\"}\n" //
				+ "]}\n" //
				+ "]"); //
	}
}
