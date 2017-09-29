package tools.vitruv.framework.vsum.repositories;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder;
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolver;
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolverImpl;
import tools.vitruv.framework.change.uuid.UuidResolver;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.correspondence.CorrespondenceModelImpl;
import tools.vitruv.framework.correspondence.CorrespondenceProviding;
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.repository.VitruvDomainRepository;
import tools.vitruv.framework.tuid.TuidManager;
import tools.vitruv.framework.util.ResourceSetUtil;
import tools.vitruv.framework.util.bridges.EcoreResourceBridge;
import tools.vitruv.framework.util.command.EMFCommandBridge;
import tools.vitruv.framework.util.command.VitruviusRecordingCommand;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.ModelRepository;
import tools.vitruv.framework.vsum.helper.FileSystemHelper;

public class ResourceRepositoryImpl implements ModelRepository, CorrespondenceProviding {
    private static final Logger logger = Logger.getLogger(ResourceRepositoryImpl.class.getSimpleName());

    private final ResourceSet resourceSet;
    private final VitruvDomainRepository metamodelRepository;

    private final Map<VURI, ModelInstance> modelInstances;
    private InternalCorrespondenceModel correspondenceModel;
    private final FileSystemHelper fileSystemHelper;
    private final File folder;
    private final AtomicEmfChangeRecorder changeRecorder;
    private UuidGeneratorAndResolver uuidGeneratorAndResolver;

    public UuidGeneratorAndResolver getUuidGeneratorAndResolver() {
        return this.uuidGeneratorAndResolver;
    }

    public ResourceRepositoryImpl(final File folder, final VitruvDomainRepository metamodelRepository) {
        this(folder, metamodelRepository, null);
    }

    public ResourceRepositoryImpl(final File folder, final VitruvDomainRepository metamodelRepository,
            final ClassLoader classLoader) {
        this.metamodelRepository = metamodelRepository;
        this.folder = folder;

        this.resourceSet = new ResourceSetImpl();
        ResourceSetUtil.addExistingFactoriesToResourceSet(this.resourceSet);

        this.modelInstances = new HashMap<VURI, ModelInstance>();
        this.fileSystemHelper = new FileSystemHelper(this.folder);

        initializeUuidProviderAndResolver();
        // TODO HK Use one monitor per model and turn on strict mode depending on the kind of model/view
        // (textual vs. semantic)
        this.changeRecorder = new AtomicEmfChangeRecorder(this.uuidGeneratorAndResolver, this.uuidGeneratorAndResolver,
                false);

        initializeCorrespondenceModel();
        loadVURIsOfVSMUModelInstances();
    }

    /**
     * Supports three cases: 1) get registered 2) create non-existing 3) get unregistered but existing
     * that contains at most a root element without children. But throws an exception if an instance
     * that contains more than one element exists at the uri.
     *
     * DECISION Since we do not throw an exception (which can happen in 3) we always return a valid
     * model. Hence the caller do not have to check whether the retrieved model is null.
     */
    private ModelInstance getAndLoadModelInstanceOriginal(final VURI modelURI,
            final boolean forceLoadByDoingUnloadBeforeLoad) {
        final ModelInstance modelInstance = getModelInstanceOriginal(modelURI);
        try {
            if (modelURI.getEMFUri().toString().startsWith("pathmap")
                    || URIUtil.existsResourceAtUri(modelURI.getEMFUri())) {
                modelInstance.load(getMetamodelByURI(modelURI).getDefaultLoadOptions(),
                        forceLoadByDoingUnloadBeforeLoad);
            }
        } catch (RuntimeException re) {
            // could not load model instance --> this should only be the case when the
            // model is not existing yet
            logger.info("Exception during loading of model instance " + modelInstance + " occured: " + re);
        }
        return modelInstance;
    }

    @Override
    public ModelInstance getModel(final VURI modelURI) {
        return getAndLoadModelInstanceOriginal(modelURI, false);
    }

