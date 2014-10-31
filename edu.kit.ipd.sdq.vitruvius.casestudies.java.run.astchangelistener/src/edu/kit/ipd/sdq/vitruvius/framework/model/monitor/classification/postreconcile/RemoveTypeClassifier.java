package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.PreviousASTState;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.ConcreteChangeClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.DeleteClassEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.DeleteInterfaceEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.DeleteTypeEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.JavaModel2AST;

public class RemoveTypeClassifier implements ConcreteChangeClassifier {

    @Override
    public List<DeleteTypeEvent> match(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            PreviousASTState previousState) {
        List<DeleteTypeEvent> removeEvents = new ArrayList<DeleteTypeEvent>();
        IJavaElement element = delta.getElement();
        if (!(element instanceof ICompilationUnit))
            return removeEvents;
        CompilationUnit oldCompilationUnit = previousState.getOldCompilationUnit(currentCompilationUnit);

        Queue<IJavaElementDelta> bfsQueue = new LinkedList<IJavaElementDelta>();
        bfsQueue.add(delta);

        while (!bfsQueue.isEmpty()) {
            IJavaElementDelta top = bfsQueue.remove();
            removeEvents.addAll(checkIfRemovedType(top, currentCompilationUnit, oldCompilationUnit));
            for (IJavaElementDelta child : top.getAffectedChildren())
                bfsQueue.add(child);
        }

        return removeEvents;
    }

    private List<DeleteTypeEvent> checkIfRemovedType(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            CompilationUnit oldCompilationUnit) {
        List<DeleteTypeEvent> removes = new ArrayList<DeleteTypeEvent>(1);
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.TYPE && delta.getKind() == IJavaElementDelta.REMOVED) {
            IType itype = (IType) element;
            TypeDeclaration type = JavaModel2AST.getTypeDeclaration(itype, oldCompilationUnit);
            if (type.isInterface())
                removes.add(new DeleteInterfaceEvent(currentCompilationUnit, type));
            else
                removes.add(new DeleteClassEvent(currentCompilationUnit, type));
        }
        return removes;
    }
}
