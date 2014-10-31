package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;

public interface ChangeSynchronizing {
    /**
     * Resort changes and igores undos/redos.
     *
     * @param change
     *            list of changes
     */
    void synchronizeChanges(List<Change> changes);

    // @HideableComfort
    void synchronizeChange(Change change);
}