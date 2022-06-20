package tools.vitruv.framework.vsum.internal

import java.util.HashMap
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.domains.repository.VitruvDomainRepository

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import tools.vitruv.change.composite.recording.ChangeRecorder
import static tools.vitruv.change.correspondence.CorrespondenceModelFactory.createCorrespondenceModel
import tools.vitruv.change.correspondence.InternalCorrespondenceModel
import org.eclipse.emf.common.util.URI
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import tools.vitruv.change.composite.description.VitruviusChange
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.mapFixed
import tools.vitruv.framework.propagation.ChangeInPropagation
import java.util.Set
import static com.google.common.base.Preconditions.checkState
import java.util.HashSet

package class ResourceRepositoryImpl implements ModelRepository {
	static val logger = Logger.getLogger(ResourceRepositoryImpl)
	val ResourceSet modelsResourceSet
	val ResourceSet correspondencesResourceSet
	val VitruvDomainRepository domainRepository
	val Map<URI, ModelInstance> modelInstances = new HashMap()
	val VsumFileSystemLayout fileSystemLayout
	val InternalCorrespondenceModel correspondenceModel
	val extension FileExtensionRecorderMapping fileExtensionsRecorderMapping = new FileExtensionRecorderMapping
	
	var isRecording = false
	var isLoading = false
	
	/**
	 * Manages change recorders for file extensions. Ensures that only one change recorder per file extension exists.
	 * A recorder is assigned to a set of file extensions (for the case that multiple file extensions belong to
	 * the same domain of models and should be recorder together) and recorders can be retrieved for a given
	 * file extension.
	 */
	private static class FileExtensionRecorderMapping {
		val Map<Set<String>, ChangeRecorder> fileExtensionsToRecorder = new HashMap()
		val Map<String, Set<String>> fileExtensionToExtensionsSet = new HashMap()
		val Map<ChangeRecorder, Boolean> shouldTransitivelyPropagate = new HashMap()

		def Set<ChangeRecorder> getRecorders() {
			fileExtensionsToRecorder.values.toSet
		}

		def boolean hasRecorder(String fileExtension) {
			fileExtensionsToRecorder.containsKey(fileExtensionToExtensionsSet.get(fileExtension))
		}

		def ChangeRecorder getRecorder(String fileExtension) {
			fileExtensionsToRecorder.get(fileExtensionToExtensionsSet.get(fileExtension))
		}

		def boolean shouldPropagateTransitively(ChangeRecorder recorder) {
			shouldTransitivelyPropagate.get(recorder)
		}

		def void registerRecorder(Set<String> fileExtensions, boolean shouldTransitivelyPropagate,
			ResourceSet recorderResourceSet) {
			fileExtensionToExtensionsSet.keySet.forEach [
				checkState(!fileExtensions.contains(it), "there already is a recorder for metamodel %s", it)
			]
			val fileExtensionsSet = new HashSet(fileExtensions)
			fileExtensions.forEach[fileExtensionToExtensionsSet.put(it, fileExtensionsSet)]
			val recorder = new ChangeRecorder(recorderResourceSet)
			fileExtensionsToRecorder.put(fileExtensionsSet, recorder)
			this.shouldTransitivelyPropagate.put(recorder, shouldTransitivelyPropagate)
		}
	}

	new(VsumFileSystemLayout fileSystemLayout, VitruvDomainRepository domainRepository) {
		this.domainRepository = domainRepository
		this.fileSystemLayout = fileSystemLayout
		this.modelsResourceSet = new ResourceSetImpl().withGlobalFactories()
		this.correspondencesResourceSet = new ResourceSetImpl().withGlobalFactories()
		this.correspondenceModel = createCorrespondenceModel(fileSystemLayout.correspondencesURI)
		this.modelsResourceSet.eAdapters += new ResourceRegistrationAdapter [
			if(!isLoading) getCreateOrLoadModel(it.URI)
		]
	}

	override loadExistingModels() {
		isLoading = true
		readModelsFile()
		correspondenceModel.loadSerializedCorrespondences(modelsResourceSet)
		isLoading = false
	}

	private def writeModelsFile() {
		Files.write(fileSystemLayout.modelsNamesFilesPath, modelsResourceSet.resources.map[URI.toString])
	}

	private def readModelsFile() {
		try {
			for (modelPath : Files.readAllLines(fileSystemLayout.modelsNamesFilesPath)) {
				val uri = URI.createURI(modelPath)
				modelsResourceSet.loadOrCreateResource(uri)
				createOrLoadModel(uri)
			}
		} catch (NoSuchFileException e) {
			// There are no existing models, so don't do anything
		}
	}

	override getCorrespondenceModel() {
		correspondenceModel.genericView
	}

	override getModel(URI modelURI) {
		modelInstances.get(modelURI)
	}

	private def getCreateOrLoadModel(URI modelURI) {
		getModel(modelURI) ?: createOrLoadModel(modelURI)
	}

	def private createOrLoadModel(URI modelURI) {
		val resource = if ((modelURI.isFile || modelURI.isPlatform)) {
				modelsResourceSet.getOrCreateResource(modelURI)
			} else {
				modelsResourceSet.loadOrCreateResource(modelURI)
			}
		val modelInstance = new ModelInstance(resource)
		this.modelInstances.put(modelURI, modelInstance)
		modelInstance.registerRecorder()
		return modelInstance
	}

	def private void registerRecorder(ModelInstance modelInstance) {
		// Only monitor modifiable models (file / platform URIs, not pathmap URIs)
		if (modelInstance.URI.isFile || modelInstance.URI.isPlatform) {
			if (!hasRecorder(modelInstance.URI.fileExtension)) {
				var shouldTransitivelyPropagate = true
				val domain = domainRepository.getDomainForFileExtension(modelInstance.URI.fileExtension)
				val fileExtensions = if (domain !== null) {
					shouldTransitivelyPropagate = domain.shouldTransitivelyPropagateChanges  
					domain.fileExtensions
				} else {
					#{modelInstance.URI.fileExtension}
				}
				registerRecorder(fileExtensions, shouldTransitivelyPropagate, modelsResourceSet)
			}
			val recorder = getRecorder(modelInstance.URI.fileExtension)
			recorder.addToRecording(modelInstance.resource)
			if (this.isRecording && !recorder.isRecording) {
				recorder.beginRecording()
			}
		}
	}

	override void persistAsRoot(EObject rootEObject, URI uri) {
		getCreateOrLoadModel(uri).addRoot(rootEObject)
	}

	override void saveOrDeleteModels() {
		if(logger.isDebugEnabled) logger.debug('''Saving all models of model repository for VSUM «fileSystemLayout»''')
		val modelInstancesIterator = modelInstances.entrySet.iterator
		while (modelInstancesIterator.hasNext()) {
			val modelInstance = modelInstancesIterator.next().value
			if (modelInstance.empty) {
				modelInstance.delete()
				modelInstancesIterator.remove()
			} else {
				modelInstance.save()
			}
		}
		correspondenceModel.save()
		writeModelsFile()
	}
	
	override Iterable<ChangeInPropagation> recordChanges(Runnable changeApplicator) {
		recorders.forEach[beginRecording()]
		isRecording = true
		logger.debug("Start recording virtual model")
		changeApplicator.run()
		logger.debug("End recording virtual model")
		isRecording = false
		recorders.forEach[endRecording()]
		return fileExtensionsRecorderMapping.recorders.filter[change.containsConcreteChange].mapFixed [
			new ChangeInPropagation(change, fileExtensionsRecorderMapping.shouldPropagateTransitively(it))
		]
	}

	override VitruviusChange applyChange(VitruviusChange change) {
		change.resolveAndApply(modelsResourceSet)
	}

	override URI getMetadataModelURI(String... metadataKey) {
		fileSystemLayout.getConsistencyMetadataModelURI(metadataKey)
	}

	override Resource getModelResource(URI uri) {
		getCreateOrLoadModel(uri).resource
	}

	override close() {
		fileExtensionsToRecorder.values.forEach[close()]
		modelsResourceSet.resources.forEach[unload]
		correspondencesResourceSet.resources.forEach[unload]
		modelsResourceSet.resources.clear()
		correspondencesResourceSet.resources.clear()
	}

	override getModelResources() {
		return modelsResourceSet.resources
	}

}
