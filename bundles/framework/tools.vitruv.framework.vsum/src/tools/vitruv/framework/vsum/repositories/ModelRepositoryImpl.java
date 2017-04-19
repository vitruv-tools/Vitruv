package tools.vitruv.framework.vsum.repositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.correspondence.CorrespondenceModelImpl;
import tools.vitruv.framework.correspondence.CorrespondenceProviding;
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.repository.ModelRepository;
import tools.vitruv.framework.domains.repository.VitruvDomainPair;
import tools.vitruv.framework.domains.repository.VitruvDomainRepository;
import tools.vitruv.framework.tuid.TuidManager;
import tools.vitruv.framework.util.bridges.EMFBridge;
import tools.vitruv.framework.util.bridges.EcoreResourceBridge;
import tools.vitruv.framework.util.command.EMFCommandBridge;
import tools.vitruv.framework.util.command.VitruviusRecordingCommand;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.helper.FileSystemHelper;

public class ModelRepositoryImpl implements ModelRepository, CorrespondenceProviding {
    private static final Logger logger = Logger.getLogger(ModelRepositoryImpl.class.getSimpleName());

    private final ResourceSet resourceSet;
    private final VitruvDomainRepository metamodelRepository;

    private final Map<VURI, ModelInstance> modelInstances;
    private final List<InternalCorrespondenceModel> correspondenceModels;
    private final FileSystemHelper fileSystemHelper;
    private final String vsumName;

    public ModelRepositoryImpl(final String vsumName, final VitruvDomainRepository metamodelRepository) {
        this(vsumName, metamodelRepository, null);
    }

    public ModelRepositoryImpl(final String vsumName, final VitruvDomainRepository metamodelRepository,
            final ClassLoader classLoader) {
        this.metamodelRepository = metamodelRepository;
        this.vsumName = vsumName;

        this.resourceSet = new ResourceSetImpl();

        this.modelInstances = new HashMap<VURI, ModelInstance>();
        this.correspondenceModels = new ArrayList<InternalCorrespondenceModel>();
        this.fileSystemHelper = new FileSystemHelper(vsumName);
        loadVURIsOfVSMUModelInstances();
    }

    /**
     * Supports three cases: 1) get registered 2) create non-existing 3) get unregistered but
     * existing that contains at most a root element without children. But throws an exception if an
     * instance that contains more than one element exists at the uri.
     *
     * DECISION Since we do not throw an exception (which can happen in 3) we always return a valid
     * model. Hence the caller do not have to check whether the retrieved model is null.
     */
    private ModelInstance getAndLoadModelInstanceOriginal(final VURI modelURI,
            final boolean forceLoadByDoingUnloadBeforeLoad) {
        final ModelInstance modelInstance = getModelInstanceOriginal(modelURI);
        try {
            if (EMFBridge.existsResourceAtUri(modelURI.getEMFUri())) {
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
            createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
                @Override
                public Void call() {
                    // case 2 or 3
                    ModelInstance internalModelInstance = getOrCreateUnregisteredModelInstance(modelURI);
                    ModelRepositoryImpl.this.modelInstances.put(modelURI, internalModelInstance);
                    saveVURIsOfVsumModelInstances();
                    return null;
                }
            });
            modelInstance = this.modelInstances.get(modelURI);
        }
        return modelInstance;
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
                try {
                    EcoreResourceBridge.saveResource(resourceToSave, metamodel.getDefaultSaveOptions());
                } catch (IOException e) {
                    throw new RuntimeException("Could not save VURI + " + modelInstance.getURI() + ": " + e);
                }
                return null;
            }

        });
    }

    @Override
    public void persistRootElement(final VURI vuri, final EObject rootEObject) {
        final ModelInstance modelInstance = getModel(vuri);
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                TuidManager.getInstance().registerObjectUnderModification(rootEObject);
                final Resource resource = modelInstance.getResource();

                resource.getContents().add(rootEObject);
                resource.setModified(true);

                logger.debug("Create model with resource: " + resource);
                TuidManager.getInstance().updateTuidsOfRegisteredObjects();
                TuidManager.getInstance().flushRegisteredObjectsUnderModification();
                return null;
            }
        });
    }

    @Override
    public void saveAllModels() {
        logger.debug("Saving all models of model repository for VSUM: " + this.vsumName);
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
                for (InternalCorrespondenceModel correspondenceModel : ModelRepositoryImpl.this.correspondenceModels) {
                    if (correspondenceModel.changedAfterLastSave()) {
                        logger.debug("  Saving correspondence model: " + correspondenceModel.getResource());
                        correspondenceModel.saveModel();
                        correspondenceModel.resetChangedAfterLastSave();
                    }
                }
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
        Resource modelResource = EcoreResourceBridge.loadResourceAtURI(emfURI, this.resourceSet,
                metamodel.getDefaultLoadOptions());
        ModelInstance modelInstance = new ModelInstance(modelURI, modelResource);
        return modelInstance;
    }

    private void createCorrespondenceModel(final VitruvDomainPair mapping) {
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(() -> {
            VURI correspondencesVURI = this.fileSystemHelper.getCorrespondencesVURI(mapping.getMetamodelA().getURI(),
                    mapping.getMetamodelB().getURI());
            Resource correspondencesResource = this.resourceSet.createResource(correspondencesVURI.getEMFUri());
            InternalCorrespondenceModel correspondenceModel = new CorrespondenceModelImpl(mapping, this,
                    correspondencesVURI, correspondencesResource);
            this.correspondenceModels.add(correspondenceModel);
            return null;
        });
    }

    private boolean existsCorrespondenceModel(final VitruvDomainPair metamodelPair) {
        for (CorrespondenceModel correspondenceModel : this.correspondenceModels) {
            if (correspondenceModel.getMapping().equals(metamodelPair)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the correspondenceModel for the mapping from the metamodel at the first VURI to the
     * metamodel at the second VURI or the other way round
     *
     * @return the found correspondenceModel or null if there is none
     */
    @Override
    public CorrespondenceModel getCorrespondenceModel(final VURI mmAVURI, final VURI mmBVURI) {
        VitruvDomain mmA = this.metamodelRepository.getDomain(mmAVURI);
        VitruvDomain mmB = this.metamodelRepository.getDomain(mmBVURI);
        if (mmA == null || mmB == null) {
            throw new IllegalArgumentException("Metamodel is not contained in the metamodel repository");
        }
        VitruvDomainPair metamodelPair = new VitruvDomainPair(mmA, mmB);
        if (!existsCorrespondenceModel(metamodelPair)) {
            // Correspondence model does not exist, so create it
            createCorrespondenceModel(metamodelPair);
        }
        for (CorrespondenceModel correspondenceModel : this.correspondenceModels) {
            if (correspondenceModel.getMapping().equals(metamodelPair)) {
                return correspondenceModel;
            }
        }
        throw new IllegalStateException(
                "Correspondence model does not exist, although it was existing or created before");
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

    // private void loadAndMapCorrepondenceInstances() {
    // for (Metamodel metamodel : this.metamodelManaging) {
    // for (Metamodel metamodel2 : this.metamodelManaging) {
    // if (metamodel != metamodel2
    // && getCorrespondenceModel(metamodel.getURI(), metamodel2.getURI()) == null) {
    // createCorrespondenceModel(new MetamodelPair(metamodel, metamodel2));
    // }
    // }
    // }
    // }

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
                    ModelRepositoryImpl.this.modelInstances.remove(vuri);
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

}
