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
import org.apache.log4j.Logger
import org.apache.log4j.Level

class VersioningFacade implements ChangeObserver {
	val logger = Logger::getLogger(VersioningFacade)
	val InternalVirtualModel virtualModel

	val Map<VURI, AtomicEmfChangeRecorder> pathsToRecorders
	val List<SourceTargetPair> sourceTargetPairs
	@Accessors(PUBLIC_GETTER)
	val List<ChangeMatch> changesMatches

	new(InternalVirtualModel virtualModel) {
		logger.level = Level::DEBUG
		pathsToRecorders = new HashMap
		this.virtualModel = virtualModel
		sourceTargetPairs = new ArrayList
		changesMatches = new ArrayList
	}

	def void addPathToRecorded(VURI resourceVuri) {
		if (null !== pathsToRecorders.get(resourceVuri))
			throw new IllegalStateException('''VURI«resourceVuri» has already been observed''')
		val recorder = new AtomicEmfChangeRecorder
		pathsToRecorders.put(resourceVuri, recorder)
		logger.debug('''Start recording on VURI «resourceVuri»''')
		recorder.startRecordingOn(resourceVuri)
	}

	def void recordOriginalAndCorrespondentChanges(VURI orignal, List<VURI> targets) {
		targets.forEach[addPathToRecorded]
		sourceTargetPairs.add(new SourceTargetPair(orignal, targets))
	}

	def List<TransactionalChange> getChanges(VURI vuri) {
		val changes = new ArrayList<TransactionalChange>
		val recorder = pathsToRecorders.get(vuri)
		virtualModel.executeCommand [|
			changes += recorder.endRecording
			return null
		]
		logger.debug('''Restart recording on VURI «vuri»''')
		recorder.startRecordingOn(vuri)
		changes
	}

	private def startRecordingOn(AtomicEmfChangeRecorder recorder, VURI vuri) {
		val modelInstance = virtualModel.getModelInstance(vuri)
		recorder.beginRecording(vuri, Collections::singleton(modelInstance.resource))
	}

	override update(VURI vuri, TransactionalChange change) {
		sourceTargetPairs.filter[source == vuri].forEach [ pair |
			val Map<VURI, List<TransactionalChange>> targetToCorrespondentChanges = new HashMap
			pair.targets.forEach[targetToCorrespondentChanges.put(it, getChanges(it))]
			val match = new ChangeMatch(vuri, change, targetToCorrespondentChanges)
			logger.debug('''New match added: «match»''')
			changesMatches += match
		]

	}

}
