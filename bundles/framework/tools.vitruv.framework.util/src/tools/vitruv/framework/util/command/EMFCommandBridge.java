package tools.vitruv.framework.util.command;

import java.util.concurrent.Callable;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

public class EMFCommandBridge {

    private EMFCommandBridge() {
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

}
