package tools.vitruv.framework.vsum.repositories

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
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
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import tools.vitruv.framework.change.recording.ChangeRecorder
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.util.ResourceRegistrationAdapter

class ResourceRepositoryImpl implements ModelRepository {
	static val logger = Logger.getLogger(ResourceRepositoryImpl)
	val ResourceSet resourceSet
	val VitruvDomainRepository domainRepository
	val Map<VURI, ModelInstance> modelInstances = new HashMap()
	val VsumFileSystemLayout fileSystemLayout
	@Accessors
	val UuidGeneratorAndResolver uuidGeneratorAndResolver
	val Map<VitruvDomain, ChangeRecorder> domainToRecorder = new HashMap()
	var isRecording = false

	new(VsumFileSystemLayout fileSystemLayout, VitruvDomainRepository domainRepository) {
		this.domainRepository = domainRepository
		this.fileSystemLayout = fileSystemLayout
		this.resourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(domainRepository)
		this.uuidGeneratorAndResolver = initializeUuidProviderAndResolver()
		loadVURIsOfVSMUModelInstances()
		resourceSet.eAdapters += new ResourceRegistrationAdapter [getModel(VURI.getInstance(it))]
	}

	override getModel(VURI modelURI) {
		val existingInstance = modelInstances.get(modelURI)
		if (existingInstance !== null) {
			return existingInstance
		}
		modelURI.createOrLoadModel(false)
	}
	
	def private createOrLoadModel(VURI modelURI, boolean forceLoadAndRelinkUuids) {
		checkState(getDomainForURI(modelURI) !== null, "Cannot create a new model instance at the URI '%s' because no domain is registered for that URI", modelURI)
		val resource = if (modelURI.EMFUri.toString().startsWith("pathmap") || forceLoadAndRelinkUuids) {
			loadResource(modelURI, !forceLoadAndRelinkUuids)
		} else {
			createResource(modelURI)
		}
		val modelInstance = new ModelInstance(resource)
		this.modelInstances.put(modelURI, modelInstance)
		modelInstance.registerRecorder()
		return modelInstance
	}
	
	def private createResource(VURI modelURI) {
		var resource = this.resourceSet.getResource(modelURI.EMFUri, false)
		if (resource === null) {
			resource = this.resourceSet.createResource(modelURI.EMFUri)
		}
		return resource
	}

	def private loadResource(VURI modelURI, boolean generateUuids) {
		val resource = URIUtil.loadResourceAtURI(modelURI.EMFUri, this.resourceSet)
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
		if (modelInstance.URI.EMFUri.isFile() || modelInstance.URI.EMFUri.isPlatform()) {
			domainToRecorder.computeIfAbsent(getDomainForURI(modelInstance.URI)) [
				new ChangeRecorder(this.uuidGeneratorAndResolver)
			] => [
				addToRecording(modelInstance.resource)
				if (isRecording && !it.isRecording) {
					beginRecording()
				}
			]
		}
	}
	
	override void persistAsRoot(EObject rootEObject, VURI vuri) {
		vuri.model.addRoot(rootEObject)
	}

	override void saveOrDeleteModels() {
		logger.debug('''Saving all models of model repository for VSUM «fileSystemLayout»''')
		for (modelInstance : modelInstances.values) {
			if (modelInstance.empty) {
				modelInstance.delete
			} else {
				modelInstance.save
			}
		}
		saveVURIsOfVsumModelInstances()
	}

	def private initializeUuidProviderAndResolver() {
		var uuidProviderVURI = fileSystemLayout.uuidProviderAndResolverVURI
		logger.trace('''Creating or loading uuid provider and resolver model from: «uuidProviderVURI»''')
		var Resource uuidProviderResource = resourceSet.loadOrCreateResource(uuidProviderVURI.EMFUri)
		// TODO HK We cannot enable strict mode here, because for textual views we will not get
		// create changes in any case. We should therefore use one monitor per model and turn on
		// strict mode
		// depending on the kind of model/view (textual vs. semantic)
		new UuidGeneratorAndResolverImpl(this.resourceSet, uuidProviderResource, false)
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
		domainToRecorder.values.forEach[endRecording()]
		return domainToRecorder.values.map [ recorder |
			val compChange = VitruviusChangeFactory.instance.createCompositeTransactionalChange()
			recorder.changes.forEach[compChange.addChange(it)]
			return compChange
		].filter[it.containsConcreteChange()]
	}

	override VURI getMetadataModelURI(String... metadataKey) {
		fileSystemLayout.getConsistencyMetadataModelVURI(metadataKey)
	}

	override Resource getModelResource(VURI vuri) {
		getModel(vuri).resource
	}

	def dispose() {
		resourceSet.resources.forEach[unload]
		resourceSet.resources.clear
	}

}
