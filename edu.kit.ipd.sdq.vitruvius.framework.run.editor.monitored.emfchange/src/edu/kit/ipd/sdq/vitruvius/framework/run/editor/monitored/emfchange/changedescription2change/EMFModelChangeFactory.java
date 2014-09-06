package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

/**
 * A factory for {@link EMFModelChange} objects.
 */
final class EMFModelChangeFactory {

    private EMFModelChangeFactory() {

    }

    /**
     * Creates an {@link EMFModelChange} object.
     * 
     * @param change
     *            The model change to be represented by the new object.
     * 
     * @param uri
     *            The model's URI.
     * 
     * @return The created {@link EMFModelChange} object.
     */
    public static EMFModelChange createEMFModelChange(EChange change, VURI uri) {
        return new EMFModelChange(change, uri);
    }
}
