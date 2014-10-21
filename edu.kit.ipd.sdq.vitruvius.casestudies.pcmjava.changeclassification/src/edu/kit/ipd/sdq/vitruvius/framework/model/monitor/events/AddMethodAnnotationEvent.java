package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.Annotation;

public class AddMethodAnnotationEvent extends AnnotationEvent {

    public AddMethodAnnotationEvent(Annotation annotation) {
        super(annotation);
    }

    @Override
    public String toString() {
        return "AddMethodAnnotationEvent [annotation=" + this.annotation.getTypeName().getFullyQualifiedName() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
