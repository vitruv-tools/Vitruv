package tools.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.IMethod;
import org.junit.Test;

import tools.vitruvius.applications.jmljava.tests.plugintests.util.EditorManipulator.SaveChangesAction;

public class AddParameterTest extends FrameworkTestBase {

	@Test
	public void testBasic() throws Exception {
		IMethod method = codeElementUtil.getMethod("APDU.java", "APDU", "getOutBlockSize");
		assertNotNull(method);
		SaveChangesAction saveAction = editorManipulator.addParameter(method);
		waitForSynchronisationToFinish();
		saveAction.run();
		createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
}
