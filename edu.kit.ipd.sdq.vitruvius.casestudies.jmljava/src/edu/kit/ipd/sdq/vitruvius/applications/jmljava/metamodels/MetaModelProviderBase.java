package edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.MetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.Metamodel;

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
