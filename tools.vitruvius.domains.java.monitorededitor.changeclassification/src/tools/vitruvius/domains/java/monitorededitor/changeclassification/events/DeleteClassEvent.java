package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class DeleteClassEvent extends DeleteTypeEvent {

    public DeleteClassEvent(CompilationUnit compilationUnitAfterDelete, TypeDeclaration type) {
        super(compilationUnitAfterDelete, type);
    }

    @Override
    public String toString() {
        return "DeleteClassEvent [class=" + this.type.getName().getIdentifier() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }
}
