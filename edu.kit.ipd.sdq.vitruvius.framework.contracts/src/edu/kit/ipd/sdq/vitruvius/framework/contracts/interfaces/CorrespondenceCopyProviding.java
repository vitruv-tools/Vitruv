package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface CorrespondenceCopyProviding {
    CorrespondenceModel getCorrespondenceModelCopy(VURI model1URI, VURI model2URI);
}
