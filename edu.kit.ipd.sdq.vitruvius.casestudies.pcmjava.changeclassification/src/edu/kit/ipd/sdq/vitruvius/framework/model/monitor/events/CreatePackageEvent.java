package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.core.resources.IResource;

public class CreatePackageEvent extends PackageEvent {

    public CreatePackageEvent(String packageName, IResource iResource) {
        super(packageName, iResource);
    }

    @Override
    public String toString() {
        return "CreatePackageEvent [package=" + this.packageName + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
