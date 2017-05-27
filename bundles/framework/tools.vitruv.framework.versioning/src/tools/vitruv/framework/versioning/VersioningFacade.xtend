package tools.vitruv.framework.versioning

import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import java.util.Map
import java.util.HashMap
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalVirtualModel
import java.util.Collections
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.change.description.TransactionalChange
import java.util.List
import java.util.ArrayList

class VersioningFacade {
	@Data
	static class RecorderPair {
		InternalVirtualModel virtualModel
		AtomicEmfChangeRecorder recorder
	}

	val Map<String, RecorderPair> pathsToRecorders

	new() {
		pathsToRecorders = new HashMap
	}

	def void addPathToRecorded(String path, InternalVirtualModel virtualModel) {
		val recorder = new AtomicEmfChangeRecorder
		val resourceVuri = VURI::getInstance(path)
		val modelInstance = virtualModel.getModelInstance(resourceVuri)
		pathsToRecorders.put(path, new RecorderPair(virtualModel, recorder))
		recorder.beginRecording(resourceVuri, Collections::singleton(modelInstance.resource))
	}

	def List<TransactionalChange> getChanges(String path) {
		val changes = new ArrayList<TransactionalChange>
		val recorder = pathsToRecorders.get(path).getRecorder
		val virtualModel = pathsToRecorders.get(path).getVirtualModel
		virtualModel.executeCommand [|
			changes += recorder.endRecording
			return null
		]
		changes
	}
}
