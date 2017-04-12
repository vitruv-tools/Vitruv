package tools.vitruv.framework.tests

import tools.vitruv.framework.tests.VitruviusEMFCasestudyTest

/**
 * This is the test class to be extended by tests for applications.
 * The {@link initializeTestModel} method has to define the initialization of a source test model.
 * After changes that have to be synchronized, {@link saveAndSynchronizeChanges} has to be called.
 */
abstract class VitruviusChangePropagationTest extends VitruviusEMFCasestudyTest {

	/**
	 * Set up test resources and initialize the test model,
	 * acting as a template method for the {@link initializeTestModel} method.
	 * 
	 * @throws Throwable
	 */
	public override void beforeTest() throws Throwable {
		super.beforeTest();
		initializeTestModel();
	}
	
	protected abstract def void initializeTestModel();
	
	
}
