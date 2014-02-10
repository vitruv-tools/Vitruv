package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EChange;

public class EMFModelChange extends Change {

    private EChange eChange;

    public EMFModelChange(final EChange eChange) {
        this.eChange = eChange;
    }

    public EChange getEChange() {
        return this.eChange;
    }
}
