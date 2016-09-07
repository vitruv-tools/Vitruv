package tools.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.IMethod;
import org.junit.Test;

import tools.vitruvius.applications.jmljava.tests.plugintests.util.EditorManipulator.SaveChangesAction;

public class ChangeMethodBodyTest extends FrameworkTestBase {

	@Test
	public void testAddNonPureStatementToPureMethodUsedInSpec() throws Exception {
		IMethod method = codeElementUtil.getMethod("APDU.java", "APDU", "getBuffer");
		assertNotNull(method);
		String newStatement = "setOutgoing();";
		SaveChangesAction saveAction = editorManipulator.addStatementToMethod(method, newStatement);
		waitForSynchronisationToFinish();
		saveAction.run();
		assertTransformationAborted();
	}
	
	@Test
	public void testAddNonPureStatementToPureMethodNotUsedInSpec() throws Exception {
		IMethod method = codeElementUtil.getMethod("APDU.java", "APDU", "getInBlockSize");
		assertNotNull(method);
		String newStatement = "waitExtension();";
		SaveChangesAction saveAction = editorManipulator.addStatementToMethod(method, newStatement);
		waitForSynchronisationToFinish();
		saveAction.run();
		createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRemoveNonPureStatementWithRemainingNonPureStatements() throws Exception {
		IMethod method = codeElementUtil.getMethod("APDU.java", "APDU", "setOutgoing");
		assertNotNull(method);
		int methodBodyLineToRemove = 3;
		SaveChangesAction saveAction = editorManipulator.removeLineFromMethod(method, methodBodyLineToRemove);
		waitForSynchronisationToFinish();
		saveAction.run();
		createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRemoveNonPureStatementWithoutRemainingNonPureStatements() throws Exception {
		IMethod method = codeElementUtil.getMethod("RMIService.java", "RMIService", "setInvokeInstructionByte");
		assertNotNull(method);
		int methodBodyLineToRemove = 0;
		SaveChangesAction saveAction = editorManipulator.removeLineFromMethod(method, methodBodyLineToRemove);
		waitForSynchronisationToFinish();
		saveAction.run();
		createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
}
