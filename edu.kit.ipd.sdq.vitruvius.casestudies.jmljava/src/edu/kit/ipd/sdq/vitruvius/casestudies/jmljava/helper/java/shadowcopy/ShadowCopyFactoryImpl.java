package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

/**
 * Implementation of the ShadowCopyFactory.
 * 
 * @author Stephan Seifermann
 */
public class ShadowCopyFactoryImpl implements ShadowCopyFactory {

    private final ModelURIProvider javaModelUriProvider;
    
    /**
     * Constructor.
     * @param javaModelUriProvider The model uri provider instance for Java models.
     */
    @Inject
    ShadowCopyFactoryImpl(@Named("JavaModelUriProvider") ModelURIProvider javaModelUriProvider) {
        this.javaModelUriProvider = javaModelUriProvider;
    }
    
    @Override
    public ShadowCopy create(CorrespondenceInstance ci, boolean useJMLCopy) {
        return new ShadowCopyImpl(ci, javaModelUriProvider, useJMLCopy);
    }

    @Override
    public ShadowCopy create(CorrespondenceInstance ci) {
        return new ShadowCopyImpl(ci, javaModelUriProvider);
    }

}
