package edu.kit.ipd.sdq.vitruvius.dsls.response.tests

import edu.kit.ipd.sdq.vitruvius.tests.VitruviusEMFCasestudyTest
import org.junit.runner.Description
import edu.kit.ipd.sdq.vitruvius.dsls.response.executor.AbstractResponseChange2CommandTransforming
import responses.ResponseChange2CommandTransformingProviding
import java.util.function.Supplier

abstract class AbstractResponseTests extends VitruviusEMFCasestudyTest {

	new(Supplier<ResponseChange2CommandTransformingProviding> change2CommandTransformingProvidingSupplier) {
		super(change2CommandTransformingProvidingSupplier);
	}

	protected override Class<?> getChange2CommandTransformerClass() {
		return AbstractResponseChange2CommandTransforming;
	}
	
	/**
	 * Set up test resources and initialize the test model,
	 * acting as a template method for the {@link initializeTestModel} method.
	 * 
	 * @throws Throwable
	 */
	public override void beforeTest(Description description) throws Throwable {
		super.beforeTest(description);
		initializeTestModel();
	}
	
	protected abstract def void initializeTestModel();

}
