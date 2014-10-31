package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface ModelProviding extends ModelCopyProviding {
    ModelInstance getAndLoadModelInstanceOriginal(VURI uri);

    void saveModelInstanceOriginal(VURI vuri);
}
