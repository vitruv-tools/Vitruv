package tools.vitruv.framework.change.recording.impl

import java.util.Collection
import java.util.List
import java.util.Set

import org.apache.log4j.Level
import org.apache.log4j.Logger

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Accessors

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.description.impl.LegacyEMFModelChangeImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import tools.vitruv.framework.change.echange.resolve.StagingArea
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import java.util.stream.Collectors

class AtomicEmfChangeRecorderImpl implements AtomicEmfChangeRecorder {
	static extension VitruviusChangeFactory = VitruviusChangeFactory::instance
	static extension Logger = Logger::getLogger(AtomicEmfChangeRecorderImpl)
	val AtomicChangeRecorder changeRecorder
	val Set<Notifier> elementsToObserve
	val boolean unresolveRecordedChanges
	val boolean updateTuids
	@Accessors(PUBLIC_GETTER)
	val List<TransactionalChange> resolvedChanges
	@Accessors(PUBLIC_GETTER)
	val List<TransactionalChange> unresolvedChanges

	/**
	 * Constructor for the AtmoicEMFChangeRecorder, which does not unresolve
	 * the recorded changes, but updated Tuids.
	 */
	new() {
		this(false, true)
	}

	/**
	 * Constructors which updated Tuids
	 * 
	 * @param unresolveRecordedChanges -
	 * 		The recorded changes will be replaced by unresolved changes, which referenced EObjects are proxy objects.
	 */
	new(boolean unresolveRecordedChanges) {
		this(unresolveRecordedChanges, true)
	}

	/**
	 * Constructor for AtomicEMFChangeRecorder
	 * @param unresolveRecordedChanges -
	 * 		The recorded changes will be replaced by unresolved changes, which referenced EObjects are proxy objects.
	 * @param updateTuids -
	 * 		specifies whether TUIDs shall be updated or not.
	 */
	new(boolean unresolveRecordedChanges, boolean updateTuids) {
		changeRecorder = new AtomicChangeRecorder
		changeRecorder.recordingTransientFeatures = false
		changeRecorder.resolveProxies = true
		elementsToObserve = newHashSet
		resolvedChanges = newArrayList
		this.unresolveRecordedChanges = unresolveRecordedChanges
		this.updateTuids = updateTuids
		unresolvedChanges = newArrayList

		// TODO PS Remove
		level = Level::INFO
	}

	override void beginRecording() {
		changeRecorder.reset
		changeRecorder.beginRecording(elementsToObserve)
	}

	override void addToRecording(Notifier elementToObserve) {
		elementsToObserve += elementToObserve
		if (isRecording)
			changeRecorder.beginRecording(elementsToObserve)
	}

	override void removeFromRecording(Notifier elementToObserve) {
		elementsToObserve -= elementToObserve
		if (isRecording)
			changeRecorder.beginRecording(elementsToObserve)
	}

	override stopRecording() {
		if (!recording)
			throw new IllegalStateException
		changeRecorder.endRecording
	}

	override clearNotifiers() {
		elementsToObserve.clear
	}

	override endRecording() {
		if (!recording)
			throw new IllegalStateException
		unresolvedChanges.clear
		resolvedChanges.clear
		changeRecorder.endRecording
		// Only take those that do not contain only objectsToAttach (I don't know why)
		val changeDescriptions = changeRecorder.changeDescriptions
		val relevantChangeDescriptions = changeDescriptions.filter [
			!(objectChanges.isEmpty && resourceChanges.isEmpty)
		].toList.immutableCopy
		relevantChangeDescriptions.reverseView.forEach[applyAndReverse]
		val currentUnresolved = relevantChangeDescriptions.filterNull.map [
			createModelChange(true, unresolveRecordedChanges && updateTuids)
		].filterNull.toList

		correctChanges(currentUnresolved)

		relevantChangeDescriptions.reverseView.forEach[applyAndReverse]
		val currentResolved = relevantChangeDescriptions.filterNull.map [
			createModelChange(false, !unresolveRecordedChanges && updateTuids)
		].filterNull.toList

		val filterFunction = [ List<TransactionalChange> changes |
			// TODO PS When recording on some domains, the first change is a deletion of the root 
			// an afterwards, the real change sequence starts.
			val relevantChanges = if (changes.length > 1 &&
					changes.get(0).EChanges.get(0) instanceof RemoveAndDeleteRoot<?> &&
					changes.get(0).EChanges.get(1) instanceof CreateAndInsertRoot<?>) {
					val firstEChange = changes.get(0).EChanges.get(0) as RemoveAndDeleteRoot<?>
					val secondEChange = changes.get(0).EChanges.get(1) as CreateAndInsertRoot<?>
					val uriEqual = firstEChange.removeChange.uri == secondEChange.insertChange.uri
					val objectEqual = EcoreUtil::equals(
						firstEChange.deleteChange.affectedEObject,
						secondEChange.createChange.affectedEObject
					)
					if (uriEqual && objectEqual) {
						changes.drop(1).toList
					} else {
						changes
					}
				} else {
					changes
				}
			// TODO HK: Replace this correspondence exclusion with an inclusion of only file extensions
			// that are
			// supported by the domains of the VirtualModel
			relevantChanges.stream.filter [ change |
				change.URI === null || !change.URI.EMFUri.toString.endsWith("correspondence")
			].collect(Collectors::toList)
		]

		val filteredResolved = filterFunction.apply(currentResolved)
		val filteredUnresolved = filterFunction.apply(currentUnresolved)

		unresolvedChanges += filteredUnresolved
		resolvedChanges += filteredResolved
	}

