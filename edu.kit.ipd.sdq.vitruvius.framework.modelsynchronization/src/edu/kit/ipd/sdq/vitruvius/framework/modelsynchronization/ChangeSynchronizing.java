package edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;

public interface ChangeSynchronizing {
    /**
     * Resort changes and igores undos/redos.
     *
     * @param change
     *            list of changes
     * @return TODO
     */
    List<List<VitruviusChange>> synchronizeChange(VitruviusChange change);

}