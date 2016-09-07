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

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RemoveMethodEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class RemoveMethodClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<? extends ChangeClassifyingEvent> classifyChange(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        List<RemoveMethodEvent> removes = new ArrayList<RemoveMethodEvent>(1);
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.METHOD && delta.getKind() == IJavaElementDelta.REMOVED) {
            IMethod imethod = (IMethod) element;
            IType itype = (IType) imethod.getParent();
            int line = CompilationUnitUtil.getLineNumberOfMethod(imethod, itype.getElementName().toString(),
                    oldCompilationUnit);
            MethodDeclaration method = JavaModel2AST.getMethodDeclaration((IMethod) element, oldCompilationUnit);
            TypeDeclaration typeAfterRemove = JavaModel2AST.getTypeDeclaration(itype, currentCompilationUnit);
            removes.add(new RemoveMethodEvent(typeAfterRemove, method, line));
        }
        return removes;
    }
}
