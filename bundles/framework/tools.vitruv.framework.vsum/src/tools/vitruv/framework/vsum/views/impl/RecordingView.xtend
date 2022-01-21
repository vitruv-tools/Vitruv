package tools.vitruv.framework.vsum.views.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.recording.ChangeRecorder
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
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.vsum.views.View

/**
 * A {@link View} that records changes to its resources and allows to propagate them 
 * back to the underlying models using the {@link #commitChanges} method.
 */
class RecordingView implements ModifiableView, ChangePropagationListener {
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
		update()
	}

	override rootObjects() {
		checkNotClosed()
		viewResourceSet.resources.map[contents].flatten.toList
	}

	override isModified() {
		return viewChanged
	}

	override haveViewSourcesChanged() {
		return modelChanged
	}

	override update() {
		checkNotClosed()
		if (isModified) {
			throw new UnsupportedOperationException("Cannot update from model when view is modified.")
		}
		changeRecorder.endRecordingAndClose()
		modelChanged = false
		viewType.updateView(this)
		viewChanged = false
		viewResourceSet.addChangeListeners()
		changeRecorder = new ChangeRecorder(viewResourceSet)
		changeRecorder.addToRecording(viewResourceSet)
		changeRecorder.beginRecording()
	}

	override commitChanges() {
		checkNotClosed()
		changeRecorder.endRecording()
		val propagatedChanges = viewSource.propagateChange(changeRecorder.change)
		viewChanged = false
		update() // view shall not be dirty, thus update on commit
		return propagatedChanges
	}

	override close() throws Exception {
		if (!closed) {
			closed = true
			changeRecorder.close()
			viewResourceSet.resources.forEach[unload()]
			viewResourceSet.resources.clear()
			viewResourceSet.removeChangeListeners()
		}
		viewSource.removeChangePropagationListener(this)
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
	}

	override void moveRoot(EObject object, URI newLocation) {
		checkState(rootObjects.contains(object), "view must contain element %s to move", object)
		viewResourceSet.resources.findFirst[contents.contains(object)].URI = newLocation
	}

	def private void checkNotClosed() {
		checkState(!closed, "view is already closed!")
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
	
	private def void removeChangeListeners(ResourceSet resourceSet) {
		resourceSet.allContents.forEach [
			eAdapters.clear()
		]
	}

	private def void endRecordingAndClose(ChangeRecorder recorder) {
		if (recorder !== null && recorder.isRecording) {
			recorder.endRecording()
		}
		recorder?.close()
	}

	override modifyContents((ResourceSet)=>void modificationFunction) {
		modificationFunction.apply(viewResourceSet)
	}

}
