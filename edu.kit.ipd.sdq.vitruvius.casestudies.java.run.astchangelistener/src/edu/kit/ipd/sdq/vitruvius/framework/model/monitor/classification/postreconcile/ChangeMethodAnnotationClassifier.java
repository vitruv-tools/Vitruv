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
    protected List<? extends ChangeClassifyingEvent> classifyChange(final IJavaElementDelta delta,
            final CompilationUnit currentCompilationUnit, final CompilationUnit oldCompilationUnit) {
        final List<AnnotationEvent> returns = new ArrayList<AnnotationEvent>(1);
        final IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.METHOD && delta.getKind() == IJavaElementDelta.CHANGED
                && (delta.getFlags() & IJavaElementDelta.F_ANNOTATIONS) != 0) {
            final IMethod imethod = (IMethod) element;
            final IType itype = (IType) imethod.getParent();
            final int line = CompilationUnitUtil.getLineNumberOfMethod(imethod, itype.getElementName().toString(),
                    currentCompilationUnit);
            final MethodDeclaration changed = JavaModel2AST.getMethodDeclaration((IMethod) element,
                    currentCompilationUnit);
            for (final IJavaElementDelta annotationDelta : delta.getAnnotationDeltas()) {
                AnnotationEvent event = null;
                final MethodDeclaration original = CompilationUnitUtil.findMethodDeclarationOnLine(line,
                        oldCompilationUnit);
                if (annotationDelta.getKind() == IJavaElementDelta.ADDED) {
                    event = this.createAddMethodAnnotationEvent(changed, annotationDelta, original);
                } else if (annotationDelta.getKind() == IJavaElementDelta.REMOVED) {
                    event = this.createRemoveMethodAnnotationEvent(original, annotationDelta, changed);
                }
                if (event != null) {
                    returns.add(event);
                }
            }

        }
        return returns;
    }

    private RemoveMethodAnnotationEvent createRemoveMethodAnnotationEvent(final MethodDeclaration original,
            final IJavaElementDelta annotationDelta, final MethodDeclaration changed) {
        final IAnnotation iannotation = (IAnnotation) annotationDelta.getElement();
        final Annotation astAnnotation = JavaModel2AST.getAnnotation(iannotation, original);
        if (astAnnotation != null) {
            return new RemoveMethodAnnotationEvent(astAnnotation, changed);
        }
        return null;
    }

    private AddMethodAnnotationEvent createAddMethodAnnotationEvent(final MethodDeclaration changed,
            final IJavaElementDelta annotationDelta, final MethodDeclaration oldMethodDeclaration) {
        final IAnnotation iannotation = (IAnnotation) annotationDelta.getElement();
        final Annotation astAnnotation = JavaModel2AST.getAnnotation(iannotation, changed);
        if (astAnnotation != null) {
            return new AddMethodAnnotationEvent(oldMethodDeclaration, astAnnotation);
        }
        return null;
    }
}
