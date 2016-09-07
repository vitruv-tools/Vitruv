package tools.vitruvius.domains.java.monitorededitor;

import tools.vitruvius.framework.change.description.VitruviusChange;

public interface ChangeSubmitter {

    void submitChange(final VitruviusChange change);

}
