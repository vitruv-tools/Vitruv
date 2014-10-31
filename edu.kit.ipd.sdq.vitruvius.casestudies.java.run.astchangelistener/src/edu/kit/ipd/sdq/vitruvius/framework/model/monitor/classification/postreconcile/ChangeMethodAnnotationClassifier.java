package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.AddMethodAnnotationEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.AnnotationEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RemoveMethodAnnotationEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.CompilationUnitUtil;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.JavaModel2AST;

public class ChangeMethodAnnotationClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<? extends ChangeClassifyingEvent> classifyChange(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        List<AnnotationEvent> returns = new ArrayList<AnnotationEvent>(1);
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.METHOD && delta.getKind() == IJavaElementDelta.CHANGED
                && (delta.getFlags() & IJavaElementDelta.F_ANNOTATIONS) != 0) {
            IMethod imethod = (IMethod) element;
            IType itype = (IType) imethod.getParent();
            int line = CompilationUnitUtil.getLineNumberOfMethod(imethod, itype.getElementName().toString(),
                    currentCompilationUnit);
            MethodDeclaration changed = JavaModel2AST.getMethodDeclaration((IMethod) element, currentCompilationUnit);
            for (IJavaElementDelta annotationDelta : delta.getAnnotationDeltas()) {
                AnnotationEvent event = null;
                if (annotationDelta.getKind() == IJavaElementDelta.ADDED) {
                    event = createAddMethodAnnotationEvent(changed, annotationDelta);
                } else if (annotationDelta.getKind() == IJavaElementDelta.REMOVED) {
                    MethodDeclaration original = CompilationUnitUtil.findMethodDeclarationOnLine(line,
                            oldCompilationUnit);
                    event = createRemoveMethodAnnotationEvent(original, annotationDelta);
                }
                if (event != null)
                    returns.add(event);
            }

        }
        return returns;
    }

    private RemoveMethodAnnotationEvent createRemoveMethodAnnotationEvent(MethodDeclaration original,
            IJavaElementDelta annotationDelta) {
        IAnnotation iannotation = (IAnnotation) annotationDelta.getElement();
        Annotation astAnnotation = JavaModel2AST.getAnnotation(iannotation, original);
        if (astAnnotation != null)
            return new RemoveMethodAnnotationEvent(astAnnotation);
        return null;
    }

    private AddMethodAnnotationEvent createAddMethodAnnotationEvent(MethodDeclaration changed,
            IJavaElementDelta annotationDelta) {
        IAnnotation iannotation = (IAnnotation) annotationDelta.getElement();
        Annotation astAnnotation = JavaModel2AST.getAnnotation(iannotation, changed);
        if (astAnnotation != null)
            return new AddMethodAnnotationEvent(astAnnotation);
        return null;
    }
}
