package tools.vitruv.framework.vsum.repositories

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
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelImpl
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.ModelRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.helper.FileSystemHelper

class ModelRepositoryImpl implements ModelRepository, CorrespondenceProviding {
	static val Logger logger = Logger::getLogger(ModelRepositoryImpl.simpleName)
	val ResourceSet resourceSet
	val VitruvDomainRepository metamodelRepository
	val Map<VURI, ModelInstance> modelInstances
	InternalCorrespondenceModel correspondenceModel
	val FileSystemHelper fileSystemHelper
	val String vsumName

	new(String vsumName, VitruvDomainRepository metamodelRepository) {
		this(vsumName, metamodelRepository, null)
	}

	new(String vsumName, VitruvDomainRepository metamodelRepository, ClassLoader classLoader) {
		this.metamodelRepository = metamodelRepository
		this.vsumName = vsumName
		resourceSet = new ResourceSetImpl
		modelInstances = new HashMap<VURI, ModelInstance>
		fileSystemHelper = new FileSystemHelper(vsumName)
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
	def private ModelInstance getAndLoadModelInstanceOriginal(VURI modelURI, boolean forceLoadByDoingUnloadBeforeLoad) {
		val modelInstance = getModelInstanceOriginal(modelURI)
		try {
			if (modelURI.EMFUri.toString.startsWith("pathmap") || EMFBridge::existsResourceAtUri(modelURI.EMFUri))
				modelInstance.load(getMetamodelByURI(modelURI).defaultLoadOptions, forceLoadByDoingUnloadBeforeLoad)
		} catch (RuntimeException re) {
			// could not load model instance --> this should only be the case when the
			// model is not existing yet
			logger.info('''Exception during loading of model instance «modelInstance» occured: «re»''')
		}
		modelInstance
	}

	override ModelInstance getModel(VURI modelURI) {
		getAndLoadModelInstanceOriginal(modelURI, false)
	}

	override void forceReloadModelIfExisting(VURI modelURI) {
		if (existsModelInstance(modelURI))
			getAndLoadModelInstanceOriginal(modelURI, true)

	}

	def private ModelInstance getModelInstanceOriginal(VURI modelURI) {
		var modelInstance = modelInstances.get(modelURI)
		if (modelInstance === null) {
			createRecordingCommandAndExecuteCommandOnTransactionalDomain([ // case 2 or 3
				val internalModelInstance = getOrCreateUnregisteredModelInstance(modelURI)
				ModelRepositoryImpl.this.modelInstances.put(modelURI, internalModelInstance)
				saveVURIsOfVsumModelInstances
				return null
			])
			modelInstance = modelInstances.get(modelURI)
		}
		modelInstance
	}

	def private boolean existsModelInstance(VURI modelURI) {
		modelInstances.containsKey(modelURI)
	}

	def private void saveModelInstance(ModelInstance modelInstance) {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			val metamodel = getMetamodelByURI(modelInstance.URI)
			val resourceToSave = modelInstance.resource
			try {
				EcoreResourceBridge::saveResource(resourceToSave, metamodel.defaultSaveOptions)
			} catch (IOException e) {
				throw new RuntimeException('''Could not save VURI + «modelInstance.URI»: «e»''')
			}
			null
		])
	}

	override void persistRootElement(VURI vuri, EObject rootEObject) {
		val modelInstance = getModel(vuri)
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			TuidManager::instance.registerObjectUnderModification(rootEObject)
			val resource = modelInstance.resource
			resource.contents.add(rootEObject)
			resource.modified = true
			logger.debug('''Create model with resource: «resource»''')
			TuidManager::instance.updateTuidsOfRegisteredObjects
			TuidManager::instance.flushRegisteredObjectsUnderModification
			null
		])
	}

	override void saveAllModels() {
		logger.debug('''Saving all models of model repository for VSUM: «vsumName»''')
		saveAllChangedModels
		saveAllChangedCorrespondenceModels
	}

	def private void deleteEmptyModels() {
		val List<VURI> vurisToDelete = new ArrayList<VURI>
		modelInstances.values.filter[rootElements.empty].forEach[vurisToDelete.add(URI)]
		vurisToDelete.forEach[deleteModel]
	}

	def private void saveAllChangedModels() {
		deleteEmptyModels
		modelInstances.values.filter[resource.modified].forEach [
			logger.debug('''  Saving resource: «resource»''')
			saveModelInstance(it)
		]
	}

	def private void saveAllChangedCorrespondenceModels() {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			logger.debug('''  Saving correspondence model: «ModelRepositoryImpl.this.correspondenceModel.resource»''')
			ModelRepositoryImpl.this.correspondenceModel.saveModel
			ModelRepositoryImpl.this.correspondenceModel.resetChangedAfterLastSave
			null
		])
	}

	def private ModelInstance getOrCreateUnregisteredModelInstance(VURI modelURI) {
		val fileExtension = modelURI.fileExtension
		val metamodel = metamodelRepository.getDomain(fileExtension)
		if (metamodel === null)
			throw new RuntimeException('''Cannot create a new model instance at the uri '«»«modelURI»' because no metamodel is registered for the file extension '«»«fileExtension»'!''')
		loadModelInstance(modelURI, metamodel)
	}

	def private ModelInstance loadModelInstance(VURI modelURI, VitruvDomain metamodel) {
		val emfURI = modelURI.EMFUri
		val modelResource = EcoreResourceBridge::loadResourceAtURI(emfURI, resourceSet, metamodel.defaultLoadOptions)
		val modelInstance = new ModelInstance(modelURI, modelResource)
		modelInstance
	}

	def private void initializeCorrespondenceModel() {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			val correspondencesVURI = fileSystemHelper.correspondencesVURI
			var Resource correspondencesResource = null
			if (EMFBridge::existsResourceAtUri(correspondencesVURI.EMFUri)) {
				logger.debug('''Loading correspondence model from: «fileSystemHelper.correspondencesVURI»''')
				correspondencesResource = resourceSet.getResource(correspondencesVURI.EMFUri, true)
			} else {
				correspondencesResource = resourceSet.createResource(correspondencesVURI.EMFUri)
			}
			correspondenceModel = new CorrespondenceModelImpl(this, metamodelRepository, correspondencesVURI,
				correspondencesResource)
			null
		])
	}

	/** 
	 * Returns the correspondence model in this model repository
	 * @return the correspondence model
	 */
	override CorrespondenceModel getCorrespondenceModel() {
		correspondenceModel
	}

	def private void loadVURIsOfVSMUModelInstances() {
		val Set<VURI> vuris = fileSystemHelper.loadVsumVURIsFromFile
		vuris.forEach [
			val VitruvDomain metamodel = getMetamodelByURI(it)
			val ModelInstance modelInstance = loadModelInstance(it, metamodel)
			modelInstances.put(it, modelInstance)
		]
	}

	def private void saveVURIsOfVsumModelInstances() {
		fileSystemHelper.saveVsumVURIsToFile(modelInstances.keySet)
	}

	def private VitruvDomain getMetamodelByURI(VURI uri) {
		val fileExtension = uri.fileExtension
		metamodelRepository.getDomain(fileExtension)
	}

	// private void loadAndMapCorrepondenceInstances() {
	// for (Metamodel metamodel : this.metamodelManaging) {
	// for (Metamodel metamodel2 : this.metamodelManaging) {
	// if (metamodel != metamodel2
	// && getCorrespondenceModel(metamodel.URI, metamodel2.URI) == null) {
	// createCorrespondenceModel(new MetamodelPair(metamodel, metamodel2))
	// }
	// }
	// }
	// }
	def private synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
		if (null === TransactionalEditingDomain.Factory::INSTANCE.getEditingDomain(resourceSet))
			TransactionalEditingDomain.Factory::INSTANCE.createEditingDomain(resourceSet)
		TransactionalEditingDomain.Factory::INSTANCE.getEditingDomain(resourceSet)
	}

	def private void deleteModel(VURI vuri) {
		val modelInstance = getModelInstanceOriginal(vuri)
		val resource = modelInstance.resource
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			try {
				logger.debug('''Deleting resource: «resource»''')
				resource.delete(null)
				ModelRepositoryImpl.this.modelInstances.remove(vuri)
			} catch (IOException e) {
				logger.info('''Deletion of resource «resource» did not work. Reason: «e»''')
			}
			null
		])
	}

	override void createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable) {
		EMFCommandBridge::createAndExecuteVitruviusRecordingCommand(callable, getTransactionalEditingDomain())
	}

	override void executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command) {
		EMFCommandBridge::executeVitruviusRecordingCommand(getTransactionalEditingDomain(), command)
	}

	override void executeOnResourceSet(Consumer<ResourceSet> function) {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void> {
			override call() throws Exception {
				function.accept(ModelRepositoryImpl.this.resourceSet)
				null
			}
		})
	}
}
