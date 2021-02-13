package tools.vitruv.framework.vsum.repositories

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import java.io.IOException
import java.util.HashMap
import java.util.Map
import java.util.function.Consumer
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.vsum.ModelRepository

import static java.util.Collections.emptyMap
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.loadOrCreateResource
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import tools.vitruv.framework.change.recording.ChangeRecorder

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
	}

	def private ChangeRecorder getOrCreateChangeRecorder(VURI vuri) {
		domainToRecorder.computeIfAbsent(getDomainForURI(vuri)) [
			new ChangeRecorder(this.uuidGeneratorAndResolver)
		]
	}

	/** 
	 * Supports three cases: 1) get registered 2) create non-existing 3) get unregistered but
	 * existing that contains at most a root element without children. But throws an exception if an
	 * instance that contains more than one element exists at the uri.
	 * DECISION Since we do not throw an exception (which can happen in 3) we always return a valid
	 * model. Hence the caller do not have to check whether the retrieved model is null.
	 */
	def private ModelInstance getAndLoadModelInstanceOriginal(VURI modelURI, boolean forceLoadByDoingUnloadBeforeLoad) {
		val ModelInstance modelInstance = getModelInstanceOriginal(modelURI)
		try {
			if (modelURI.EMFUri.toString().startsWith("pathmap") || URIUtil.existsResourceAtUri(modelURI.EMFUri)) {
				modelInstance.load(forceLoadByDoingUnloadBeforeLoad)
				relinkUuids(modelInstance)
			}
		} catch (RuntimeException re) {
			// could not load model instance --> this should only be the case when the
			// model is not existing yet
			if (logger.isInfoEnabled) {
				logger.info('''Exception during loading of model instance «modelInstance» occured: «re»''')
			}
		}

		return modelInstance
	}

	def private void relinkUuids(ModelInstance modelInstance) {
		for (EObject root : modelInstance.rootElements) {
			root.eAllContents.forEachRemaining [uuidGeneratorAndResolver.registerEObject(it)]
		}
	}

	override ModelInstance getModel(VURI modelURI) {
		return getAndLoadModelInstanceOriginal(modelURI, false)
	}

	override void forceReloadModelIfExisting(VURI modelURI) {
		if (existsModelInstance(modelURI)) {
			getAndLoadModelInstanceOriginal(modelURI, true)
		}
	}

	def private ModelInstance getModelInstanceOriginal(VURI modelURI) {
		var ModelInstance modelInstance = modelInstances.get(modelURI)
		if (modelInstance === null) {
			// case 2 or 3
			modelInstance = getOrCreateUnregisteredModelInstance(modelURI)
			registerModelInstance(modelURI, modelInstance)
		}
		return modelInstance
	}

	def private void registerModelInstance(VURI modelUri, ModelInstance modelInstance) {
		this.modelInstances.put(modelUri, modelInstance)
		// Do not record other URI types than file and platform (e.g. pathmap) because they cannot
		// be modified
		if (modelUri.EMFUri.isFile() || modelUri.EMFUri.isPlatform()) {
			var recorder = getOrCreateChangeRecorder(modelUri)
			recorder.addToRecording(modelInstance.resource)
			if (isRecording && !recorder.isRecording()) {
				recorder.beginRecording()
			}
		}
		saveVURIsOfVsumModelInstances()
	}

	def private boolean existsModelInstance(VURI modelURI) {
		return modelInstances.containsKey(modelURI)
	}

	def private void saveModelInstance(ModelInstance modelInstance) {
		var resourceToSave = modelInstance.resource
		try {
			EcoreResourceBridge.saveResource(resourceToSave, emptyMap)
		} catch (IOException e) {
			logger.warn('''Model could not be saved: «modelInstance.URI»''')
			throw new RuntimeException('''Could not save VURI «modelInstance.URI»''', e)
		}
	}

	override void persistAsRoot(EObject rootEObject, VURI vuri) {
		val ModelInstance modelInstance = getModelInstanceOriginal(vuri)
		TuidManager.instance.registerObjectUnderModification(rootEObject)
		val resource = modelInstance.resource
		resource.contents += rootEObject
		resource.modified = true
		if (logger.isDebugEnabled) logger.debug('''Create model with resource: «resource»''')
		TuidManager.instance.updateTuidsOfRegisteredObjects()
		// Usually we should deregister the object, but since we do not know if it was
		// registered before and if the other objects should still be registered
		// we cannot remove it or flush the registry
	}

	override void saveAllModels() {
		if (logger.isDebugEnabled) logger.debug('''Saving all models of model repository for VSUM «fileSystemLayout»''')
		saveAllChangedModels()
	}

	def private void deleteEmptyModels() {
		// materialize the models to delete because else we’ll get a ConcurrentModificationException
		modelInstances.values.filter[rootElements.isEmpty].toList.forEach[deleteModel(it.URI)]
	}

	def private void saveAllChangedModels() {
		deleteEmptyModels()
		for (modelInstance : this.modelInstances.values) {
			val resourceToSave = modelInstance.resource
			if (resourceToSave.isModified()) {
				logger.trace('''Saving resource: «resourceToSave»''')
				saveModelInstance(modelInstance)
				resourceToSave.setModified(false)
			}
		}
	}

	def private ModelInstance getOrCreateUnregisteredModelInstance(VURI modelURI) {
		if (getDomainForURI(modelURI) === null) {
			throw new RuntimeException( '''Cannot create a new model instance at the uri '«modelURI»' because no domain is registered for the URI «modelURI»!''')
		}
		val modelResource = URIUtil.loadResourceAtURI(modelURI.EMFUri, this.resourceSet)
		val modelInstance = new ModelInstance(modelURI, modelResource)
		relinkUuids(modelInstance)
		return modelInstance
	}

	def private initializeUuidProviderAndResolver() {
		var uuidProviderVURI = fileSystemLayout.uuidProviderAndResolverVURI
		if (logger.isTraceEnabled) {
			logger.trace('''Creating or loading uuid provider and resolver model from: «uuidProviderVURI»''')
		}
		var Resource uuidProviderResource = resourceSet.loadOrCreateResource(uuidProviderVURI.EMFUri)
		// TODO HK We cannot enable strict mode here, because for textual views we will not get
		// create changes in any case. We should therefore use one monitor per model and turn on
		// strict mode
		// depending on the kind of model/view (textual vs. semantic)
		new UuidGeneratorAndResolverImpl(this.resourceSet, uuidProviderResource, false)
	}

	def private void loadVURIsOfVSMUModelInstances() {
		for (VURI vuri : fileSystemLayout.loadVsumVURIs()) {
			var modelInstance = getOrCreateUnregisteredModelInstance(vuri)
			registerModelInstance(vuri, modelInstance)
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
		return domainToRecorder.values
			.map [recorder | VitruviusChangeFactory.instance.createCompositeTransactionalChange(recorder.changes)]
			.filter [containsConcreteChange]
			.toList()
	}

	def private void deleteModel(VURI vuri) {
		val modelInstance = getModelInstanceOriginal(vuri)
		val resource = modelInstance.resource
		try {
			logger.debug('''Deleting resource: «resource»''')
			resource.delete(null)
			modelInstances.remove(vuri)
		} catch (IOException e) {
			logger.error('''Deletion of resource «resource» did not work.''', e)
		}
	}

	override void executeOnUuidResolver(Consumer<UuidResolver> function) {
		function.accept(uuidGeneratorAndResolver)
	}

	override VURI getMetadataModelURI(String... metadataKey) {
		fileSystemLayout.getConsistencyMetadataModelVURI(metadataKey)
	}

	override Resource getModelResource(VURI vuri) {
		getModelInstanceOriginal(vuri).resource
	}

	def dispose() {
		resourceSet.resources.forEach[unload]
		resourceSet.resources.clear
	}

}
