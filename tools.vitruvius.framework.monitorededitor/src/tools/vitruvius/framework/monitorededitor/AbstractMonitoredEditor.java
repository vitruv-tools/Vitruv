package tools.vitruvius.framework.monitorededitor;

import tools.vitruvius.framework.metamodel.ModelProviding;
import tools.vitruvius.framework.modelsynchronization.ChangeSynchronizing;

public abstract class AbstractMonitoredEditor {
    protected final ChangeSynchronizing changeSynchronizing;
    protected final ModelProviding modelProviding;

    public AbstractMonitoredEditor(final ChangeSynchronizing changeSynchronizing, final ModelProviding modelProviding) {
        this.changeSynchronizing = changeSynchronizing;
        this.modelProviding = modelProviding;
    }

}
