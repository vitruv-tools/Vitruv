package edu.kit.ipd.sdq.vitruvius.integration.handler;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils;
import edu.kit.ipd.sdq.vitruvius.commandexecuter.CommandExecutingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.ChangePreparingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.userinteractor.UserInteractor;
import edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.integration.LoggerConfigurator;
import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.IMModelImplExtractor;
import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.PCMRepositoryExtractor;
import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.PCMSystemExtractor;
import edu.kit.ipd.sdq.vitruvius.integration.strategies.PCMRepositoryIntegrationStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.strategies.PCMSystemIntegrationStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.util.UnorderedReferencesRespectingEqualityHelper;

/**
 * Handler for the context menu event created when a user rightclicks a model and selects
 * "integrate".
 *
 * @author Sven Leonhardt
 */
public class IntegrationHandler extends AbstractHandler {

    private int keepOldModel;
    private final Logger logger = LogManager.getRootLogger();
    private VSUMImpl vsum;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {

        LoggerConfigurator.setUpLogger();

        final ISelection selection = HandlerUtil.getActiveMenuSelection(event);
        final IStructuredSelection structuredSelection = (IStructuredSelection) selection;

        final Object firstElement = structuredSelection.getFirstElement();

        final UserInteracting dialog = new UserInteractor();
        this.keepOldModel = dialog.selectFromMessage(UserInteractionType.MODAL, "Keep old model?", "No", "Yes");

        // selection was single file -> repository or system
        if (firstElement instanceof IFile) {
            if (((IFile) firstElement).getFileExtension().equals("repository")) {
                this.integratePCMRepository((IResource) firstElement, null);
            }
            if (((IFile) firstElement).getFileExtension().equals("system")) {
                this.integratePCMSystem((IResource) firstElement);
            }

            // selection was JavaProject -> code integration
        } else if (firstElement instanceof IJavaProject) {
            this.integrateCode(firstElement);
        } else {
            throw new IllegalArgumentException("Selected entry must be a file or project");
        }

        // only for testing, so we don't need to manually delete it every time
        this.cleanUpIntegration();

        return null;
    }

