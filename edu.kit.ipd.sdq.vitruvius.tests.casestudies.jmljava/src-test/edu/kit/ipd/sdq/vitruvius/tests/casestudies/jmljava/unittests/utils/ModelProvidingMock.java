package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;

public class ModelProvidingMock implements ModelProviding {
    final Map<VURI, ModelInstance> mapping = new HashMap<VURI, ModelInstance>();

    public void add(final ModelInstance model) {
        this.mapping.put(model.getURI(), model);
    }

    @Override
    public ModelInstance getAndLoadModelInstanceOriginal(final VURI uri) {
        return this.mapping.get(uri);
    }

    @Override
    public ModelInstance getModelInstanceCopy(final VURI uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveModelInstanceOriginal(final VURI vuri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TransactionalEditingDomain getTransactionalEditingDomain() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void attachTransactionalEditingDomain() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void detachTransactionalEditingDomain() {
        throw new UnsupportedOperationException();
    }
}
