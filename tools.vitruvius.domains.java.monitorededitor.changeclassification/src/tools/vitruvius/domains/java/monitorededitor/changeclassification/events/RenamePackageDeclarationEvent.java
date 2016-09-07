package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.PackageDeclaration;

public class RenamePackageDeclarationEvent extends ChangeClassifyingEvent {

    public final PackageDeclaration packageDeclaration;

    public RenamePackageDeclarationEvent(PackageDeclaration packageDeclaration) {
        this.packageDeclaration = packageDeclaration;
    }

    @Override
    public String toString() {
        return "AddPackageDeclarationEvent [package=" + this.packageDeclaration.getName().getFullyQualifiedName() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
