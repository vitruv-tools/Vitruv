package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.vsum.ChangePropagationAbortCause
import tools.vitruv.framework.vsum.VirtualModel

/**
 * A basic view that passes by default the entirety of its underlying model as a copy.
 * IMPORTANT: This is a prototypical implementation for concept exploration and therefore subject to change.
 */
class BasicModelView implements View {
    protected ResourceSet viewResourceSet
    protected boolean modelChanged
    ChangeRecorder changeRecorder
    val VirtualModel virtualModel
    boolean closed
    val ViewType viewType

    new(ViewType viewType, VirtualModel virtualModel) {
        this.virtualModel = virtualModel
        this.viewType = viewType
        virtualModel.addChangePropagationListener(this)
        // changeRecorder = new ChangeRecorder(viewResourceSet)
        update
    }

    override rootObjects() {
        viewResourceSet.resources.map[contents].flatten.toList
    }

    override isModified() { // TODO TS: Alternatively this could be done via a model change listener, what is better?
        return viewResourceSet.resources.stream.anyMatch[isModified]
    }

    override hasVSUMChanged() {
        return modelChanged
    }

    override update() {
        if(changeRecorder !== null) {
            if(changeRecorder.isRecording) {
                changeRecorder.endRecording
            }
            if(!changeRecorder.change.EChanges.empty) {
                throw new UnsupportedOperationException("Cannot update from model when view is modified.")
            }
        }
        modelChanged = false
        viewResourceSet = viewType.updateView(this)
        changeRecorder = new ChangeRecorder(viewResourceSet)
        changeRecorder.beginRecording
    }

    override commitChanges() { // TODO TS: Should the view save all its resources here?
        changeRecorder.endRecording
        val propagatedChanges = virtualModel.propagateChange(changeRecorder.change)
        update // view shall not be dirty, thus update on commit
        return propagatedChanges
    }

    override close() throws Exception { // TODO TS: Proof other methods against calls when the view is closed
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

    override finishedChangePropagation() {
        modelChanged = true
    }

    override startedChangePropagation() {
        // do nothing
    }

}
