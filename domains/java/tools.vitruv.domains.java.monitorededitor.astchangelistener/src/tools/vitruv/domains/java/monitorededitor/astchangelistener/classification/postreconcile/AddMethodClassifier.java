package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.AddMethodEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class AddMethodClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<AddMethodEvent> classifyChange(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            CompilationUnit oldCompilationUnit) {
        List<AddMethodEvent> adds = new ArrayList<AddMethodEvent>(1);
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.METHOD && delta.getKind() == IJavaElementDelta.ADDED) {
            IMethod imethod = (IMethod) element;
            IType itype = (IType) imethod.getParent();
            int line = CompilationUnitUtil.getLineNumberOfMethod(imethod, itype.getElementName().toString(),
                    currentCompilationUnit);
            MethodDeclaration method = JavaModel2AST.getMethodDeclaration((IMethod) element, currentCompilationUnit);
            TypeDeclaration typeBeforeAdd = JavaModel2AST.getTypeDeclaration(itype, oldCompilationUnit);
            adds.add(new AddMethodEvent(typeBeforeAdd, method, line));
        }
        return adds;
    }
}
