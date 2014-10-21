package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.core.resources.IResource;

public class RenamePackageEvent extends ChangeClassifyingEvent {

    public final String originalPackageName;
    public final String renamedPackageName;
    public final IResource originalIResource;
    public final IResource renamedIResource;

    public RenamePackageEvent(String originalPackageName, String renamedPackageName, IResource originalIResource,
            IResource renamedIResource) {
        this.originalPackageName = originalPackageName;
        this.renamedPackageName = renamedPackageName;
        this.originalIResource = originalIResource;
        this.renamedIResource = renamedIResource;
    }

    @Override
    public String toString() {
        return "RenamePackageEvent [original=" + this.originalPackageName + ", renamed=" + this.renamedPackageName
                + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
