package tools.vitruvius.domains.java.monitorededitor.refactoringlistener.refactoringParticipants;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;

/**
 * The refactoring notification of Eclipse does not work properly! 
 * We do not get notified about this refactoring at the moment. 
 */
public class MoveMethodParticipant extends MoveParticipant {

    @Override
    protected boolean initialize(Object element) {
        // TODO Auto-generated method stub
        System.out.println("Move METHOD!!");
        return false;
    }

    @Override
    public boolean initialize(RefactoringProcessor processor, Object element, RefactoringArguments arguments) {
        // TODO Auto-generated method stub
        System.out.println("Move METHOD 2!!");
        return super.initialize(processor, element, arguments);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

}
