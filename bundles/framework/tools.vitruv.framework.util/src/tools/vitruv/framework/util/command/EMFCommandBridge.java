package tools.vitruv.framework.util.command;

import java.util.concurrent.Callable;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

public class EMFCommandBridge {

    private EMFCommandBridge() {
    }
   
    public static VitruviusRecordingCommand createVitruviusRecordingCommand(final Callable<Void> callable, TransactionalEditingDomain domain) {
        VitruviusRecordingCommand recordingCommand = new VitruviusRecordingCommand(domain) {
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
    
    public static void executeVitruviusRecordingCommand(final VitruviusRecordingCommand command) {
        executeCommand(command);
        command.rethrowRuntimeExceptionIfExisting();
    }
    
    private static void executeCommand(final VitruviusRecordingCommand command) {
    	command.executeAndRethrowException();
    }

    public static void createAndExecuteVitruviusRecordingCommand(final Callable<Void> callable,
    		final TransactionalEditingDomain domain) {
        final VitruviusRecordingCommand command = createVitruviusRecordingCommand(callable, domain);
        executeVitruviusRecordingCommand(command);
    }

}
