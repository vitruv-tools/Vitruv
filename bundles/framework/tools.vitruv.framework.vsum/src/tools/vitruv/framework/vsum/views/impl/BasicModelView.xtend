package tools.vitruv.framework.vsum.views.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.recording.ChangeRecorder
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.vsum.views.ChangeableViewSource
import tools.vitruv.framework.vsum.views.ViewSelection
import tools.vitruv.framework.vsum.models.ChangePropagationListener
import tools.vitruv.framework.vsum.models.ChangePropagationAbortCause
import tools.vitruv.framework.vsum.views.ViewSelector

/**
 * A basic view that passes by default the entirety of its underlying model as a copy.
 * IMPORTANT: This is a prototypical implementation for concept exploration and therefore subject to change.
 */
class BasicModelView implements ModifiableView, ChangePropagationListener {
	@Accessors(PUBLIC_GETTER)
	val ViewSelection selection
	@Accessors(PUBLIC_GETTER)
	val ViewCreatingViewType<? extends ViewSelector> viewType
	@Accessors(PUBLIC_GETTER)
	val ChangeableViewSource viewSource
	val ResourceSet viewResourceSet
	ChangeRecorder changeRecorder
	boolean modelChanged
	boolean viewChanged
	boolean closed

	protected new(ViewCreatingViewType<? extends ViewSelector> viewType, ChangeableViewSource viewSource,
		ViewSelection selection) {
		this.viewType = viewType
		this.selection = selection
		this.viewSource = viewSource
		viewSource.addChangePropagationListener(this)
		viewResourceSet = new ResourceSetImpl().withGlobalFactories
		update
	}

	override rootObjects() {
		checkNotClosed
		viewResourceSet.resources.map[contents].flatten.toList
	}

	override isModified() {
		return viewChanged
	}

	override hasVSUMChanged() {
		return modelChanged
	}

	override update() {
		checkNotClosed
		if (isModified) {
			throw new UnsupportedOperationException("Cannot update from model when view is modified.")
		}
		changeRecorder.endRecordingAndClose
		modelChanged = false
		viewType.updateView(this)
		viewChanged = false
		viewResourceSet.addChangeListeners
		changeRecorder = new ChangeRecorder(viewResourceSet)
		changeRecorder.addToRecording(viewResourceSet)
		changeRecorder.beginRecording
	}

	override commitChanges() { // TODO TS: Should the view save all its resources/delete empty ones here?
		checkNotClosed
		changeRecorder.endRecording
		val propagatedChanges = viewSource.propagateChange(changeRecorder.change)
		viewChanged = false
		update // view shall not be dirty, thus update on commit
		return propagatedChanges
	}

	override close() throws Exception {
		if (!closed) {
			closed = true
			changeRecorder.close
		}
	}

	override isClosed() {
		return closed
	}

	override abortedChangePropagation(ChangePropagationAbortCause cause) {
		// do nothing
	}

	override finishedChangePropagation() { // TODO TS: Should views be de-registered upon closing?
		modelChanged = true
	}

	override startedChangePropagation() {
		// do nothing
	}

	override void registerRoot(EObject object, URI persistAt) {
		viewResourceSet.createResource(persistAt) => [
			contents += object
		]
		rootObjects += object
	}

	def private void checkNotClosed() {
		if (closed) {
			throw new IllegalStateException("View is already closed!")
		}
	}

	private def void addChangeListeners(ResourceSet resourceSet) {
		resourceSet.allContents.forEach [
			eAdapters += new AdapterImpl() {
				override notifyChanged(Notification message) {
					viewChanged = true
				}
			}
		]
	}

	private def void endRecordingAndClose(ChangeRecorder recorder) {
		if (recorder !== null && recorder.isRecording) {
			recorder.endRecording
		}
		recorder?.close
	}

	override modifyContents((ResourceSet)=>void modificationFunction) {
		modificationFunction.apply(viewResourceSet)
	}

}
