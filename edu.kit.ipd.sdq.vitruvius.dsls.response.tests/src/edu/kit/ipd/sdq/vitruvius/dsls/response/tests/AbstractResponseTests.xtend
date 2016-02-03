package edu.kit.ipd.sdq.vitruvius.dsls.response.tests

import edu.kit.ipd.sdq.vitruvius.tests.VitruviusEMFCasestudyTest
import org.junit.runner.Description
import responses.ResponseChange2CommandTransformingProviding
import java.util.function.Supplier
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import java.util.Collections
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseChange2CommandTransforming

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

	protected def void createAndSychronizeModel(String modelName, EObject rootElement) {
		if (modelName.isNullOrEmpty || rootElement == null) {
			throw new IllegalArgumentException();
		}
		val resource = this.resourceSet.createResource(
			URI.createPlatformResourceURI(modelName.platformModelPath, true)
		);
		resource.contents.add(rootElement);
		EcoreResourceBridge.saveResource(resource);
		synchronizeFileChange(FileChangeKind.CREATE, VURI.getInstance(resource));
		this.changeRecorder.beginRecording(#[rootElement]);
	}
	
	protected def void deleteAndSychronizeModel(String modelName) {
		if (modelName.isNullOrEmpty) {
			throw new IllegalArgumentException();
		}
		val resource = this.resourceSet.getResource(
			URI.createPlatformResourceURI(modelName.platformModelPath, true), true
		);
		resource.delete(Collections.EMPTY_MAP);
		synchronizeFileChange(FileChangeKind.DELETE, VURI.getInstance(resource));
	}
	
	protected abstract def String getPlatformModelPath(String modelName);
}
