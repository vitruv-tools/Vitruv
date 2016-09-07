package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;

public class RemoveAnnotationEvent extends AnnotationEvent {

    public final BodyDeclaration bodyAfterChange;

    public RemoveAnnotationEvent(final Annotation annotation, final BodyDeclaration bodyAfterChange) {
        super(annotation);
        this.bodyAfterChange = bodyAfterChange;
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
