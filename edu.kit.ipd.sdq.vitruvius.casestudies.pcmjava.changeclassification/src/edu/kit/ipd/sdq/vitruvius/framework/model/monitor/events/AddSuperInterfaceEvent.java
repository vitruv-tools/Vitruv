package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class AddSuperInterfaceEvent extends ChangedSuperTypesEvent {

    public AddSuperInterfaceEvent(TypeDeclaration baseType, Type superInterface) {
        super(baseType, superInterface);
    }

    @Override
    public String toString() {
        return "AddSuperInterfaceEvent [baseType: " + this.baseType.getName() + ", superInterface: " + this.superType
                + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
