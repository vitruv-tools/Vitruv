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

// TODO remove once resources are handled by domains
class IntermediateResourceBridgeI extends IntermediateResourceBridgeImpl {

	static val SAME_FOLDER = URI.createURI('.')

	Intermediate _intermediateCorrespondence

	def private getIntermediateCorrespondence() {
		if (_intermediateCorrespondence === null) {
			_intermediateCorrespondence = findIntermediateCorrespondence(this)
		}
		return _intermediateCorrespondence
	}

	def private fileExtensionChanged(String oldFileExtension) {
		this.fullPathChanged(path, name, oldFileExtension)
	}

	def private nameChanged(String oldName) {
		// TODO continuing here breaks stuff when changes are reapplied by Vitruv
		// Remove the following line once changes are recorded directly and there’s no need
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
		return path + (path.endsWith('/') ? '' : '/') + name + '.' + fileExtension
	}

	def private canBePersisted() {
		path !== null && name !== null && fileExtension !== null && content !== null
			&& correspondenceModel !== null && resourceAccess !== null
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
		baseURI.appendSegments(path.split('/').filter[length > 0])
			.appendSegment(name).appendFileExtension(fileExtension)
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
		// modified a containment reference's internal content
		if (content == newContent) return;
		this.content = newContent
		if (baseURI === null && correspondenceModel !== null) { // TODO
			val uri = PersistenceHelper.getURIFromSourceProjectFolder(findPersistedNonIntermediateObject, 'fake.ext')
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

	// only available if content has been set
	override getEmfResource() {
		return content?.eResource
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

	def private findPersistedNonIntermediateObject() {
		// The search for persisted non-intermediate objects takes into account transitive intermediate correspondences
		// of the corresponding intermediate, and its container. Taking the container into account is required if the
		// participation for which the corresponding intermediate originally got created has been deleted and then
		// recreated due to the intermediate being moved to a different container right after creation. This can for
		// example happen due to an attribute reference being matched during intermediate creation.
		// TODO This could be avoided if the original participation objects would not get deleted and recreated again.
		val intermediates = intermediateCorrespondence.transitiveIntermediateCorrespondences
		intermediates += intermediateCorrespondenceContainer

		val resourceHaving = intermediates.flatMap [
			ReactionsCorrespondenceHelper.getCorrespondingModelElements(it, EObject, null, [
				!(it instanceof Intermediate) && !(it instanceof Resource) && eResource !== null
			], correspondenceModel)
		].head
		if (resourceHaving === null) {
			throw new IllegalStateException('''Could not find any transitive correspondence or container of ‹«content
				»› that already has a resource!''')
		}
		return resourceHaving
	}

	def private getIntermediateCorrespondenceContainer() {
		val container = intermediateCorrespondence.eContainer
		if (container instanceof Intermediate) {
			return container
		} else {
			return null
		}
	}

	def private Set<Intermediate> getTransitiveIntermediateCorrespondences(Intermediate startIntermediate) {
		val existingIntermediate = new HashSet<Intermediate>
		existingIntermediate.add(startIntermediate)
		return existingIntermediate.transitiveIntermediateCorrespondences
	}

	def private Set<Intermediate> getTransitiveIntermediateCorrespondences(Set<Intermediate> foundIntermediates) {
		// Next step in breadth-first search:
		// Collecting to Set removes duplicates and avoids a ConcurrentModificationException when adding the results to
		// the result Set.
		val transitiveIntermediates = foundIntermediates.flatMap [ intermediate |
			ReactionsCorrespondenceHelper.getCorrespondingModelElements(intermediate, Intermediate, null, null,
				correspondenceModel)
		].toSet

		// Add to result Set: This removes objects which we have already found before.
		if (foundIntermediates.addAll(transitiveIntermediates)) {
			// Repeat until no more new intermediates are found:
			return foundIntermediates.transitiveIntermediateCorrespondences
		} else {
			return foundIntermediates
		}
	}

	def private findIntermediateCorrespondence(EObject object) {
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
		val resource = eObject.eResource
		checkArgument(resource !== null, "The provided object must be in a resource!")
		val objectResourceUri = resource.URI
		val projectUri = PersistenceHelper.getURIFromSourceProjectFolder(eObject, 'fake.ext')
		baseURI = SAME_FOLDER.resolve(projectUri).withoutTrailingSlash
		path = SAME_FOLDER.resolve(objectResourceUri).deresolve(baseURI).toString
		fileExtension = objectResourceUri.fileExtension
		name = objectResourceUri.lastSegment.toString.withoutFileExtension
		content = eObject
		isPersisted = true
	}

	def URI withoutTrailingSlash(URI uri) {
		if (uri.lastSegment.length === 0) {
			return uri.trimSegments(1)
		}
		return uri
	}
}
