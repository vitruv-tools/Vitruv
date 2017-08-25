package tools.vitruv.extensions.dslruntime.commonalities.resources.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.util.datatypes.VURI

import static tools.vitruv.extensions.dslruntime.commonalities.CommonalitiesConstants.*

class IntermediateResourceBridgeI extends IntermediateResourceBridgeImpl {

	static val WATCHER = new ResourceIWatcher()
	
	// TODO is this too hacky?
	ChangePropagationResult propagationResult
	CorrespondenceModel correspondenceModel

	new() {
		eAdapters.add(WATCHER);
	}

	private static class ResourceIWatcher extends AdapterImpl {
		override notifyChanged(extension Notification msg) {
			if (eventType != Notification.SET) return;
			val resource = notifier as IntermediateResourceBridgeI
			switch feature {
				case ResourcesPackage.RESOURCE__NAME:
					resource.nameChanged(oldValue as String)
				case ResourcesPackage.RESOURCE__PATH:
					resource.pathChanged(oldValue as String)
				case ResourcesPackage.RESOURCE__FILE_EXTENSION:
					resource.fileExtensionChanged(oldValue as String)
				case ResourcesPackage.RESOURCE__CONTENT:
					resource.contentChanged()
			}
		}
	}

	def fileExtensionChanged(String oldFileExtension) {
		if (this.canBePersisted) {
			discardOldAndPersistNew(fullPath(path, name, oldFileExtension))
		}
	}

	def private nameChanged(String oldName) {
		if (this.canBePersisted) {
			discardOldAndPersistNew(fullPath(path, oldName, fileExtension))
		}
	}

	def private pathChanged(String oldPath) {
		if (!this.canBePersisted) {
			discardOldAndPersistNew(fullPath(oldPath, name, fileExtension))
		}
	}

	def private contentChanged() {
		if (this.canBePersisted) {
			discardOldAndPersistNew(fullPath)
		}
	}

	def private getFullPath() {
		fullPath(path, name, fileExtension)
	}

	def private static fullPath(String path, String name, String fileExtension) {
		path + '/' + name + '.' + fileExtension
	}

	def private canBePersisted() {
		path !== null && name !== null && fileExtension !== null && content !== null
	}

	def private discardOldAndPersistNew(String oldPath) {
		val alreadyPersistedObject = content.existingCorrespondence
		if (this.wasPersisted) {
			val oldUri = PersistenceHelper.getURIFromSourceProjectFolder(alreadyPersistedObject, oldPath)
			alreadyPersistedObject.eResource.resourceSet.getResource(oldUri, true).contents.clear()
		}
		val newUri = PersistenceHelper.getURIFromSourceProjectFolder(alreadyPersistedObject, fullPath)
		persistTo(newUri)
	}

	override remove() {
		val alreadyPersistedObject = content.existingCorrespondence
		val uri = PersistenceHelper.getURIFromSourceProjectFolder(alreadyPersistedObject, fullPath)
		alreadyPersistedObject.eResource.resourceSet.getResource(uri, true).contents.clear()
	}

	def private persistTo(URI targetUri) {
		val toPersist = content
		EcoreUtil.remove(toPersist)
		propagationResult.registerForEstablishPersistence(toPersist, VURI.getInstance(targetUri))
		wasPersisted = true
	}

	override setPropagationResult(ChangePropagationResult executionState) {
		this.propagationResult = propagationResult
	}

	override setCorrespondenceModel(CorrespondenceModel correspondenceModel) {
		this.correspondenceModel = correspondenceModel
	}

	def private getExistingCorrespondence(EObject object) {
		val intermediateCorrespondence = ReactionsCorrespondenceHelper.
			getCorrespondingModelElements(object, EObject, '', [
				eClass.ESuperTypes.findFirst[name == INTERMEDIATE_MODEL_NONROOT_CLASS] !== null
			], correspondenceModel).head
		if (intermediateCorrespondence === null) {
			throw new IllegalStateException('''Could not find the intermediate correspondence of <«object»>!''')
		}
		val resourceHaving = ReactionsCorrespondenceHelper.getCorrespondingModelElements(intermediateCorrespondence,
			EObject, '', [eResource !== null], correspondenceModel).head
		if (resourceHaving === null) {
			throw new IllegalStateException('''Could not find any transitive correspondence of <«object»> that already has a resource!''')
		}
		return resourceHaving
	}
}
