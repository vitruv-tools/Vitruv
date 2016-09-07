package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.CreateClassEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.CreateInterfaceEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.CreateTypeEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class CreateTypeClassifier implements ConcreteChangeClassifier {

    @Override
    public List<CreateTypeEvent> match(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            PreviousASTState previousState) {
        List<CreateTypeEvent> createEvents = new ArrayList<CreateTypeEvent>();
        CompilationUnit oldCompilationUnit = previousState.getOldCompilationUnit(currentCompilationUnit);
        IJavaElement element = delta.getElement();
        if (!(element instanceof ICompilationUnit))
            return createEvents;

        Queue<IJavaElementDelta> bfsQueue = new LinkedList<IJavaElementDelta>();
        bfsQueue.add(delta);

        while (!bfsQueue.isEmpty()) {
            IJavaElementDelta top = bfsQueue.remove();
            createEvents.addAll(checkIfAddedType(top, currentCompilationUnit, oldCompilationUnit));
            for (IJavaElementDelta child : top.getAffectedChildren())
                bfsQueue.add(child);
        }

        return createEvents;
    }

    private List<CreateTypeEvent> checkIfAddedType(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            CompilationUnit oldCompilationUnit) {
        List<CreateTypeEvent> creates = new ArrayList<CreateTypeEvent>(1);
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.TYPE && delta.getKind() == IJavaElementDelta.ADDED) {
            IType itype = (IType) element;
            TypeDeclaration type = JavaModel2AST.getTypeDeclaration(itype, currentCompilationUnit);
            try {
                if (itype.isInterface())
                    creates.add(new CreateInterfaceEvent(oldCompilationUnit, type));
                else if (itype.isClass())
                    creates.add(new CreateClassEvent(oldCompilationUnit, type));
            } catch (JavaModelException e) {
                e.printStackTrace();
            }
        }
        return creates;
    }
}
