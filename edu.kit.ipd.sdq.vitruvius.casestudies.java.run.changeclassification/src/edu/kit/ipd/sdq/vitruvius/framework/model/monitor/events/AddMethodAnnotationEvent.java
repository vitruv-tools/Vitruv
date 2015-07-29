package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class AddMethodAnnotationEvent extends AnnotationEvent {

    public final MethodDeclaration methodBeforeAdd;

    public AddMethodAnnotationEvent(final MethodDeclaration methodBeforeAdd, final Annotation annotation) {
        super(annotation);
        this.methodBeforeAdd = methodBeforeAdd;
    }

    @Override
    public String toString() {
        return "AddMethodAnnotationEvent [annotation=" + this.annotation.getTypeName().getFullyQualifiedName() + "]";
    }

    @Override
    public void accept(final ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
