package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

public class RemoveImportEvent extends ChangeClassifyingEvent {

    public final CompilationUnit compilationUnitAfterRemove;
    public final ImportDeclaration importDeclaration;

    public RemoveImportEvent(CompilationUnit compilationUnitAfterRemove, ImportDeclaration importDeclaration) {
        this.compilationUnitAfterRemove = compilationUnitAfterRemove;
        this.importDeclaration = importDeclaration;
    }

    @Override
    public String toString() {
        return "RemoveImportEvent [import " + this.importDeclaration.getName().getFullyQualifiedName() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
