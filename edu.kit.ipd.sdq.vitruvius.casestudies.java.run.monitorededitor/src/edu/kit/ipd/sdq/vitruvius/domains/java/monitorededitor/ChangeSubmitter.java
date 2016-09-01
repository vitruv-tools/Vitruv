package edu.kit.ipd.sdq.vitruvius.domains.java.monitorededitor;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;

public interface ChangeSubmitter {

    void submitChange(final VitruviusChange change);

}
