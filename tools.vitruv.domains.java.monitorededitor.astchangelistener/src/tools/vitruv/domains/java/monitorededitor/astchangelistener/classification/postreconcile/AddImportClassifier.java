package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.AddImportEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class AddImportClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<AddImportEvent> classifyChange(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            CompilationUnit oldCompilationUnit) {
        List<AddImportEvent> adds = new ArrayList<AddImportEvent>(4);
        importContainerAdded(delta, adds, currentCompilationUnit, oldCompilationUnit);
        importDeclarationAdded(delta, adds, currentCompilationUnit, oldCompilationUnit);
        return adds;
    }

    private void importDeclarationAdded(IJavaElementDelta delta, List<AddImportEvent> adds,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.IMPORT_DECLARATION && delta.getKind() == IJavaElementDelta.ADDED) {
            ImportDeclaration importDeclaration = JavaModel2AST.getImportDeclaration((IImportDeclaration) element,
                    currentCompilationUnit);
            adds.add(new AddImportEvent(oldCompilationUnit, importDeclaration));
        }
    }

    private void importContainerAdded(IJavaElementDelta delta, List<AddImportEvent> adds,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.IMPORT_CONTAINER && delta.getKind() == IJavaElementDelta.ADDED) {
            for (Object importDeclaration : currentCompilationUnit.imports()) {
                adds.add(new AddImportEvent(oldCompilationUnit, (ImportDeclaration) importDeclaration));
            }
        }
    }

}
