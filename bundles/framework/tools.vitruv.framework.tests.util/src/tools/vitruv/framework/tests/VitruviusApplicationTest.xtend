package tools.vitruv.framework.tests

import edu.kit.ipd.sdq.commons.util.java.lang.StringUtil
import java.io.IOException
import java.util.Collections
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
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
abstract class VitruviusApplicationTest extends VitruviusUnmonitoredApplicationTest {
	Map<VURI, AtomicEmfChangeRecorder> uriToChangeRecorder

	override beforeTest() {
		super.beforeTest
		uriToChangeRecorder = newHashMap
		setup
	}

	override afterTest() {
		uriToChangeRecorder.filter[a, recorder|recorder.recording].forEach[a, recorder|recorder.endRecording]
		cleanup
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
	 * @return a list with the {@link PropagatedChange}s, containing the original and consequential changes
	 * @throws IOException
	 */
	def protected List<PropagatedChange> saveAndSynchronizeChanges(EObject object) throws IOException {
		val resource = object.eResource
		EcoreResourceBridge::saveResource(resource)
		val List<PropagatedChange> result = propagateChanges(VURI::getInstance(resource))
		startRecordingChanges(resource)
		return result
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
		if (StringUtil::isEmpty(modelPathInProject) || rootElement === null)
			throw new IllegalArgumentException
		val resource = modelPathInProject.createModelResource
		resource.startRecordingChanges
		resource.contents += rootElement
		saveAndSynchronizeChanges(rootElement)
	}

	/**
	 * The model at the given path is deleted. The old root element is removed
	 * and changes for that removal are propagated.
	 * @param modelPathInProjectpath within project to remove model from
	 * @throws IOException
	 */
	def protected void deleteAndSynchronizeModel(String modelPathInProject) throws IOException {
		if (StringUtil::isEmpty(modelPathInProject)) {
			throw new IllegalArgumentException
		}
		val Resource resource = getModelResource(modelPathInProject)
		val VURI vuri = VURI::getInstance(resource)
		resource.delete(Collections.EMPTY_MAP)
		vuri.propagateChanges
	}

	private def List<PropagatedChange> propagateChanges(VURI vuri) {
		val recorder = uriToChangeRecorder.get(vuri)
		recorder.endRecording
		val changes = if (unresolveChanges)
				recorder.unresolvedChanges
			else
				recorder.resolvedChanges
		val ChangeCloner changeCloner = new ChangeCloner
		val copiedChanges = changes.map [
			changeCloner.clone(it)
		]
		val testForUnresolved = [List<? extends VitruviusChange> l|l.map[EChanges].flatten.exists[resolved]]
		if (unresolveChanges && (testForUnresolved.apply(changes) || testForUnresolved.apply(copiedChanges)))
			throw new IllegalStateException
		val compositeChange = VitruviusChangeFactory::instance.createCompositeChange(changes)
		val result = virtualModel.propagateChange(compositeChange)
		return result
	}

	private def startRecordingChanges(Resource resource) {
		val vuri = VURI::getInstance(resource)
		val AtomicEmfChangeRecorder recorder = new AtomicEmfChangeRecorderImpl(unresolveChanges)
		uriToChangeRecorder.put(vuri, recorder)
		recorder.beginRecording(vuri, Collections::singleton(resource))
	}
}
