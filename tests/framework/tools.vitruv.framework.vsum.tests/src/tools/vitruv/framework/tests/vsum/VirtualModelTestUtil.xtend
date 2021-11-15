package tools.vitruv.framework.tests.vsum

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.recording.ChangeRecorder

/**
 * Utility methods for the VSUM and view test cases.
 */
@Utility
class VirtualModelTestUtil {

    /**
     * Create a recorder, start recording a resource set, apply changes, stop and return the recorded changes.
     */
    def static VitruviusChange recordChanges(ResourceSet resourceSet, Runnable changesToPerform) {
        val recorder = new ChangeRecorder(resourceSet)
        recorder.addToRecording(resourceSet)
        recorder.beginRecording
        changesToPerform.run()
        val result = recorder.endRecording
        recorder.close
        return result
    }

}
