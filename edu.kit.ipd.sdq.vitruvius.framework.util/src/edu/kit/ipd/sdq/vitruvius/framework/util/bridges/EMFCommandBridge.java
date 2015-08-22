package edu.kit.ipd.sdq.vitruvius.framework.util.bridges;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VitruviusRecordingCommand;

// if a blackboard parameter is needed this class should be move to contracts.util.bridges
public class EMFCommandBridge {

    private EMFCommandBridge() {
    }

    public static Command createCommand(final Runnable runnable) {
        final VitruviusRecordingCommand recordingCommand = new VitruviusRecordingCommand() {
            @Override
            protected void doExecute() {
                runnable.run();
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
