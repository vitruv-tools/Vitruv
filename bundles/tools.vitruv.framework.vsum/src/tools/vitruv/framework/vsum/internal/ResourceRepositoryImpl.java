package tools.vitruv.framework.vsum.internal;

import static com.google.common.base.Preconditions.checkState;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static tools.vitruv.change.correspondence.model.CorrespondenceModelFactory.createPersistableCorrespondenceModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.model.PersistableCorrespondenceModel;
import tools.vitruv.change.correspondence.view.CorrespondenceModelViewFactory;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.propagation.impl.ResourceRegistrationAdapter;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;

class ResourceRepositoryImpl implements ModelRepository {
	private static final Logger LOGGER = Logger.getLogger(ResourceRepositoryImpl.class);

	private final ResourceSet modelsResourceSet = withGlobalFactories(new ResourceSetImpl());
	private final Map<URI, ModelInstance> modelInstances = new HashMap<>();
	private final FileExtensionRecorderMapping fileExtensionRecorderMapping = new FileExtensionRecorderMapping();
	private final PersistableCorrespondenceModel correspondenceModel;

	private final VsumFileSystemLayout fileSystemLayout;

	private boolean isRecording = false;
	private boolean isLoading = false;

	/**
	 * Manages change recorders for file extensions. Ensures that only one change
	 * recorder per file extension exists. A recorder is assigned to a set of file
	 * extensions (for the case that multiple file extensions belong to the same
	 * domain of models and should be recorder together) and recorders can be
	 * retrieved for a given file extension.
	 */
	private static class FileExtensionRecorderMapping {
		private final Map<Set<String>, ChangeRecorder> fileExtensionsToRecorder = new HashMap<>();
		private final Map<String, Set<String>> fileExtensionToExtensionsSet = new HashMap<>();

		Set<ChangeRecorder> getRecorders() {
			return new HashSet<>(fileExtensionsToRecorder.values());
		}

		boolean hasRecorder(String fileExtension) {
			return fileExtensionsToRecorder.containsKey(fileExtensionToExtensionsSet.get(fileExtension));
		}

		ChangeRecorder getRecorder(String fileExtension) {
			return fileExtensionsToRecorder.get(fileExtensionToExtensionsSet.get(fileExtension));
		}

		void registerRecorder(Set<String> fileExtensions, ResourceSet recorderResourceSet) {
			fileExtensionToExtensionsSet.keySet().forEach(
					it -> checkState(!fileExtensions.contains(it), "there already is a recorder for metamodel " + it));
			Set<String> fileExtensionsSet = new HashSet<>(fileExtensions);
			fileExtensions.forEach(it -> fileExtensionToExtensionsSet.put(it, fileExtensionsSet));
			ChangeRecorder recorder = new ChangeRecorder(recorderResourceSet);
			fileExtensionsToRecorder.put(fileExtensionsSet, recorder);
		}
	}

	ResourceRepositoryImpl(VsumFileSystemLayout fileSystemLayout) {
		this.fileSystemLayout = fileSystemLayout;
		this.correspondenceModel = createPersistableCorrespondenceModel(fileSystemLayout.getCorrespondencesURI());
		modelsResourceSet.eAdapters()
				.add(new ResourceRegistrationAdapter(resource -> getCreateOrLoadModelUnlessLoading(resource.getURI())));
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
		try {
			for (String modelPath : Files.readAllLines(fileSystemLayout.getModelsNamesFilesPath())) {
				URI uri = URI.createURI(modelPath);
				loadOrCreateResource(modelsResourceSet, uri);
				createOrLoadModel(uri);
			}
		} catch (NoSuchFileException e) {
			// There are no existing models, so don't do anything
		}
	}

	@Override
	public EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel() {
		return CorrespondenceModelViewFactory.createEditableCorrespondenceModelView(correspondenceModel);
	}

	@Override
	public ModelInstance getModel(URI modelUri) {
		return modelInstances.get(modelUri);
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
			String fileExtension = modelInstance.getURI().fileExtension();
			if (!fileExtensionRecorderMapping.hasRecorder(fileExtension)) {
				fileExtensionRecorderMapping.registerRecorder(Set.of(fileExtension), modelsResourceSet);
			}
			ChangeRecorder recorder = fileExtensionRecorderMapping.getRecorder(fileExtension);
			recorder.addToRecording(modelInstance.getResource());
			if (isRecording && !recorder.isRecording()) {
				recorder.beginRecording();
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
				modelInstance.delete();
				modelInstancesIterator.remove();
			} else {
				modelInstance.save();
			}
		}
		correspondenceModel.save();
		try {
			writeModelsFile();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Iterable<TransactionalChange> recordChanges(Runnable changeApplicator) {
		fileExtensionRecorderMapping.getRecorders().forEach(ChangeRecorder::beginRecording);
		isRecording = true;
		LOGGER.debug("Start recording virtual model");
		changeApplicator.run();
		LOGGER.debug("End recording virtual model");
		isRecording = false;
		fileExtensionRecorderMapping.getRecorders().forEach(ChangeRecorder::endRecording);
		return fileExtensionRecorderMapping.getRecorders().stream().map(ChangeRecorder::getChange)
				.filter(TransactionalChange::containsConcreteChange).toList();
	}

	@Override
	public VitruviusChange applyChange(VitruviusChange change) {
		return change.resolveAndApply(modelsResourceSet);
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
		fileExtensionRecorderMapping.fileExtensionsToRecorder.values().forEach(ChangeRecorder::close);
		modelsResourceSet.getResources().forEach(Resource::unload);
		modelsResourceSet.getResources().clear();
	}
}
