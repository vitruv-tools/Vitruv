package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public abstract class ChangedSuperTypesEvent extends ChangeClassifyingEvent {

    public final TypeDeclaration baseType;
    public final Type superType;

    public ChangedSuperTypesEvent(TypeDeclaration baseType, Type superType) {
        this.baseType = baseType;
        this.superType = superType;
    }

    @Override
    public String toString() {
        return "ChangedSuperTypesEvent [baseType: " + this.baseType.getName() + ", superType: " + this.superType + "]";
    }

}
