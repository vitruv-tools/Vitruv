package tools.vitruvius.framework.vsum;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

import tools.vitruvius.framework.correspondence.CorrespondenceModel;
import tools.vitruvius.framework.correspondence.CorrespondenceModelImpl;
import tools.vitruvius.framework.correspondence.CorrespondenceProviding;
import tools.vitruvius.framework.correspondence.InternalCorrespondenceModel;
import tools.vitruvius.framework.metamodel.Mapping;
import tools.vitruvius.framework.metamodel.MappingManaging;
import tools.vitruvius.framework.metamodel.Metamodel;
import tools.vitruvius.framework.metamodel.MetamodelManaging;
import tools.vitruvius.framework.metamodel.ModelInstance;
import tools.vitruvius.framework.metamodel.ModelProviding;
import tools.vitruvius.framework.tuid.TUID;
import tools.vitruvius.framework.util.bridges.EcoreResourceBridge;
import tools.vitruvius.framework.util.command.EMFCommandBridge;
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand;
import tools.vitruvius.framework.util.datatypes.Pair;
import tools.vitruvius.framework.util.datatypes.VURI;
import tools.vitruvius.framework.vsum.helper.FileSystemHelper;

public class VSUMImpl implements ModelProviding, CorrespondenceProviding {

    private static final Logger logger = Logger.getLogger(VSUMImpl.class.getSimpleName());

    private final MappingManaging mappingManaging;
    private final MetamodelManaging metamodelManaging;

    protected final Map<VURI, ModelInstance> modelInstances;
    private final ResourceSet resourceSet;
    private final Map<Mapping, InternalCorrespondenceModel> mapping2CorrespondenceModelMap;

    public VSUMImpl(final MetamodelManaging metamodelManaging, final MappingManaging mappingManaging) {
        this(metamodelManaging, mappingManaging, null);
    }

