package edu.kit.ipd.sdq.vitruvius.framework.model.monitor;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;

public interface ChangeSubmitter {

    void submitChange(final VitruviusChange change);

}
