package tools.vitruv.framework.vsum;

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
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.metamodel.MetamodelPair;
import tools.vitruv.framework.metamodel.MetamodelRepository;
import tools.vitruv.framework.metamodel.ModelInstance;
import tools.vitruv.framework.metamodel.ModelProviding;
import tools.vitruv.framework.tuid.TuidManager;
import tools.vitruv.framework.util.bridges.EMFBridge;
import tools.vitruv.framework.util.bridges.EcoreResourceBridge;
import tools.vitruv.framework.util.command.EMFCommandBridge;
import tools.vitruv.framework.util.command.VitruviusRecordingCommand;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.helper.FileSystemHelper;

class VSUMImpl implements ModelProviding, CorrespondenceProviding {

    private static final Logger logger = Logger.getLogger(VSUMImpl.class.getSimpleName());

    private final MetamodelRepository metamodelManaging;

    protected final Map<VURI, ModelInstance> modelInstances;
    private final ResourceSet resourceSet;

    private final List<InternalCorrespondenceModel> correspondenceModels;

    public VSUMImpl(final MetamodelRepository metamodelManaging) {
        this(metamodelManaging, null);
    }

    public VSUMImpl(final MetamodelRepository metamodelManaging, final ClassLoader classLoader) {
        this.metamodelManaging = metamodelManaging;

        this.resourceSet = new ResourceSetImpl();

        this.modelInstances = new HashMap<VURI, ModelInstance>();
        this.correspondenceModels = new ArrayList<InternalCorrespondenceModel>();

        loadVURIsOfVSMUModelInstances();
        loadAndMapCorrepondenceInstances();
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
    public ModelInstance getAndLoadModelInstanceOriginal(final VURI modelURI) {
        return getAndLoadModelInstanceOriginal(modelURI, false);
    }

    @Override
    public void forceReloadModelInstanceOriginalIfExisting(final VURI modelURI) {
        if (existsModelInstance(modelURI)) {
            getAndLoadModelInstanceOriginal(modelURI, true);
        }
    }

    public ModelInstance getModelInstanceOriginal(final VURI modelURI) {
        ModelInstance modelInstance = this.modelInstances.get(modelURI);
        if (modelInstance == null) {
            createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
                @Override
                public Void call() {
                    // case 2 or 3
                    ModelInstance internalModelInstance = getOrCreateUnregisteredModelInstance(modelURI);
                    VSUMImpl.this.modelInstances.put(modelURI, internalModelInstance);
                    saveVURIsOfVSUMModelInstances();
                    return null;
                }
            });
            modelInstance = this.modelInstances.get(modelURI);
        }
        return modelInstance;
    }

    public boolean existsModelInstance(final VURI modelURI) {
        return this.modelInstances.containsKey(modelURI);
    }

    private void saveModelInstance(final ModelInstance modelInstance) {
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Metamodel metamodel = getMetamodelByURI(modelInstance.getURI());
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
    public void createModelInstance(final VURI vuri, final EObject rootEObject) {
        final ModelInstance modelInstance = getAndLoadModelInstanceOriginal(vuri);
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                TuidManager.getInstance().registerObjectUnderModification(rootEObject);
                final Resource resource = modelInstance.getResource();
                // clear the resource first
                resource.getContents().clear();
                resource.getContents().add(rootEObject);

                VSUMImpl.this.saveModelInstance(modelInstance);
                TuidManager.getInstance().updateTuidsOfRegisteredObjects();
                TuidManager.getInstance().flushRegisteredObjectsUnderModification();
                return null;
            }
        });
    }

    @Override
    public void saveAllModels() {
        saveAllChangedModels();
        saveAllChangedCorrespondenceModels();
    }

    private void saveAllChangedModels() {
        for (ModelInstance modelInstance : this.modelInstances.values()) {
            Resource resourceToSave = modelInstance.getResource();
            if (resourceToSave.isModified()) {
                saveModelInstance(modelInstance);
            }
        }
    }

    private void saveAllChangedCorrespondenceModels() {
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (InternalCorrespondenceModel correspondenceModel : VSUMImpl.this.correspondenceModels) {
                    if (correspondenceModel.changedAfterLastSave()) {
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
        Metamodel metamodel = this.metamodelManaging.getMetamodel(fileExtension);
        if (metamodel == null) {
            throw new RuntimeException("Cannot create a new model instance at the uri '" + modelURI
                    + "' because no metamodel is registered for the file extension '" + fileExtension + "'!");
        }
        return getOrCreateUnregisteredModelInstance(modelURI, metamodel);
    }

    private ModelInstance getOrCreateUnregisteredModelInstance(final VURI modelURI, final Metamodel metamodel) {
        ModelInstance modelInstance = loadModelInstance(modelURI, metamodel);
        // getOrCreateAllCorrespondenceModelsForMM(metamodel);
        return modelInstance;
    }

    private ModelInstance loadModelInstance(final VURI modelURI, final Metamodel metamodel) {
        URI emfURI = modelURI.getEMFUri();
        Resource modelResource = EcoreResourceBridge.loadResourceAtURI(emfURI, this.resourceSet,
                metamodel.getDefaultLoadOptions());
        ModelInstance modelInstance = new ModelInstance(modelURI, modelResource);
        return modelInstance;
    }

    private void createAndRegisterCorrespondenceModel(final MetamodelPair mapping) {
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(() -> {
            InternalCorrespondenceModel correspondenceModel;
            VURI correspondencesVURI = FileSystemHelper.getCorrespondencesVURI(mapping.getMetamodelA().getURI(),
                    mapping.getMetamodelB().getURI());
            correspondenceModel = createCorrespondenceModel(mapping, correspondencesVURI);
            this.correspondenceModels.add(correspondenceModel);
            return null;
        });
    }

    private InternalCorrespondenceModel createCorrespondenceModel(final MetamodelPair mapping,
            final VURI correspondencesVURI) {
        Resource correspondencesResource = this.resourceSet.createResource(correspondencesVURI.getEMFUri());
        return new CorrespondenceModelImpl(mapping, this, correspondencesVURI, correspondencesResource);
    }

    private boolean existsCorrespondenceModel(final MetamodelPair metamodelPair) {
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
        Metamodel mmA = this.metamodelManaging.getMetamodel(mmAVURI);
        Metamodel mmB = this.metamodelManaging.getMetamodel(mmBVURI);
        if (mmA == null || mmB == null) {
            throw new IllegalArgumentException("Metamodel is not contained in the metamodel repository");
        }
        MetamodelPair metamodelPair = new MetamodelPair(mmA, mmB);
        if (!existsCorrespondenceModel(metamodelPair)) {
            // Correspondence model does not exist, so create it
            createAndRegisterCorrespondenceModel(metamodelPair);
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
        Set<VURI> vuris = FileSystemHelper.loadVSUMvURIsFromFile();
        for (VURI vuri : vuris) {
            Metamodel metamodel = getMetamodelByURI(vuri);
            ModelInstance modelInstance = loadModelInstance(vuri, metamodel);
            this.modelInstances.put(vuri, modelInstance);
        }
    }

    private void saveVURIsOfVSUMModelInstances() {
        FileSystemHelper.saveVSUMvURIsToFile(this.modelInstances.keySet());
    }

    private Metamodel getMetamodelByURI(final VURI uri) {
        String fileExtension = uri.getFileExtension();
        return this.metamodelManaging.getMetamodel(fileExtension);
    }

    private void loadAndMapCorrepondenceInstances() {
        for (Metamodel metamodel : this.metamodelManaging) {
            for (Metamodel metamodel2 : this.metamodelManaging) {
                if (metamodel != metamodel2
                        && getCorrespondenceModel(metamodel.getURI(), metamodel2.getURI()) == null) {
                    createAndRegisterCorrespondenceModel(new MetamodelPair(metamodel, metamodel2));
                }
            }
        }
    }

    private synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
        if (null == TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resourceSet)) {
            attachTransactionalEditingDomain();
        }
        return TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resourceSet);
    }

    private void attachTransactionalEditingDomain() {
        TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(this.resourceSet);
    }

    @Override
    public void deleteModelInstanceOriginal(final VURI vuri) {
        final ModelInstance modelInstance = getModelInstanceOriginal(vuri);
        final Resource resource = modelInstance.getResource();
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    resource.delete(null);
                    VSUMImpl.this.modelInstances.remove(modelInstance);
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
