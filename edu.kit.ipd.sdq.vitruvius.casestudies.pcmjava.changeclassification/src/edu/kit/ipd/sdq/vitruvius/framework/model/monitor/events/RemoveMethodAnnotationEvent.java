package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.Annotation;

public class RemoveMethodAnnotationEvent extends AnnotationEvent {

    public RemoveMethodAnnotationEvent(Annotation annotation) {
        super(annotation);
    }

    @Override
    public String toString() {
        return "RemoveMethodAnnotationEvent [annotation=" + this.annotation.getTypeName().getFullyQualifiedName() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
