package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.IAnnotatable;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.AddAnnotationEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.AnnotationEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RemoveAnnotationEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class ChangeAnnotationClassifier extends SingleNodeChangeClassifier {

    private static final Logger logger = Logger.getLogger(ChangeAnnotationClassifier.class.getName());

    @Override
    protected List<? extends ChangeClassifyingEvent> classifyChange(final IJavaElementDelta delta,
            final CompilationUnit currentCompilationUnit, final CompilationUnit oldCompilationUnit) {
        final List<AnnotationEvent> returns = new ArrayList<AnnotationEvent>(1);
        final IJavaElement element = delta.getElement();
        if (delta.getKind() == IJavaElementDelta.CHANGED && (delta.getFlags() & IJavaElementDelta.F_ANNOTATIONS) != 0) {
            /*
             * since delta.getgetAnnotationDeltas is empty always (even if
             * IJavaElementDelta.F_ANNOTATIONS us != 0), we need to figure out which annotation has
             * been changed manually :(
             *
             * hack: we only support one annotation per element.--> if iAnnotatable.getAnnotations()
             * is empty, we assume that the existing annotation has been removed. if it is not
             * empty, we assume that the first annotation within iAnnotatable.getAnnotations(); has
             * been added
             */
            final IAnnotatable iAnnotatable = (IAnnotatable) element;
            IAnnotation[] annotations = null;
            final BodyDeclaration changed = JavaModel2AST.getBodyDeclaration(iAnnotatable, currentCompilationUnit);
            final int line = CompilationUnitUtil.getLineNumberOfAnnotation(iAnnotatable, currentCompilationUnit);
            BodyDeclaration original = CompilationUnitUtil.findBodyDeclarationOnLine(line, oldCompilationUnit);
            if (null == original) {
                original = CompilationUnitUtil.findBodyDeclarationOnLine(line + 1, oldCompilationUnit);
            }
            if (null == original) {
                original = CompilationUnitUtil.findBodyDeclarationOnLine(line - 1, oldCompilationUnit);
            }
            if (null == original) {
                return returns;
            }
            try {
                annotations = iAnnotatable.getAnnotations();
            } catch (final JavaModelException e) {
                e.printStackTrace();
                logger.info("JavaModelException occured during iAnnotatable.getAnnotations() " + e);
                return returns;
            }
            if (1 == annotations.length) {
                // annotation added
                final IAnnotation addedAnnotation = annotations[0];
                final AnnotationEvent event = this.createAddAnnotationEvent(changed, addedAnnotation, original);
                returns.add(event);
            } else if (0 == annotations.length) {
                final AnnotationEvent event = this.createRemoveAnnotationEvent(original, changed);
                returns.add(event);
            } else {
                logger.info("more than one annotation are currently not supported by the Java monitor");
            }
        }
        return returns;
    }

    private RemoveAnnotationEvent createRemoveAnnotationEvent(final BodyDeclaration original,
            final BodyDeclaration changed) {
        Annotation astAnnotation = null;
        for (final Object object : original.modifiers()) {
            if (object instanceof Annotation) {
                astAnnotation = (Annotation) object;
            }
        }
        if (astAnnotation != null) {
            return new RemoveAnnotationEvent(astAnnotation, changed);
        }
        return null;
    }

    private AddAnnotationEvent createAddAnnotationEvent(final BodyDeclaration changed, final IAnnotation iannotation,
            final BodyDeclaration oldBodyDeclaration) {
        final Annotation astAnnotation = JavaModel2AST.getAnnotation(iannotation, changed);
        if (astAnnotation != null) {
            return new AddAnnotationEvent(oldBodyDeclaration, astAnnotation);
        }
        return null;
    }
}
