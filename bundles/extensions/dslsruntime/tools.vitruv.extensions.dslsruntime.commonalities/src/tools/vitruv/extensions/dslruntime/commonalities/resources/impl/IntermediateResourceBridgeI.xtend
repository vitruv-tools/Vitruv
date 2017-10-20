package tools.vitruv.extensions.dslruntime.commonalities.resources.impl

import java.util.HashSet
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.extensions.dslruntime.commonalities.resources.Resource
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.datatypes.VURI

import static com.google.common.base.Preconditions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

// TODO remove once resources are handled by domains
class IntermediateResourceBridgeI extends IntermediateResourceBridgeImpl {

	static val SAME_FOLDER = URI.createURI('.')

	Intermediate intermediateCorrespondence

	def private fileExtensionChanged(String oldFileExtension) {
		this.fullPathChanged(path, name, oldFileExtension)
	}

	def private nameChanged(String oldName) {
		// TODO continuing here breaks stuff when changes are reapplied by Vitruv
		// Remove the following line once changes are recorded directly and ther’s no need
		// for rolling them back anymore.
		if (isPersisted) return;
		this.fullPathChanged(path, oldName, fileExtension)
	}

	def private pathChanged(String oldPath) {
		this.fullPathChanged(oldPath, name, fileExtension)
	}

	def private fullPathChanged(String oldPath, String oldName, String oldFileExtension) {
		if (this.isPersisted) {
			discard(getPersistanceUri(oldPath, oldName, oldFileExtension))
		}
		if (this.canBePersisted) {
			persist()
		}
	}

	def private contentChanged() {
		if (this.isPersisted) {
			discard(persistanceUri)
		}
		if (this.canBePersisted) {
			persist()
		}
	}

	override getFullPath() {
		fullPath(path, name, fileExtension)
	}

	def private static fullPath(String path, String name, String fileExtension) {
		if (path === null || name === null || fileExtension === null) return null
		path + '/' + name + '.' + fileExtension
	}

	def private canBePersisted() {
		path !== null && name !== null && fileExtension !== null && content !== null && correspondenceModel !== null &&
			resourceAccess !== null
	}

	def private discard(URI oldUri) {
		// TODO handling if content == null
		isPersisted = false
	}

	def private persist() {
		TuidManager.instance.updateTuidsOfRegisteredObjects()
		resourceAccess.persistAsRoot(content, VURI.getInstance(persistanceUri))
		this.isPersisted = true
		if (eContainer === null) {
			IntermediateModelManagement.addResourceBridge(intermediateCorrespondence.eResource, this,
				intermediateCorrespondence)
		}
	}

	def private getPersistanceUri() {
		getPersistanceUri(path, name, fileExtension)
	}

	def private getPersistanceUri(String path, String name, String fileExtension) {
		baseURI.appendSegments(path.split('/').filter [length > 0]).appendSegment(name).appendFileExtension(fileExtension)
	}

	override remove() {
		discard(persistanceUri)
	}

	override setContent(EObject newContent) {
		// hack ahead:
		// we may not keep the element in the containment reference, as this
		// breaks TUID calculation. But the content reference must be a
		// containment reference to be used with the in-operator. So we’re
		// avoiding any notifications here so the framework will not realize we
		// modified a containment referenceinternalContent
		if (content == newContent) return;
		this.content = newContent
		intermediateCorrespondence = newContent.intermediateCorrespondence
		if (baseURI === null && correspondenceModel !== null) { // TODO
			val uri = PersistenceHelper.getURIFromSourceProjectFolder(persistedNonIntermediateCorrespondence,
				'fake.ext')
			baseURI = SAME_FOLDER.resolve(uri).withoutTrailingSlash
		}
		contentChanged()
	}

	override getContent() {
		return null;
	}

	override eIsSet(int featureID) {
		if (featureID == ResourcesPackage.RESOURCE__CONTENT) {
			return false;
		}
		return super.eIsSet(featureID)
	}

	override getIntermediateId() {
		fullPath
	}

	override setPath(String newPath) {
		if (path == newPath) return;
		val oldPath = path
		super.setPath(newPath)
		this.pathChanged(oldPath)
	}

	override setName(String newName) {
		if (name == newName) return;
		val oldName = name
		super.setName(newName)
		this.nameChanged(oldName)
	}

	override setFileExtension(String newFileExtension) {
		if (fileExtension == newFileExtension) return;
		val oldFileExtension = fileExtension
		super.setFileExtension(newFileExtension)
		this.fileExtensionChanged(oldFileExtension)
	}

	def private getPersistedNonIntermediateCorrespondence() {
		val resourceHaving = transitiveIntermediateCorrespondences.flatMap [
			ReactionsCorrespondenceHelper.getCorrespondingModelElements(intermediateCorrespondence, EObject, null, [
				!(it instanceof Intermediate) && !(it instanceof Resource) && eResource !== null
			], correspondenceModel)
		].head
		if (resourceHaving === null) {
			throw new IllegalStateException('''Could not find any transitive correspondence of ‹«content»› that already has a resource!''')
		}
		return resourceHaving
	}

	def private getTransitiveIntermediateCorrespondences() {
		val existingIntermediate = new HashSet<Intermediate>
		existingIntermediate.add(intermediateCorrespondence)
		return existingIntermediate.transitiveIntermediateCorrespondences
	}

	def private Iterable<Intermediate> getTransitiveIntermediateCorrespondences(Set<Intermediate> foundIntermediates) {
		foundIntermediates + foundIntermediates.flatMap [ intermediate |
			ReactionsCorrespondenceHelper.getCorrespondingModelElements(intermediate, Intermediate, null, [
				!foundIntermediates.contains(it)
			], correspondenceModel)
		].toSet.flatMap[transitiveIntermediateCorrespondences]
	}

	def private getIntermediateCorrespondence(EObject object) {
		if (correspondenceModel === null) return null; // TODO
		val result = ReactionsCorrespondenceHelper.getCorrespondingModelElements(object, Intermediate, null, null,
			correspondenceModel).head
		if (result === null) {
			throw new IllegalStateException('''Could not find the intermediate correspondence of ‹«object»›!''')
		}
		return result
	}

	def private static withoutFileExtension(String s) {
		val dotIndex = s.lastIndexOf('.')
		return if (dotIndex != -1) s.substring(0, dotIndex) else s
	}

	override initialiseForModelElement(EObject eObject) {
		checkArgument(eObject.eResource !== null, "The provided object must be in a resource!")
		val objectResourceUri = eObject.eResource.URI
		val projectUri = PersistenceHelper.getURIFromSourceProjectFolder(eObject, 'fake.ext')
		baseURI = SAME_FOLDER.resolve(projectUri).withoutTrailingSlash
		path = SAME_FOLDER.resolve(objectResourceUri).deresolve(baseURI).toString
		fileExtension = objectResourceUri.fileExtension
		name = objectResourceUri.lastSegment.toString.withoutFileExtension
		content = eObject
		isPersisted = true
		intermediateCorrespondence = eObject.intermediateCorrespondence
	}
	
	def URI withoutTrailingSlash(URI uri) {
		if (uri.lastSegment.length === 0) {
			return uri.trimSegments(1)
		}
		return uri
	}

}
