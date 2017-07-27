package tools.vitruv.framework.vsum.repositories

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import java.io.File
import java.io.IOException
import java.util.List
import java.util.Map
import java.util.Set
import java.util.concurrent.Callable
import java.util.function.Consumer
import java.util.stream.Collectors
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.transaction.TransactionalEditingDomain
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.change.recording.impl.AtomicEmfChangeRecorderImpl
import tools.vitruv.framework.correspondence.CorrespondenceModelImpl
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.ResourceSetUtil
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalModelRepository
import tools.vitruv.framework.vsum.helper.FileSystemHelper

class ResourceRepositoryImpl implements InternalModelRepository, CorrespondenceProviding {
	static val VM_ARGUMENT_UNRESOLVE_PROPAGATED_CHANGES = "unresolvePropagatedChanges"
	static extension Logger = Logger::getLogger(ResourceRepositoryImpl.simpleName)
	@Accessors(PUBLIC_GETTER)
	val ResourceSet resourceSet
	val VitruvDomainRepository metamodelRepository
	val Map<VURI, ModelInstance> modelInstances
	InternalCorrespondenceModel correspondenceModel
	val FileSystemHelper fileSystemHelper
	val File folder
	val AtomicEmfChangeRecorder changeRecorder
	@Accessors(PUBLIC_GETTER)
	val List<TransactionalChange> lastResolvedChanges
	@Accessors(PUBLIC_GETTER)
	val List<TransactionalChange> lastUnresolvedChanges

	new(File folder, VitruvDomainRepository metamodelRepository) {
		this(folder, metamodelRepository, null)
	}

	new(File folder, VitruvDomainRepository metamodelRepository, ClassLoader classLoader) {
		this.metamodelRepository = metamodelRepository
		this.folder = folder
		this.resourceSet = new ResourceSetImpl
		ResourceSetUtil::addExistingFactoriesToResourceSet(this.resourceSet)
		modelInstances = newHashMap
		fileSystemHelper = new FileSystemHelper(this.folder)
		initializeCorrespondenceModel
		loadVURIsOfVSMUModelInstances
		changeRecorder = new AtomicEmfChangeRecorderImpl(unresolveChanges, false)
		changeRecorder.addToRecording(this.resourceSet)
		lastResolvedChanges = newArrayList
		lastUnresolvedChanges = newArrayList
	}

	override unresolveChanges() {
		val String unresolvePropagatedChanges = System::getProperty(VM_ARGUMENT_UNRESOLVE_PROPAGATED_CHANGES)
		return unresolvePropagatedChanges !== null
	}

	override getModel(VURI modelURI) {
		return getAndLoadModelInstanceOriginal(modelURI, false)
	}

	override forceReloadModelIfExisting(VURI modelURI) {
		if (existsModelInstance(modelURI))
			getAndLoadModelInstanceOriginal(modelURI, true)
	}

	override persistRootElement(VURI vuri, EObject rootEObject) {
		val ModelInstance modelInstance = getModelInstanceOriginal(vuri)
		createRecordingCommandAndExecuteCommandOnTransactionalDomain[
			TuidManager.instance.registerObjectUnderModification(rootEObject)
			val Resource resource = modelInstance.resource
			resource.contents += rootEObject
			resource.modified = true
			debug('''Create model with resource: «resource»''')
			TuidManager::instance.updateTuidsOfRegisteredObjects
			TuidManager::instance.flushRegisteredObjectsUnderModification
			return null
		]
	}

	override saveAllModels() {
		debug('''Saving all models of model repository for VSUM: «folder»''')
		saveAllChangedModels
		saveAllChangedCorrespondenceModels
	}

	private def void deleteEmptyModels() {
		val List<VURI> vurisToDelete = newArrayList
		modelInstances.values.filter[rootElements.empty].forEach[vurisToDelete += URI]
		vurisToDelete.forEach[deleteModel]
	}

	private def void saveAllChangedModels() {
		deleteEmptyModels
		modelInstances.values.filter[resource.modified].forEach [
			debug('''  Saving resource: «resource»''')
			saveModelInstance
		]
	}

