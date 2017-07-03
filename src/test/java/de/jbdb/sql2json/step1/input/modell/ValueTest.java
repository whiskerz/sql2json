package de.jbdb.sql2json.step1.input.modell;

import static de.jbdb.sql2json.Sql2JSONTestObjects.TEST_VALUE1;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.jbdb.sql2json.step1.input.modell.Value;

public class ValueTest {

	@Test
	public void testCreateValue_Whitespace_Quotes() throws Exception {
		Value value = new Value("	'" + TEST_VALUE1 + "  '      ");

		assertThat(value.toString()).isEqualTo(TEST_VALUE1);
	}
}
