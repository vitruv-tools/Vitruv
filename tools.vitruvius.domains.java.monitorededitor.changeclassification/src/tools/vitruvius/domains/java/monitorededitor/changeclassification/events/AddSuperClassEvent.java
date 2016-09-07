package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class AddSuperClassEvent extends ChangedSuperTypesEvent {

    public AddSuperClassEvent(TypeDeclaration baseType, Type superClass) {
        super(baseType, superClass);
    }

    @Override
    public String toString() {
        return "AddSuperClassEvent [baseType: " + this.baseType.getName() + ", superClass: " + this.superType + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
