package tools.vitruvius.applications.pcmjava.reconstructionintegration.strategies;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.system.System;

import tools.vitruvius.applications.pcmjava.reconstructionintegration.composite.SystemTraversalStrategy;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.InvariantEnforcerFacadeBuilder;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.PCMSystemElementSelector;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.PCMSystemExtractor;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPBeginChar;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPJavaKeywords;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPSpecialChars;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPVitruviusKeywords;
import tools.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPWhiteSpace;
import tools.vitruvius.domains.pcm.util.RepositoryModelLoader;
import tools.vitruvius.extensions.constructionsimulation.invariantcheckers.IMModelImplExtractor;
import tools.vitruvius.extensions.constructionsimulation.invariantcheckers.InvariantEnforcer;
import tools.vitruvius.extensions.constructionsimulation.traversal.ITraversalStrategy;
import tools.vitruvius.framework.change.description.VitruviusChange;

/**
 * This integration strategy is used for PCM system models. It checks only JaMoPP invariants as
 * "target" meta model.
 *
 * @author Sven Leonhardt
 */
public class PCMSystemIntegrationStrategy extends PCMIntegrationStrategy {

    /**
     * Instantiates a new PCM system integration strategy.
     */
    public PCMSystemIntegrationStrategy() {
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

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PCMSystemElementSelector(),
                new PCMtoJaMoPPJavaKeywords()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PCMSystemElementSelector(),
                new PCMtoJaMoPPSpecialChars()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PCMSystemElementSelector(),
                new PCMtoJaMoPPVitruviusKeywords()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PCMSystemElementSelector(),
                new PCMtoJaMoPPWhiteSpace()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PCMSystemElementSelector(),
                new PCMtoJaMoPPBeginChar()));

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
    protected EList<VitruviusChange> createChangeModels(final IResource resource, final Resource validModel) {

        // create correct URI for valid model
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IPath workspaceDir = workspace.getRoot().getLocation();
        String workspaceString = workspaceDir.toString();
        workspaceString = workspaceString.replace("/", File.separator);

        final String platRelativeModelLoc = validModel.getURI().toFileString().replace(workspaceString, "");
        final URI modelUri = URI.createPlatformResourceURI(platRelativeModelLoc, true);

        // traverse model and get ordered list of changes
        EList<VitruviusChange> changes = null;

        final IMModelImplExtractor<?> extractor = new PCMSystemExtractor();
        final System system = (System) extractor.getImpl(validModel);

        final ITraversalStrategy<System> systemTraversal = new SystemTraversalStrategy();

        try {
            changes = systemTraversal.traverse(system, modelUri, null);
        } catch (final UnsupportedOperationException e) {
            this.logger.error(e.getMessage());
        }
        return changes;

    }

}
