package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class RemoveMethodAnnotationEvent extends AnnotationEvent {

    public final MethodDeclaration methodAfterChange;

    public RemoveMethodAnnotationEvent(final Annotation annotation, final MethodDeclaration methodAfterChange) {
        super(annotation);
        this.methodAfterChange = methodAfterChange;
    }

    @Override
    public String toString() {
        return "RemoveMethodAnnotationEvent [annotation=" + this.annotation.getTypeName().getFullyQualifiedName() + "]";
    }

    @Override
    public void accept(final ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
