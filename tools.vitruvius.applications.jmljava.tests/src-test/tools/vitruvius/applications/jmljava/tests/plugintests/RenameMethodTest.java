package tools.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.eclipse.jdt.core.IMethod;
import org.junit.Ignore;
import org.junit.Test;

public class RenameMethodTest extends FrameworkTestBase {

	@Test
	public void testRegularClassUsedInSpec() throws Exception {
		performTest("APDU.java", "APDU", "getBuffer", null);
	}
	
	@Test
	public void testRegularClassNotUsedInSpec() throws Exception {
		performTest("APDU.java", "APDU", "getNAD", null);
	}
	
	@Ignore
	@Test
	public void testRegularClassNameClash() throws Exception {
		fail("No model methods available in test project.");
	}
	
	@Ignore
	@Test
	public void testAbstractClassUsedInSpec() throws Exception {
		fail("No method of an abstract class is used in spec in test project.");
	}
	
	@Test
	public void testAbstractClassNotUsedInSpec() throws Exception {
		performTest("Applet.java", "Applet", "install", null);
	}
	
	@Test
	public void testAbstractClassAbstractMethod() throws Exception {
		performTest("Applet.java", "Applet", "process", null);
	}
	
	private void performTest(String cu, String type, String name, String params) throws Exception {
		final String testMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		IMethod method = codeElementUtil.getMethod(cu, type, name, params);
		assertNotNull(method);
		editorManipulator.renameMethod(method);
		waitForSynchronisationToFinish();
		createAndCompareDiff(testMethodName);
	}
	
}
