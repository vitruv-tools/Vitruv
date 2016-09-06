package edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.IField;
import org.junit.Test;


public class RenameFieldTest extends FrameworkTestBase {

	@Test
	public void testRegularFieldUsedInSpec() throws Exception {
		performTest("AID.java", "AID", "_theAID", null, false);
	}
	
	@Test
	public void testRegularFieldNotUsedInSpec() throws Exception {
		performTest("APDU.java", "APDU", "_instance", null, false);
	}
	
	@Test
	public void testRegularFieldNotUsedInSpecNameClash() throws Exception {
		performTest("Dispatcher.java", "Dispatcher", "_phases", "_totalServices", true);
	}
	
	private void performTest(String cu, String type, String name, String newName, boolean assertAborted) throws Exception {
		final String testMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		IField field = codeElementUtil.getField(cu, type, name);
		assertNotNull(field);
		if (newName == null) {
			editorManipulator.renameField(field);			
		} else {
			editorManipulator.renameField(field, newName);
		}
		waitForSynchronisationToFinish();
		if (assertAborted) {
			assertTransformationAborted();
		} else {
			createAndCompareDiff(testMethodName);			
		}
	}
	
}
