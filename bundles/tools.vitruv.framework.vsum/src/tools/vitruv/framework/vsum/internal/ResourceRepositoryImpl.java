package tools.vitruv.framework.vsum.internal;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;
import static tools.vitruv.change.correspondence.model.CorrespondenceModelFactory.createPersistableCorrespondenceModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.model.PersistableCorrespondenceModel;
import tools.vitruv.change.correspondence.view.CorrespondenceModelViewFactory;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.propagation.impl.ResourceRegistrationAdapter;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;

class ResourceRepositoryImpl implements ModelRepository {
	private static final Logger LOGGER = Logger.getLogger(ResourceRepositoryImpl.class);
	
	private final ResourceSet modelsResourceSet = new ResourceSetImpl();
	private final Map<URI, ModelInstance> modelInstances = new HashMap<>();
	private final PersistableCorrespondenceModel correspondenceModel;
	private UuidResolver uuidResolver = UuidResolver.create(modelsResourceSet);
	private final ChangeRecorder changeRecorder = new ChangeRecorder(modelsResourceSet);
	private final VitruviusChangeResolver<Uuid> changeResolver = VitruviusChangeResolver.forUuids(uuidResolver);

	private final VsumFileSystemLayout fileSystemLayout;

	private boolean isRecording = false;
	private boolean isLoading = false;

	ResourceRepositoryImpl(VsumFileSystemLayout fileSystemLayout) {
		this.fileSystemLayout = fileSystemLayout;
		this.correspondenceModel = createPersistableCorrespondenceModel(fileSystemLayout.getCorrespondencesURI());
		modelsResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*",
				new PathmapAwareModelInstanceFactory());
		modelsResourceSet.eAdapters().add(new ResourceRegistrationAdapter(resource -> {
			getCreateOrLoadModelUnlessLoading(resource.getURI());
		}));
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

	private void writeModelsFile() throws IOException {
		Files.write(fileSystemLayout.getModelsNamesFilesPath(),
				modelsResourceSet.getResources().stream().map(Resource::getURI).map(URI::toString).toList());
	}
	
	private void readModelsFile() throws IOException {
		List<URI> modelUris;
		try {
			modelUris = Files.readAllLines(fileSystemLayout.getModelsNamesFilesPath()).stream().map(URI::createURI)
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
		return CorrespondenceModelViewFactory.createEditableCorrespondenceModelView(correspondenceModel);
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
		ModelInstance modelInstance;
		if (modelUri.toString().contains("pathmap")) {
			// loadOrCreateResource
			if(modelsResourceSet.getResources().stream().anyMatch(resource -> resource.getURI().equals(modelUri))) {
				modelInstance = (ModelInstance) modelsResourceSet.getResource(modelUri, false);
			} else {
				ResourceSet stdResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
				Resource pathMapResource = getOrCreateResource(stdResourceSet, modelUri);
				modelInstance = new ModelInstance(modelUri);
				modelInstance.getContents().addAll(EcoreUtil.copyAll(pathMapResource.getContents()));
				modelsResourceSet.getResources().add(modelInstance);
			}
		} else if (modelUri.isFile() || modelUri.isPlatform()) {
			modelInstance = (ModelInstance) getOrCreateResource(modelsResourceSet, modelUri);
		} else {
			modelInstance = (ModelInstance) loadOrCreateResource(modelsResourceSet, modelUri);
		}

		modelInstances.put(modelUri, modelInstance);
		registerRecorder(modelInstance);
		modelInstance.setModified(false);
		return modelInstance;
	}

	private void registerRecorder(ModelInstance modelInstance) {
		// Only monitor modifiable models (file / platform URIs, not pathmap URIs)
		if (modelInstance.getURI().isFile() || modelInstance.getURI().isPlatform()) {
			changeRecorder.addToRecording(modelInstance);
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
			LOGGER.debug("Saving all models of model repository for VSUM " + fileSystemLayout);
		}
		Iterator<Entry<URI, ModelInstance>> modelInstancesIterator = modelInstances.entrySet().iterator();
		while (modelInstancesIterator.hasNext()) {
			ModelInstance modelInstance = modelInstancesIterator.next().getValue();
			if (modelInstance.isEmpty()) {
				try {
					modelInstance.delete(null);
				} catch (IOException e) {
					LOGGER.error("Model could not be deleted: " + modelInstance.getURI(), e);
					throw new IllegalStateException("Could not delete URI " + modelInstance.getURI(), e);
				}
				modelInstancesIterator.remove();
			} else {
				try {
					// move to modelInstance?
					if(modelInstance.isModified()) { 
						modelInstance.save(null);
					}
				} catch (IOException e) {
					LOGGER.error("Model could not be saved: " + modelInstance.getURI(), e);
					throw new IllegalStateException("Could not save URI " + modelInstance.getURI(), e);
				}
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
		LOGGER.debug("Start recording virtual model");
		changeApplicator.run();
		LOGGER.debug("End recording virtual model");
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
		return getCreateOrLoadModel(uri);
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