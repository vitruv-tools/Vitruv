package tools.vitruv.framework.vsum.repositories

import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Set
import java.util.concurrent.Callable
import java.util.function.Consumer
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.transaction.TransactionalEditingDomain
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.correspondence.CorrespondenceModelImpl
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalModelRepository
import tools.vitruv.framework.vsum.helper.FileSystemHelper

class ModelRepositoryImpl implements InternalModelRepository, CorrespondenceProviding {
	static val Logger logger = Logger::getLogger(ModelRepositoryImpl.simpleName)
	@Accessors(PUBLIC_GETTER)
	InternalCorrespondenceModel correspondenceModel
	val File folder
	val FileSystemHelper fileSystemHelper
	val Map<VURI, ModelInstance> modelInstances
	@Accessors(PUBLIC_GETTER)
	val ResourceSet resourceSet
	val VitruvDomainRepository metamodelRepository

	new(File folder, VitruvDomainRepository metamodelRepository) {
		this(folder, metamodelRepository, null)
	}

	new(File folder, VitruvDomainRepository metamodelRepository, ClassLoader classLoader) {
		this.metamodelRepository = metamodelRepository
		this.folder = folder
		resourceSet = new ResourceSetImpl
		modelInstances = new HashMap<VURI, ModelInstance>
		fileSystemHelper = new FileSystemHelper(this.folder)
		initializeCorrespondenceModel
		loadVURIsOfVSMUModelInstances
	}

	/**
	 * Supports three cases: 1) get registered 2) create non-existing 3) get unregistered but
	 * existing that contains at most a root element without children. But throws an exception if an
	 * instance that contains more than one element exists at the uri.
	 * DECISION Since we do not throw an exception (which can happen in 3) we always return a valid
	 * model. Hence the caller do not have to check whether the retrieved model is null.
	 */
	def ModelInstance getAndLoadModelInstanceOriginal(VURI modelURI, boolean forceLoadByDoingUnloadBeforeLoad) {
		val ModelInstance modelInstance = getModelInstanceOriginal(modelURI)
		try {
			if (modelURI.EMFUri.toString.startsWith("pathmap") || EMFBridge::existsResourceAtUri(modelURI.EMFUri))
				modelInstance.load(getMetamodelByURI(modelURI).defaultLoadOptions, forceLoadByDoingUnloadBeforeLoad)
		} catch (RuntimeException re) {
// could not load model instance --> this should only be the case when the
// model is not existing yet
			logger.info('''Exception during loading of model instance «modelInstance» occured: «re»''')
		}

		return modelInstance
	}

	override getModel(VURI modelURI) {
		getAndLoadModelInstanceOriginal(modelURI, false)
	}

	override forceReloadModelIfExisting(VURI modelURI) {
		if (existsModelInstance(modelURI))
			getAndLoadModelInstanceOriginal(modelURI, true)
	}

	def ModelInstance getModelInstanceOriginal(VURI modelURI) {
		var modelInstance = modelInstances.get(modelURI)
		if (modelInstance === null) {
			createRecordingCommandAndExecuteCommandOnTransactionalDomain([ // case 2 or 3
				val internalModelInstance = getOrCreateUnregisteredModelInstance(modelURI)
				ModelRepositoryImpl.this.modelInstances.put(modelURI, internalModelInstance)
				saveVURIsOfVsumModelInstances()
				return null
			])
			modelInstance = modelInstances.get(modelURI)
		}
		return modelInstance
	}

	private def boolean existsModelInstance(VURI modelURI) {
		modelInstances.containsKey(modelURI)
	}

