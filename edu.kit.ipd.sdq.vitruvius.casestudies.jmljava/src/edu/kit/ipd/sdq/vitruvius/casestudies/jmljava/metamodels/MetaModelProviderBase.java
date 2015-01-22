package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.MetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;

/**
 * Base class for meta-model providers, which still have to construct the meta-model.
 */
public abstract class MetaModelProviderBase implements MetaModelProvider {
 
    @Override
    public Metamodel getMetaModel() {
        return constructMetaModel();
    }
    
    /**
     * Creates the meta-model.
     * @return The meta-model.
     */
    protected abstract Metamodel constructMetaModel();

}