	private def void saveAllChangedCorrespondenceModels() {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain[
			debug('''  Saving correspondence model: «ResourceRepositoryImpl.this.correspondenceModel.resource»''')
			ResourceRepositoryImpl.this.correspondenceModel.saveModel
			ResourceRepositoryImpl.this.correspondenceModel.resetChangedAfterLastSave
			return null
		]
	}

	def ModelInstance getOrCreateUnregisteredModelInstance(VURI modelURI) {
		val String fileExtension = modelURI.fileExtension
		val VitruvDomain metamodel = metamodelRepository.getDomain(fileExtension)
		if (metamodel ===
			null)
			throw new RuntimeException('''
				Cannot create a new model instance at the uri '«»«modelURI»' because no metamodel is registered for the file extension '«»«fileExtension»'!
			''')
		return loadModelInstance(modelURI, metamodel)
	}

	def ModelInstance loadModelInstance(VURI modelURI, VitruvDomain metamodel) {
		val URI emfURI = modelURI.EMFUri
		val Resource modelResource = URIUtil::loadResourceAtURI(emfURI, resourceSet, metamodel.defaultLoadOptions)
		val ModelInstance modelInstance = ModelInstance::createModelInstance(modelURI, modelResource)
		return modelInstance
	}

	/**
	 * Returns the correspondence model in this model repository
	 * @return the correspondence model
	 */
	override getCorrespondenceModel() {
		return correspondenceModel
	}

	override startRecording() {
		changeRecorder.beginRecording
		debug("Start recording virtual model")
	}

	override endRecording() {
		val List<TransactionalChange> result = newArrayList
		executeRecordingCommand(EMFCommandBridge::createVitruviusRecordingCommand [
			changeRecorder.endRecording
			return null
		])

		lastResolvedChanges.clear
		lastUnresolvedChanges.clear
		val resolvedChanges = changeRecorder.resolvedChanges
		val unresolvedChanges = changeRecorder.unresolvedChanges
		// TODO HK: Replace this correspondence exclusion with an inclusion of only file extensions
		// that are
		// supported by the domains of the VirtualModel
		val filterFunction = [ List<TransactionalChange> changes |
			changes.parallelStream.filter [ change |
				change.URI === null || !change.URI.EMFUri.toString.endsWith("correspondence")
			].collect(Collectors::toList)
		]
		val filteredResolved = filterFunction.apply(resolvedChanges)
		val filteredUnresolved = filterFunction.apply(unresolvedChanges)
		lastResolvedChanges += filteredResolved
		lastUnresolvedChanges += filteredUnresolved
		result += if (unresolveChanges) filteredUnresolved else filteredResolved

		debug("End recording virtual model")
		return result
	}