	override isRecording() {
		changeRecorder.recording
	}

	override dispose() {
		changeRecorder.dispose
	}

	private def createModelChange(ChangeDescription changeDescription, boolean unresolveChanges, boolean updateTuids) {
		var TransactionalChange result = null
		if (unresolveChanges) {
			result = createEMFModelChange(changeDescription)
			changeDescription.applyAndReverse
		} else {
			result = createLegacyEMFModelChange(changeDescription)
			if (updateTuids) {
				result.applyForward
			} else {
				(result as LegacyEMFModelChangeImpl).applyForwardWithoutTuidUpdate
			}
		}

		return result
	}

	/*
	 * The recorder doesn't produce the correct changes, because all created changes of one change
	 * description are resolved to the same state before all changes were applied. Every atomic change must be resolved
	 * directly to the state before itself is applied => roll everything back and re-apply all changes, while
	 * correcting the wrong changes.
	 */
	private def void correctChanges(List<TransactionalChange> changes) {
		val eChanges = changes.map[EChanges].flatten.toList
		// Roll back
		eChanges.reverseView.forEach [
			updateStagingArea // corrects the missing or wrong staging area of CreateEObject changes.
			if (resolved)
				applyBackward
		]
		// Apply again and unresolve the results if necessary
		eChanges.forEach [ c |
			val copy = EcoreUtil::copy(c)
			EChangeUnresolver::unresolve(c)
			if (copy.resolved)
				copy.applyForward
		]
	}

	/*
	 * Updates the staging area to the current state of the model.
	 */
	private def dispatch void updateStagingArea(EChange change) {
		// Is needed to create a dispatch method which is applicable for EChange base class.
	}

	private def dispatch void updateStagingArea(CreateEObject<EObject> change) {
		// The newly created object is in an resource after the change, so
		// the correct staging area can be chosen, before the change
		// is applied backward.
		change.stagingArea = StagingArea::getStagingArea(change.affectedEObject.eResource)
	}

	private def dispatch void updateStagingArea(CompoundEChange change) {
		change.atomicChanges.forEach[updateStagingArea]
	}

	static class AtomicChangeRecorder extends ChangeRecorder {
		boolean isDisposed = false
		@Accessors(PUBLIC_GETTER)
		val Collection<Object> rootObjects
		val List<ChangeDescription> changeDescriptions

		new() {
			changeDescriptions = newArrayList
			rootObjects = newHashSet
			setRecordingTransientFeatures(false)
			setResolveProxies(true)
			reset
		}

		def void reset() {
			changeDescriptions.clear
		}

		override dispose() {
			isDisposed = true
			super.dispose
		}

		override notifyChanged(Notification notification) {
			if (isRecording && !isDisposed) {
				super.notifyChanged(notification)
				endRecording
				beginRecording(rootObjects)
			}
		}

		override beginRecording(Collection<?> rObjects) {
			if (!isDisposed) {
				rootObjects.clear
				rootObjects += rObjects
				super.beginRecording(rootObjects)
			}
		}

		override endRecording() {
			if (!isDisposed)
				changeDescriptions += super.endRecording
			return changeDescription
		}
	}

}
