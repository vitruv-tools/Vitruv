package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeMethodModifiersEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.CompilationUnitUtil;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.JavaModel2AST;

public class ChangeMethodModifiersClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<? extends ChangeClassifyingEvent> classifyChange(final IJavaElementDelta delta,
            final CompilationUnit currentCompilationUnit, final CompilationUnit oldCompilationUnit) {
        final List<ChangeMethodModifiersEvent> returns = new ArrayList<ChangeMethodModifiersEvent>(1);
        final IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.METHOD && delta.getKind() == IJavaElementDelta.CHANGED
                && (delta.getFlags() & IJavaElementDelta.F_MODIFIERS) != 0
                && (delta.getFlags() & IJavaElementDelta.F_ANNOTATIONS) == 0) {
            final IMethod imethod = (IMethod) element;
            final IType itype = (IType) imethod.getParent();
            final int line = CompilationUnitUtil.getLineNumberOfMethod(imethod, itype.getElementName().toString(),
                    currentCompilationUnit);
            final MethodDeclaration changed = JavaModel2AST.getMethodDeclaration((IMethod) element,
                    currentCompilationUnit);
            final MethodDeclaration original = CompilationUnitUtil.findMethodDeclarationOnLine(line,
                    oldCompilationUnit);
            if (changed != null && original != null) {
                returns.add(new ChangeMethodModifiersEvent(original, changed, line));
            }
        }
        return returns;
    }
}
