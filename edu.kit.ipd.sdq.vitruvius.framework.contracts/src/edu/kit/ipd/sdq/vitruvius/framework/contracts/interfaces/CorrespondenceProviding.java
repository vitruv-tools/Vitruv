package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;

public interface CorrespondenceProviding extends CorrespondenceCopyProviding {
    CorrespondenceInstance getCorrespondenceInstanceOriginal(VURI model1URI, VURI model2URI);

    Set<InternalCorrespondenceInstance> getOrCreateAllCorrespondenceInstances(VURI model1uri);
}
