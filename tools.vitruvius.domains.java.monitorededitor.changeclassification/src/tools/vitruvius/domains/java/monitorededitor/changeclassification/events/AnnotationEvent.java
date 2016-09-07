package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Annotation;

public abstract class AnnotationEvent extends ChangeClassifyingEvent {

    public final Annotation annotation;

    public AnnotationEvent(Annotation annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "AnnotationEvent [annotation=" + this.annotation.getTypeName().getFullyQualifiedName() + "]";
    }

}
