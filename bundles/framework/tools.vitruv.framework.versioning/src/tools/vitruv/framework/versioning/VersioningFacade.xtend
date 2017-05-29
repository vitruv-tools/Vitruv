package tools.vitruv.framework.versioning

import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.List
import java.util.Map
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.tests.ChangeObserver
import org.eclipse.xtend.lib.annotations.Accessors

class VersioningFacade implements ChangeObserver {
	val InternalVirtualModel virtualModel
	@Accessors(PUBLIC_GETTER)
	val List<List<TransactionalChange>> changeUpdates

	val Map<String, AtomicEmfChangeRecorder> pathsToRecorders

	new(InternalVirtualModel virtualModel) {
		pathsToRecorders = new HashMap
		this.virtualModel = virtualModel
		changeUpdates = new ArrayList<List<TransactionalChange>>
	}

	def void addPathToRecorded(String path) {
		val recorder = new AtomicEmfChangeRecorder
		val resourceVuri = VURI::getInstance(path)
		val modelInstance = virtualModel.getModelInstance(resourceVuri)
		pathsToRecorders.put(path, recorder)
		recorder.beginRecording(resourceVuri, Collections::singleton(modelInstance.resource))
	}

	def void clearChangeUpdates() {
		changeUpdates.clear
	}
	
	def List<TransactionalChange> getChanges(String path) {
		val changes = new ArrayList<TransactionalChange>
		val recorder = pathsToRecorders.get(path)
		virtualModel.executeCommand [|
			changes += recorder.endRecording
			return null
		]
		changes
	}

	override update(List<TransactionalChange> changes) {
		changeUpdates.add(changes)
	}

}
