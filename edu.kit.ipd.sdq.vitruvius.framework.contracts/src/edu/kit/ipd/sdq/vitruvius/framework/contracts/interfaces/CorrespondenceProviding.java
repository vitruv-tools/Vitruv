package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;

public interface CorrespondenceProviding extends CorrespondenceCopyProviding {
    CorrespondenceInstance getCorrespondenceInstanceOriginal(VURI model1URI, VURI model2URI);

    void decorateCorrespondenceInstance(VURI mmAVURI, VURI mmBVURI, InternalCorrespondenceInstance originalCI,
            InternalCorrespondenceInstance decoratedCI);

    Set<InternalCorrespondenceInstance> getAllCorrespondenceInstances(VURI model1uri);
}
