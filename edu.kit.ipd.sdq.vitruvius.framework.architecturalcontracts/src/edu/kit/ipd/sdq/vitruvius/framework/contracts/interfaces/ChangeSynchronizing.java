package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;
import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface ChangeSynchronizing {
    /**
     * Resort changes and igores undos/redos.
     * 
     * @param change
     *            list of changes
     * @param sourceModelURI
     *            model in which all of the changes occured
     */
    Set<VURI> synchronizeChanges(List<Change> changes, VURI sourceModelURI);

    // @HideableComfort
    Set<VURI> synchronizeChange(Change change, VURI sourceModelURI);
}