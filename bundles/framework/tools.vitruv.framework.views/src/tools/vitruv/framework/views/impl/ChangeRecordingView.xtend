package tools.vitruv.framework.views.impl

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.views.ChangeableViewSource
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.ViewSelection
import tools.vitruv.framework.views.ViewSelector

import static com.google.common.base.Preconditions.checkArgument

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

/**
 * A {@link View} that records changes to its resources and allows to propagate them 
 * back to the underlying models using the {@link #commitChanges} method.
 */
class ChangeRecordingView extends AbstractModifiableView {
    ChangeRecorder changeRecorder

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

    override update() {
        changeRecorder.endRecordingAndClose()
        super.update()
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
        if (!isClosed) {
            changeRecorder.close()
        }
        super.close()
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
