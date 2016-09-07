package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RenameClassEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RenameInterfaceEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RenameTypeEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class RenameTypeClassifier implements ConcreteChangeClassifier {

    @Override
    public List<RenameTypeEvent> match(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            PreviousASTState previousState) {
        List<RenameTypeEvent> renameEvents = new ArrayList<RenameTypeEvent>();
        CompilationUnit oldUnit = previousState.getOldCompilationUnit(currentCompilationUnit);

        Queue<IJavaElementDelta> bfsQueue = new LinkedList<IJavaElementDelta>();
        bfsQueue.add(delta);

        while (!bfsQueue.isEmpty()) {
            IJavaElementDelta top = bfsQueue.remove();
            IJavaElementDelta[] children = top.getAffectedChildren();
            if (children.length == 2) {
                IJavaElement element1 = children[0].getElement();
                IJavaElement element2 = children[1].getElement();
                if (element1.getElementType() == IJavaElement.TYPE && element2.getElementType() == IJavaElement.TYPE) {
                    RenameTypeEvent event = createRenameTypeEvent(children[0], children[1], currentCompilationUnit,
                            oldUnit);
                    if (event != null)
                        renameEvents.add(event);
                }
            }
            for (IJavaElementDelta child : children)
                bfsQueue.add(child);
        }

        return renameEvents;
    }

    private RenameTypeEvent createRenameTypeEvent(IJavaElementDelta dtype1, IJavaElementDelta dtype2,
            CompilationUnit currentUnit, CompilationUnit oldUnit) {
        TypeDeclaration original = null, renamed = null;
        IType itype1 = (IType) dtype1.getElement();
        IType itype2 = (IType) dtype2.getElement();

        if (dtype1.getKind() == IJavaElementDelta.REMOVED && dtype2.getKind() == IJavaElementDelta.ADDED) {
            original = JavaModel2AST.getTypeDeclaration(itype1, oldUnit);
            renamed = JavaModel2AST.getTypeDeclaration(itype2, currentUnit);
        } else if (dtype2.getKind() == IJavaElementDelta.REMOVED && dtype1.getKind() == IJavaElementDelta.ADDED) {
            renamed = JavaModel2AST.getTypeDeclaration(itype1, currentUnit);
            original = JavaModel2AST.getTypeDeclaration(itype2, oldUnit);
        }
        if (original.isInterface() && renamed.isInterface())
            return new RenameInterfaceEvent(original, renamed);
        else if (!original.isInterface() && !renamed.isInterface())
            return new RenameClassEvent(original, renamed);
        else
            return null;
    }

}
