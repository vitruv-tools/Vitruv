package tools.vitruv.testutils

import tools.vitruv.testutils.VitruvApplicationTest
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.EObject
import java.nio.file.Path
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import static com.google.common.base.Preconditions.checkArgument
import org.eclipse.emf.common.notify.Notifier
import tools.vitruv.framework.correspondence.CorrespondenceModel

/** 
 * DO NOT USE THIS CALL!
 * This is a temporary fallback class for our existing application tests until we have adapted them to the new
 * framework defined by the {@link VitruvApplicationTest}.
 * It serves as a temporary proxy to be removed after their adaptation and should not be used to implement any 
 * new application tests.
 */
@Deprecated
abstract class LegacyVitruvApplicationTest extends VitruvApplicationTest {
	/** 
	 * Saves the model containing the given {@link EObject} and propagates changes that were recorded for it.
	 * @return a list with the {@link PropagatedChange}s, containing the original and consequential changes.
	 */
	@Deprecated
	def protected List<PropagatedChange> saveAndSynchronizeChanges(EObject object) {
		return saveAndSynchronizeChanges(object.eResource)
	}

	/** 
	 * Saves the provided {@link Resource} and propagates changes that were recorded for it.
	 * @return a list with the {@link PropagatedChange}s, containing the original * and consequential changes.
	 */
	@Deprecated
	def protected List<PropagatedChange> saveAndSynchronizeChanges(Resource resource) {
		changeRecorder.endRecording()
		var compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changeRecorder.changes)
		compositeChange.changedResource?.save(emptyMap)
		var propagationResult = virtualModel.propagateChange(compositeChange)
		changeRecorder.beginRecording()
		return propagationResult
	}

	/** 
	 * Creates a model with the given root element at the given path within the test project.
	 * Propagates the changes for inserting the root element.
	 * @param modelPathInProject path within project to persist the model at
	 * @param rootElement        root element to persist
	 */
	@Deprecated
	def protected void createAndSynchronizeModel(String modelPathInProject, EObject rootElement) {
		var Resource resource = resourceAt(Path.of(modelPathInProject))
		startRecordingChanges(resource)
		resource.contents += rootElement
		saveAndSynchronizeChanges(rootElement)
	}

	/** 
	 * Deletes the model at the provided {@code modelPathInProject}. Propagates changes for removing the root elements.
	 */
	@Deprecated
	def protected List<PropagatedChange> deleteAndSynchronizeModel(String modelPathInProject) {
		val resource = resourceAt(Path.of(modelPathInProject))
		resource.delete(emptyMap())
		val result = saveAndSynchronizeChanges(resource)
		stopRecordingChanges(resource)
		return result;
	}

	override CorrespondenceModel getCorrespondenceModel() { 
		virtualModel.correspondenceModel
	}
	
	@Deprecated
	def private void startRecordingChanges(Notifier notifier) {
		checkArgument(notifier !== null, '''The object to record changes of is null!''')
		this.changeRecorder.addToRecording(notifier)
	}
	
	@Deprecated
	def protected void startRecordingChanges(EObject object) {
		checkArgument(object !== null, '''The object to record changes of is null!''')
		object.eResource.startRecordingChanges
	}

	@Deprecated	
	def private void stopRecordingChanges(Notifier notifier) {
		checkArgument(notifier !== null, '''The object to stop recording changes of is null!''')
		this.changeRecorder.removeFromRecording(notifier)
	}
	
	@Deprecated
	def protected void stopRecordingChanges(EObject object) {
		checkArgument(object !== null, '''The object to stop recording changes of is null!''')
		object.eResource.stopRecordingChanges
	}
	
}
