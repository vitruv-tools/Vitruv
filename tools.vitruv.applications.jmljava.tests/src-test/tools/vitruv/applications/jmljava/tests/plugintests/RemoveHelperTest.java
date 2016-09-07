package tools.vitruv.applications.jmljava.tests.plugintests;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import tools.vitruv.domains.jml.language.jML.JMLSpecMemberModifier;

public class RemoveHelperTest extends JMLModifierTestBase {

	@Ignore
	@Test
	public void testBasic() {
		fail("There are no methods declared as helper methods in the evaluation project.");
	}
	
	@Ignore
	@Test
	public void testWithConflict() {
		fail("There are no methods declared as helper methods in the evaluation project.");
	}

	@Override
	protected JMLSpecMemberModifier getJMLModifier() {
		return JMLSpecMemberModifier.HELPER;
	}
	
}
