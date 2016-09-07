package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RemoveImportEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class RemoveImportClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<RemoveImportEvent> classifyChange(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            CompilationUnit oldCompilationUnit) {
        List<RemoveImportEvent> removes = new ArrayList<RemoveImportEvent>(4);
        importContainerRemoved(delta, removes, currentCompilationUnit, oldCompilationUnit);
        importDeclarationRemoved(delta, removes, currentCompilationUnit, oldCompilationUnit);
        return removes;
    }

    private void importDeclarationRemoved(IJavaElementDelta delta, List<RemoveImportEvent> removes,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.IMPORT_DECLARATION && delta.getKind() == IJavaElementDelta.REMOVED) {
            ImportDeclaration importDeclaration = JavaModel2AST.getImportDeclaration((IImportDeclaration) element,
                    oldCompilationUnit);
            removes.add(new RemoveImportEvent(currentCompilationUnit, importDeclaration));
        }
    }

    private void importContainerRemoved(IJavaElementDelta delta, List<RemoveImportEvent> removes,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.IMPORT_CONTAINER && delta.getKind() == IJavaElementDelta.REMOVED) {
            // every remaining import declaration has been removed
            for (Object importDeclaration : oldCompilationUnit.imports()) {
                removes.add(new RemoveImportEvent(currentCompilationUnit, (ImportDeclaration) importDeclaration));
            }
        }
    }
}
