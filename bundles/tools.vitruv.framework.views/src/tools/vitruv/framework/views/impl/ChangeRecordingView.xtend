package tools.vitruv.framework.views.impl

import java.util.List
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.EChangeIdManager
import tools.vitruv.change.atomic.id.IdResolver
import tools.vitruv.change.composite.recording.ChangeRecorder
import tools.vitruv.framework.views.CommittableView
import tools.vitruv.framework.views.View
import tools.vitruv.change.changederivation.StateBasedChangeResolutionStrategy

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState

import static extension tools.vitruv.change.atomic.resolve.EChangeIdResolverAndApplicator.applyBackward
import static extension tools.vitruv.change.atomic.resolve.EChangeIdResolverAndApplicator.applyForward

/**
 * A {@link View} that records changes to its resources and allows to propagate them 
 * back to the underlying models using the {@link #commitChanges} method.
 */
class ChangeRecordingView implements ModifiableView, CommittableView {
    @Delegate
    BasicView view
    ChangeRecorder changeRecorder

    protected new(BasicView view) {
        checkArgument(view !== null, "view must not be null")
        checkState(!view.isModified, "view must not be modified")
        this.view = view
        setupChangeRecorder
    }

    override update() {
        changeRecorder.endRecordingAndClose()
        view.update()
        setupChangeRecorder
    }

    private def setupChangeRecorder() {
        changeRecorder = new ChangeRecorder(view.viewResourceSet)
        changeRecorder.addToRecording(view.viewResourceSet)
        changeRecorder.beginRecording()
    }
	def private void assignIds(List<EChange> changes) {
		val idResolver = IdResolver.create(view.viewResourceSet)
		val idManager = new EChangeIdManager(idResolver)
		changes.toList.reverseView.forEach[applyBackward]
		changes.forEach[
			idManager.setOrGenerateIds(it)
			it.applyForward(idResolver)
		]
	}

    override commitChanges() {
        view.checkNotClosed()
        val recordedChange = changeRecorder.endRecording()
        assignIds(recordedChange.EChanges)
        view.viewType.commitViewChanges(this, recordedChange)
        view.viewChanged = false
        changeRecorder.beginRecording()
    }

    override close() throws Exception {
        if (!isClosed) {
            changeRecorder.close()
        }
        view.close()
    }

    private def void endRecordingAndClose(ChangeRecorder recorder) {
        if (recorder.isRecording) {
            recorder.endRecording()
        }
        recorder.close()
    }

    override withChangeRecordingTrait() {
        val newView = view.withChangeRecordingTrait
        changeRecorder.close
        return newView
    }

    override withChangeDerivingTrait(StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        val newView = view.withChangeDerivingTrait(changeResolutionStrategy)
        changeRecorder.close
        return newView
    }
}
