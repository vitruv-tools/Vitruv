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
 * Basic test class for all Vitruvius tests that require a test project within the test workspace. 
 * The class creates a test project for each test case within the workspace of the Eclipse test instance.
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

	/**
	 * Initializes each test case and creates the test project in the test workspace. 
	 * When overwriting this method, ensure that the super method gets called.  
	 */
	@Before
	public void beforeTest() {
		TuidManager.getInstance().reinitialize();
		String testMethodName = testName.getMethodName();
		this.currentTestProject = initializeTestProject(testMethodName);
	}

	/**
	 * Initializes a test project in the test workspace with the given name,
	 * extended by a timestamp.
	 * 
	 * @param testName
	 *            - the name of the test project
	 * @return the created test project
	 */
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