package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.PackageDeclaration;

public class ChangePackageDeclarationEvent extends ChangeClassifyingEvent {

    public final PackageDeclaration original;
    public final PackageDeclaration changed;

    public ChangePackageDeclarationEvent(PackageDeclaration original, PackageDeclaration changed) {
        this.original = original;
        this.changed = changed;
    }

    @Override
    public String toString() {
        return "ChangePackageDeclarationEvent [original=" + this.original.getName() + ", renamed="
                + this.changed.getName() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