    public VSUMImpl(final MetamodelManaging metamodelManaging, final MappingManaging mappingManaging,
            final ClassLoader classLoader) {
        this.metamodelManaging = metamodelManaging;
        this.mappingManaging = mappingManaging;

        this.resourceSet = new ResourceSetImpl();

        this.modelInstances = new HashMap<VURI, ModelInstance>();
        this.mapping2CorrespondenceModelMap = new HashMap<Mapping, InternalCorrespondenceModel>();

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
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() {
                try {
                    modelInstance.load(getMetamodelByURI(modelURI).getDefaultLoadOptions(),
                            forceLoadByDoingUnloadBeforeLoad);
                } catch (RuntimeException re) {
                    // could not load model instance --> this should only be the case when the
                    // model is not existing yet
                    logger.info("Exception during loading of model instance " + modelInstance + " occured: " + re);
                }
                return null;
            }
        });

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

    /**
     * Saves the resource for the given vuri. If the VURI is not existing yet it will be created.
     *
     * @param vuri
     *            The VURI to save
     */
    @Override
    public void saveExistingModelInstanceOriginal(final VURI vuri) {
        saveExistingModelInstanceOriginal(vuri, null);
    }

    private void saveExistingModelInstanceOriginal(final VURI vuri,
            final Pair<EObject, TUID> tuidToUpdateWithRootEObjectPair) {
        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                ModelInstance modelInstanceToSave = getModelInstanceOriginal(vuri);
                Metamodel metamodel = getMetamodelByURI(vuri);
                Resource resourceToSave = modelInstanceToSave.getResource();
                try {
                    EcoreResourceBridge.saveResource(resourceToSave, metamodel.getDefaultSaveOptions());
                } catch (IOException e) {
                    throw new RuntimeException("Could not save VURI + " + vuri + ": " + e);
                }
                saveAllChangedCorrespondences(modelInstanceToSave, tuidToUpdateWithRootEObjectPair);
                for (EObject root : modelInstanceToSave.getRootElements()) {
                    metamodel.removeRootFromTUIDCache(root);
                }
                return null;
            }

        });
    }

    @Override
    public void saveModelInstanceOriginalWithEObjectAsOnlyContent(final VURI vuri, final EObject rootEObject,
            final TUID oldTUID) {
        final ModelInstance modelInstance = getAndLoadModelInstanceOriginal(vuri);

        createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                final Resource resource = modelInstance.getResource();
                // clear the resource first
                resource.getContents().clear();
                resource.getContents().add(rootEObject);
                VSUMImpl.this.saveExistingModelInstanceOriginal(vuri, new Pair<EObject, TUID>(rootEObject, oldTUID));
                return null;
            }
        });
    }

    private void saveAllChangedCorrespondences(final ModelInstance modelInstanceToSave,
            final Pair<EObject, TUID> tuidToUpdateNewRootEObjectPair) {
        VURI metamodeURI = modelInstanceToSave.getMetamodeURI();
        Metamodel metamodel = this.metamodelManaging.getMetamodel(metamodeURI);
        Set<InternalCorrespondenceModel> allCorrespondenceModels = getOrCreateAllInternalCorrespondenceModelsForMM(
                metamodel);
        for (InternalCorrespondenceModel correspondenceModel : allCorrespondenceModels) {
            if (null != tuidToUpdateNewRootEObjectPair && tuidToUpdateNewRootEObjectPair.getSecond() != null) {
                correspondenceModel.updateTUID(tuidToUpdateNewRootEObjectPair.getSecond(),
                        tuidToUpdateNewRootEObjectPair.getFirst());
            }
            if (correspondenceModel.changedAfterLastSave()) {
                correspondenceModel.saveModel();
                correspondenceModel.resetChangedAfterLastSave();
            }
        }
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
        getOrCreateAllCorrespondenceModelsForMM(metamodel);
        return modelInstance;
    }

    private ModelInstance loadModelInstance(final VURI modelURI, final Metamodel metamodel) {
        URI emfURI = modelURI.getEMFUri();
        Resource modelResource = EcoreResourceBridge.loadResourceAtURI(emfURI, this.resourceSet,
                metamodel.getDefaultLoadOptions());
        ModelInstance modelInstance = new ModelInstance(modelURI, modelResource);
        return modelInstance;
    }

    private Collection<Mapping> getAllMappings(final Metamodel metamodel) {
        return this.mappingManaging.getAllMappings(metamodel);
    }

    public Set<CorrespondenceModel> getOrCreateAllCorrespondenceModelsForMM(final Metamodel metamodel) {
        return new HashSet<CorrespondenceModel>(getOrCreateAllInternalCorrespondenceModelsForMM(metamodel));
    }

    private Set<InternalCorrespondenceModel> getOrCreateAllInternalCorrespondenceModelsForMM(
            final Metamodel metamodel) {
        Collection<Mapping> mappings = getAllMappings(metamodel);
        Set<InternalCorrespondenceModel> correspondenceModels = new HashSet<InternalCorrespondenceModel>(
                null == mappings ? 0 : mappings.size());
        if (null == mappings) {
            logger.warn("mappings == null. No correspondence instace for MM: " + metamodel + " created."
                    + "Empty correspondence list will be returned");
            return correspondenceModels;
        }
        for (Mapping mapping : mappings) {
            InternalCorrespondenceModel correspondenceModel = this.mapping2CorrespondenceModelMap.get(mapping);
            if (correspondenceModel == null) {
                correspondenceModel = createAndRegisterCorrespondenceModel(mapping);
            }
            correspondenceModels.add(correspondenceModel);
        }
        return correspondenceModels;
    }

    private InternalCorrespondenceModel createAndRegisterCorrespondenceModel(final Mapping mapping) {
        InternalCorrespondenceModel correspondenceModel;
        VURI correspondencesVURI = FileSystemHelper.getCorrespondencesVURI(mapping.getMetamodelA().getURI(),
                mapping.getMetamodelB().getURI());
        correspondenceModel = createCorrespondenceModel(mapping, correspondencesVURI);
        // if (correspondenceModel instanceof InternalCorrespondenceModel) {
        // loadAndInitializeCorrespondenceModelDecorators(correspondenceModel);
        // }
        this.mapping2CorrespondenceModelMap.put(mapping, correspondenceModel);
        return correspondenceModel;
    }

    private InternalCorrespondenceModel createCorrespondenceModel(final Mapping mapping,
            final VURI correspondencesVURI) {
        Resource correspondencesResource = this.resourceSet.createResource(correspondencesVURI.getEMFUri());
        return new CorrespondenceModelImpl(mapping, this, correspondencesVURI, correspondencesResource);
    }

    // private void loadAndInitializeCorrespondenceModelDecorators(
    // final InternalCorrespondenceModel correspondenceModel) {
    // Set<String> fileExtPrefixesForObjects =
    // correspondenceModel.getFileExtPrefixesForObjectsToLoad();
    // Map<String, Object> fileExtPrefix2ObjectMap = new HashMap<String, Object>();
    // for (String fileExtPrefix : fileExtPrefixesForObjects) {
    // String fileName = getFileNameForCorrespondenceModelDecorator(correspondenceModel,
    // fileExtPrefix);
    // Object loadedObject = FileSystemHelper.loadObjectFromFile(fileName, this.classLoader);
    // fileExtPrefix2ObjectMap.put(fileExtPrefix, loadedObject);
    // }
    // correspondenceModel.initialize(fileExtPrefix2ObjectMap);
    // }

    // @Override
    public ModelInstance getModelInstanceOriginalForImport(final VURI uri) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns the correspondenceModel for the mapping from the metamodel at the first VURI to the
     * metamodel at the second VURI or the other way round
     *
     * @return the found correspondenceModel or null if there is none
     */
    @Override
    public CorrespondenceModel getCorrespondenceModel(final VURI mmAVURI, final VURI mmBVURI) {
        Mapping mapping = this.mappingManaging.getMapping(mmAVURI, mmBVURI);
        if (this.mapping2CorrespondenceModelMap.containsKey(mapping)) {
            return this.mapping2CorrespondenceModelMap.get(mapping);
        }
        logger.warn("no mapping found for the metamodel at: " + mmAVURI + " and the metamodel at: " + mmBVURI);
        return null;
    }

    /**
     * Returns all correspondences instances for a given VURI. null will be returned. We are not
     * creating new CorrespondenceModel here, cause we can not guess the linked model. The method
     * {@link getCorrespondenceModelOriginal} must be called before to create the appropriate
     * correspondence instance
     *
     * @see tools.vitruvius.framework.correspondence.datatypes. CorrespondenceModel
     * @return set that contains all CorrespondenceModels for the VURI or null if there is non
     */
    @Override
    public Set<CorrespondenceModel> getOrCreateAllCorrespondenceModels(final VURI model1uri) {
        Metamodel metamodelForUri = this.metamodelManaging.getMetamodel(model1uri.getFileExtension());
        return getOrCreateAllCorrespondenceModelsForMM(metamodelForUri);
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
        Metamodel[] metamodels = this.metamodelManaging.getAllMetamodels();
        for (Metamodel metamodel : metamodels) {
            getOrCreateAllCorrespondenceModelsForMM(metamodel);
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
    public void detachTransactionalEditingDomain() {
        TransactionalEditingDomain domain = TransactionalEditingDomain.Factory.INSTANCE
                .getEditingDomain(this.resourceSet);
        if (domain != null) {
            domain.dispose();
        }
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
