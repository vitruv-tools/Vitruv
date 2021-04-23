package tools.vitruv.framework.vsum.internal

import java.util.HashMap
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import tools.vitruv.framework.change.recording.ChangeRecorder
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.util.ResourceRegistrationAdapter
import static tools.vitruv.framework.correspondence.CorrespondenceModelFactory.createCorrespondenceModel
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import org.eclipse.emf.common.util.URI
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import tools.vitruv.framework.change.description.VitruviusChange

package class ResourceRepositoryImpl implements ModelRepository {
	static val logger = Logger.getLogger(ResourceRepositoryImpl)
	val ResourceSet modelsResourceSet
	val ResourceSet correspondencesResourceSet
	val VitruvDomainRepository domainRepository
	val Map<URI, ModelInstance> modelInstances = new HashMap()
	val VsumFileSystemLayout fileSystemLayout
	val InternalCorrespondenceModel correspondenceModel
	val Map<VitruvDomain, ChangeRecorder> domainToRecorder = new HashMap()
	var isRecording = false
	var isLoading = false

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
		getModel(modelURI) 
			?: createOrLoadModel(modelURI)
	}

	def private createOrLoadModel(URI modelURI) {
		checkState(getDomainForURI(modelURI) !== null,
			"Cannot create a new model instance at the URI '%s' because no domain is registered for that URI", modelURI)
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
			val recorder = domainToRecorder.computeIfAbsent(getDomainForURI(modelInstance.URI)) [
				new ChangeRecorder(modelsResourceSet)
			]
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

	def private VitruvDomain getDomainForURI(URI uri) {
		domainRepository.getDomain(uri.fileExtension)
	}

	override void startRecording() {
		domainToRecorder.values.forEach[beginRecording()]
		isRecording = true
		logger.debug("Start recording virtual model")
	}

	override Iterable<? extends TransactionalChange> endRecording() {
		logger.debug("End recording virtual model")
		isRecording = false
		domainToRecorder.values.forEach[endRecording()]
		return domainToRecorder.values
			.map [recorder | recorder.change]
			.filter[containsConcreteChange]
			.toList()
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
		domainToRecorder.values.forEach[close()]
		modelsResourceSet.resources.forEach[unload]
		correspondencesResourceSet.resources.forEach[unload]
		modelsResourceSet.resources.clear()
		correspondencesResourceSet.resources.clear()
	}

}
