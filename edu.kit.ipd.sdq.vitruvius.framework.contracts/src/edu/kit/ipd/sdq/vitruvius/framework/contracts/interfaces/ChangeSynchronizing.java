package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.RecordedChange;

public interface ChangeSynchronizing {
    /**
     * Resort changes and igores undos/redos.
     *
     * @param change
     *            list of changes
     * @return TODO
     */
    List<List<Change>> synchronizeChanges(RecordedChange change);

}