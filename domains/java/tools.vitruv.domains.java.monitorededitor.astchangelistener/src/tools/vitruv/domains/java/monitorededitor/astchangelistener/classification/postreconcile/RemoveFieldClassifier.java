package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RemoveFieldEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class RemoveFieldClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<? extends ChangeClassifyingEvent> classifyChange(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        List<RemoveFieldEvent> removes = new ArrayList<RemoveFieldEvent>(1);
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.FIELD && delta.getKind() == IJavaElementDelta.REMOVED) {
            VariableDeclarationFragment fieldFragment = JavaModel2AST.getVariableDeclarationFragmentByName(
                    (IField) element, oldCompilationUnit);
            int line = CompilationUnitUtil.getLineNumberOfASTNode(fieldFragment);
            IType itype = (IType) element.getParent();
            TypeDeclaration typeAfterRemove = JavaModel2AST.getTypeDeclaration(itype, currentCompilationUnit);
            removes.add(new RemoveFieldEvent(typeAfterRemove, fieldFragment, line));
        }
        return removes;
    }
}
