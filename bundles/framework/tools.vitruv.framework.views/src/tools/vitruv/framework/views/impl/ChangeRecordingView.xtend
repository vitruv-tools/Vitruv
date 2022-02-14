package tools.vitruv.framework.views.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.models.ChangePropagationAbortCause
import tools.vitruv.framework.change.models.ChangePropagationListener
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.views.ChangeableViewSource
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.ViewSelection
import tools.vitruv.framework.views.ViewSelector

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

/**
 * A {@link View} that records changes to its resources and allows to propagate them 
 * back to the underlying models using the {@link #commitChanges} method.
 */
class ChangeRecordingView implements ModifiableView, ChangePropagationListener {
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
		checkArgument(viewType !== null, "view type must not be null")
		checkArgument(selection !== null, "view source must not be null")
		checkArgument(viewSource !== null, "view selection must not be null")
		this.viewType = viewType
		this.selection = selection
		this.viewSource = viewSource
		viewSource.addChangePropagationListener(this)
		viewResourceSet = new ResourceSetImpl().withGlobalFactories
		update()
	}

	override getRootObjects() {
		checkNotClosed()
		viewResourceSet.resources.map[contents].flatten.toList
	}

	override isModified() {
		return viewChanged
	}

	override isOutdated() {
		return modelChanged
	}

	override update() {
		checkNotClosed()
		checkState(!isModified, "cannot update from model when view is modified")
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
		changeRecorder.beginRecording()
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

	override finishedChangePropagation() {
		modelChanged = true
	}

	override startedChangePropagation() {
		// do nothing
	}

	override void registerRoot(EObject object, URI persistAt) {
		checkNotClosed()
		checkArgument(object !== null, "object to register as root must not be null")
		checkArgument(persistAt !== null, "URI for root to register must not be null")
		viewResourceSet.createResource(persistAt) => [
			contents += object
		]
	}

	override void moveRoot(EObject object, URI newLocation) {
		checkNotClosed()
		checkArgument(object !== null, "object to move must not be null")
		checkState(rootObjects.contains(object), "view must contain element %s to move", object)
		checkArgument(newLocation !== null, "URI for new location of root must not be null")
		viewResourceSet.resources.findFirst[contents.contains(object)].URI = newLocation
	}

	def private void checkNotClosed() {
		checkState(!closed, "view is already closed!")
	}

	private def void addChangeListeners(Notifier notifier) {
		notifier.eAdapters += new AdapterImpl() {
			override notifyChanged(Notification message) {
				viewChanged = true
			}
		}
		switch (notifier) {
			ResourceSet: notifier.resources.forEach[addChangeListeners()]
			Resource: notifier.contents.forEach[addChangeListeners()]
			EObject: notifier.eContents.forEach[addChangeListeners]
		}
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
