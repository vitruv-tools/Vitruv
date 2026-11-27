package tools.vitruv.framework.vsum.internal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.model.PersistableCorrespondenceModel;
import tools.vitruv.change.correspondence.view.CorrespondenceModelViewFactory;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.propagation.impl.ResourceRegistrationAdapter;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.internal.messages.InfoMessages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.*;
import static tools.vitruv.change.correspondence.model.CorrespondenceModelFactory.createPersistableCorrespondenceModel;

class ResourceRepositoryImpl implements ModelRepository {
    private static final Logger LOGGER = LogManager.getLogger(ResourceRepositoryImpl.class);

    private final ResourceSet modelsResourceSet = withGlobalFactories(new ResourceSetImpl());
    private final Map<URI, ModelInstance> modelInstances = new HashMap<>();
    //private final PersistableCorrespondenceModel correspondenceModel;
    //Linh:new
    private PersistableCorrespondenceModel correspondenceModel;

    private volatile UuidResolver uuidResolver = UuidResolver.create(modelsResourceSet);
    private final ChangeRecorder changeRecorder = new ChangeRecorder(modelsResourceSet);
    private VitruviusChangeResolver<Uuid> changeResolver =
            VitruviusChangeResolverFactory.forUuids(uuidResolver);

    //private final VsumFileSystemLayout fileSystemLayout;
    //Linh:new
    private VsumFileSystemLayout fileSystemLayout;


    private boolean isRecording = false;
    private boolean isLoading = false;

    ResourceRepositoryImpl(VsumFileSystemLayout fileSystemLayout) {
        this.fileSystemLayout = fileSystemLayout;
        this.correspondenceModel =
                createPersistableCorrespondenceModel(fileSystemLayout.getCorrespondencesURI());
        modelsResourceSet
                .eAdapters()
                .add(
                        new ResourceRegistrationAdapter(
                                resource -> getCreateOrLoadModelUnlessLoading(resource.getURI())));
    }

