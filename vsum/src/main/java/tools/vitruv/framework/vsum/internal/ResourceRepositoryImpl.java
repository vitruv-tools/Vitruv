package tools.vitruv.framework.vsum.internal;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static tools.vitruv.change.correspondence.model.CorrespondenceModelFactory.createPersistableCorrespondenceModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

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

class ResourceRepositoryImpl implements ModelRepository {
    private static final Logger LOGGER = LogManager.getLogger(ResourceRepositoryImpl.class);

    private final ResourceSet modelsResourceSet = withGlobalFactories(new ResourceSetImpl());
    private final Map<URI, ModelInstance> modelInstances = new HashMap<>();
    private final PersistableCorrespondenceModel correspondenceModel;
    private UuidResolver uuidResolver = UuidResolver.create(modelsResourceSet);
    private final ChangeRecorder changeRecorder = new ChangeRecorder(modelsResourceSet);
    private VitruviusChangeResolver<Uuid> changeResolver =
            VitruviusChangeResolverFactory.forUuids(uuidResolver);

    private final VsumFileSystemLayout fileSystemLayout;

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
    public void reload() {
        LOGGER.info("Reloading models from file system");

        // Stop recording changes during reload
        boolean wasRecording = isRecording;
        if (isRecording) {
            changeRecorder.endRecording();
            isRecording = false;
        }

        // Remove all current resources from change recorder tracking BEFORE unloading
        LOGGER.debug("Removing {} models from change recorder", modelsResourceSet.getResources().size());
        var resourcesToRemove = new ArrayList<>(modelsResourceSet.getResources());
        for (Resource resource : resourcesToRemove) {
            if (resource.getURI().isFile() || resource.getURI().isPlatform()) {
                changeRecorder.removeFromRecording(resource);
            }
        }

        // Unload all existing resources
        LOGGER.debug("Unloading models");
        modelsResourceSet.getResources().forEach(Resource::unload);
        modelsResourceSet.getResources().clear();
        modelInstances.clear();

        // Clear package registry to prevent stale metamodel references
        LOGGER.debug("Clearing package registry");
        modelsResourceSet.getPackageRegistry().clear();

        // Reset UUID resolver
        LOGGER.debug("Resetting UUID resolver");
        uuidResolver = UuidResolver.create(modelsResourceSet);

        // Recreate the change resolver with new UUID resolver
        changeResolver = VitruviusChangeResolverFactory.forUuids(uuidResolver);

        // Always use file system discovery
        LOGGER.debug("Discovering models from file system");
        isLoading = true;

        try {
            discoverAndLoadModels();
        } catch (Exception e) {
            LOGGER.error("Failed to discover models from file system", e);
        }

        // Load correspondences (tolerant of missing models)
        try {
            correspondenceModel.loadSerializedCorrespondences(modelsResourceSet);
        } catch (Exception e) {
            LOGGER.warn("Failed to load correspondences (will rebuild as needed): {}", e.getMessage());
        }

        isLoading = false;

        // Resume recording if it was active before
        if (wasRecording) {
            changeRecorder.beginRecording();
            isRecording = true;
        }

        LOGGER.info("Models reloaded successfully");
    }

    private void discoverAndLoadModels() throws IOException {
        // TODO: only temporal fix
        Path rootPath = fileSystemLayout.getVsumProjectFolder();

        LOGGER.debug("Scanning {} for model files", rootPath);

        // Find all model files (including model, model2, model3, model99, etc.)
        List<URI> discoveredModels = Files.list(rootPath)
                .filter(Files::isRegularFile)
                .filter(path -> {
                    String name = path.getFileName().toString();
                    // Match: .xmi, .model, .model2, .model3, .model99, etc.
                    return name.endsWith(".xmi") ||
                            name.matches(".*\\.model\\d*") ||  // Matches .model, .model2, .model3, etc.
                            name.endsWith(".ecore") ||
                            name.endsWith(".uml") ||
                            name.endsWith(".notation");
                })
                .map(path -> URI.createFileURI(path.toAbsolutePath().toString()))
                .toList();

        LOGGER.info("Discovered {} model file(s) on file system", discoveredModels.size());

        if (discoveredModels.isEmpty()) {
            LOGGER.warn("No model files found in repository root");
            return;
        }

        // Load resources
        for (URI uri : discoveredModels) {
            LOGGER.debug("Loading model: {}", uri);
            try {
                loadOrCreateResource(modelsResourceSet, uri);
            } catch (Exception e) {
                LOGGER.warn("Failed to load model {}: {}", uri, e.getMessage());
                // Continue with other models
            }
        }

        // Try to load UUIDs (tolerant of missing objects)
        try {
            uuidResolver.loadFromUri(fileSystemLayout.getUuidsURI());
            LOGGER.debug("UUID mappings loaded successfully");
        } catch (Exception e) {
            LOGGER.warn("Could not load UUID mappings (will regenerate): {}", e.getMessage());
        }

        // Create model instances
        for (URI uri : discoveredModels) {
            try {
                createOrLoadModel(uri);
            } catch (Exception e) {
                LOGGER.warn("Failed to create model instance for {}: {}", uri, e.getMessage());
            }
        }

        // Update models.models file to reflect current state
        try {
            writeModelsFile();
            LOGGER.debug("Updated models.models with discovered models");
        } catch (IOException e) {
            LOGGER.warn("Failed to write models.models (non-critical)", e);
        }
    }

    private void writeModelsFile() throws IOException {
        Files.write(
                fileSystemLayout.getModelsNamesFilesPath(),
                modelsResourceSet.getResources().stream()
                        .map(Resource::getURI)
                        .map(URI::toString)
                        .toList());
    }

    private void readModelsFile() throws IOException {
        List<URI> modelUris;
        try {
            modelUris =
                    Files.readAllLines(fileSystemLayout.getModelsNamesFilesPath()).stream()
                            .map(URI::createURI)
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
