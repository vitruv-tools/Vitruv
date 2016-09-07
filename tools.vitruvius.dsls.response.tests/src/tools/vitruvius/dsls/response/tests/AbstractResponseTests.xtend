package tools.vitruvius.dsls.response.tests

import tools.vitruvius.framework.tests.VitruviusEMFCasestudyTest
import org.junit.runner.Description
import org.eclipse.emf.common.util.URI
import tools.vitruvius.framework.util.bridges.EcoreResourceBridge
import tools.vitruvius.framework.util.datatypes.VURI
import java.util.Collections
import org.eclipse.emf.ecore.EObject
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.core.resources.ResourcesPlugin
import java.io.File
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static org.junit.Assert.*;
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruvius.framework.change.description.FileChange.FileChangeKind

abstract class AbstractResponseTests extends VitruviusEMFCasestudyTest {

	/**
	 * Set up test resources and initialize the test model,
	 * acting as a template method for the {@link initializeTestModel} method.
	 * 
	 * @throws Throwable
	 */
	public override void beforeTest(Description description) throws Throwable {
		super.beforeTest(description);
		initializeTestModel();
		Logger.rootLogger.level = Level.ERROR;
	}
	
	protected abstract def void initializeTestModel();

	protected def void createAndSychronizeModel(String modelPathInProject, EObject rootElement) {
		if (modelPathInProject.isNullOrEmpty || rootElement == null) {
			throw new IllegalArgumentException();
		}
		val resource = this.resourceSet.createResource(
			URI.createPlatformResourceURI(modelPathInProject.platformModelPath, true)
		);
		resource.contents.add(rootElement);
		EcoreResourceBridge.saveResource(resource);
		synchronizeFileChange(FileChangeKind.CREATE, VURI.getInstance(resource));
		//resource.eAdapters.add(changeRecorder);
		this.changeRecorder.beginRecording(VURI.getInstance(resource), #[rootElement]);
	}
	
	protected def void deleteAndSychronizeModel(String modelPathInProject) {
		if (modelPathInProject.isNullOrEmpty) {
			throw new IllegalArgumentException();
		}
		val resource = this.resourceSet.getResource(
			URI.createPlatformResourceURI(modelPathInProject.platformModelPath, true), true
		);
		resource.delete(Collections.EMPTY_MAP);
		synchronizeFileChange(FileChangeKind.DELETE, VURI.getInstance(resource));
	}
	
	protected def String getPlatformModelPath(String modelPathInProject) {
		return this.currentTestProjectName + "/" + modelPathInProject
	}
	
	protected def String getAbsoluteModelPath(String modelPathInProject) {
		return ResourcesPlugin.workspace.root.location.append("/" + modelPathInProject.platformModelPath).toOSString;
	}
	
	protected def EObject getRoot(String modelPathInProject) {
		return getRoot(modelPathInProject, false);
	}
	
	protected def EObject getRoot(String modelPathInProject, boolean forceReload) {
		return modelPathInProject.getModelResource(forceReload)?.allContents.next;
	}
	
	protected def VURI getModelVURI(String modelPathInProject) {
		return VURI.getInstance(modelPathInProject.platformModelPath);	
	}
	
	protected def Resource getModelResource(String modelPathInProject, boolean forceReload) {
		var resource = this.resourceSet.getResource(modelPathInProject.modelVURI.getEMFUri(), false);
		if (forceReload && resource != null) {
			resource.unload;
		}
		resource = this.resourceSet.getResource(modelPathInProject.modelVURI.getEMFUri(), true);
		return resource;
	}
	
	protected def Resource getModelResource(String modelPathInProject) {
		return getModelResource(modelPathInProject, false);
	}
	
	protected def void assertModelExists(String modelPathInProject) {
		assertTrue(modelPathInProject.absoluteModelPath + " does not exist bust should", 
			new File(modelPathInProject.absoluteModelPath).exists()
		);
	}
	
	protected def void assertModelNotExists(String modelPathInProject) {
		assertFalse(modelPathInProject.absoluteModelPath + " does exist but should not", 
			new File(modelPathInProject.absoluteModelPath).exists()
		);
	}
	
	protected def void assertModelsEqual(String modelNameWithExtension1, String modelNameWithExtension2) {
		val testResourceSet = new ResourceSetImpl();
		val root = testResourceSet.getResource(modelNameWithExtension1.modelVURI.getEMFUri(), true).contents.get(0);
		val root2 = testResourceSet.getResource(modelNameWithExtension2.modelVURI.getEMFUri(), true).contents.get(0);
		assertTrue(EcoreUtil.equals(root, root2));
	}
	
	protected def void saveAndSynchronizeChanges(EObject object) {
		EcoreResourceBridge.saveResource(object.eResource());
		this.triggerSynchronization(VURI.getInstance(object.eResource()));
	}
	
}
