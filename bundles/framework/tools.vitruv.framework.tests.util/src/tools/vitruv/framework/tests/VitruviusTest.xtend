package tools.vitruv.framework.tests

import java.util.function.Function
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.CoreException
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.rules.TestName
import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.tuid.TuidManager
import static org.junit.Assert.fail

/**
 * Basic test class for all Vitruvius tests that require a test project within
 * the test workspace. The class creates a test project for each test case
 * within the workspace of the Eclipse test instance.
 * @author Heiko Klare
 */
abstract class VitruviusTest {
	@Rule
	public TestName testName = new TestName
	IProject currentTestProject
	Function<String, IProject> testProjectCreator

	@BeforeClass
	def static void setUpAllTests() {
		TestUtil::initializeLogger
	}

	/**
	 * Initializes a test project in the test workspace with the given name,
	 * extended by a timestamp.
	 * @param testName- the name of the test project
	 * @return the created test project
	 */
	def static IProject initializeTestProject(String testName) {
		return try {
			TestUtil::createProject(testName, true)
		} catch (CoreException e) {
			fail("Exception during creation of test project")
			null
		}
	}

	new() {
		testProjectCreator = [s|VitruviusTest::initializeTestProject(s)]
	}

	/**
	 * Initializes each test case and creates the test project in the test
	 * workspace. When overwriting this method, ensure that the super method
	 * gets called.
	 */
	@Before
	def void beforeTest() {
		TuidManager::instance.reinitialize
		val testMethodName = testName.getMethodName
		currentTestProject = testProjectCreator.apply(testMethodName)
	}

	/**
	 * Overwrites the default creator for initializing the test project with the
	 * given one
	 * @param testProjectCreator- the new test project creator
	 */
	def protected void setTestProjectCreator(Function<String, IProject> testProjectCreator) {
		this.testProjectCreator = testProjectCreator
	}

	def protected IProject getCurrentTestProject() {
		currentTestProject
	}
}
