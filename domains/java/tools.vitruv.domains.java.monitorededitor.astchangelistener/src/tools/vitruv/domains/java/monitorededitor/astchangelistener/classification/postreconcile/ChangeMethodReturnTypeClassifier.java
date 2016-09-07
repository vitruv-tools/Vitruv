package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeMethodReturnTypeEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class ChangeMethodReturnTypeClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<? extends ChangeClassifyingEvent> classifyChange(final IJavaElementDelta delta,
            final CompilationUnit currentCompilationUnit, final CompilationUnit oldCompilationUnit) {
        final List<ChangeMethodReturnTypeEvent> returns = new ArrayList<ChangeMethodReturnTypeEvent>(1);
        final IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.METHOD && delta.getKind() == IJavaElementDelta.CHANGED
                && (delta.getFlags() & IJavaElementDelta.F_ANNOTATIONS) == 0) {
            final IMethod imethod = (IMethod) element;
            final IType itype = (IType) imethod.getParent();
            final int line = CompilationUnitUtil.getLineNumberOfMethod(imethod, itype.getElementName().toString(),
                    currentCompilationUnit);
            final MethodDeclaration changed = JavaModel2AST.getMethodDeclaration((IMethod) element,
                    currentCompilationUnit);
            final MethodDeclaration original = CompilationUnitUtil.findMethodDeclarationOnLine(line,
                    oldCompilationUnit);

            if (changed != null && original != null
                    && !changed.getReturnType2().subtreeMatch(AST_MATCHER, original.getReturnType2())) {
                returns.add(new ChangeMethodReturnTypeEvent(original, changed, line));
            }
        }
        return returns;
    }
}
