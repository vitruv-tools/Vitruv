package edu.kit.ipd.sdq.vitruvius.dsls.response.tests

import edu.kit.ipd.sdq.vitruvius.tests.VitruviusEMFCasestudyTest
import org.junit.runner.Description
import java.util.function.Supplier
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import java.util.Collections
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding
import org.apache.log4j.Logger
import org.apache.log4j.Level
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer.ChangeSynchronizerImpl
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.core.resources.ResourcesPlugin
import java.io.File
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.eclipse.emf.ecore.util.EcoreUtil

abstract class AbstractResponseTests extends VitruviusEMFCasestudyTest {

	new(Supplier<? extends Change2CommandTransformingProviding> change2CommandTransformingProvidingSupplier) {
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
		Logger.rootLogger.level = Level.DEBUG;
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
	
	protected override setUserInteractor(UserInteracting newUserInteracting,
		ChangeSynchronizerImpl changeSynchronizerImpl) throws Throwable {
		// Do nothing
	}
	
	protected def String getPlatformModelPath(String modelNameWithExtension) {
		return this.currentTestProjectName + "/model/" + modelNameWithExtension
	}
	
	protected def String getAbsoluteModelPath(String modelNameWithExtension) {
		return ResourcesPlugin.workspace.root.location.append("/" + modelNameWithExtension.platformModelPath).toOSString;
	}
	
	protected def EObject getRoot(String modelNameWithExtension) {
		return getRoot(modelNameWithExtension, false);
	}
	
	protected def EObject getRoot(String modelNameWithExtension, boolean forceReload) {
		return modelNameWithExtension.getModelResource(forceReload)?.allContents.next;
	}
	
	protected def VURI getModelVURI(String modelNameWithExtension) {
		return VURI.getInstance(modelNameWithExtension.platformModelPath);	
	}
	
	protected def Resource getModelResource(String modelNameWithExtension, boolean forceReload) {
		var resource = this.resourceSet.getResource(modelNameWithExtension.modelVURI.getEMFUri(), false);
		if (forceReload && resource != null) {
			resource.unload;
		}
		resource = this.resourceSet.getResource(modelNameWithExtension.modelVURI.getEMFUri(), true);
		return resource;
	}
	
	protected def Resource getModelResource(String modelNameWithExtension) {
		return getModelResource(modelNameWithExtension, false);
	}
	
	protected def void assertModelExists(String modelNameWithExtension) {
		assertTrue(new File(modelNameWithExtension.absoluteModelPath).exists());
	}
	
	protected def void assertModelNotExists(String modelNameWithExtension) {
		assertFalse(new File(modelNameWithExtension.absoluteModelPath).exists());
	}
	
	protected def void assertModelsEqual(String modelNameWithExtension1, String modelNameWithExtension2) {
		val testResourceSet = new ResourceSetImpl();
		val root = testResourceSet.getResource(modelNameWithExtension1.modelVURI.getEMFUri(), true).contents.get(0);
		val root2 = testResourceSet.getResource(modelNameWithExtension2.modelVURI.getEMFUri(), true).contents.get(0);
		assertTrue(EcoreUtil.equals(root, root2));
	}
}
