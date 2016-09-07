package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public abstract class DeleteTypeEvent extends ChangeClassifyingEvent {

    public final CompilationUnit compilationUnitAfterDelete;
    public final TypeDeclaration type;

    public DeleteTypeEvent(CompilationUnit compilationUnitAfterDelete, TypeDeclaration type) {
        this.compilationUnitAfterDelete = compilationUnitAfterDelete;
        this.type = type;
    }

    @Override
    public String toString() {
        return "DeleteTypeEvent [type=" + this.type.getName().getIdentifier() + "]";
    }

}
