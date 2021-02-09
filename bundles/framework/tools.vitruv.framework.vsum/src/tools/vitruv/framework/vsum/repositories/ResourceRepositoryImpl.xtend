package tools.vitruv.framework.vsum.repositories

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import java.io.File
import java.io.IOException
import java.util.HashMap
import java.util.Map
import java.util.concurrent.Callable
import java.util.function.Consumer
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelFactory
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
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
import tools.vitruv.framework.vsum.helper.FileSystemHelper

import static java.util.Collections.emptyMap
import static extension tools.vitruv.framework.util.ResourceSetUtil.getRequiredTransactionalEditingDomain
import static extension tools.vitruv.framework.util.ResourceSetUtil.getTransactionalEditingDomain
import static extension tools.vitruv.framework.util.command.EMFCommandBridge.executeVitruviusRecordingCommandAndFlushHistory
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.loadOrCreateResource
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

class ResourceRepositoryImpl implements ModelRepository, CorrespondenceProviding {
	static val logger = Logger.getLogger(ResourceRepositoryImpl.simpleName)
	val ResourceSet resourceSet
	val VitruvDomainRepository domainRepository
	val Map<VURI, ModelInstance> modelInstances = new HashMap()
	val InternalCorrespondenceModel correspondenceModel
	val FileSystemHelper fileSystemHelper
	val File folder
	@Accessors
	val UuidGeneratorAndResolver uuidGeneratorAndResolver
	val Map<VitruvDomain, AtomicEmfChangeRecorder> domainToRecorder = new HashMap()
	var isRecording = false

	new(File folder, VitruvDomainRepository metamodelRepository) {
		this(folder, metamodelRepository, null)
	}

	new(File folder, VitruvDomainRepository domainRepository, ClassLoader classLoader) {
		this.domainRepository = domainRepository
		this.folder = folder
		this.resourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(domainRepository)
		try {
			this.fileSystemHelper = new FileSystemHelper(folder)
		} catch (IOException e) {
			val message = '''Unable to initialize V-SUM metadata folders in folder: «folder»'''
			logger.error(message, e)
			throw new IllegalStateException(message, e)
		}
		this.uuidGeneratorAndResolver = initializeUuidProviderAndResolver()
		this.correspondenceModel = initializeCorrespondenceModel()
		loadVURIsOfVSMUModelInstances()
	}

	def private AtomicEmfChangeRecorder getOrCreateChangeRecorder(VURI vuri) {
		var VitruvDomain domain = getDomainForURI(vuri)
		domainToRecorder.putIfAbsent(domain, new AtomicEmfChangeRecorder(this.uuidGeneratorAndResolver))
		return domainToRecorder.get(domain)
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
			logger.info('''Exception during loading of model instance «modelInstance» occured: «re»''')
		}

