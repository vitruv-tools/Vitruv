package edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.ILocalVariable;
import org.junit.Test;

public class RenameParameterTest extends FrameworkTestBase {

	@Test
	public void testUsedInSpec() throws Exception {
		performTest("APDU.java", "APDU", "receiveBytes", null, "bOff");
	}
	
	@Test
	public void testNotUsedInSpec() throws Exception {
		performTest("Applet.java", "Applet", "getShareableInterfaceObject", null, "clientAID");
	}
	
	private void performTest(String cu, String type, String name, String parameterTypes, String params) throws Exception {
		final String testMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		ILocalVariable param = codeElementUtil.getParameter(cu, type, name, parameterTypes, params);
		assertNotNull(param);
		editorManipulator.renameParameter(param);
		waitForSynchronisationToFinish();
		createAndCompareDiff(testMethodName);
	}
	
}
