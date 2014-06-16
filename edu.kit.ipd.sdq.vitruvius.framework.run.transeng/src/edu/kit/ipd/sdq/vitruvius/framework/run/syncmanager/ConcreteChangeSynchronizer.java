package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.Set;

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

    abstract Set<VURI> synchronizeChange(Change change, VURI sourceModelURI);
}
