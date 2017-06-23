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
import org.eclipse.emf.common.util.URI
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
	static final Logger logger = Logger.getLogger(ModelRepositoryImpl.getSimpleName())
	final ResourceSet resourceSet
	final VitruvDomainRepository metamodelRepository
	final Map<VURI, ModelInstance> modelInstances
	InternalCorrespondenceModel correspondenceModel
	final FileSystemHelper fileSystemHelper
	final File folder

	new(File folder, VitruvDomainRepository metamodelRepository) {
		this(folder, metamodelRepository, null)
	}

	new(File folder, VitruvDomainRepository metamodelRepository, ClassLoader classLoader) {
		this.metamodelRepository = metamodelRepository
		this.folder = folder
		this.resourceSet = new ResourceSetImpl()
		this.modelInstances = new HashMap<VURI, ModelInstance>()
		this.fileSystemHelper = new FileSystemHelper(this.folder)
		initializeCorrespondenceModel()
		loadVURIsOfVSMUModelInstances()
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
			if (modelURI.getEMFUri().toString().startsWith("pathmap") ||
				EMFBridge.existsResourceAtUri(modelURI.getEMFUri())) {
				modelInstance.load(getMetamodelByURI(modelURI).getDefaultLoadOptions(),
					forceLoadByDoingUnloadBeforeLoad)
			}
		} catch (RuntimeException re) {
			// could not load model instance --> this should only be the case when the
			// model is not existing yet
			logger.info('''Exception during loading of model instance «modelInstance» occured: «re»''')
		}

		return modelInstance
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
		var ModelInstance modelInstance = this.modelInstances.get(modelURI)
		if (modelInstance === null) {
			createRecordingCommandAndExecuteCommandOnTransactionalDomain([ // case 2 or 3
				var ModelInstance internalModelInstance = getOrCreateUnregisteredModelInstance(modelURI)
				ModelRepositoryImpl.this.modelInstances.put(modelURI, internalModelInstance)
				saveVURIsOfVsumModelInstances()
				return null
			])
			modelInstance = this.modelInstances.get(modelURI)
		}
		return modelInstance
	}

	def private boolean existsModelInstance(VURI modelURI) {
		return this.modelInstances.containsKey(modelURI)
	}

	def private void saveModelInstance(ModelInstance modelInstance) {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			var VitruvDomain metamodel = getMetamodelByURI(modelInstance.getURI())
			var Resource resourceToSave = modelInstance.getResource()
			try {
				EcoreResourceBridge.saveResource(resourceToSave, metamodel.getDefaultSaveOptions())
			} catch (IOException e) {
				throw new RuntimeException('''Could not save VURI + «modelInstance.getURI()»: «e»''')
			}
			return null
		])
	}

	override void persistRootElement(VURI vuri, EObject rootEObject) {
		val ModelInstance modelInstance = getModel(vuri)
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			TuidManager.getInstance().registerObjectUnderModification(rootEObject)
			val Resource resource = modelInstance.getResource()
			resource.getContents().add(rootEObject)
			resource.setModified(true)
			logger.debug('''Create model with resource: «resource»''')
			TuidManager.getInstance().updateTuidsOfRegisteredObjects()
			TuidManager.getInstance().flushRegisteredObjectsUnderModification()
			return null
		])
	}

	override void saveAllModels() {
		logger.debug('''Saving all models of model repository for VSUM: «this.folder»''')
		saveAllChangedModels()
		saveAllChangedCorrespondenceModels()
	}

	def private void deleteEmptyModels() {
		var List<VURI> vurisToDelete = new ArrayList<VURI>()
		for (ModelInstance modelInstance : this.modelInstances.values()) {
			if (modelInstance.getRootElements().isEmpty()) {
				vurisToDelete.add(modelInstance.getURI())
			}
		}
		for (VURI vuri : vurisToDelete) {
			deleteModel(vuri)
		}
	}

	def private void saveAllChangedModels() {
		deleteEmptyModels()
		for (ModelInstance modelInstance : this.modelInstances.values()) {
			var Resource resourceToSave = modelInstance.getResource()
			if (resourceToSave.isModified()) {
				logger.debug('''  Saving resource: «resourceToSave»''')
				saveModelInstance(modelInstance)
			}
		}
	}

	def private void saveAllChangedCorrespondenceModels() {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			logger.
				debug('''  Saving correspondence model: «ModelRepositoryImpl.this.correspondenceModel.getResource()»''')
			ModelRepositoryImpl.this.correspondenceModel.saveModel()
			ModelRepositoryImpl.this.correspondenceModel.resetChangedAfterLastSave()
			return null
		])
	}

	def private ModelInstance getOrCreateUnregisteredModelInstance(VURI modelURI) {
		var String fileExtension = modelURI.getFileExtension()
		var VitruvDomain metamodel = this.metamodelRepository.getDomain(fileExtension)
		if (metamodel ===
			null) {
			throw new RuntimeException('''Cannot create a new model instance at the uri '«»«modelURI»' because no metamodel is registered for the file extension '«»«fileExtension»'!''')
		}
		return loadModelInstance(modelURI, metamodel)
	}

	def private ModelInstance loadModelInstance(VURI modelURI, VitruvDomain metamodel) {
		var URI emfURI = modelURI.getEMFUri()
		var Resource modelResource = EcoreResourceBridge.loadResourceAtURI(emfURI, this.resourceSet,
			metamodel.getDefaultLoadOptions())
		var ModelInstance modelInstance = new ModelInstance(modelURI, modelResource)
		return modelInstance
	}

	def private void initializeCorrespondenceModel() {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			{
				var VURI correspondencesVURI = this.fileSystemHelper.getCorrespondencesVURI()
				var Resource correspondencesResource = null
				if (EMFBridge.existsResourceAtUri(correspondencesVURI.getEMFUri())) {
					logger.
						debug('''Loading correspondence model from: «this.fileSystemHelper.getCorrespondencesVURI()»''')
					correspondencesResource = this.resourceSet.getResource(correspondencesVURI.getEMFUri(), true)
				} else {
					correspondencesResource = this.resourceSet.createResource(correspondencesVURI.getEMFUri())
				}
				this.correspondenceModel = new CorrespondenceModelImpl(this, this.metamodelRepository,
					correspondencesVURI, correspondencesResource)
				return null
			}
		])
	}

	/** 
	 * Returns the correspondence model in this model repository
	 * @return the correspondence model
	 */
	override CorrespondenceModel getCorrespondenceModel() {
		return this.correspondenceModel
	}

	def private void loadVURIsOfVSMUModelInstances() {
		var Set<VURI> vuris = this.fileSystemHelper.loadVsumVURIsFromFile()
		for (VURI vuri : vuris) {
			var VitruvDomain metamodel = getMetamodelByURI(vuri)
			var ModelInstance modelInstance = loadModelInstance(vuri, metamodel)
			this.modelInstances.put(vuri, modelInstance)
		}
	}

	def private void saveVURIsOfVsumModelInstances() {
		this.fileSystemHelper.saveVsumVURIsToFile(this.modelInstances.keySet())
	}

	def private VitruvDomain getMetamodelByURI(VURI uri) {
		var String fileExtension = uri.getFileExtension()
		return this.metamodelRepository.getDomain(fileExtension)
	}

	// private void loadAndMapCorrepondenceInstances() {
	// for (Metamodel metamodel : this.metamodelManaging) {
	// for (Metamodel metamodel2 : this.metamodelManaging) {
	// if (metamodel != metamodel2
	// && getCorrespondenceModel(metamodel.getURI(), metamodel2.getURI()) == null) {
	// createCorrespondenceModel(new MetamodelPair(metamodel, metamodel2));
	// }
	// }
	// }
	// }
	def private synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
		if (null === TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resourceSet)) {
			TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(this.resourceSet)
		}
		return TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resourceSet)
	}

	def private void deleteModel(VURI vuri) {
		val ModelInstance modelInstance = getModelInstanceOriginal(vuri)
		val Resource resource = modelInstance.getResource()
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

	override void createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable) {
		EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(callable, getTransactionalEditingDomain())
	}

	override void executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command) {
		EMFCommandBridge.executeVitruviusRecordingCommand(getTransactionalEditingDomain(), command)
	}

	override void executeOnResourceSet(Consumer<ResourceSet> function) {
		createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			function.accept(ModelRepositoryImpl.this.resourceSet)
			return null
		])
	}
}
