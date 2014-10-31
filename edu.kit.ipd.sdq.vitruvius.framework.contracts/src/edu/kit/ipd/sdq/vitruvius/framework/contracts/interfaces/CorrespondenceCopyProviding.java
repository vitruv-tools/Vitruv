package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface CorrespondenceCopyProviding {
    CorrespondenceInstance getCorrespondenceInstanceCopy(VURI model1URI, VURI model2URI);
}