		return modelInstance
	}

	def private void relinkUuids(ModelInstance modelInstance) {
		for (EObject root : modelInstance.rootElements) {
			root.eAllContents.forEachRemaining [ object |
				if (uuidGeneratorAndResolver.hasUuid(object)) {
					uuidGeneratorAndResolver.registerEObject(this.uuidGeneratorAndResolver.getUuid(object), object)
				} else {
					// TODO Move logic to UUID generator and resolver and reuse the special cases
					// for Java
					logger.trace('''Element «object» has no UUID that can be linked during resource reload''')
				}
			]
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
			executeAsCommand [
				// case 2 or 3
				var ModelInstance internalModelInstance = getOrCreateUnregisteredModelInstance(modelURI)
				registerModelInstance(modelURI, internalModelInstance)
			]
			modelInstance = modelInstances.get(modelURI)
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
		executeAsCommand [
			var resourceToSave = modelInstance.resource
			try {
				if (!resourceSet.requiredTransactionalEditingDomain.isReadOnly(resourceToSave)) {
					// we allow resources without a domain for internal uses.
					EcoreResourceBridge.saveResource(resourceToSave, emptyMap)
				}
			} catch (IOException e) {
				logger.warn('''Model could not be saved: «modelInstance.URI»''')
				throw new RuntimeException('''Could not save VURI «modelInstance.URI»''', e)
			}
			return null
		]
	}

	override void persistAsRoot(EObject rootEObject, VURI vuri) {
		val ModelInstance modelInstance = getModelInstanceOriginal(vuri)
		executeAsCommand [
			TuidManager.instance.registerObjectUnderModification(rootEObject)
			val resource = modelInstance.resource
			resource.contents += rootEObject
			resource.modified = true
			logger.debug('''Create model with resource: «resource»'''.toString)
			TuidManager.instance.updateTuidsOfRegisteredObjects()
		// Usually we should deregister the object, but since we do not know if it was
		// registered before and if the other objects should still be registered
		// we cannot remove it or flush the registry
		]
	}

	override void saveAllModels() {
		logger.debug('''Saving all models of model repository for VSUM: «this.folder»''')
		saveAllChangedModels()
		saveAllChangedCorrespondenceModels()
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

	def private void saveAllChangedCorrespondenceModels() {
		executeAsCommand [
			logger.trace( '''Saving correspondence model: «correspondenceModel.URI»''')
			correspondenceModel.saveModel()
		]
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

	def private initializeCorrespondenceModel() {
		executeAsCommand [
			var correspondencesVURI = fileSystemHelper.correspondencesVURI
			logger.trace('''Creating or loading correspondence model from: «correspondencesVURI»''')
			val correspondencesResource = resourceSet.loadOrCreateResource(correspondencesVURI.EMFUri)
			correspondencesResource.save(null)
			var recorder = getOrCreateChangeRecorder(correspondencesVURI)
			recorder.addToRecording(correspondencesResource)
			recorder.beginRecording()
			val correspondenceModel = CorrespondenceModelFactory.instance.createCorrespondenceModel(
				new TuidResolverImpl(domainRepository, this), uuidGeneratorAndResolver, this, domainRepository,
				correspondencesVURI, correspondencesResource)
			recorder.endRecording()
			recorder.addToRecording(correspondencesResource)
			correspondenceModel
		]
	}

	def private initializeUuidProviderAndResolver() {
		executeAsCommand [
			var uuidProviderVURI = fileSystemHelper.uuidProviderAndResolverVURI
			logger.trace('''Creating or loading uuid provider and resolver model from: «uuidProviderVURI»''')
			var Resource uuidProviderResource = resourceSet.loadOrCreateResource(uuidProviderVURI.EMFUri)
			// TODO HK We cannot enable strict mode here, because for textual views we will not get
			// create changes in any case. We should therefore use one monitor per model and turn on
			// strict mode
			// depending on the kind of model/view (textual vs. semantic)
			new UuidGeneratorAndResolverImpl(this.resourceSet, uuidProviderResource, false)
		]
	}

	/** 
	 * Returns the correspondence model in this model repository
	 * @return the correspondence model
	 */
	override CorrespondenceModel getCorrespondenceModel() {
		correspondenceModel.genericView
	}

	def private void loadVURIsOfVSMUModelInstances() {
		for (VURI vuri : fileSystemHelper.loadVsumVURIsFromFile()) {
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
		executeAsCommand[
			domainToRecorder.values.forEach[endRecording()]
		]
		return domainToRecorder.values.map [ recorder |
			val compChange = VitruviusChangeFactory.instance.createCompositeTransactionalChange()
			recorder.changes.forEach[compChange.addChange(it)]
			return compChange
		].filter[it.containsConcreteChange()]
	}

	def private void deleteModel(VURI vuri) {
		val modelInstance = getModelInstanceOriginal(vuri)
		val resource = modelInstance.resource
		executeAsCommand [
			try {
				logger.debug('''Deleting resource: «resource»''')
				resource.delete(null)
				modelInstances.remove(vuri)
			} catch (IOException e) {
				logger.error('''Deletion of resource «resource» did not work.''', e)
				return null
			}
		]
	}

	override <T> T executeAsCommand(Callable<T> command) {
		resourceSet.requiredTransactionalEditingDomain.executeVitruviusRecordingCommandAndFlushHistory(command)
	}

	override VitruviusRecordingCommand executeAsCommand(Runnable command) {
		resourceSet.requiredTransactionalEditingDomain.executeVitruviusRecordingCommandAndFlushHistory(command)
	}

	override void executeOnUuidResolver(Consumer<UuidResolver> function) {
		executeAsCommand [function.accept(uuidGeneratorAndResolver)]
	}

	override VURI getMetadataModelURI(String... metadataKey) {
		fileSystemHelper.getConsistencyMetadataModelVURI(metadataKey)
	}

	override Resource getModelResource(VURI vuri) {
		getModelInstanceOriginal(vuri).resource
	}

	def dispose() {
		resourceSet.transactionalEditingDomain?.dispose
		resourceSet.resources.forEach[unload]
		resourceSet.resources.clear
	}

}
