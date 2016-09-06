package edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests.util.EditorManipulator.SaveChangesAction;

public class AddImportTest extends FrameworkTestBase {

	@Test
	public void testBasic() throws Exception {
		ICompilationUnit cu = codeElementUtil.getCompilationUnit("AID.java");
		assertNotNull(cu);
		SaveChangesAction saveAction = editorManipulator.addImport(cu, "\nimport javacard.security.AESKey;\n");
		waitForSynchronisationToFinish();
		saveAction.run();
		createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
}
