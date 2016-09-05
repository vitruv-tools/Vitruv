package edu.kit.ipd.sdq.vitruvius.integration.pcm.strategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.Repository;

import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.RepositoryModelLoader;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.invariantcheckers.IMModelImplExtractor;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.invariantcheckers.InvariantEnforcer;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.traversal.ITraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.util.ResourceHelper;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.InvariantEnforcerFacadeBuilder;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.PCMRepositoryElementSelector;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.PCMRepositoryExtractor;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPBeginChar;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPJavaKeywords;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPSameIdentifier;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPSpecialChars;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPVitruviusKeywords;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPWhiteSpace;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.withocl.PJIE_ComponentInterfaceImplementsAmbiguity;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.traversal.composite.CompositeTraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.traversal.repository.RepositoryTraversalStrategy;

/**
 * This integration strategy is used for PCM repository models. It checks only JaMoPP invariants as
 * "target" meta model.
 *
 * @author Johannes Hoor
 *
 */
public class PCMRepositoryIntegrationStrategy extends PCMIntegrationStrategy {

    /**
     * Instantiates a new PCM standard repository integration strategy.
     */
    public PCMRepositoryIntegrationStrategy() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.kit.ipd.sdq.vitruvius.integration.strategies.IntegrationStategy#loadModel(java.lang.
     * String )
     */
    @Override
    protected Resource loadModel(final String path) {

        return RepositoryModelLoader.loadPCMResource(path);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.strategies.IntegrationStategy#checkAndEnforceInvariants
     * (org.eclipse.emf.ecore.resource.Resource)
     */
    @Override
    protected Resource checkAndEnforceInvariants(Resource model) {

        final List<InvariantEnforcer> enforcers = new ArrayList<>();

        // enforcers.add(new PCMtoJaMoPPComponentInterfaceImplementsAmbiguity());

        enforcers.add(new PJIE_ComponentInterfaceImplementsAmbiguity());

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PCMRepositoryElementSelector(),
                new PCMtoJaMoPPJavaKeywords()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PCMRepositoryElementSelector(),
                new PCMtoJaMoPPWhiteSpace()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PCMRepositoryElementSelector(),
                new PCMtoJaMoPPSpecialChars()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PCMRepositoryElementSelector(),
                new PCMtoJaMoPPVitruviusKeywords()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PCMRepositoryElementSelector(),
                new PCMtoJaMoPPBeginChar()));

        enforcers.add(new PCMtoJaMoPPSameIdentifier());

        for (final InvariantEnforcer enf : enforcers) {
            model = enf.loadEnforceReturn(model);
        }

        return model;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.strategies.IntegrationStategy#createChangeModels(org
     * .eclipse.core.resources.IResource, org.eclipse.emf.ecore.resource.Resource)
     */
    @Override
    protected EList<VitruviusChange> createChangeModels(final IResource originalResource, final Resource validModel) {

        // create correct URI for valid model
        final URI modelUri = ResourceHelper.createPlatformUriForResource(validModel);

        // traverse model and get ordered list of changes
        EList<VitruviusChange> changes = null;

        final IMModelImplExtractor<Repository> extractor = new PCMRepositoryExtractor();
        final Repository repository = extractor.getImpl(validModel);

        final ITraversalStrategy<Repository> repoTraversal = new RepositoryTraversalStrategy();
        final ITraversalStrategy<Repository> compTraversal = new CompositeTraversalStrategy();

        try {
            changes = repoTraversal.traverse(repository, modelUri, null);
            changes = compTraversal.traverse(repository, modelUri, changes);
        } catch (final UnsupportedOperationException e) {
            this.logger.error(e.getMessage());
        }
        return changes;

    }

}
