package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public abstract class CreateTypeEvent extends ChangeClassifyingEvent {

    public final CompilationUnit compilationUnitBeforeCreate;
    public final TypeDeclaration type;

    public CreateTypeEvent(CompilationUnit compilationUnitBeforeCreate, TypeDeclaration type) {
        this.compilationUnitBeforeCreate = compilationUnitBeforeCreate;
        this.type = type;
    }

    @Override
    public String toString() {
        return "CreateTypeEvent [type=" + this.type.getName().getIdentifier() + "]";
    }

}
