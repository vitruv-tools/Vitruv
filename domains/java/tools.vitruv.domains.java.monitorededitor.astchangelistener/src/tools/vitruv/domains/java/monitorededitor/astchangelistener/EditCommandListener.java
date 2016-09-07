package tools.vitruv.domains.java.monitorededitor.astchangelistener;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

/**
 * @author messinger
 * 
 *         Implements and registers an {@link IExecutionListener} which listens to Eclipse edit
 *         commands. If the edit command is a CUT, the {@link ASTChangeListener} is set to withhold
 *         events once. Can be paused.
 * 
 */
class EditCommandListener implements IExecutionListener {

    private static final String EDIT_CUT_ID = "org.eclipse.ui.edit.cut";
    private final ASTChangeListener astListener;
    private boolean listening = false;

    public EditCommandListener(ASTChangeListener astListener) {
        this.astListener = astListener;
        ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
        service.addExecutionListener(this);
    }
    
    public void revokeRegistrations() {
        ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
        service.removeExecutionListener(this);
    }

    public boolean isListening() {
        return this.listening;
    }

    public void startListening() {
        this.listening = true;
    }

    public void stopListening() {
        this.listening = false;
    }

    @Override
    public void notHandled(String commandId, NotHandledException exception) {
        // Do nothing.
    }

    @Override
    public void postExecuteFailure(String commandId, ExecutionException exception) {
        // Do nothing.
    }

    @Override
    public void postExecuteSuccess(String commandId, Object returnValue) {
        // Do nothing.
    }

    @Override
    public void preExecute(String commandId, ExecutionEvent event) {
        if (!this.listening)
            return;
        if (isEditCutCommand(event)) {
            EditCommandListener.this.astListener.withholdEventsOnce(true);
        }
    }

    private boolean isEditCutCommand(ExecutionEvent event) {
        if (event.getCommand().getId().equals(EDIT_CUT_ID)) {
            return true;
        }
        return false;
    }

}
