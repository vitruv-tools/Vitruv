package tools.vitruv.framework.vsum.views

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.vsum.ChangePropagationAbortCause
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.views.selection.ViewSelector
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * A basic view that passes by default the entirety of its underlying model as a copy.
 * IMPORTANT: This is a prototypical implementation for concept exploration and therefore subject to change.
 */
package class BasicModelView implements ModifiableView {
    val VirtualModel virtualModel
    @Accessors(PUBLIC_GETTER)
    val ViewSelector selector
    val UpdatingViewType viewType
    val ResourceSet viewResourceSet
    ChangeRecorder changeRecorder
    boolean modelChanged
    boolean viewChanged
    boolean closed

    protected new(UpdatingViewType viewType, ViewSelector selector, VirtualModel virtualModel) {
        this.virtualModel = virtualModel
        this.viewType = viewType
        this.selector = selector
        viewResourceSet = new ResourceSetImpl().withGlobalFactories
        virtualModel.addChangePropagationListener(this)
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
        if(isModified) {
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
        val propagatedChanges = virtualModel.propagateChange(changeRecorder.change)
        viewChanged = false
        update // view shall not be dirty, thus update on commit
        return propagatedChanges
    }

    override close() throws Exception {
        if(!closed) {
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

    def private void checkNotClosed() {
        if(closed) {
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
        if(recorder !== null && recorder.isRecording) {
            recorder.endRecording
        }
        recorder?.close
    }
				
	override modifyContents((ResourceSet)=>void modificationFunction) {
		modificationFunction.apply(viewResourceSet)
	}

}
