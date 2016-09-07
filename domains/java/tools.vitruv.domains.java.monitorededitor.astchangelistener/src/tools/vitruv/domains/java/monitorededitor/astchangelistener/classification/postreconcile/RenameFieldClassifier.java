package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RenameFieldEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class RenameFieldClassifier implements ConcreteChangeClassifier {

    @Override
    public List<RenameFieldEvent> match(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            PreviousASTState previousState) {
        List<RenameFieldEvent> renameEvents = new ArrayList<RenameFieldEvent>();
        CompilationUnit oldCompilationUnit = previousState.getOldCompilationUnit(currentCompilationUnit);

        Queue<IJavaElementDelta> bfsQueue = new LinkedList<IJavaElementDelta>();
        bfsQueue.add(delta);

        while (!bfsQueue.isEmpty()) {
            IJavaElementDelta top = bfsQueue.remove();
            IJavaElementDelta[] children = top.getAffectedChildren();
            if (children.length == 2) {
                IJavaElement element1 = children[0].getElement();
                IJavaElement element2 = children[1].getElement();
                if (element1.getElementType() == IJavaElement.FIELD && element2.getElementType() == IJavaElement.FIELD) {
                    RenameFieldEvent event = createRenameFieldEvent(children[0], children[1], currentCompilationUnit,
                            oldCompilationUnit);
                    if (event != null)
                        renameEvents.add(event);
                }
            }
            for (IJavaElementDelta child : children)
                bfsQueue.add(child);
        }

        return renameEvents;
    }

    private RenameFieldEvent createRenameFieldEvent(IJavaElementDelta dfield1, IJavaElementDelta dfield2,
            CompilationUnit currentUnit, CompilationUnit oldUnit) {
        VariableDeclarationFragment original = null, renamed = null;
        IField ifield1 = (IField) dfield1.getElement();
        IField ifield2 = (IField) dfield2.getElement();

        if (dfield1.getKind() == IJavaElementDelta.REMOVED && dfield2.getKind() == IJavaElementDelta.ADDED) {
            original = JavaModel2AST.getVariableDeclarationFragmentByName(ifield1, oldUnit);
            renamed = JavaModel2AST.getVariableDeclarationFragmentByName(ifield2, currentUnit);
        } else if (dfield2.getKind() == IJavaElementDelta.REMOVED && dfield1.getKind() == IJavaElementDelta.ADDED) {
            renamed = JavaModel2AST.getVariableDeclarationFragmentByName(ifield1, currentUnit);
            original = JavaModel2AST.getVariableDeclarationFragmentByName(ifield2, oldUnit);
        }
        if (renamed == null || original == null)
            return null;

        int line = CompilationUnitUtil.getLineNumberOfASTNode(original);
        if (line == CompilationUnitUtil.getLineNumberOfASTNode(renamed))
            return new RenameFieldEvent(original, renamed, line);
        else
            return null;
    }

}
