package edu.kit.ipd.sdq.vitruvius.framework.model.monitor;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;

public interface ChangeSubmitter {

    void submitChange(final VitruviusChange change);

}
