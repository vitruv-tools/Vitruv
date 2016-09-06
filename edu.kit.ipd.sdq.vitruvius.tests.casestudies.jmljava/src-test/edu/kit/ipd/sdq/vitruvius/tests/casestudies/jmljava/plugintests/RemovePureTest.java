package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests;

import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier;

public class RemovePureTest extends JMLModifierTestBase {

	@Test
	public void testBasic() throws Exception {
		performTest("APDU.jml", "APDU", "getInBlockSize", false);
	}
	
	@Test
	public void testConflict() throws Exception {
		performTest("APDU.jml", "APDU", "getProtocol", true);
	}
	
	private void performTest(final String fileName, final String typeName, final String methodName, boolean abortExpected) throws Exception {
		performTest(fileName, typeName, methodName, false, abortExpected);
	}

	@Override
	protected JMLSpecMemberModifier getJMLModifier() {
		return JMLSpecMemberModifier.PURE;
	}
	
}
