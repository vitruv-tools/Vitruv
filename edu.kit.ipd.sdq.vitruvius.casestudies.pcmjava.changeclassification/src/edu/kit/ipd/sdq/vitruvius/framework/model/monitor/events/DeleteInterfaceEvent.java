package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class DeleteInterfaceEvent extends DeleteTypeEvent {

    public DeleteInterfaceEvent(CompilationUnit compilationUnitAfterDelete, TypeDeclaration type) {
        super(compilationUnitAfterDelete, type);
    }

    @Override
    public String toString() {
        return "DeleteInterfaceEvent [interface=" + this.type.getName().getIdentifier() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }
}
