package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postchange;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.DeleteClassEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.DeleteInterfaceEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.DeleteTypeEvent;

public class RemoveCompilationUnitClassifier implements ConcreteChangeClassifier {

    private static final List<DeleteTypeEvent> EMPTY_LIST = new ArrayList<DeleteTypeEvent>(0);

    @Override
    public List<DeleteTypeEvent> match(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            PreviousASTState previousState) {
        List<DeleteTypeEvent> addEvents = new ArrayList<DeleteTypeEvent>();

        Queue<IJavaElementDelta> bfsQueue = new LinkedList<IJavaElementDelta>();
        bfsQueue.add(delta);

        while (!bfsQueue.isEmpty()) {
            IJavaElementDelta top = bfsQueue.remove();
            addEvents.addAll(checkIfRemovedCompilationUnit(top, currentCompilationUnit, previousState));
            for (IJavaElementDelta child : top.getAffectedChildren())
                bfsQueue.add(child);
        }

        return addEvents;
    }

    private List<DeleteTypeEvent> checkIfRemovedCompilationUnit(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit, PreviousASTState previousState) {
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.COMPILATION_UNIT && delta.getKind() == IJavaElementDelta.REMOVED) {
            ICompilationUnit unit = (ICompilationUnit) element;

            CompilationUnit oldCompilationUnit = previousState.getOldCompilationUnit(unit);
            if (oldCompilationUnit != null)
                return createRemoveTypeEvents(currentCompilationUnit, oldCompilationUnit);
        }
        return EMPTY_LIST;
    }

    // use previousState CompilationUnit!!! otherwise error
    private List<DeleteTypeEvent> createRemoveTypeEvents(CompilationUnit currentCompilationUnit,
            CompilationUnit oldCompilationUnit) {
        List<DeleteTypeEvent> removes = new ArrayList<DeleteTypeEvent>(3);
        for (Object t : oldCompilationUnit.types()) {
            if (!(t instanceof TypeDeclaration))
                continue;
            TypeDeclaration type = (TypeDeclaration) t;
            if (type.isInterface())
                removes.add(new DeleteInterfaceEvent(currentCompilationUnit, type));
            else
                // type is class
                removes.add(new DeleteClassEvent(currentCompilationUnit, type));
        }
        return removes;
    }
}
