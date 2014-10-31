package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassModifiersEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeInterfaceModifiersEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeTypeModifiersEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.CompilationUnitUtil;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.JavaModel2AST;

public class ChangeTypeModifiersClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<? extends ChangeClassifyingEvent> classifyChange(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        List<ChangeTypeModifiersEvent> returns = new ArrayList<ChangeTypeModifiersEvent>(1);
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.TYPE && delta.getKind() == IJavaElementDelta.CHANGED
                && (delta.getFlags() & IJavaElementDelta.F_MODIFIERS) != 0) {
            TypeDeclaration changed = JavaModel2AST.getTypeDeclaration((IType) element, currentCompilationUnit);
            int line = CompilationUnitUtil.getLineNumberOfASTNode(changed);
            TypeDeclaration original = CompilationUnitUtil.findTypeDeclarationOnLine(line, oldCompilationUnit);

            if (changed != null && original != null)
                returns.add(createChangeEvent(original, changed));
        }
        return returns;
    }

    private ChangeTypeModifiersEvent createChangeEvent(TypeDeclaration original, TypeDeclaration changed) {
        if (original.isInterface() && changed.isInterface())
            return new ChangeInterfaceModifiersEvent(original, changed);
        if (!original.isInterface() && !changed.isInterface())
            return new ChangeClassModifiersEvent(original, changed);
        //TODO what else? change from class to interface or vice versa seems odd...
        return null;
    }

}
