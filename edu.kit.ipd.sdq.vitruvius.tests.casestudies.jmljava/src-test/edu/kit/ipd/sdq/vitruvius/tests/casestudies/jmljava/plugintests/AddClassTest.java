package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests.util.EditorManipulator.SaveChangesAction;

public class AddClassTest extends FrameworkTestBase {

	@Ignore // internal OpenJML bug
	@Test
	public void testBasic() throws Exception {
		ICompilationUnit cu = codeElementUtil.getCompilationUnit("AID.java");
		assertNotNull(cu);
		SaveChangesAction saveAction = editorManipulator.addSecondaryClass(cu);
		waitForSynchronisationToFinish();
		saveAction.run();
		createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testWithConflict() throws Exception {
		ICompilationUnit cu = codeElementUtil.getCompilationUnit("AID.java");
		assertNotNull(cu);
		SaveChangesAction saveAction = editorManipulator.addSecondaryClass(cu, "AID");
		waitForSynchronisationToFinish();
		saveAction.run();
		assertTransformationAborted();
	}
	
}
