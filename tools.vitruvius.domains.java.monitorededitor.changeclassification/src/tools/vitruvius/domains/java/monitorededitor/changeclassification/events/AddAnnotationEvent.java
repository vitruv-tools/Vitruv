package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;

public class AddAnnotationEvent extends AnnotationEvent {

    public final BodyDeclaration bodyDeclaration;

    public AddAnnotationEvent(final BodyDeclaration bodyDeclaration, final Annotation annotation) {
        super(annotation);
        this.bodyDeclaration = bodyDeclaration;
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
