package tools.vitruv.applications.pcmjava.reconstructionintegration.strategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.reconstructionintegration.composite.CompositeTraversalStrategy;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.InvariantEnforcerFacadeBuilder;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.PCMRepositoryElementSelector;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.PCMRepositoryExtractor;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPBeginChar;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPJavaKeywords;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPSameIdentifier;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPSpecialChars;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPVitruviusKeywords;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPWhiteSpace;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.withocl.PJIE_ComponentInterfaceImplementsAmbiguity;
import tools.vitruv.applications.pcmjava.reconstructionintegration.repository.RepositoryTraversalStrategy;
import tools.vitruv.domains.pcm.util.RepositoryModelLoader;
import tools.vitruv.extensions.constructionsimulation.invariantcheckers.IMModelImplExtractor;
import tools.vitruv.extensions.constructionsimulation.invariantcheckers.InvariantEnforcer;
import tools.vitruv.extensions.constructionsimulation.traversal.ITraversalStrategy;
import tools.vitruv.extensions.constructionsimulation.util.ResourceHelper;
import tools.vitruv.framework.change.description.VitruviusChange;

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
     * @see tools.vitruv.integration.strategies.IntegrationStategy#loadModel(java.lang.
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
     * tools.vitruv.integration.strategies.IntegrationStategy#checkAndEnforceInvariants
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
     * tools.vitruv.integration.strategies.IntegrationStategy#createChangeModels(org
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
