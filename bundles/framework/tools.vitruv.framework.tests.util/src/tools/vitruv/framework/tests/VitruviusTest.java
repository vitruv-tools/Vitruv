package tools.vitruv.framework.tests;

import static org.junit.Assert.fail;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.tuid.TuidManager;

/**
 * Basic test class for all Vitruvius tests.
 * Creates a test project for each test case.
 * 
 * @author Heiko Klare
 */
public class VitruviusTest {
	@Rule
	public TestName testName = new TestName();
	
	private IProject currentTestProject;

	@BeforeClass
	public static void setUpAllTests() {
		TestUtil.initializeLogger();
	}

	public VitruviusTest() {
		super();
	}

	@Before
	public void beforeTest() {
		TuidManager.getInstance().reinitialize();
		String testMethodName = testName.getMethodName();
		this.currentTestProject = initializeTestProject(testMethodName);
	}
	
	protected IProject initializeTestProject(final String testName) {
		IProject testProject = null;
		try {
			testProject = TestUtil.createProject(testName);
		} catch (CoreException e) {
			fail("Exception during creation of test project");
		}
		return testProject;
	}

	protected IProject getCurrentTestProject() {
		return currentTestProject;
	}

}