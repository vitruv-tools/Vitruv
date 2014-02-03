package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;

class EMFModelSynchronizer extends ConcreteChangeSynchronizer {

    public EMFModelSynchronizer(final ModelProviding modelProviding, final ChangeSynchronizing changeSynchronizing) {
        super(modelProviding, changeSynchronizing);
    }

    @Override
    public void synchronizeChange(final Change change, final VURI sourceModelURI) {
        // TODO Auto-generated method stub

    }

}
