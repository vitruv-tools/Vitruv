package tools.vitruv.framework.vsum.repositories

import java.util.HashMap
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.vsum.ModelRepository

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import tools.vitruv.framework.change.recording.ChangeRecorder
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.util.ResourceRegistrationAdapter
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver
import static tools.vitruv.framework.correspondence.CorrespondenceModelFactory.createCorrespondenceModel
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.vsum.ModelInstance

class ResourceRepositoryImpl implements ModelRepository {
	static val logger = Logger.getLogger(ResourceRepositoryImpl)
	val ResourceSet modelsResourceSet
	val ResourceSet correspondencesResourceSet
	val VitruvDomainRepository domainRepository
	val Map<URI, ModelInstance> modelInstances = new HashMap()
	val VsumFileSystemLayout fileSystemLayout
	val UuidGeneratorAndResolver uuidGeneratorAndResolver
	val InternalCorrespondenceModel correspondenceModel
	val Map<VitruvDomain, ChangeRecorder> domainToRecorder = new HashMap()
	var isRecording = false

	new(VsumFileSystemLayout fileSystemLayout, VitruvDomainRepository domainRepository) {
		this.domainRepository = domainRepository
		this.fileSystemLayout = fileSystemLayout
		this.modelsResourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(domainRepository)
		this.correspondencesResourceSet = new ResourceSetImpl().withGlobalFactories()
		this.uuidGeneratorAndResolver = createUuidGeneratorAndResolver(modelsResourceSet,
			fileSystemLayout.uuidProviderAndResolverURI)
		this.correspondenceModel = createCorrespondenceModel(fileSystemLayout.correspondencesURI)
		this.modelsResourceSet.eAdapters += new ResourceRegistrationAdapter[getCreateOrLoadModel(it.URI)]
	}

	override loadExistingModels() {
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		correspondenceModel.loadSerializedCorrespondences(modelsResourceSet)
	}

	override getUuidResolver() {
		return uuidGeneratorAndResolver
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
				new ChangeRecorder(this.uuidGeneratorAndResolver)
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
		if (logger.isDebugEnabled) logger.debug('''Saving all models of model repository for VSUM «fileSystemLayout»''')
		for (modelInstance : modelInstances.values) {
			if (modelInstance.empty) {
				modelInstance.delete()
			} else {
				modelInstance.save()
			}
		}
		correspondenceModel.save()
		uuidGeneratorAndResolver.save()
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
		domainToRecorder.values.forEach [endRecording()]
		return domainToRecorder.values
			.map [recorder | VitruviusChangeFactory.instance.createCompositeTransactionalChange(recorder.changes)]
			.filter [containsConcreteChange]
			.toList()
	}

	override URI getMetadataModelURI(String... metadataKey) {
		fileSystemLayout.getConsistencyMetadataModelURI(metadataKey)
	}

	override Resource getModelResource(URI uri) {
		getCreateOrLoadModel(uri).resource
	}

	override close() {
		modelsResourceSet.resources.forEach[unload]
		correspondencesResourceSet.resources.forEach[unload]
		modelsResourceSet.resources.clear()
		correspondencesResourceSet.resources.clear()
		uuidGeneratorAndResolver.close()
	}
	
}
