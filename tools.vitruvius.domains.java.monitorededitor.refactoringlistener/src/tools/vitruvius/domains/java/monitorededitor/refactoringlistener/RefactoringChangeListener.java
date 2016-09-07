package tools.vitruvius.domains.java.monitorededitor.refactoringlistener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.history.IRefactoringExecutionListener;
import org.eclipse.ltk.core.refactoring.history.IRefactoringHistoryService;
import org.eclipse.ltk.core.refactoring.history.RefactoringExecutionEvent;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

import tools.vitruvius.domains.java.monitorededitor.changeclassification.ChangeOperationListener;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruvius.domains.java.monitorededitor.refactoringlistener.RefactoringStatusListener.RefactoringStatus;

/**
 * The {@link RefactoringChangeListener} is notified by {@link RefactoringParticipant}s when the
 * developer used a refactoring to edit the code. {@link ChangeClassifyingEvent}s are submitted and
 * transmitted to registered {@link ChangeOperationListener}s. A {@link QuickFixListener} listens to
 * Eclipse quick assist operations and also generates {@link ChangeClassifyingEvent}s. Registered
 * {@link RefactoringStatusListener} get informed before a refactoring is executed and after it has
 * been informed.
 */
public final class RefactoringChangeListener implements IStartup, IRefactoringExecutionListener, IExecutionListener {

    private static final String REFACTORING_CATEGORY_ID = "org.eclipse.jdt.ui.category.refactoring";

    private static RefactoringChangeListener instance;

    private static final Logger LOG = Logger.getLogger(RefactoringChangeListener.class);
    private final List<ChangeOperationListener> listeners;
    private final List<RefactoringStatusListener> statusListeners;
    private boolean listening = false;
    private List<String> monitoredProjectNames;

    private RefactoringChangeListener(String... projectNames) {
        this.monitoredProjectNames = Arrays.asList(projectNames);
        this.listeners = new ArrayList<ChangeOperationListener>(5);
        this.statusListeners = new ArrayList<RefactoringStatusListener>();
        startListening();
        IRefactoringHistoryService refactoringService = RefactoringCore.getHistoryService();
        refactoringService.addExecutionListener(this);
        ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
        service.addExecutionListener(this);
    }

    /**
     * This is a pretty useful comment.
     * 
     * @return
     */
    public static synchronized RefactoringChangeListener getInstance(String... projectNames) {
        if (instance == null)
            instance = new RefactoringChangeListener(projectNames);
        if (projectNames.length != 0)
            instance.monitoredProjectNames = Arrays.asList(projectNames);
        return instance;
    }
    
    public static synchronized void destroyInstance() {
        if (instance == null) {
            return;
        }
        instance.revokeRegistrations();
        instance = null;
    }
    
    private void revokeRegistrations() {
        RefactoringCore.getHistoryService().removeExecutionListener(this);
        ((ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class)).removeExecutionListener(this);
    }

    public boolean isMonitoredProject(String projectName) {
        return this.monitoredProjectNames.contains(projectName);
    }

    @Override
    public void executionNotification(RefactoringExecutionEvent event) {
        if (!this.listening)
            return; // ignore event

        if (event.getEventType() == RefactoringExecutionEvent.ABOUT_TO_PERFORM)
            notifyAllStatusListeners(RefactoringStatus.ABOUT_POST_EXECUTE, event);
        else if (event.getEventType() == RefactoringExecutionEvent.PERFORMED)
            notifyAllStatusListeners(RefactoringStatus.POST_EXECUTE, event);
    }

    @Override
    public void preExecute(String commandId, ExecutionEvent event) {
        if (!this.listening)
            return; // ignore event
        if (isRefactoringCommand(event))
            notifyAllStatusListeners(RefactoringStatus.PRE_EXECUTE, event);
    }

    private boolean isRefactoringCommand(ExecutionEvent event) {
        try {
            if (event.getCommand().getCategory().getId().equals(REFACTORING_CATEGORY_ID)) {
                return true;
            }
        } catch (NotDefinedException e) {
            e.printStackTrace();
        }
        return false;
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
    public void earlyStartup() {
        System.err.println("RefactoringChangeListener.earlyStartup() called");
    }

    private void notifyAll(List<ChangeClassifyingEvent> events) {
        for (ChangeClassifyingEvent event : events) {
            notifyAll(event);
        }
    }

    private void notifyAll(ChangeClassifyingEvent event) {
        for (ChangeOperationListener listener : this.listeners) {
            LOG.info("Received refactoring event: " + event.toString());
            listener.update(event);
        }
    }

    public void addListener(ChangeOperationListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ChangeOperationListener listener) {
        this.listeners.remove(listener);
    }

    private void notifyAllStatusListeners(RefactoringStatus status, Object event) {
        for (RefactoringStatusListener listener : this.statusListeners) {
            switch (status) {
            case PRE_EXECUTE:
                listener.preExecute();
                break;
            case POST_EXECUTE:
                listener.postExecute();
                break;
            case ABOUT_POST_EXECUTE:
                listener.aboutPostExecute();
                break;
            default:
                break;
            }
        }
    }

    public void addListener(RefactoringStatusListener listener) {
        this.statusListeners.add(listener);
    }

    public void removeListener(RefactoringStatusListener listener) {
        this.statusListeners.remove(listener);
    }

    @Override
    public void notHandled(String commandId, NotHandledException exception) {
    }

    @Override
    public void postExecuteFailure(String commandId, ExecutionException exception) {
    }

    @Override
    public void postExecuteSuccess(String commandId, Object returnValue) {
    }

    public void addChangeClassifyingEvent(ChangeClassifyingEvent event) {
        if (event != null) {
            notifyAll(event);
        }
    }

    public void addChangeClassifyingEvents(List<ChangeClassifyingEvent> events) {
        if (events != null)
            notifyAll(events);
    }
}
