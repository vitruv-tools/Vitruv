package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils;

import java.util.HashMap;
import java.util.Map;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;

public class ModelProvidingMock implements ModelProviding {
    final Map<VURI, ModelInstance> mapping = new HashMap<VURI, ModelInstance>();
            
    public void add(ModelInstance model) {
        mapping.put(model.getURI(), model);
    }
    
    @Override
    public ModelInstance getAndLoadModelInstanceOriginal(VURI uri) {
        return mapping.get(uri);
    }

    @Override
    public ModelInstance getModelInstanceCopy(VURI uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveModelInstanceOriginal(VURI vuri) {
        throw new UnsupportedOperationException();
    }
}
