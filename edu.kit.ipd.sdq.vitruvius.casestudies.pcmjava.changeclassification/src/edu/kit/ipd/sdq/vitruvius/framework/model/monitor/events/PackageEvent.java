package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.core.resources.IResource;

public abstract class PackageEvent extends ChangeClassifyingEvent {

    public final String packageName;
    public final IResource iResource;

    public PackageEvent(String packageName, IResource iResource) {
        this.packageName = packageName;
        this.iResource = iResource;
    }
}
