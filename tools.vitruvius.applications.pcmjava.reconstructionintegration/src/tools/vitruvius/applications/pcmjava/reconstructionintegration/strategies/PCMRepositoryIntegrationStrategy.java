package tools.vitruvius.applications.pcmjava.reconstructionintegration.strategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruvius.applications.pcmjava.reconstructionintegration.composite.CompositeTraversalStrategy;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.InvariantEnforcerFacadeBuilder;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.PCMRepositoryElementSelector;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.PCMRepositoryExtractor;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPBeginChar;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPJavaKeywords;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPSameIdentifier;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPSpecialChars;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPVitruviusKeywords;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPWhiteSpace;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.withocl.PJIE_ComponentInterfaceImplementsAmbiguity;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.repository.RepositoryTraversalStrategy;
import tools.vitruvius.domains.pcm.util.RepositoryModelLoader;
import tools.vitruvius.extensions.constructionsimulation.invariantcheckers.IMModelImplExtractor;
import tools.vitruvius.extensions.constructionsimulation.invariantcheckers.InvariantEnforcer;
import tools.vitruvius.extensions.constructionsimulation.traversal.ITraversalStrategy;
import tools.vitruvius.extensions.constructionsimulation.util.ResourceHelper;
import tools.vitruvius.framework.change.description.VitruviusChange;

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
     * @see tools.vitruvius.integration.strategies.IntegrationStategy#loadModel(java.lang.
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
     * tools.vitruvius.integration.strategies.IntegrationStategy#checkAndEnforceInvariants
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
     * tools.vitruvius.integration.strategies.IntegrationStategy#createChangeModels(org
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
