package edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.IMethod;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests.util.EditorManipulator.SaveChangesAction;

public class RemoveMethodTest extends FrameworkTestBase {

	@Test
	public void testBasic() throws Exception {
		IMethod method = codeElementUtil.getMethod("APDU.java", "APDU", "sendBytesLong");
		assertNotNull(method);
		SaveChangesAction saveAction = editorManipulator.removeMethod(method);
		waitForSynchronisationToFinish();
		saveAction.run();
		createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRemoveWithConflict() throws Exception {
		IMethod method = codeElementUtil.getMethod("APDU.java", "APDU", "getBuffer");
		assertNotNull(method);
		SaveChangesAction saveAction = editorManipulator.removeMethod(method);
		waitForSynchronisationToFinish();
		saveAction.run();
		assertTransformationAborted();
	}
	
}
