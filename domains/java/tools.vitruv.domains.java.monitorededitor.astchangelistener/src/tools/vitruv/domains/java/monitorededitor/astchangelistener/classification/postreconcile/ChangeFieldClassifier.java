package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public abstract class ChangeFieldClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<? extends ChangeClassifyingEvent> classifyChange(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        List<ChangeClassifyingEvent> changes = new ArrayList<ChangeClassifyingEvent>(1);
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.FIELD && delta.getKind() == IJavaElementDelta.CHANGED) {
            FieldDeclaration oldField = JavaModel2AST.getFieldDeclarationByName((IField) element, oldCompilationUnit);
            FieldDeclaration changedField = JavaModel2AST.getFieldDeclarationByFullSignature((IField) element,
                    currentCompilationUnit);
            int line = CompilationUnitUtil.getLineNumberOfASTNode(changedField);
            ChangeClassifyingEvent change = classifyChange((IField) element, oldField, changedField, line);
            if (change != null)
                changes.add(change);
        }
        return changes;
    }

    protected abstract ChangeClassifyingEvent classifyChange(IField ifield, FieldDeclaration original,
            FieldDeclaration changed, int line);
}
