package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface CorrespondenceProviding {
    CorrespondenceInstance getCorrespondenceInstance(VURI model1URI, VURI model2URI);

    Set<CorrespondenceInstance> getAllCorrespondenceInstances(VURI model1uri);
}
