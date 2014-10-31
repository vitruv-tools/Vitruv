package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

public class AddImportEvent extends ChangeClassifyingEvent {

    public final CompilationUnit compilationUnitBeforeAdd;
    public final ImportDeclaration importDeclaration;

    public AddImportEvent(CompilationUnit compilationUnitBeforeImport, ImportDeclaration importDeclaration) {
        this.compilationUnitBeforeAdd = compilationUnitBeforeImport;
        this.importDeclaration = importDeclaration;
    }

    @Override
    public String toString() {
        return "AddImportEvent [import " + this.importDeclaration.getName().getFullyQualifiedName() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
