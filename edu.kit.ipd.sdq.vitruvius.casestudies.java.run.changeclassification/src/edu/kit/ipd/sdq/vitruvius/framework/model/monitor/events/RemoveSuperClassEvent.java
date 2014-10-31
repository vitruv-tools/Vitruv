package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class RemoveSuperClassEvent extends ChangedSuperTypesEvent {

    public RemoveSuperClassEvent(TypeDeclaration baseType, Type superClass) {
        super(baseType, superClass);
    }

    @Override
    public String toString() {
        return "RemoveSuperClassEvent  [baseType: " + this.baseType.getName() + ", superClass: " + this.superType + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