    @Override
    public void forceReloadModelIfExisting(final VURI modelURI) {
        if (existsModelInstance(modelURI)) {
            getAndLoadModelInstanceOriginal(modelURI, true);
        }
    }

    private ModelInstance getModelInstanceOriginal(final VURI modelURI) {
        ModelInstance modelInstance = this.modelInstances.get(modelURI);
        if (modelInstance == null) {
            createRecordingCommandAndExecuteCommandOnTransactionalDomain(() -> {
                // case 2 or 3
                ModelInstance internalModelInstance = getOrCreateUnregisteredModelInstance(modelURI);
                registerModelInstance(modelURI, internalModelInstance);
                return null;
            });
            modelInstance = this.modelInstances.get(modelURI);
        }
        return modelInstance;
    }

    private void registerModelInstance(final VURI modelUri, final ModelInstance modelInstance) {
        ResourceRepositoryImpl.this.modelInstances.put(modelUri, modelInstance);
        ResourceRepositoryImpl.this.changeRecorder.addToRecording(modelInstance.getResource());
        saveVURIsOfVsumModelInstances();
    }

    private boolean existsModelInstance(final VURI modelURI) {
        return this.modelInstances.containsKey(modelURI);
    }

    private void saveModelInstance(final ModelInstance modelInstance) {
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                VitruvDomain metamodel = getMetamodelByURI(modelInstance.getURI());
                Resource resourceToSave = modelInstance.getResource();
                final Map<Object, Object> saveOptions = metamodel != null ? metamodel.getDefaultSaveOptions()
                        : Collections.emptyMap();
                try {
                    // we allow resources without a domain for internal uses.
                    EcoreResourceBridge.saveResource(resourceToSave, saveOptions);
                } catch (IOException e) {
                    throw new RuntimeException("Could not save VURI + " + modelInstance.getURI() + ": " + e);
                }
                return null;
            }

        });
    }

    @Override
    public void persistAsRoot(final EObject rootEObject, final VURI vuri) {
        final ModelInstance modelInstance = getModelInstanceOriginal(vuri);
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                TuidManager.getInstance().registerObjectUnderModification(rootEObject);
                final Resource resource = modelInstance.getResource();

                resource.getContents().add(rootEObject);
                resource.setModified(true);

                logger.debug("Create model with resource: " + resource);
                TuidManager.getInstance().updateTuidsOfRegisteredObjects();
                // Usually we should deregister the object, but since we do not know if it was
                // registered before and if the other objects should still be registered
                // we cannot remove it or flush the registry
                return null;
            }
        });
    }

    @Override
    public void saveAllModels() {
        logger.debug("Saving all models of model repository for VSUM: " + this.folder);
        saveAllChangedModels();
        saveAllChangedCorrespondenceModels();
    }

    private void deleteEmptyModels() {
        List<VURI> vurisToDelete = new ArrayList<VURI>();
        for (ModelInstance modelInstance : this.modelInstances.values()) {
            if (modelInstance.getRootElements().isEmpty()) {
                vurisToDelete.add(modelInstance.getURI());
            }
        }
        for (VURI vuri : vurisToDelete) {
            deleteModel(vuri);
        }
    }

    private void saveAllChangedModels() {
        deleteEmptyModels();
        for (ModelInstance modelInstance : this.modelInstances.values()) {
            Resource resourceToSave = modelInstance.getResource();
            if (resourceToSave.isModified()) {
                logger.debug("  Saving resource: " + resourceToSave);
                saveModelInstance(modelInstance);
            }
        }
    }

    private void saveAllChangedCorrespondenceModels() {
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                logger.debug("  Saving correspondence model: "
                        + ResourceRepositoryImpl.this.correspondenceModel.getResource());
                ResourceRepositoryImpl.this.correspondenceModel.saveModel();
                ResourceRepositoryImpl.this.correspondenceModel.resetChangedAfterLastSave();
                return null;
            }
        });
    }

    private ModelInstance getOrCreateUnregisteredModelInstance(final VURI modelURI) {
        String fileExtension = modelURI.getFileExtension();
        VitruvDomain metamodel = this.metamodelRepository.getDomain(fileExtension);
        if (metamodel == null) {
            throw new RuntimeException("Cannot create a new model instance at the uri '" + modelURI
                    + "' because no metamodel is registered for the file extension '" + fileExtension + "'!");
        }
        return loadModelInstance(modelURI, metamodel);
    }

    private ModelInstance loadModelInstance(final VURI modelURI, final VitruvDomain metamodel) {
        URI emfURI = modelURI.getEMFUri();
        Resource modelResource = URIUtil.loadResourceAtURI(emfURI, this.resourceSet, metamodel.getDefaultLoadOptions());
        ModelInstance modelInstance = new ModelInstance(modelURI, modelResource);
        return modelInstance;
    }

    /**
     * Create a model instance for which it’s not necessary to have a domain.
     *
     * @param modelURI
     *            The uri to create the resource at.
     */
    private ModelInstance loadModelInstanceWithoutDomain(final VURI modelURI) {
        URI emfURI = modelURI.getEMFUri();
        Resource modelResource = URIUtil.loadResourceAtURI(emfURI, this.resourceSet, Collections.emptyMap());
        ModelInstance modelInstance = new ModelInstance(modelURI, modelResource);
        return modelInstance;
    }

    private void initializeCorrespondenceModel() {
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(() -> {
            VURI correspondencesVURI = this.fileSystemHelper.getCorrespondencesVURI();
            Resource correspondencesResource = null;
            if (URIUtil.existsResourceAtUri(correspondencesVURI.getEMFUri())) {
                logger.debug("Loading correspondence model from: " + this.fileSystemHelper.getCorrespondencesVURI());
                correspondencesResource = this.resourceSet.getResource(correspondencesVURI.getEMFUri(), true);
            } else {
                correspondencesResource = this.resourceSet.createResource(correspondencesVURI.getEMFUri());
            }
            this.changeRecorder.addToRecording(correspondencesResource);
            this.changeRecorder.beginRecording();
            this.correspondenceModel = new CorrespondenceModelImpl(new TuidResolverImpl(this.metamodelRepository, this),
                    this, this.metamodelRepository, correspondencesVURI, correspondencesResource);
            this.changeRecorder.endRecording();
            return null;
        });
    }

    private void initializeUuidProviderAndResolver() {
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(() -> {
            VURI uuidProviderVURI = this.fileSystemHelper.getUuidProviderAndResolverVURI();
            Resource uuidProviderResource = null;
            if (URIUtil.existsResourceAtUri(uuidProviderVURI.getEMFUri())) {
                logger.debug("Loading uuid provider and resolver model from: "
                        + this.fileSystemHelper.getUuidProviderAndResolverVURI());
                uuidProviderResource = this.resourceSet.getResource(uuidProviderVURI.getEMFUri(), true);
            } else {
                uuidProviderResource = this.resourceSet.createResource(uuidProviderVURI.getEMFUri());
            }
            this.uuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(this.resourceSet, uuidProviderResource);
            return null;
        });
    }

    /**
     * Returns the correspondence model in this model repository
     *
     * @return the correspondence model
     */
    @Override
    public CorrespondenceModel getCorrespondenceModel() {
        return this.correspondenceModel;
    }

    private void loadVURIsOfVSMUModelInstances() {
        Set<VURI> vuris = this.fileSystemHelper.loadVsumVURIsFromFile();
        for (VURI vuri : vuris) {
            VitruvDomain metamodel = getMetamodelByURI(vuri);
            ModelInstance modelInstance = loadModelInstance(vuri, metamodel);
            this.modelInstances.put(vuri, modelInstance);
        }
    }

    private void saveVURIsOfVsumModelInstances() {
        this.fileSystemHelper.saveVsumVURIsToFile(this.modelInstances.keySet());
    }

    private VitruvDomain getMetamodelByURI(final VURI uri) {
        String fileExtension = uri.getFileExtension();
        return this.metamodelRepository.getDomain(fileExtension);
    }

    @Override
    public void startRecording() {
        this.changeRecorder.beginRecording();
        logger.debug("Start recording virtual model");
    }

    @Override
    public Iterable<TransactionalChange> endRecording() {
        logger.debug("End recording virtual model");
        executeRecordingCommand(EMFCommandBridge.createVitruviusRecordingCommand(() -> {
            this.changeRecorder.endRecording();
            return null;
        }));
        return this.changeRecorder.getChanges();
    }

    private synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
        if (null == TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resourceSet)) {
            TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(this.resourceSet);
        }
        return TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resourceSet);
    }

    private void deleteModel(final VURI vuri) {
        final ModelInstance modelInstance = getModelInstanceOriginal(vuri);
        final Resource resource = modelInstance.getResource();
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    logger.debug("Deleting resource: " + resource);
                    resource.delete(null);
                    ResourceRepositoryImpl.this.modelInstances.remove(vuri);
                } catch (final IOException e) {
                    logger.info("Deletion of resource " + resource + " did not work. Reason: " + e);
                }
                return null;
            }
        });
    }

    @Override
    public void createRecordingCommandAndExecuteCommandOnTransactionalDomain(final Callable<Void> callable) {
        EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(callable, getTransactionalEditingDomain());
    }

    @Override
    public void executeRecordingCommandOnTransactionalDomain(final VitruviusRecordingCommand command) {
        EMFCommandBridge.executeVitruviusRecordingCommand(getTransactionalEditingDomain(), command);
    }

    @Override
    public void executeOnUuidResolver(final Consumer<UuidResolver> function) {
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() {
                function.accept(ResourceRepositoryImpl.this.uuidGeneratorAndResolver);
                return null;
            }
        });
    }

    @Override
    public void executeRecordingCommand(final VitruviusRecordingCommand command) {
        executeRecordingCommandOnTransactionalDomain(command);
    }

    @Override
    public Resource getResourceForMetadataStorage(final String... storageKey) {
        final StringBuilder safeStorageKey = new StringBuilder();
        if (storageKey.length == 0) {
            throw new IllegalArgumentException("The key must have at least one part!");
        }

        try {
            for (int i = 0; i < storageKey.length; i++) {
                final String keyPart = storageKey[i];
                if (keyPart == null) {
                    throw new IllegalArgumentException("A key part must not be null!");
                }
                // URL-encoding the string makes it save for being a file part,
                // except for the cases '', '.' and '..'
                // we thus use _ as a escape character
                final String preparedKeyPart;
                switch (keyPart) {
                case ".":
                    preparedKeyPart = "_.";
                    break;
                case "..":
                    preparedKeyPart = "_._.";
                    break;
                case "":
                    preparedKeyPart = "_";
                    break;
                default:
                    preparedKeyPart = keyPart.replaceAll("_", "__");
                    break;
                }
                String encodedKeyPart = URLEncoder.encode(preparedKeyPart, "UTF-8");
                safeStorageKey.append(encodedKeyPart);

                // ensure a file extension is present
                if (i == storageKey.length - 1) {
                    if (!encodedKeyPart.contains(".")) {
                        safeStorageKey.append(".metadata");
                    }
                } else {
                    safeStorageKey.append(File.separatorChar);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding is not present on this platform!");
        }
        final VURI storageVuri = this.fileSystemHelper.getConsistencyMetadataVURI(safeStorageKey.toString());
        ModelInstance modelInstance = this.modelInstances.get(storageVuri);
        if (modelInstance == null) {
            createRecordingCommandAndExecuteCommandOnTransactionalDomain(() -> {
                // case 2 or 3
                ModelInstance internalModelInstance = loadModelInstanceWithoutDomain(storageVuri);
                registerModelInstance(storageVuri, internalModelInstance);
                return null;
            });
            modelInstance = this.modelInstances.get(storageVuri);
        }
        return modelInstance.getResource();
    }
}
