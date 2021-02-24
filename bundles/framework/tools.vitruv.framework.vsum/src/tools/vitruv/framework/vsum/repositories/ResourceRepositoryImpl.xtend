package tools.vitruv.framework.vsum.repositories

import java.util.HashMap
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.vsum.ModelRepository

import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.loadOrCreateResource
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.getOrCreateResource
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import tools.vitruv.framework.change.recording.ChangeRecorder
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.util.ResourceRegistrationAdapter
import tools.vitruv.framework.correspondence.CorrespondenceModelFactory
import tools.vitruv.framework.correspondence.CorrespondenceModel

class ResourceRepositoryImpl implements ModelRepository {
	static val logger = Logger.getLogger(ResourceRepositoryImpl)
	val ResourceSet modelsResourceSet
	val ResourceSet correspondencesResourceSet
	val VitruvDomainRepository domainRepository
	val Map<VURI, ModelInstance> modelInstances = new HashMap()
	val VsumFileSystemLayout fileSystemLayout
	val UuidGeneratorAndResolver uuidGeneratorAndResolver
	@Accessors(PUBLIC_GETTER)
	val CorrespondenceModel correspondenceModel
	val Map<VitruvDomain, ChangeRecorder> domainToRecorder = new HashMap()
	var isRecording = false

	new(VsumFileSystemLayout fileSystemLayout, VitruvDomainRepository domainRepository) {
		this.domainRepository = domainRepository
		this.fileSystemLayout = fileSystemLayout
		this.modelsResourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(domainRepository)
		this.correspondencesResourceSet = new ResourceSetImpl().withGlobalFactories()
		this.uuidGeneratorAndResolver = initializeUuidProviderAndResolver()
		this.correspondenceModel = initializeCorrespondenceModel().genericView
		this.modelsResourceSet.eAdapters += new ResourceRegistrationAdapter [getModel(VURI.getInstance(it))]
		loadVURIsOfVSMUModelInstances()
	}
	
	override getUuidResolver() {
		return uuidGeneratorAndResolver
	}

	override getModel(VURI modelURI) {
		modelInstances.get(modelURI)
			?: createOrLoadModel(modelURI, false)
	}
	
	def private createOrLoadModel(VURI modelURI, boolean forceLoadAndRelinkUuids) {
		checkState(getDomainForURI(modelURI) !== null, "Cannot create a new model instance at the URI '%s' because no domain is registered for that URI", modelURI)
		val resource = if ((modelURI.EMFUri.isFile || modelURI.EMFUri.isPlatform) && !forceLoadAndRelinkUuids) {
			getOrCreateResource(modelURI)
		} else {
			loadOrCreateResource(modelURI, !forceLoadAndRelinkUuids)
		}
		val modelInstance = new ModelInstance(resource)
		this.modelInstances.put(modelURI, modelInstance)
		modelInstance.registerRecorder()
		return modelInstance
	}
	
	def private getOrCreateResource(VURI modelURI) {
		return modelsResourceSet.getOrCreateResource(modelURI.EMFUri)
	}

	def private loadOrCreateResource(VURI modelURI, boolean generateUuids) {
		val resource = modelsResourceSet.loadOrCreateResource(modelURI.EMFUri)
		if (!generateUuids) {
			relinkUuids(resource)
		} else {
			generateUuids(resource)
		}
		return resource
	}
	
	def private void relinkUuids(Resource resource) {
		resource.allContents.forEachRemaining [uuidGeneratorAndResolver.registerEObject(it)]
	}
	
	def private void generateUuids(Resource resource) {
		resource.allContents.forEachRemaining [uuidGeneratorAndResolver.generateUuid(it)]
	}
	
	def private void registerRecorder(ModelInstance modelInstance) {
		// Only monitor modifiable models (file / platform URIs, not pathmap URIs)
		if (modelInstance.URI.EMFUri.isFile || modelInstance.URI.EMFUri.isPlatform) {
			val recorder = domainToRecorder.computeIfAbsent(getDomainForURI(modelInstance.URI)) [
				new ChangeRecorder(this.uuidGeneratorAndResolver)
			]
			recorder.addToRecording(modelInstance.resource)
			if (this.isRecording && !recorder.isRecording) {
				recorder.beginRecording()
			}
		}
	}
	
	override void persistAsRoot(EObject rootEObject, VURI vuri) {
		vuri.model.addRoot(rootEObject)
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
		saveVURIsOfVsumModelInstances()
	}

	def private initializeUuidProviderAndResolver() {
		var uuidProviderVURI = fileSystemLayout.uuidProviderAndResolverVURI
		logger.trace('''Creating or loading uuid provider and resolver model from: «uuidProviderVURI»''')
		var Resource uuidProviderResource = modelsResourceSet.loadOrCreateResource(uuidProviderVURI.EMFUri)
		new UuidGeneratorAndResolverImpl(this.modelsResourceSet, uuidProviderResource)
	}
	
	def private initializeCorrespondenceModel() {
		var correspondencesVURI = fileSystemLayout.correspondencesVURI
		logger.trace('''Creating or loading correspondence model from: «correspondencesVURI»''')
		val correspondencesResource = correspondencesResourceSet.loadOrCreateResource(
			correspondencesVURI.EMFUri)
		modelInstances.put(correspondencesVURI, new ModelInstance(correspondencesResource))
		CorrespondenceModelFactory.instance.createCorrespondenceModel(
			uuidGeneratorAndResolver, correspondencesVURI, correspondencesResource)
	}

	def private void loadVURIsOfVSMUModelInstances() {
		for (VURI vuri : fileSystemLayout.loadVsumVURIs()) {
			createOrLoadModel(vuri, true)
		}
	}

	def private void saveVURIsOfVsumModelInstances() {
		// TODO Reimplement saving of V-SUM with a proper reload mechanism
		// fileSystemHelper.saveVsumVURIsToFile(modelInstances.keySet)
	}

	def private VitruvDomain getDomainForURI(VURI uri) {
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

	override VURI getMetadataModelURI(String... metadataKey) {
		fileSystemLayout.getConsistencyMetadataModelVURI(metadataKey)
	}

	override Resource getModelResource(VURI vuri) {
		getModel(vuri).resource
	}

	override close() {
		modelsResourceSet.resources.forEach[unload]
		correspondencesResourceSet.resources.forEach[unload]
		modelsResourceSet.resources.clear
		correspondencesResourceSet.resources.clear
	}

}
