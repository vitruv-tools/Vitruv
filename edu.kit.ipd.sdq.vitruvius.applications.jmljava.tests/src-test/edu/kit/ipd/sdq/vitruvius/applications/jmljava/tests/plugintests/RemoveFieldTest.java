package edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.eclipse.jdt.core.IField;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests.util.EditorManipulator.SaveChangesAction;

public class RemoveFieldTest extends FrameworkTestBase {

	@Ignore
	@Test
	public void testBasic() throws Exception {
		fail("No field can be removed without producing syntax errors.");
	}
	
	@Test
	public void testRemoveWithConflict() throws Exception {
		IField field = codeElementUtil.getField("APDU.java", "APDU", "_Lc");
		assertNotNull(field);
		SaveChangesAction saveAction = editorManipulator.removeField(field);
		waitForSynchronisationToFinish();
		saveAction.run();
		assertTransformationAborted();
	}
}