    @Override
    public void loadExistingModels() {
        isLoading = true;
        try {
            readModelsFile();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        correspondenceModel.loadSerializedCorrespondences(modelsResourceSet);
        isLoading = false;
    }

    @Override
    public void reinitialize(VsumFileSystemLayout newLayout) {
        this.fileSystemLayout = newLayout;
        this.correspondenceModel = createPersistableCorrespondenceModel(newLayout.getCorrespondencesURI());
        reload();
    }

    @Override
    public void reload() {
        LOGGER.info("Reloading models from file system");
        // 1.step: stop recording changes during reload
        // if changeRecoreder is still on during reload, it would trigger some unwanted deletion events for every element being unloaded
        boolean wasRecording = isRecording;
        if (isRecording) {
            changeRecorder.endRecording();
            isRecording = false;
        }

        // 2.step: remove all current resources from change recorder tracking before unloading
        // to prevent any dangling references in the recorder
        LOGGER.debug("Removing {} models from change recorder", modelsResourceSet.getResources().size());
        var resourcesToRemove = new ArrayList<>(modelsResourceSet.getResources());
        for (Resource resource : resourcesToRemove) {
            if (resource.getURI().isFile() || resource.getURI().isPlatform()) {
                changeRecorder.removeFromRecording(resource);
            }
        }

        // 3.step: unload all existing resources to avoid any proxy resolution failures
        LOGGER.debug("Unloading models");
        modelsResourceSet.getResources().forEach(Resource::unload);
        modelsResourceSet.getResources().clear();
        modelInstances.clear();
        LOGGER.debug("Clearing package registry");
        modelsResourceSet.getPackageRegistry().clear(); // Clear package registry to prevent stale metamodel references

        // 4.step: reset UUID Resolver
        LOGGER.debug("Resetting UUID resolver");
        uuidResolver = UuidResolver.create(modelsResourceSet);
        // Recreate the change resolver with new UUID resolver
        changeResolver = VitruviusChangeResolverFactory.forUuids(uuidResolver);

        // 5.step: load models, UUID resolver, and correspondences - mirrors loadExistingModels().
        // readModelsFile() must come first so model objects are in memory before correspondences
        // try to resolve their href references. Without this, the UUID resolver stays empty
        // after reload and throws "unknown element" on the next commitChanges().
        isLoading = true;
        try {
            readModelsFile();
        } catch (IOException e) {
            LOGGER.warn("Failed to read models file during reload: {}", e.getMessage());
        }
        try {
            correspondenceModel.loadSerializedCorrespondences(modelsResourceSet);
            LOGGER.info("Loaded correspondences from disk successfully");
        } catch (Exception e) {
            LOGGER.warn("Failed to load correspondences (will rebuild as needed): {}", e.getMessage(), e);
        }
        isLoading = false;

        // 6.step: resume recording if it was active before
        if (wasRecording) {
            changeRecorder.beginRecording();
            isRecording = true;
        }
        LOGGER.info("Models reloaded successfully");
    }


    private void writeModelsFile() throws IOException {
        // Exclude internal V-SUM resources that live under .vitruvius/ -- these are framework
        // artefacts (uuid resolver helpers, cached metadata) that must not be reloaded as user
        // models on the next startup.  Only the user-visible model files belong in models.models.
        Path vitruviusDir = fileSystemLayout.getVsumProjectFolder().resolve(".vitruvius");
        Files.write(
                fileSystemLayout.getModelsNamesFilesPath(),
                modelsResourceSet.getResources().stream()
                        .map(Resource::getURI)
                        .filter(uri -> {
                            if (!uri.isFile()) return true;
                            Path filePath = Path.of(uri.toFileString());
                            return !filePath.startsWith(vitruviusDir);
                        })
                        // Normalise to file:/// so the URI round-trips identically on reload.
                        // Without normalisation, Windows file URIs can flip between file:/C:/...
                        // and file:///C:/..., causing EcoreUtil.resolve() to miss the resource
                        // in modelsResourceSet and silently drop inherited correspondences.
                        .map(uri -> uri.isFile() ? URI.createFileURI(uri.toFileString()) : uri)
                        .map(URI::toString)
                        .toList());
    }

    private void readModelsFile() throws IOException {
        List<URI> modelUris;
        try {
            modelUris =
                    Files.readAllLines(fileSystemLayout.getModelsNamesFilesPath()).stream()
                            .map(URI::createURI)
                            // Normalise to file:/// to match what writeModelsFile writes and
                            // what EcoreUtil.resolve expects when looking up in modelsResourceSet.
                            .map(uri -> uri.isFile() ? URI.createFileURI(uri.toFileString()) : uri)
                            .filter(uri -> !uri.isFile()
                                || new java.io.File(uri.toFileString()).exists())
                            .toList();

        } catch (NoSuchFileException e) {
            // There are no existing models, so don't do anything
            return;
        }
        modelUris.forEach(uri -> loadOrCreateResource(modelsResourceSet, uri));
        uuidResolver.loadFromUri(fileSystemLayout.getUuidsURI());
        modelUris.forEach(this::createOrLoadModel);
    }

    @Override
    public EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel() {
        return CorrespondenceModelViewFactory.createEditableCorrespondenceModelView(
                correspondenceModel);
    }

    @Override
    public ModelInstance getModel(URI modelUri) {
        return modelInstances.get(modelUri);
    }

    @Override
    public UuidResolver getUuidResolver() {
        return uuidResolver;
    }

    private ModelInstance getCreateOrLoadModelUnlessLoading(URI modelUri) {
        if (isLoading) {
            return null;
        }
        return getCreateOrLoadModel(modelUri);
    }

    private ModelInstance getCreateOrLoadModel(URI modelUri) {
        ModelInstance instance = getModel(modelUri);
        if (instance != null) {
            return instance;
        }
        return createOrLoadModel(modelUri);
    }

    private ModelInstance createOrLoadModel(URI modelUri) {
        Resource resource;
        if (modelUri.isFile() || modelUri.isPlatform()) {
            resource = getOrCreateResource(modelsResourceSet, modelUri);
        } else {
            resource = loadOrCreateResource(modelsResourceSet, modelUri);
        }
        ModelInstance modelInstance = new ModelInstance(resource);
        modelInstances.put(modelUri, modelInstance);
        registerRecorder(modelInstance);
        return modelInstance;
    }

    private void registerRecorder(ModelInstance modelInstance) {
        // Only monitor modifiable models (file / platform URIs, not pathmap URIs)
        if (modelInstance.getURI().isFile() || modelInstance.getURI().isPlatform()) {
            changeRecorder.addToRecording(modelInstance.getResource());
            if (isRecording && !changeRecorder.isRecording()) {
                changeRecorder.beginRecording();
            }
        }
    }

    @Override
    public void persistAsRoot(EObject rootObject, URI uri) {
        getCreateOrLoadModel(uri).addRoot(rootObject);
    }

    @Override
    public void saveOrDeleteModels() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(InfoMessages.DEBUG_SAVING_ALL_MODELS, fileSystemLayout);
        }
        Iterator<Entry<URI, ModelInstance>> modelInstancesIterator =
                modelInstances.entrySet().iterator();
        while (modelInstancesIterator.hasNext()) {
            ModelInstance modelInstance = modelInstancesIterator.next().getValue();
            if (modelInstance.isEmpty()) {
                modelInstance.delete();
                modelInstancesIterator.remove();
            } else {
                modelInstance.save();
            }
        }
        correspondenceModel.save();
        try {
            writeModelsFile();
            uuidResolver.storeAtUri(fileSystemLayout.getUuidsURI());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Iterable<TransactionalChange<EObject>> recordChanges(Runnable changeApplicator) {
        changeRecorder.beginRecording();
        isRecording = true;
        LOGGER.debug(InfoMessages.DEBUG_START_RECORDING_VIRTUAL_MODEL);
        changeApplicator.run();
        LOGGER.debug(InfoMessages.DEBUG_END_RECORDING_VIRTUAL_MODEL);
        isRecording = false;
        changeRecorder.endRecording();
        TransactionalChange<EObject> change = changeRecorder.getChange();
        changeResolver.assignIds(change);
        return change.containsConcreteChange() ? List.of(change) : List.of();
    }

    @Override
    public VitruviusChange<EObject> applyChange(VitruviusChange<Uuid> change) {
        return changeResolver.resolveAndApply(change);
    }

    @Override
    public URI getMetadataModelURI(String... metadataKey) {
        return fileSystemLayout.getConsistencyMetadataModelURI(metadataKey);
    }

    @Override
    public Resource getModelResource(URI uri) {
        return getCreateOrLoadModel(uri).getResource();
    }

    @Override
    public Collection<Resource> getModelResources() {
        return modelsResourceSet.getResources();
    }

    @Override
    public void close() {
        changeRecorder.close();
        modelsResourceSet.getResources().forEach(Resource::unload);
        modelsResourceSet.getResources().clear();
        uuidResolver = null;
    }
}
