package edu.kit.ipd.sdq.vitruvius.framework.command.util;

import java.util.concurrent.Callable;

import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.vitruvius.framework.command.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.command.VitruviusRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.command.VitruviusTransformationRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelProviding;

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

    public static TransformationResult executeVitruviusRecordingCommand(final ModelProviding modelProviding,
            final VitruviusTransformationRecordingCommand command) {
        executeVitruviusRecordingCommand(modelProviding, (VitruviusRecordingCommand) command);
        return command.getTransformationResult();
    }

    private static void executeVitruviusRecordingCommand(final ModelProviding modelProviding,
            final VitruviusRecordingCommand command) {
        final TransactionalEditingDomain domain = modelProviding.getTransactionalEditingDomain();
        command.setTransactionDomain(domain);
        domain.getCommandStack().execute(command);
        command.rethrowRuntimeExceptionIfExisting();
    }

    public static void createAndExecuteVitruviusRecordingCommand(final Callable<Void> callable,
            final ModelProviding modelProviding) {
        final VitruviusRecordingCommand command = createVitruviusRecordingCommand(callable);
        executeVitruviusRecordingCommand(modelProviding, command);
    }

    public TransformationResult getTransformationResult() {
        return this.transformationResult;
    }

}
