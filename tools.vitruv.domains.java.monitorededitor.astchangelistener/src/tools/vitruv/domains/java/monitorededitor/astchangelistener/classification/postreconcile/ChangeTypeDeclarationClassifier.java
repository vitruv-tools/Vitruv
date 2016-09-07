package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;

public abstract class ChangeTypeDeclarationClassifier implements ConcreteChangeClassifier {

    @Override
    public List<? extends ChangeClassifyingEvent> match(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit, PreviousASTState previousState) {
        List<ChangeClassifyingEvent> changeEvents = new ArrayList<ChangeClassifyingEvent>();
        if (currentCompilationUnit == null)
            return changeEvents;
        CompilationUnit oldCompilationUnit = previousState.getOldCompilationUnit(currentCompilationUnit);
        for (Object t : currentCompilationUnit.types()) {
            if (!(t instanceof TypeDeclaration))
                // t is annotation or enum
                continue;
            TypeDeclaration type = (TypeDeclaration) t;
            Queue<IJavaElementDelta> bfsQueue = new LinkedList<IJavaElementDelta>();
            bfsQueue.add(delta);

            while (!bfsQueue.isEmpty()) {
                IJavaElementDelta top = bfsQueue.remove();
                changeEvents.addAll(classifyChange(top, type, currentCompilationUnit, oldCompilationUnit));
                for (IJavaElementDelta child : top.getAffectedChildren())
                    bfsQueue.add(child);
            }
        }
        return changeEvents;
    }

    protected abstract List<? extends ChangeClassifyingEvent> classifyChange(IJavaElementDelta delta,
            TypeDeclaration type, CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit);
}
