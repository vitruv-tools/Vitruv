package tools.vitruv.framework.tests

import java.io.IOException
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import org.apache.commons.lang.StringUtils
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.change.recording.impl.AtomicEmfChangeRecorderImpl
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
abstract class VitruviusApplicationTest extends VitruviusUnmonitoredApplicationTest implements ChangeObservable {
	Collection<ChangeObserver> observers
	AtomicEmfChangeRecorder changeRecorder

	override final beforeTest() {
		super.beforeTest
		changeRecorder = new AtomicEmfChangeRecorderImpl(unresolveChanges)
		observers = new ArrayList
		setup
	}

	override final afterTest() {
		if (changeRecorder.recording)
			changeRecorder.endRecording
		cleanup
	}

	override final registerObserver(ChangeObserver observer) {
		observers.add(observer)
	}

	override final unRegisterObserver(ChangeObserver observer) {
		observers.remove(observer)
	}

	override final notifyObservers(VURI vuri, TransactionalChange change) {
		observers.forEach[update(vuri, change)]
	}

	/**
	 * Defines, if recorded changes shall be unresolved and resolved by the change propagation in the VSUM::
	 * This defaults to <code>false</code>. If the used metamodel allows to use the
	 * deresolution mechanism, overwrite this method an return <code>true</code>
	 * @return <code>true</code> if recored changes shall be unresolved, <code>false</code> otherwise
	 */
	def protected boolean unresolveChanges() {
		false
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
		if (StringUtils::isEmpty(modelPathInProject) || rootElement === null)
			throw new IllegalArgumentException
		val resource = modelPathInProject.createModelResource
		resource.startRecordingChanges
		resource.getContents.add(rootElement)
		rootElement.saveAndSynchronizeChanges
	}

	/**
	 * The model at the given path is deleted. The old root element is removed
	 * and changes for that removal are propagated.
	 * @param modelPathInProjectpath within project to remove model from
	 * @throws IOException
	 */
	def protected void deleteAndSynchronizeModel(String modelPathInProject) throws IOException {
		if (StringUtils::isEmpty(modelPathInProject))
			throw new IllegalArgumentException
		val resource = modelPathInProject.getModelResource
		val vuri = VURI::getInstance(resource)
		resource.delete(Collections::EMPTY_MAP)
		vuri.propagateChanges
	}

	def private propagateChanges(VURI vuri) {
		val changes = changeRecorder.endRecording
		changes.forEach([
// TODO PS Check, if CompositeContainerChange creation is necessary
// CompositeContainerChange compositeChange =VitruviusChangeFactory.getInstance.createCompositeChange(Collections.singleton(change));
			virtualModel.propagateChange(it)
// PS NotifyObservers has to be called after change propagation
			notifyObservers(vuri, it)
		])
	}

	def private startRecordingChanges(Resource resource) {
		val vuri = VURI::getInstance(resource)
		changeRecorder.beginRecording(vuri, Collections::singleton(resource))
	}
}
