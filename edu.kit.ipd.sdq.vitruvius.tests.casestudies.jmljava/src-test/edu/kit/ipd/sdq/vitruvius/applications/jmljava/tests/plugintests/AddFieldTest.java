package edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.IType;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests.util.EditorManipulator.SaveChangesAction;

public class AddFieldTest extends FrameworkTestBase {

	@Test
	public void testBasic() throws Exception {
		IType clazz = codeElementUtil.getType("APDU.java", "APDU");
		assertNotNull(clazz);
		SaveChangesAction saveAction = editorManipulator.addField(clazz);
		waitForSynchronisationToFinish();
		saveAction.run();
		createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testWithConflict() throws Exception {
		IType clazz = codeElementUtil.getType("Dispatcher.java", "Dispatcher");
		assertNotNull(clazz);
		SaveChangesAction saveAction = editorManipulator.addMember(clazz, "int _totalServices;\n");
		waitForSynchronisationToFinish();
		saveAction.run();
		assertTransformationAborted();
	}
	
}
