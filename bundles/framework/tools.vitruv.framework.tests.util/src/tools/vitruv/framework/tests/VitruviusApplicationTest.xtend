package tools.vitruv.framework.tests

import java.io.IOException
import java.util.Collections
import java.util.List
import org.apache.commons.lang.StringUtils
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.recording.AtomicEMFChangeRecorder
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.VURI

/** 
 * Basic test class for all Vitruvius application tests that require a test
 * project and a VSUM project within the test workspace and use a monitor for
 * recording changes of models. The class creates a test project and a VSUM for
 * each test case within the workspace of the Eclipse test instance.
 * It provides several methods for handling models and their resources, as well
 * as for recording changes, triggering their synchronization and creating and
 * deleting synchronized models.
 * @author langhamm
 * @author Heiko Klare
 */
abstract class VitruviusApplicationTest extends VitruviusUnmonitoredApplicationTest {
	AtomicEMFChangeRecorder changeRecorder

	override final void beforeTest() {
		super.beforeTest
		this.changeRecorder = new AtomicEMFChangeRecorder(true)
		setup
	}

	override final void afterTest() {
		if (changeRecorder.recording) {
			changeRecorder.endRecording
		}
		cleanup
	}

	/** 
	 * This method gets called at the beginning of each test case, after the
	 * test project and VSUM have been initialized. It can be used, for example,
	 * to initialize the test models.
	 */
	def protected abstract void setup()

	/** 
	 * This method gets called at the end of each test case. It can be used for
	 * clean up actions.
	 */
	def protected abstract void cleanup()

	def private void propagateChanges(VURI vuri) {
		val List<TransactionalChange> changes = changeRecorder.endRecording
		val CompositeContainerChange compositeChange = VitruviusChangeFactory::instance.createCompositeChange(changes)
		virtualModel.propagateChange(compositeChange)
	}

	def private void startRecordingChanges(Resource resource) {
		val VURI vuri = VURI::getInstance(resource)
		changeRecorder.beginRecording(vuri, Collections::singleton(resource))
	}

	/** 
	 * Starts recording changes for the model of the given {@link EObject}
	 * @param objectthe {@link EObject} to record changes for
	 */
	def protected void startRecordingChanges(EObject object) {
		startRecordingChanges(object.eResource)
	}

	/** 
	 * The model containing the given {@link EObject} gets saved and changes
	 * that were recorded for that model get propagated. Recording is restarted
	 * afterwards.
	 * @param objectthe {@link EObject} to save and propagated recorded changes
	 * for
	 * @throws IOException
	 */
	def protected void saveAndSynchronizeChanges(EObject object) throws IOException {
		val resource = object.eResource
		EcoreResourceBridge::saveResource(resource)
		propagateChanges(VURI::getInstance(resource))
		startRecordingChanges(resource)
	}

	/** 
	 * A model with the given root element at the given path within the test
	 * project gets created. The changes for the insertion of the root element
	 * are propagated and recording of further changes is started. No call to{@link #startRecordingChanges(EObject)} is necessary.
	 * @param modelPathInProjectpath within project to persist the model at
	 * @param rootElementroot element to persist
	 * @throws IOException
	 */
	def protected void createAndSynchronizeModel(String modelPathInProject, EObject rootElement) throws IOException {
		if (StringUtils::isEmpty(modelPathInProject) || rootElement === null) {
			throw new IllegalArgumentException
		}
		val resource = createModelResource(modelPathInProject)
		startRecordingChanges(resource)
		resource.contents.add(rootElement)
		saveAndSynchronizeChanges(rootElement)
	}

	/** 
	 * The model at the given path is deleted. The old root element is removed
	 * and changes for that removal are propagated.
	 * @param modelPathInProjectpath within project to remove model from
	 * @throws IOException
	 */
	def protected void deleteAndSynchronizeModel(String modelPathInProject) throws IOException {
		if (StringUtils::isEmpty(modelPathInProject)) {
			throw new IllegalArgumentException
		}
		val Resource resource = getModelResource(modelPathInProject)
		val VURI vuri = VURI::getInstance(resource)
		resource.delete(Collections::EMPTY_MAP)
		propagateChanges(vuri)
	}
}
