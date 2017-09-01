package tools.vitruv.framework.tests;

import java.io.File;
import java.util.function.Function;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.tuid.TuidManager;

/**
 * Basic test class for all Vitruvius tests that require a test project within
 * the test workspace. The class creates a test project for each test case
 * within the workspace of the Eclipse test instance.
 * 
 * @author Heiko Klare
 */
public abstract class VitruviusTest {
	@Rule
	public TestName testName = new TestName();

	protected static File workspace;
	private File folder;
	private Function<String, File> testProjectCreator;

	@BeforeClass
	public static void setUpAllTests() {
		TestUtil.initializeLogger();
		workspace = TestUtil.createTestWorkspace();
	}

	public VitruviusTest() {
		this.testProjectCreator = VitruviusTest::initializeTestProject;
	}

	/**
	 * Overwrites the default creator for initializing the test project with the
	 * given one
	 * 
	 * @param testProjectCreator
	 *            - the new test project creator
	 */
	protected void setTestProjectCreator(Function<String, File> testProjectCreator) {
		this.testProjectCreator = testProjectCreator;
	}

	/**
	 * Initializes each test case and creates the test project in the test
	 * workspace. When overwriting this method, ensure that the super method
	 * gets called.
	 */
	@Before
	public void beforeTest() {
		TuidManager.getInstance().reinitialize();
		String testMethodName = testName.getMethodName();
		this.folder = testProjectCreator.apply(testMethodName);
	}

	/**
	 * Initializes a test project with the given name extended by a timestamp in the temporary files folder or in the folder
	 * specified by the VM argument.
	 * 
	 * @param testName
	 *            - the name of the test project
	 * @return the created test project folder
	 */
	private static File initializeTestProject(final String testName) {
		return TestUtil.createProjectFolder(workspace, testName, true);
	}

	protected File getCurrentTestProjectFolder() {
		return folder;
	}

}