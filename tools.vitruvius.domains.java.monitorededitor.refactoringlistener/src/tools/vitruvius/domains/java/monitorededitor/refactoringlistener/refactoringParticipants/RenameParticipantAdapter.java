package tools.vitruvius.domains.java.monitorededitor.refactoringlistener.refactoringParticipants;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruvius.domains.java.monitorededitor.refactoringlistener.RefactoringChangeListener;

public abstract class RenameParticipantAdapter extends RenameParticipant {

    private final RefactoringChangeListener listener = RefactoringChangeListener.getInstance();

    public abstract ChangeClassifyingEvent classifyRefactoring(Object element, RenameArguments args);

    @Override
    protected boolean initialize(Object element) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean initialize(RefactoringProcessor processor, Object element, RefactoringArguments arguments) {
        if (this.listener.isMonitoredProject(((IJavaElement) element).getJavaProject().getElementName())) {
            RenameArguments args = (RenameArguments) arguments;
            ChangeClassifyingEvent event = classifyRefactoring(element, args);
            this.listener.addChangeClassifyingEvent(event);
        }

        return super.initialize(processor, element, arguments);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context) {
        // BUG: does not get called!!!!!!
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException {
        // BUG: does not get called!!!!!!
        // TODO Auto-generated method stub
        return null;
    }

}
