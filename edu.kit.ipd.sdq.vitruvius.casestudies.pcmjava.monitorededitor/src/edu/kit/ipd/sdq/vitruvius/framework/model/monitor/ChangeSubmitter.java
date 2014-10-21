package edu.kit.ipd.sdq.vitruvius.framework.model.monitor;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;

public interface ChangeSubmitter {

    void submitChange(final Change change);

}
