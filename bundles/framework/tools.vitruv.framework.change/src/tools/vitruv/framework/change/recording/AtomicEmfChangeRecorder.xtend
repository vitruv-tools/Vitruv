package tools.vitruv.framework.change.recording

import java.util.Collection
import java.util.List
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import tools.vitruv.framework.change.description.impl.LegacyEMFModelChangeImpl
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.echange.EChangeIdManager
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolver

class AtomicEmfChangeRecorder {
	val Set<Notifier> elementsToObserve
	var boolean unresolveRecordedChanges
	val boolean updateTuids;
	var List<TransactionalChange> resolvedChanges;
	var List<TransactionalChange> unresolvedChanges;
	val AtomicChangeRecorder changeRecorder;
	val UuidGeneratorAndResolver uuidProviderAndResolver;
	val EChangeIdManager eChangeIdManager;
	
	/**
	 * Constructor for the AtmoicEMFChangeRecorder, which does not unresolve
	 * the recorded changes, but updates {@link Tuid}s.
	 * 
	 * @param uuidProviderAndResolver -
	 * 		the {@link UuidProviderAndResolver} for ID generation
	 * @param strictMode -
	 * 		specifies whether exceptions shall be thrown if no ID exists for an element that should already have one.
	 * 		Should be set to <code>false</code> if model is not recorded from beginning
	 */
	new(UuidGeneratorAndResolver uuidProviderAndResolver, boolean strictMode) {
		this(uuidProviderAndResolver, strictMode, false, true)
	}

	/**
	 * Constructors which updates {@link Tuid}s.
	 * 
	 * @param uuidProviderAndResolver -
	 * 		the {@link UuidProviderAndResolver} for ID generation
	 * @param strictMode -
	 * 		specifies whether exceptions shall be thrown if no ID exists for an element that should already have one.
	 * 		Should be set to <code>false</code> if model is not recorded from beginning
	 * @param unresolveRecordedChanges -
	 * 		The recorded changes will be replaced by unresolved changes, which referenced EObjects are proxy objects.
	 */
	new(UuidGeneratorAndResolver uuidProviderAndResolver, boolean strictMode, boolean unresolveRecordedChanges) {
		this(uuidProviderAndResolver, strictMode, unresolveRecordedChanges, true);
	}

	/**
	 * Constructor for AtomicEMFChangeRecorder.
	 * 
	 * @param uuidProviderAndResolver -
	 * 		the {@link UuidProviderAndResolver} for ID generation
	 * @param strictMode -
	 * 		specifies whether exceptions shall be thrown if no ID exists for an element that should already have one.
	 * 		Should be set to <code>false</code> if model is not recorded from beginning
	 * @param unresolveRecordedChanges -
	 * 		The recorded changes will be replaced by unresolved changes, which referenced EObjects are proxy objects.
	 * @param updateTuids -
	 * 		specifies whether TUIDs shall be updated or not.
	 */
	new(UuidGeneratorAndResolver uuidProviderAndResolver, boolean strictMode, boolean unresolveRecordedChanges, boolean updateTuids) {
		this.elementsToObserve = newHashSet();
		this.unresolveRecordedChanges = unresolveRecordedChanges
		this.updateTuids = updateTuids;
		this.changeRecorder = new AtomicChangeRecorder();
		this.uuidProviderAndResolver = uuidProviderAndResolver;
		this.eChangeIdManager = new EChangeIdManager(uuidProviderAndResolver, strictMode)
	}

	def void beginRecording() {
		changeRecorder.reset;
		changeRecorder.beginRecording(this.elementsToObserve);
	}
	
	def void addToRecording(Notifier elementToObserve) {
		this.elementsToObserve += elementToObserve;
		if (isRecording) {
			changeRecorder.beginRecording(elementsToObserve);
		}
	}
	
	def void removeFromRecording(Notifier elementToObserve) {
		this.elementsToObserve -= elementToObserve;
		if (isRecording) {
			changeRecorder.beginRecording(elementsToObserve);
		}
	}

	/** Stops recording without returning a result */
	def void stopRecording() {
		if (!isRecording) {
			throw new IllegalStateException();
		}
		changeRecorder.endRecording();
	}

	def void endRecording() {
		if (!isRecording) {
			throw new IllegalStateException();
		}
		changeRecorder.endRecording();
		// Only take those that do not contain only objectsToAttach (I don't know why)
		val relevantChangeDescriptions = 
			changeRecorder.changeDescriptions.filter[!(objectChanges.isEmpty && resourceChanges.isEmpty)].toList
		if (unresolveRecordedChanges) {
			relevantChangeDescriptions.reverseView.forEach[applyAndReverse];
			unresolvedChanges = relevantChangeDescriptions.filterNull.map[createModelChange(true, unresolveRecordedChanges && updateTuids)].filterNull.toList;
			unresolvedChanges.map[EChanges].flatten.forEach[EChangeUnresolver.unresolve(it)]
		}
		relevantChangeDescriptions.reverseView.forEach[applyAndReverse];
		resolvedChanges = relevantChangeDescriptions.filterNull.map[createModelChange(false, !unresolveRecordedChanges && updateTuids)].filterNull.toList;
	}
	
	public def List<TransactionalChange> getUnresolvedChanges() {
		return unresolvedChanges;
	}
	
	public def List<TransactionalChange> getResolvedChanges() {
		return resolvedChanges;
	}

	private def createModelChange(ChangeDescription changeDescription, boolean unresolveChanges, boolean updateTuids) {
		var TransactionalChange result = null;
		if (unresolveChanges) {
			result = VitruviusChangeFactory.instance.createEMFModelChange(changeDescription)
			changeDescription.applyAndReverse()
		} else {
			result = VitruviusChangeFactory.instance.createLegacyEMFModelChange(changeDescription);
			if (updateTuids) {
				result.applyForward();
			} else {
				(result as LegacyEMFModelChangeImpl).applyForwardWithoutTuidUpdate();
			}
		}
		// Allow null provider and resolver for test purposes
		if (uuidProviderAndResolver !== null) {
			result.EChanges.forEach[eChangeIdManager.setOrGenerateIds(it)]
		}
		return result;
	}

	def boolean isRecording() {
		return changeRecorder.isRecording()
	}

	def void dispose() {
		changeRecorder.dispose()
	}

	/**
	 * A change recorder that restarts after each change notification to get atomic change descriptions.
	 */
	static class AtomicChangeRecorder extends ChangeRecorder {
		private Collection<?> rootObjects;
		private boolean isDisposed = false;
		@Accessors(PUBLIC_GETTER)
		private var List<ChangeDescription> changeDescriptions;
		
		new() {
			setRecordingTransientFeatures(false);
			setResolveProxies(true);
			reset();
		}
		
		public def void reset() {
			this.changeDescriptions = newArrayList;
		}
		
		override dispose() {
			this.isDisposed = true;
			super.dispose()
		}

		override notifyChanged(Notification notification) {
			if (isRecording && !isDisposed) {
				super.notifyChanged(notification);
				endRecording();
				beginRecording(rootObjects);
			}
		}

		override beginRecording(Collection<?> rootObjects) {
			if (!isDisposed) {
				this.rootObjects = rootObjects;
				super.beginRecording(rootObjects);
			}
		}

		override endRecording() {
			if (!isDisposed) {
				changeDescriptions += super.endRecording();
			}
			return changeDescription;
		}
	}

}
