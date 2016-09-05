package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.handler;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.PCMRepositoryExtractor;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.PCMSystemExtractor;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.strategies.PCMRepositoryIntegrationStrategy;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.strategies.PCMSystemIntegrationStrategy;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.util.PCMMetaModelConverter;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.PCMJavaRepositoryCreationUtil;
import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.RepositoryModelLoader;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.handler.IntegrationHandler;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.invariantcheckers.IMModelImplExtractor;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.traversal.util.UnorderedReferencesRespectingEqualityHelper;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.util.IntegrationUtil;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.util.ResourceHelper;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

public class PCMIntegrationHandler extends IntegrationHandler<IFile> {

    public PCMIntegrationHandler() {
        super(IFile.class);
    }

    private static final Logger logger = Logger.getLogger(PCMIntegrationHandler.class.getSimpleName());

    /**
     * Integrates a PCM respository model into Vitruvius.
     *
     * @param resource
     *            : the model which the user selected in eclipse
     * @param changeSynchronizing
     *            : the synchronization provider used for synchronizing the generated changes
     */
    private void integratePCMRepository(IResource resource, ChangeSynchronizing changeSynchronizing) {

        Logger.getRootLogger().setLevel(Level.ALL);

        // get URI from resource
        final URI uri = EMFBridge.getEMFPlatformUriForIResource(resource);

        // update the PCM parameter definitions
        resource = this.updateParameterDef(resource, uri);

        if (this.keepOldModel == 1) {
            this.saveOldModel(resource);
        }

        final PCMRepositoryIntegrationStrategy integrator = new PCMRepositoryIntegrationStrategy();

        if (changeSynchronizing == null) {
            final MetaRepositoryImpl pcmJavaMetarepository = PCMJavaRepositoryCreationUtil.createPCMJavaMetarepository();
            this.vsum = IntegrationUtil.createVSUM(pcmJavaMetarepository);
            changeSynchronizing = IntegrationUtil.createVitruviusCore(this.vsum, pcmJavaMetarepository);
        }

        try {
            integrator.integrateModel(resource, changeSynchronizing);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (this.keepOldModel == 1) {
            this.compareWithNewModel(resource, uri);
        }

    }

    /**
     * Integrates a PCM System model into Vitruvius.
     *
     * @param resource
     *            : the model which the user selected in eclipse
     */
    private void integratePCMSystem(final IResource resource) {

        Logger.getRootLogger().setLevel(Level.ALL);

        if (this.keepOldModel == 1) {
            this.saveOldModel(resource);
        }

        // create underlying elements (MetaRepo, VSUM,...)
        final MetaRepositoryImpl metaRepository = PCMJavaRepositoryCreationUtil.createPCMJavaMetarepository();
        final ChangeSynchronizing changeSynchronizing = IntegrationUtil.createVitruviusCore(metaRepository);

        // find all referenced repositories and integrate them first
        final EList<Repository> linkedRepositories = this.extractRepositories(resource);

        for (final Repository repository : linkedRepositories) {

            // convert EMF resource -> Eclipse IResource
            final IResource repositoryResource = ResourceHelper
                    .absoluteEmfResourceToWorkspaceRelativeIResource(repository.eResource());

            this.integratePCMRepository(repositoryResource, changeSynchronizing);
        }

        final PCMSystemIntegrationStrategy integrator = new PCMSystemIntegrationStrategy();

        try {
            integrator.integrateModel(resource, changeSynchronizing);
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Extracts all repositories referenced by a system model.
     *
     * @param resource
     *            : PCM System
     * @return : List of referenced repositories
     */
    private EList<Repository> extractRepositories(final IResource resource) {

        final IMModelImplExtractor<org.palladiosimulator.pcm.system.System> extractor = new PCMSystemExtractor();
        final Resource model = RepositoryModelLoader.loadPCMResource(resource.getLocation().toString());

        final org.palladiosimulator.pcm.system.System system = extractor.getImpl(model);

        final EList<Repository> linkedRepositories = new UniqueEList<Repository>();
        final EList<AssemblyContext> contexts = system.getAssemblyContexts__ComposedStructure();

        for (final AssemblyContext context : contexts) {

            final RepositoryComponent component = context.getEncapsulatedComponent__AssemblyContext();
            final Repository repository = component.getRepository__RepositoryComponent();

            linkedRepositories.add(repository);
        }
        return linkedRepositories;
    }

    /**
     * Updates PCM parameter definitions.
     *
     * @param resource
     *            : PCM repository
     * @param uri
     *            : PCM repository uri
     * @return : IResource to file with new parameter definitions
     */
    protected IResource updateParameterDef(IResource resource, URI uri) {
        // 1. Update model to current PCM version
        final File updatedModel = PCMMetaModelConverter.convertPCM(resource.getLocation(), false);

        // 2. update resource if model got updated
        if (PCMMetaModelConverter.isModelUpdated()) {

            // create new uri
            uri = uri.trimSegments(1);
            final String fileName = updatedModel.getName();
            uri = uri.appendSegment(fileName);

            // set resource to new file
            final IProject project = resource.getProject();
            IPath iPathForEMFUri = EMFBridge.getIPathForEMFUri(uri);

            // remove project folder from path
            iPathForEMFUri = iPathForEMFUri.removeFirstSegments(1);
            final IFile file = project.getFile(iPathForEMFUri);
            resource = file;
        }
        return resource;
    }

    protected void compareWithNewModel(final IResource resource, final URI uri) {
        // get integrated pcm model instance
        final ModelInstance integratedModelInstance = this.vsum.getModelInstanceOriginal(VURI.getInstance(uri));
        final IPath oldModelPath = this.createPathToOldModel(resource.getLocation());

        // load copy of the model before integration
        final Resource originalResource = RepositoryModelLoader.loadPCMResource(oldModelPath.toString());

        // load the two root objects from the model
        final PCMRepositoryExtractor pre = new PCMRepositoryExtractor();
        final Repository integratedRepo = pre.getImpl(integratedModelInstance.getResource());
        final Repository originalRepo = pre.getImpl(originalResource);

        // compare
        final UnorderedReferencesRespectingEqualityHelper comparator = new UnorderedReferencesRespectingEqualityHelper();

        logger.info(
                "Original Model and Integrated Model identical? " + comparator.equals(originalRepo, integratedRepo));
    }

    @Override
    protected void handleSelectedElement(final IFile firstElement) {
        if (firstElement.getFileExtension().equals("repository")) {
            this.integratePCMRepository(firstElement, null);
        }
        if (firstElement.getFileExtension().equals("system")) {
            this.integratePCMSystem(firstElement);
        }
    }

}
