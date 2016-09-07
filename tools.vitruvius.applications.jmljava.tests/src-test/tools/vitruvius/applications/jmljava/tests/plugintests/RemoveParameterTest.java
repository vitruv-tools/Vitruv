package tools.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.ILocalVariable;
import org.junit.Test;

import tools.vitruvius.applications.jmljava.tests.plugintests.util.EditorManipulator.SaveChangesAction;

public class RemoveParameterTest extends FrameworkTestBase {

	@Test
	public void testBasic() throws Exception {
		ILocalVariable parameter = codeElementUtil.getParameter("Applet.java", "Applet", "install", "bOffset");
		assertNotNull(parameter);
		SaveChangesAction saveAction = editorManipulator.removeParameter(parameter);
		waitForSynchronisationToFinish();
		saveAction.run();
		createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRemoveWithConflict() throws Exception {
		ILocalVariable parameter = codeElementUtil.getParameter("APDU.java", "APDU", "receiveBytes", "bOff");
		assertNotNull(parameter);
		SaveChangesAction saveAction = editorManipulator.removeParameter(parameter);
		waitForSynchronisationToFinish();
		saveAction.run();
		assertTransformationAborted();
	}
	
}
