package edu.kit.ipd.sdq.vitruvius.framework.monitorededitor;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;

public abstract class AbstractMonitoredEditor {
    protected final ChangeSynchronizing changeSynchronizing;
    protected final ModelProviding modelProviding;

    public AbstractMonitoredEditor(final ChangeSynchronizing changeSynchronizing, final ModelProviding modelProviding) {
        this.changeSynchronizing = changeSynchronizing;
        this.modelProviding = modelProviding;
    }

}
