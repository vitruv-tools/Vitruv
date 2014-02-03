package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;

abstract class ConcreteChangeSynchronizer {
    protected final ModelProviding modelProviding;
    protected final ChangeSynchronizing changeSynchronizing;

    public ConcreteChangeSynchronizer(final ModelProviding modelProviding, final ChangeSynchronizing changeSynchronizing) {
        this.modelProviding = modelProviding;
        this.changeSynchronizing = changeSynchronizing;
    }

    abstract void synchronizeChange(Change change, VURI sourceModelURI);
}