	override createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable) {
		EMFCommandBridge::createAndExecuteVitruviusRecordingCommand(callable, transactionalEditingDomain)
	}

	override executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command) {
		EMFCommandBridge::executeVitruviusRecordingCommand(transactionalEditingDomain, command)
	}

	override executeOnResourceSet(Consumer<ResourceSet> function) {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain[
			function.accept(ResourceRepositoryImpl.this.resourceSet)
			return null
		]
	}

	override executeRecordingCommand(VitruviusRecordingCommand command) {
		executeRecordingCommandOnTransactionalDomain(command)
	}

	private def void deleteModel(VURI vuri) {
		val ModelInstance modelInstance = getModelInstanceOriginal(vuri)
		val Resource resource = modelInstance.resource
		createRecordingCommandAndExecuteCommandOnTransactionalDomain[
			try {
				debug('''Deleting resource: «resource»''')
				resource.delete(null)
				ResourceRepositoryImpl.this.modelInstances.remove(vuri)
			} catch (IOException e) {
				info('''Deletion of resource «resource» did not work. Reason: «e»''')
			}
			return null
		]
	}

	private def synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
		if (null === TransactionalEditingDomain::Factory::INSTANCE.getEditingDomain(resourceSet))
			TransactionalEditingDomain::Factory::INSTANCE.createEditingDomain(resourceSet)
		return TransactionalEditingDomain::Factory::INSTANCE.getEditingDomain(resourceSet)
	}

	private def void saveVURIsOfVsumModelInstances() {
		fileSystemHelper.saveVsumVURIsToFile(modelInstances.keySet)
	}

	private def VitruvDomain getMetamodelByURI(VURI uri) {
		val fileExtension = uri.fileExtension
		return metamodelRepository.getDomain(fileExtension)
	}

	private def void loadVURIsOfVSMUModelInstances() {
		val Set<VURI> vuris = fileSystemHelper.loadVsumVURIsFromFile
		for (VURI vuri : vuris) {
			val VitruvDomain metamodel = getMetamodelByURI(vuri)
			val ModelInstance modelInstance = loadModelInstance(vuri, metamodel)
			modelInstances.put(vuri, modelInstance)
		}
	}

	/**
	 * Supports three cases: 1) get registered 2) create non-existing 3) get unregistered but
	 * existing that contains at most a root element without children. But throws an exception if an
	 * instance that contains more than one element exists at the uri.
	 * DECISION Since we do not throw an exception (which can happen in 3) we always return a valid
	 * model. Hence the caller do not have to check whether the retrieved model is null.
	 */
	private def ModelInstance getAndLoadModelInstanceOriginal(VURI modelURI, boolean forceLoadByDoingUnloadBeforeLoad) {
		val ModelInstance modelInstance = getModelInstanceOriginal(modelURI)
		try {
			if (modelURI.EMFUri.toString.startsWith("pathmap") || URIUtil::existsResourceAtUri(modelURI.EMFUri)) {
				modelInstance.load(getMetamodelByURI(modelURI).defaultLoadOptions, forceLoadByDoingUnloadBeforeLoad)
			}
		} catch (RuntimeException re) {
			// could not load model instance --> this should only be the case when the
			// model is not existing yet
			info('''Exception during loading of model instance «modelInstance» occured: «re»''')
		}

		return modelInstance
	}

	private def void initializeCorrespondenceModel() {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain[
			val VURI correspondencesVURI = fileSystemHelper.correspondencesVURI
			var Resource correspondencesResource = null
			if (URIUtil::existsResourceAtUri(correspondencesVURI.EMFUri)) {

				debug('''Loading correspondence model from: «fileSystemHelper.correspondencesVURI»''')
				correspondencesResource = resourceSet.getResource(correspondencesVURI.EMFUri, true)
			} else {
				correspondencesResource = resourceSet.createResource(correspondencesVURI.EMFUri)
			}
			correspondenceModel = new CorrespondenceModelImpl(new TuidResolverImpl(metamodelRepository, this), this,
				metamodelRepository, correspondencesVURI, correspondencesResource)
			return null
		]
	}

	private def ModelInstance getModelInstanceOriginal(VURI modelURI) {
		var modelInstance = modelInstances.get(modelURI)
		if (modelInstance === null) {
			createRecordingCommandAndExecuteCommandOnTransactionalDomain[ // case 2 or 3
				val ModelInstance internalModelInstance = getOrCreateUnregisteredModelInstance(modelURI)
				ResourceRepositoryImpl.this.modelInstances.put(modelURI, internalModelInstance)
				saveVURIsOfVsumModelInstances
				return null
			]
			modelInstance = modelInstances.get(modelURI)
		}
		return modelInstance
	}

	private def boolean existsModelInstance(VURI modelURI) {
		return modelInstances.containsKey(modelURI)
	}

	private def void saveModelInstance(ModelInstance modelInstance) {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain[
			val VitruvDomain metamodel = getMetamodelByURI(modelInstance.URI)
			val Resource resourceToSave = modelInstance.resource
			try {
				EcoreResourceBridge::saveResource(resourceToSave, metamodel.defaultSaveOptions)
			} catch (IOException e) {
				throw new RuntimeException('''Could not save VURI + «modelInstance.URI»: «e»''')
			}
			return null
		]
	}
// private void loadAndMapCorrepondenceInstances() {
// for (Metamodel metamodel : metamodelManaging) {
// for (Metamodel metamodel2 : metamodelManaging) {
// if (metamodel != metamodel2
// && getCorrespondenceModel(metamodel.URI, metamodel2.URI) === null) {
// createCorrespondenceModel(new MetamodelPair(metamodel, metamodel2))
// }
// }
// }
// }
}
