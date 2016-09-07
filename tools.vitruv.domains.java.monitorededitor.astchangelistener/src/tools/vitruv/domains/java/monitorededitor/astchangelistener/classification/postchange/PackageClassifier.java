package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postchange;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.CompilationUnit;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;

public abstract class PackageClassifier implements ConcreteChangeClassifier {

    protected static final List<ChangeClassifyingEvent> EMPTY_LIST = new ArrayList<ChangeClassifyingEvent>(0);

    @Override
    public List<ChangeClassifyingEvent> match(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            PreviousASTState previousState) {
        List<ChangeClassifyingEvent> pkgEvents = new ArrayList<ChangeClassifyingEvent>();

        Queue<IJavaElementDelta> bfsQueue = new LinkedList<IJavaElementDelta>();
        bfsQueue.add(delta);

        while (!bfsQueue.isEmpty()) {
            IJavaElementDelta top = bfsQueue.remove();
            pkgEvents.addAll(classifyPackageChange(top, previousState));
            for (IJavaElementDelta child : top.getAffectedChildren())
                bfsQueue.add(child);
        }

        return pkgEvents;
    }

    protected abstract List<? extends ChangeClassifyingEvent> classifyPackageChange(IJavaElementDelta delta,
            PreviousASTState previousState);
}
