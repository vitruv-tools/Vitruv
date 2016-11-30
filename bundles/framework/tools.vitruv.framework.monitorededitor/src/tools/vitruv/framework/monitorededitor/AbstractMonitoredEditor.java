package tools.vitruv.framework.monitorededitor;

import tools.vitruv.framework.vsum.VirtualModel;

public abstract class AbstractMonitoredEditor {
    protected final VirtualModel virtualModel;

    public AbstractMonitoredEditor(final VirtualModel virtualModel) {
        this.virtualModel = virtualModel;
    }

}
