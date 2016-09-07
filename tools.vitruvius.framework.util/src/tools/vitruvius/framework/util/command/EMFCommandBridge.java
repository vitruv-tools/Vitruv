package tools.vitruvius.framework.util.command;

import java.util.concurrent.Callable;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

public class EMFCommandBridge {

    protected TransformationResult transformationResult;

    private EMFCommandBridge() {
    }

    public static VitruviusTransformationRecordingCommand createVitruviusTransformationRecordingCommand(
            final Callable<TransformationResult> callable) {
        final VitruviusTransformationRecordingCommand recordingCommand = new VitruviusTransformationRecordingCommand() {
            @Override
            protected void doExecute() {
                try {
                    TransformationResult transformationResult = callable.call();
                    if (null == transformationResult) {
                        logger.warn(
                                "Transformation change result is null. This indicates that the previous transformation had an error.");
                    }
                    this.transformationResult = transformationResult;
                } catch (Throwable e) {
                    storeAndRethrowException(e);
                }
            }
        };
        return recordingCommand;
    }
   
    public static VitruviusRecordingCommand createVitruviusRecordingCommand(final Callable<Void> callable) {
        VitruviusRecordingCommand recordingCommand = new VitruviusRecordingCommand() {
            @Override
            protected void doExecute() {
                try {
                    callable.call();
                } catch (Throwable e) {
                    storeAndRethrowException(e);
                }
            }
        };
        return recordingCommand;
    }

    public static void executeVitruviusRecordingCommand(final TransactionalEditingDomain domain,
            final VitruviusRecordingCommand command) {
        command.setTransactionDomain(domain);
        executeCommand(domain, command);
        command.rethrowRuntimeExceptionIfExisting();
    }
    
    private static void executeCommand(TransactionalEditingDomain domain,
            final Command command) {
        domain.getCommandStack().execute(command);
    }

    public static void createAndExecuteVitruviusRecordingCommand(final Callable<Void> callable,
    		final TransactionalEditingDomain domain) {
        final VitruviusRecordingCommand command = createVitruviusRecordingCommand(callable);
        executeVitruviusRecordingCommand(domain, command);
    }

    public TransformationResult getTransformationResult() {
        return this.transformationResult;
    }

}
