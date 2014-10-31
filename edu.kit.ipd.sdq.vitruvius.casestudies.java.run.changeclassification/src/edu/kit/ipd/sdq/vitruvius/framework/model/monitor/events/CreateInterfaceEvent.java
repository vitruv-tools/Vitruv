package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CreateInterfaceEvent extends CreateTypeEvent {

    public CreateInterfaceEvent(CompilationUnit compilationUnitBeforeCreate, TypeDeclaration type) {
        super(compilationUnitBeforeCreate, type);
    }

    @Override
    public String toString() {
        return "CreateInterfaceEvent [interface=" + this.type.getName().getIdentifier() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