    /**
     * Integrates a PCM respository model into Vitruvius.
     *
     * @param resource
     *            : the model which the user selected in eclipse
     * @param syncManager
     *            : the synchronization provider used for synchronizing the generated changes
     */
    private void integratePCMRepository(IResource resource, ChangeSynchronizing syncManager) {

        Logger.getRootLogger().setLevel(Level.ALL);

        // get URI from resource
        final URI uri = EMFBridge.getEMFPlatformUriForIResource(resource);

        // update the PCM parameter definitions
        resource = this.updateParameterDef(resource, uri);

        if (this.keepOldModel == 1) {
            this.saveOldModel(resource);
        }

        final PCMRepositoryIntegrationStrategy integrator = new PCMRepositoryIntegrationStrategy();

        if (syncManager == null) {
            syncManager = this.createVitruviusCore();
        }

        try {
            integrator.integrateModel(resource, syncManager);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (this.keepOldModel == 1) {
            this.compareWithNewModel(resource, uri);
        }

    }

    private void compareWithNewModel(final IResource resource, final URI uri) {
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

        this.logger.info(
                "Original Model and Integrated Model identical? " + comparator.equals(originalRepo, integratedRepo));
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
        final SyncManagerImpl syncManager = this.createVitruviusCore();

        // find all referenced repositories and integrate them first
        final EList<Repository> linkedRepositories = this.extractRepositories(resource);

        for (final Repository repository : linkedRepositories) {

            // convert EMF resource -> Eclipse IResource
            final IResource repositoryResource = ResourceHelper
                    .absoluteEmfResourceToWorkspaceRelativeIResource(repository.eResource());

            this.integratePCMRepository(repositoryResource, syncManager);
        }

        final PCMSystemIntegrationStrategy integrator = new PCMSystemIntegrationStrategy();

        try {
            integrator.integrateModel(resource, syncManager);
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Integrate source code into Vitruvius 1. Create JavaProject with src folder 2. Run SoMoX on
     * this project 3. Update PCM Parameter definition 4. Extract all necessary models from the
     * model folder
     *
     * @param firstElement
     *            : the project containing the source code
     */
    private void integrateCode(final Object firstElement) {

        Logger.getRootLogger().setLevel(Level.ALL);

        final IJavaProject javaProject = (IJavaProject) firstElement;
        final IProject project = javaProject.getProject();

        // IPath projectPath = project.getFullPath(); // workspace relative Path
        final IPath projectPath = project.getLocation(); // absolute path
        final IPath models = projectPath.append("model").addTrailingSeparator().append("internal_architecture_model");

        final IPath scdmPath = models.addFileExtension("sourcecodedecorator");
        final IPath pcmPath = models.addFileExtension("repository");
        final IPath srcPath = projectPath.append("src");

        final File f = scdmPath.toFile();
        if (!f.exists()) {
            throw new IllegalArgumentException("Run SoMoX first!");
        }

        final MetaRepositoryImpl metaRepository = PCMJavaUtils.createPCMJavaMetarepository();
        final VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository, metaRepository);
        vsum.getOrCreateAllCorrespondenceInstances(
                metaRepository.getMetamodel(VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE)));

        final ICreateCorrespondenceModel transformation = new PCMJaMoPPCorrespondenceModelTransformation(
                scdmPath.toString(), pcmPath.toString(), srcPath.toString(), vsum);

        transformation.createCorrespondences();
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
    private IResource updateParameterDef(IResource resource, URI uri) {
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

    /**
     * Save the old model
     *
     * @param resource
     *            old model
     */
    private void saveOldModel(final IResource resource) {

        IPath newPath = resource.getFullPath();
        newPath = this.createPathToOldModel(newPath);

        // save old model
        try {
            resource.copy(newPath, true, null);
        } catch (final CoreException e1) {
            this.logger.error("Could not save the old model.");
        }
    }

    private IPath createPathToOldModel(final IPath path) {
        final String extension = "_beforeIntegration.repository";
        IPath newPath = path.removeFileExtension();

        String fileName = newPath.lastSegment();
        fileName = fileName.concat(extension);

        newPath = newPath.removeLastSegments(1);
        newPath = newPath.append(fileName);
        return newPath;
    }

    /**
     * Extracts all repositories referenced by a system model.
     *
     * @param resource
     *            : PCM System
     * @return : List of referenced repositories
     */
    private EList<Repository> extractRepositories(final IResource resource) {

        final IMModelImplExtractor<System> extractor = new PCMSystemExtractor();
        final Resource model = RepositoryModelLoader.loadPCMResource(resource.getLocation().toString());

        final System system = extractor.getImpl(model);

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
     * Create underlying elements (MetaRepo, VSUM, ...)
     *
     * @return : SyncManager for synchronizing changes
     */
    private ChangeSynchronizing createVitruviusCore() {

        final MetaRepositoryImpl metaRepository = PCMJavaUtils.createPCMJavaMetarepository();

        this.vsum = new VSUMImpl(metaRepository, metaRepository, metaRepository);

        final Change2CommandTransformingProviding change2CommandTransformingProviding = new Change2CommandTransformingProvidingImpl();

        final EMFModelPropagationEngineImpl propagatingChange = new EMFModelPropagationEngineImpl(
                change2CommandTransformingProviding);

        final SyncManagerImpl syncManager = new SyncManagerImpl(this.vsum, propagatingChange, this.vsum, metaRepository,
                this.vsum, null);
        final ChangePreparing changePreparing = new ChangePreparingImpl(this.vsum, this.vsum);
        final CommandExecuting commandExecuting = new CommandExecutingImpl();

        final ChangeSynchronizing changeSynchronizing = new ChangeSynchronizerImpl(this.vsum,
                change2CommandTransformingProviding, this.vsum, this.vsum, validating, synchronisationListener,
                changePreparing, commandExecuting);

        return syncManager;

    }

    /**
     * Cleanup after integration
     */
    private void cleanUpIntegration() {

        // Delete vitruvius.meta project
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IWorkspaceRoot root = workspace.getRoot();
        final IProject metaFiles = root.getProject(VSUMConstants.VSUM_PROJECT_NAME);
        try {
            metaFiles.delete(true, null);
        } catch (final CoreException e) {
            e.printStackTrace();
        }
        try {
            root.refreshLocal(0, null);
        } catch (final CoreException e) {
            e.printStackTrace();
        }

        LogManager.shutdown();

    }

}
