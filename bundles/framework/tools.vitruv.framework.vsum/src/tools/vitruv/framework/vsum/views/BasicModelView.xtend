package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.vsum.ChangePropagationAbortCause
import tools.vitruv.framework.vsum.VirtualModel
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.common.notify.Notification

/**
 * A basic view that passes by default the entirety of its underlying model as a copy.
 * IMPORTANT: This is a prototypical implementation for concept exploration and therefore subject to change.
 */
class BasicModelView implements View {
    val VirtualModel virtualModel
    val ViewType viewType
    ResourceSet viewResourceSet
    ChangeRecorder changeRecorder
    boolean modelChanged
    boolean viewChanged
    boolean closed

    new(ViewType viewType, VirtualModel virtualModel) {
        this.virtualModel = virtualModel
        this.viewType = viewType
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
        viewResourceSet = viewType.updateView(this)
        viewResourceSet.allContents.forEach [
            eAdapters += new AdapterImpl() { // TODO TS: Encapsulate this
                override notifyChanged(Notification message) {
                    viewChanged = true
                }
            }
        ]
        changeRecorder = new ChangeRecorder(viewResourceSet)
        viewResourceSet.resources.forEach[changeRecorder.addToRecording(it)]
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

    private def void endRecordingAndClose(ChangeRecorder recorder) {
        if(recorder !== null && recorder.isRecording) {
            recorder.endRecording
        }
        recorder?.close
    }

}
