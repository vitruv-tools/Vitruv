package tools.vitruv.framework.monitorededitor;

import tools.vitruv.framework.metamodel.ModelProviding;
import tools.vitruv.framework.modelsynchronization.ChangeSynchronizing;

public abstract class AbstractMonitoredEditor {
    protected final ChangeSynchronizing changeSynchronizing;
    protected final ModelProviding modelProviding;

    public AbstractMonitoredEditor(final ChangeSynchronizing changeSynchronizing, final ModelProviding modelProviding) {
        this.changeSynchronizing = changeSynchronizing;
        this.modelProviding = modelProviding;
    }

}