	private def void saveModelInstance(ModelInstance modelInstance) {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			val metamodel = getMetamodelByURI(modelInstance.URI)
			val resourceToSave = modelInstance.resource
			try {
				EcoreResourceBridge.saveResource(resourceToSave, metamodel.defaultSaveOptions)
			} catch (IOException e) {
				throw new RuntimeException('''Could not save VURI + «modelInstance.URI»: «e»''')
			}
			return null
		])
	}

	override persistRootElement(VURI vuri, EObject rootEObject) {
		val modelInstance = getModel(vuri)
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			TuidManager.instance.registerObjectUnderModification(rootEObject)
			val Resource resource = modelInstance.resource
			resource.contents.add(rootEObject)
			resource.modified = true
			logger.debug('''Create model with resource: «resource»''')
			TuidManager.instance.updateTuidsOfRegisteredObjects
			TuidManager.instance.flushRegisteredObjectsUnderModification
			return null
		])
	}

	override saveAllModels() {
		logger.debug('''Saving all models of model repository for VSUM: «folder»''')
		saveAllChangedModels
		saveAllChangedCorrespondenceModels
	}

	private def void deleteEmptyModels() {
		val List<VURI> vurisToDelete = new ArrayList<VURI>
		for (ModelInstance modelInstance : modelInstances.values) {
			if (modelInstance.rootElements.empty)
				vurisToDelete.add(modelInstance.URI)
		}
		for (VURI vuri : vurisToDelete) {
			deleteModel(vuri)
		}
	}

	private def void saveAllChangedModels() {
		deleteEmptyModels
		for (ModelInstance modelInstance : modelInstances.values) {
			val resourceToSave = modelInstance.resource
			if (resourceToSave.modified) {
				logger.debug('''  Saving resource: «resourceToSave»''')
				saveModelInstance(modelInstance)
			}
		}
	}

	private def void saveAllChangedCorrespondenceModels() {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			logger.debug('''  Saving correspondence model: «ModelRepositoryImpl.this.correspondenceModel.resource»''')
			ModelRepositoryImpl.this.correspondenceModel.saveModel
			ModelRepositoryImpl.this.correspondenceModel.resetChangedAfterLastSave
			return null
		])
	}

	def ModelInstance getOrCreateUnregisteredModelInstance(VURI modelURI) {
		val fileExtension = modelURI.fileExtension
		val metamodel = metamodelRepository.getDomain(fileExtension)
		if (null ===
			metamodel)
			throw new RuntimeException('''Cannot create a new model instance at the uri '«»«modelURI»' because no metamodel is registered for the file extension '«»«fileExtension»'!''')

		return loadModelInstance(modelURI, metamodel)
	}

	def ModelInstance loadModelInstance(VURI modelURI, VitruvDomain metamodel) {
		val emfURI = modelURI.EMFUri
		val modelResource = EcoreResourceBridge::loadResourceAtURI(emfURI, resourceSet, metamodel.defaultLoadOptions)
		val modelInstance = new ModelInstance(modelURI, modelResource)
		return modelInstance
	}

	private def void initializeCorrespondenceModel() {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			{
				val correspondencesVURI = fileSystemHelper.correspondencesVURI
				var Resource correspondencesResource = null
				if (EMFBridge.existsResourceAtUri(correspondencesVURI.EMFUri)) {
					logger.debug('''Loading correspondence model from: «fileSystemHelper.correspondencesVURI»''')
					correspondencesResource = resourceSet.getResource(correspondencesVURI.EMFUri, true)
				} else {
					correspondencesResource = resourceSet.createResource(correspondencesVURI.EMFUri)
				}
				correspondenceModel = new CorrespondenceModelImpl(this, metamodelRepository, correspondencesVURI,
					correspondencesResource)
				return null
			}
		])
	}

	private def void loadVURIsOfVSMUModelInstances() {
		val Set<VURI> vuris = fileSystemHelper.loadVsumVURIsFromFile
		for (VURI vuri : vuris) {
			val metamodel = getMetamodelByURI(vuri)
			val modelInstance = loadModelInstance(vuri, metamodel)
			modelInstances.put(vuri, modelInstance)
		}
	}

	private def void saveVURIsOfVsumModelInstances() {
		fileSystemHelper.saveVsumVURIsToFile(modelInstances.keySet)
	}

	def VitruvDomain getMetamodelByURI(VURI uri) {
		val fileExtension = uri.fileExtension
		return metamodelRepository.getDomain(fileExtension)
	}

// private void loadAndMapCorrepondenceInstances() {
// for (Metamodel metamodel : metamodelManaging) {
// for (Metamodel metamodel2 : metamodelManaging) {
// if (metamodel != metamodel2
// && getCorrespondenceModel(metamodel.uRI, metamodel2.uRI) === null) {
// createCorrespondenceModel(new MetamodelPair(metamodel, metamodel2))
// }
// }
// }
// }
	private def synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
		if (null === TransactionalEditingDomain::Factory.INSTANCE.getEditingDomain(resourceSet))
			TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(resourceSet)
		return TransactionalEditingDomain::Factory.INSTANCE.getEditingDomain(resourceSet)
	}

	private def void deleteModel(VURI vuri) {
		val ModelInstance modelInstance = getModelInstanceOriginal(vuri)
		val Resource resource = modelInstance.resource
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			try {
				logger.debug('''Deleting resource: «resource»''')
				resource.delete(null)
				ModelRepositoryImpl.this.modelInstances.remove(vuri)
			} catch (IOException e) {
				logger.info('''Deletion of resource «resource» did not work. Reason: «e»''')
			}
			return null
		])
	}

	override createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable) {
		EMFCommandBridge::createAndExecuteVitruviusRecordingCommand(callable, transactionalEditingDomain)
	}

	override executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command) {
		EMFCommandBridge::executeVitruviusRecordingCommand(transactionalEditingDomain, command)
	}

	override executeOnResourceSet(Consumer<ResourceSet> function) {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			function.accept(ModelRepositoryImpl.this.resourceSet)
			return null
		])
	}
}
