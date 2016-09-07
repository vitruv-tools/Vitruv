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

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.AddFieldEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class AddFieldClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<AddFieldEvent> classifyChange(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            CompilationUnit oldCompilationUnit) {
        List<AddFieldEvent> adds = new ArrayList<AddFieldEvent>(1);
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.FIELD && delta.getKind() == IJavaElementDelta.ADDED) {
            VariableDeclarationFragment fieldFragment = JavaModel2AST.getVariableDeclarationFragmentByName(
                    (IField) element, currentCompilationUnit);
            int line = CompilationUnitUtil.getLineNumberOfASTNode(fieldFragment);
            IType itype = (IType) element.getParent();
            TypeDeclaration typeBeforeAdd = JavaModel2AST.getTypeDeclaration(itype, oldCompilationUnit);
            adds.add(new AddFieldEvent(typeBeforeAdd, fieldFragment, line));
        }
        return adds;
    }
}
