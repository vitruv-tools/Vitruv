package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationRunnable;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusRecordingCommand;

// if a blackboard parameter is needed this class should be move to contracts.util.bridges
public class EMFCommandBridge {

    private EMFCommandBridge() {
    }

    public static Command createVitruviusRecordingCommand(final TransformationRunnable transformationRunnable) {
        final VitruviusRecordingCommand recordingCommand = new VitruviusRecordingCommand() {
            @Override
            protected void doExecute() {
                TransformationResult transformationResult = transformationRunnable.runTransformation();
                this.transformationResult = transformationResult;
            }
        };
        return recordingCommand;
    }

    public static Command createCommand(final Runnable runnable, final TransactionalEditingDomain editingDomain) {
        final RecordingCommand recordingCommand = new RecordingCommand(editingDomain) {
            @Override
            protected void doExecute() {
                runnable.run();
            }
        };
        return recordingCommand;
    }
}
