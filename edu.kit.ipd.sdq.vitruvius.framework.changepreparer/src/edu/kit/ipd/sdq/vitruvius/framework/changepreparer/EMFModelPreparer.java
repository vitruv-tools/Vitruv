package edu.kit.ipd.sdq.vitruvius.framework.changepreparer;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;

class EMFModelPreparer extends ConcreteChangePreparer {

    @Override
    public Change prepareChange(final Change change) {
        return change;
    }
}
