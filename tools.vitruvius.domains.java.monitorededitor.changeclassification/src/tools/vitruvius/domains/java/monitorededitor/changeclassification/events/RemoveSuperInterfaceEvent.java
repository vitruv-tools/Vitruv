package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class RemoveSuperInterfaceEvent extends ChangedSuperTypesEvent {

    public RemoveSuperInterfaceEvent(TypeDeclaration baseType, Type superClass) {
        super(baseType, superClass);
    }

    @Override
    public String toString() {
        return "RemoveSuperInterfaceEvent [baseType: " + this.baseType.getName() + ", superInterface: "
                + this.superType + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
