package tools.vitruv.applications.jmljava.metamodels;

import tools.vitruv.applications.jmljava.vitruvius.changesynchronizer.extensions.MetaModelProvider;
import tools.vitruv.framework.metamodel.Metamodel;

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
