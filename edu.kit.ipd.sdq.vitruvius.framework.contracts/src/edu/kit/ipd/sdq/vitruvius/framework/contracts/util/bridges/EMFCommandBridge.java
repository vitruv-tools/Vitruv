package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationRunnable;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusRecordingCommand;

public class EMFCommandBridge {

    private EMFCommandBridge() {
    }

    public static VitruviusRecordingCommand createVitruviusRecordingCommand(
            final TransformationRunnable transformationRunnable) {
        final VitruviusRecordingCommand recordingCommand = new VitruviusRecordingCommand() {
            @Override
            protected void doExecute() {
                try {
                    TransformationResult transformationResult = transformationRunnable.runTransformation();
                    if (null == transformationResult) {
                        logger.warn(
                                "Transformation change result is null. This indicates that the previous transformation had an error.");
                    }
                    this.transformationResult = transformationResult;
                } catch (Throwable e) {
                    RuntimeException r;
                    if (e instanceof RuntimeException) {
                        r = (RuntimeException) e;
                    } else {
                        // soften
                        r = new RuntimeException(e);
                    }
                    setRuntimeException(r);
                    // just log and rethrow
                    throw (e);
                }
            }
        };
        return recordingCommand;
    }
}
