package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.function.Function
import java.util.stream.Collectors

import org.apache.log4j.Level
import org.apache.log4j.Logger

import org.eclipse.xtend.lib.annotations.Accessors

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.change.recording.impl.AtomicEmfChangeRecorderImpl
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.versioning.SourceTargetPair
import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.vsum.InternalVirtualModel

class SourceTargetRecorderImpl implements SourceTargetRecorder {
	@Accessors(PUBLIC_GETTER)
	val List<ChangeMatch> changesMatches
	val Map<VURI, AtomicEmfChangeRecorder> pathsToRecorders
	val List<SourceTargetPair> sourceTargetPairs
	val InternalVirtualModel virtualModel
	val boolean unresolveRecordedChanges

	val logger = Logger::getLogger(SourceTargetRecorderImpl)

	new(InternalVirtualModel virtualModel) {
		this(virtualModel, false)
	}

	new(InternalVirtualModel virtualModel, boolean unresolveRecordedChanges) {
		changesMatches = new ArrayList
		pathsToRecorders = new HashMap
		sourceTargetPairs = new ArrayList
		this.virtualModel = virtualModel
		this.unresolveRecordedChanges = unresolveRecordedChanges

		// TODO PS Remove Level::DEBUG
		logger.level = Level::DEBUG
	}

	override void recordOriginalAndCorrespondentChanges(VURI orignal, List<VURI> targets) {
		targets.forEach[addPathToRecorded]
		sourceTargetPairs.add(new SourceTargetPair(orignal, targets))
	}

	override update(VURI vuri, TransactionalChange change) {
		sourceTargetPairs.filter[vuri == source].forEach [ pair |
			val targetToCorrespondentChanges = pair.targets.stream.collect(Collectors::toMap(Function::identity, [
				getChanges
			]))
			val match = new ChangeMatch(vuri, change, targetToCorrespondentChanges)
			logger.debug('''New match added: «match»''')
			changesMatches += match
		]
	}

	def void addPathToRecorded(VURI resourceVuri) {
		if (pathsToRecorders.containsKey(resourceVuri))
			throw new IllegalStateException('''VURI«resourceVuri» has already been observed''')
		val recorder = new AtomicEmfChangeRecorderImpl(unresolveRecordedChanges)
		pathsToRecorders.put(resourceVuri, recorder)
		recorder.startRecordingOn(resourceVuri, false)
	}

	def List<TransactionalChange> getChanges(VURI vuri) {
		val changes = new ArrayList<TransactionalChange>
		val recorder = pathsToRecorders.get(vuri)
		virtualModel.executeCommand [|
			changes += recorder.endRecording
			return null
		]
		recorder.startRecordingOn(vuri, true)
		changes
	}

	private def startRecordingOn(AtomicEmfChangeRecorder recorder, VURI vuri, boolean restart) {
		logger.debug('''«if (restart) "Restart" else "Start"» recording on VURI «vuri»''')
		val modelInstance = virtualModel.getModelInstance(vuri)
		recorder.beginRecording(vuri, Collections::singleton(modelInstance.resource))
	}
}
