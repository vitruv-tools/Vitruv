package tools.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.eclipse.jdt.core.IType;
import org.junit.Ignore;
import org.junit.Test;

import tools.vitruvius.applications.jmljava.tests.plugintests.util.EditorManipulator.SaveChangesAction;

public class AddMethodTest extends FrameworkTestBase {

	@Test
	public void testBasic() throws Exception {
		IType clazz = codeElementUtil.getType("APDU.java", "APDU");
		assertNotNull(clazz);
		SaveChangesAction saveAction = editorManipulator.addMethod(clazz);
		waitForSynchronisationToFinish();
		saveAction.run();
		createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Ignore
	@Test
	public void testWithConflict() throws Exception {
		fail("There are no model methods in the evaluation project.");
	}
	
}
