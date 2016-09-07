package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangePackageDeclarationEvent;

public class ChangePackageDeclarationClassifier implements ConcreteChangeClassifier {

    @Override
    public List<ChangePackageDeclarationEvent> match(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            PreviousASTState previousState) {
        List<ChangePackageDeclarationEvent> changePkgs = new ArrayList<ChangePackageDeclarationEvent>(1);

        Queue<IJavaElementDelta> bfsQueue = new LinkedList<IJavaElementDelta>();
        bfsQueue.add(delta);

        while (!bfsQueue.isEmpty()) {
            IJavaElementDelta top = bfsQueue.remove();
            IJavaElementDelta[] children = top.getAffectedChildren();
            if (children.length == 2) {
                IJavaElement element1 = children[0].getElement();
                IJavaElement element2 = children[1].getElement();
                if (element1.getElementType() == IJavaElement.PACKAGE_DECLARATION
                        && element2.getElementType() == IJavaElement.PACKAGE_DECLARATION) {
                    ChangePackageDeclarationEvent event = createChangePackageDeclarationEvent(children[0], children[1],
                            currentCompilationUnit, previousState);
                    if (event != null)
                        changePkgs.add(event);
                }
            }
            for (IJavaElementDelta child : children)
                bfsQueue.add(child);
        }

        return changePkgs;
    }

    private ChangePackageDeclarationEvent createChangePackageDeclarationEvent(IJavaElementDelta dpackage1,
            IJavaElementDelta dpackage2, CompilationUnit currentUnit, PreviousASTState previousState) {
        IPackageDeclaration ipackage1 = (IPackageDeclaration) dpackage1.getElement();
        IPackageDeclaration ipackage2 = (IPackageDeclaration) dpackage2.getElement();

        String newPkgName = null;
        if (dpackage1.getKind() == IJavaElementDelta.REMOVED && dpackage2.getKind() == IJavaElementDelta.ADDED) {
            newPkgName = ipackage1.getElementName();
        } else if (dpackage2.getKind() == IJavaElementDelta.REMOVED && dpackage1.getKind() == IJavaElementDelta.ADDED) {
            newPkgName = ipackage2.getElementName();
        }
        CompilationUnit oldUnit = previousState.getOldCompilationUnit(newPkgName, currentUnit.getTypeRoot()
                .getElementName());
        return new ChangePackageDeclarationEvent(oldUnit.getPackage(), currentUnit.getPackage());
    }
}
