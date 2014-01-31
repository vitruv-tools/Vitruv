package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface ChangeSynchronizing {
    /**
     * May resort changes and igores undos/redos.
     * 
     * @param change
     *            list of changes
     * @param sourceModelURI
     *            model in which all of the changes occured
     */
    void synchronizeChanges(List<Change> changes, VURI sourceModelURI);

    // @HideableComfort
    void synchronizeChange(Change change, VURI sourceModelURI);
}
