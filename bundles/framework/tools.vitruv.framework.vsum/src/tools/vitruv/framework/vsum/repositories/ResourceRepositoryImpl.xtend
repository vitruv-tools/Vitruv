package tools.vitruv.framework.vsum.repositories

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
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
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.vsum.ModelRepository

import static java.util.Collections.emptyMap
import static extension tools.vitruv.framework.util.ResourceSetUtil.getRequiredTransactionalEditingDomain
import static extension tools.vitruv.framework.util.ResourceSetUtil.getTransactionalEditingDomain
import static extension tools.vitruv.framework.util.command.EMFCommandBridge.executeVitruviusRecordingCommandAndFlushHistory
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
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
		resourceSet.eAdapters += new ResourceRegistrationAdapter [createModel(VURI.getInstance(it))]
	}

	def private ChangeRecorder getOrCreateChangeRecorder(VURI vuri) {
		domainToRecorder.computeIfAbsent(getDomainForURI(vuri)) [
			new ChangeRecorder(this.uuidGeneratorAndResolver)
		]
	}

	override ModelInstance getModel(VURI modelURI) {
		if (modelURI.EMFUri.toString().startsWith("pathmap")) {
			loadExistingModel(modelURI, true)
		} else {
			createModel(modelURI)
		}
	}
	
	def private ModelInstance createModel(VURI modelURI) {
		checkState(getDomainForURI(modelURI) !== null, "Cannot create a new model instance at the URI '%s' because no domain is registered for that URI", modelURI)
		var modelResource = this.resourceSet.getResource(modelURI.EMFUri, false)
		if (modelResource === null) {
			modelResource = this.resourceSet.createResource(modelURI.EMFUri)
		}
		val modelInstance = new ModelInstance(modelURI, modelResource)
		registerModelInstance(modelURI, modelInstance)
		return modelInstance
	}

	def private ModelInstance loadExistingModel(VURI modelURI, boolean generateUuids) {
		checkState(getDomainForURI(modelURI) !== null, "Cannot create a new model instance at the URI '%s' because no domain is registered for that URI", modelURI)
		executeAsCommand [
			val modelResource = URIUtil.loadResourceAtURI(modelURI.EMFUri, this.resourceSet)
			val modelInstance = new ModelInstance(modelURI, modelResource)
			if (!generateUuids) {
				relinkUuids(modelInstance)
			} else {
				generateUuids(modelInstance)
			}
			registerModelInstance(modelURI, modelInstance)
			return modelInstance
		]
	}
	
	def private void relinkUuids(ModelInstance modelInstance) {
		modelInstance.resource.allContents.forEachRemaining [uuidGeneratorAndResolver.registerEObject(it)]
	}
	
	def private void generateUuids(ModelInstance modelInstance) {
		modelInstance.resource.allContents.forEachRemaining [uuidGeneratorAndResolver.generateUuid(it)]
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

	override void persistAsRoot(EObject rootEObject, VURI vuri) {
		val ModelInstance modelInstance = getModel(vuri)
		executeAsCommand [
			val resource = modelInstance.resource
			resource.contents += rootEObject
			resource.modified = true
			logger.debug('''Create model with resource: «resource»'''.toString)
		]
	}

	override void saveAllModels() {
		logger.debug('''Saving all models of model repository for VSUM «fileSystemLayout»''')
		deleteEmptyModels()
		for (modelInstance : this.modelInstances.values) {
			val resourceToSave = modelInstance.resource
			if (resourceToSave.modified) {
				logger.trace('''Saving resource: «resourceToSave»''')
				saveModelInstance(modelInstance)
				resourceToSave.setModified(false)
			}
		}
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

	def private void deleteEmptyModels() {
		// materialize the models to delete because else we’ll get a ConcurrentModificationException
		modelInstances.values.filter[resource.contents.isEmpty].toList.forEach[deleteModel(it.URI)]
	}
	
	def private void deleteModel(VURI vuri) {
		val modelInstance = getModel(vuri)
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

	def private initializeUuidProviderAndResolver() {
		executeAsCommand [
			var uuidProviderVURI = fileSystemLayout.uuidProviderAndResolverVURI
			logger.trace('''Creating or loading uuid provider and resolver model from: «uuidProviderVURI»''')
			var Resource uuidProviderResource = resourceSet.loadOrCreateResource(uuidProviderVURI.EMFUri)
			// TODO HK We cannot enable strict mode here, because for textual views we will not get
			// create changes in any case. We should therefore use one monitor per model and turn on
			// strict mode
			// depending on the kind of model/view (textual vs. semantic)
			new UuidGeneratorAndResolverImpl(this.resourceSet, uuidProviderResource, false)
		]
	}

	def private void loadVURIsOfVSMUModelInstances() {
		for (VURI vuri : fileSystemLayout.loadVsumVURIs()) {
			var modelInstance = loadExistingModel(vuri, false)
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
		fileSystemLayout.getConsistencyMetadataModelVURI(metadataKey)
	}

	override Resource getModelResource(VURI vuri) {
		getModel(vuri).resource
	}

	def dispose() {
		resourceSet.transactionalEditingDomain?.dispose
		resourceSet.resources.forEach[unload]
		resourceSet.resources.clear
	}

}
