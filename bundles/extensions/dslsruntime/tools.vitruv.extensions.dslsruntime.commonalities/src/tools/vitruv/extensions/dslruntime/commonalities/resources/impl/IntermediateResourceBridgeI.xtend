package tools.vitruv.extensions.dslruntime.commonalities.resources.impl

import java.util.HashSet
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.extensions.dslruntime.commonalities.resources.Resource
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.util.datatypes.VURI

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

// TODO remove once resources are handled by domains
class IntermediateResourceBridgeI extends IntermediateResourceBridgeImpl {

	static val SAME_FOLDER = URI.createURI('.')

	Intermediate _intermediateCorrespondence

	private def getIntermediateCorrespondence() {
		if (_intermediateCorrespondence === null) {
			_intermediateCorrespondence = findIntermediateCorrespondence(this)
		}
		return _intermediateCorrespondence
	}

	private def fileExtensionChanged(String oldFileExtension) {
		this.fullPathChanged(path, name, oldFileExtension)
	}

	private def nameChanged(String oldName) {
		// TODO continuing here breaks stuff when changes are reapplied by Vitruv
		// Remove the following line once changes are recorded directly and there’s no need
		// for rolling them back anymore.
		if (isPersisted) return;
		this.fullPathChanged(path, oldName, fileExtension)
	}

	private def pathChanged(String oldPath) {
		this.fullPathChanged(oldPath, name, fileExtension)
	}

	private def fullPathChanged(String oldPath, String oldName, String oldFileExtension) {
		if (this.isPersisted) {
			discard(getResourceUri(oldPath, oldName, oldFileExtension))
		}
		if (this.canBePersisted) {
			persist()
		}
	}

	private def contentChanged() {
		if (this.isPersisted) {
			discard(resourceUri)
		}
		if (this.canBePersisted) {
			persist()
		}
	}

	override getFullPath() {
		fullPath(path, name, fileExtension)
	}

	private static def String fullPath(String path, String name, String fileExtension) {
		if (path === null || name === null || fileExtension === null) return null
		val fullPath = new StringBuilder
		if (!path.empty) {
			fullPath.append(path)
			if (!path.endsWith('/')) {
				fullPath.append('/')
			}
		}
		fullPath.append(name)
		fullPath.append('.')
		fullPath.append(fileExtension)
		return fullPath.toString
	}

	private def canBePersisted() {
		// If the content has been set, we assume that the baseURI has been set as well already:
		assertTrue(content === null || baseURI !== null)
		path !== null && name !== null && fileExtension !== null && content !== null
			&& correspondenceModel !== null && resourceAccess !== null && isPersistenceEnabled
	}

	private def discard(URI oldUri) {
		// TODO handling if content == null
		isPersisted = false
	}

	private def persist() {
		resourceAccess.persistAsRoot(content, VURI.getInstance(resourceUri))
		this.isPersisted = true
		if (eContainer === null) {
			throw new RuntimeException('''IntermediateResourceBridge has not been added to the intermediate model yet: «
				this»''')
		}
	}

	private def getResourceUri() {
		getResourceUri(path, name, fileExtension)
	}

	private def getResourceUri(String path, String name, String fileExtension) {
		assertTrue(baseURI !== null && path !== null && name !== null && fileExtension !== null)
		val relativePathUri = URI.createURI(fullPath)
		val resourceUri = relativePathUri.resolve(baseURI)
		return resourceUri
	}

	override remove() {
		discard(resourceUri)
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

		if (baseURI === null) {
			if (correspondenceModel === null) {
				throw new IllegalStateException('''IntermediateResourceBridge has not yet been setup (correspondence «
					»model is null)!''')
			}
			baseURI = findPersistedNonIntermediateObject.calculateBaseUri()
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

	override getEmfResource() {
		if (resourceAccess === null || baseURI === null || path === null || name === null || fileExtension === null) {
			return null
		}
		val resourceUri = resourceUri
		val resource = resourceAccess.getModelResource(VURI.getInstance(resourceUri))
		return resource
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

	override setIsPersistenceEnabled(boolean newIsPersistenceEnabled) {
		super.setIsPersistenceEnabled(newIsPersistenceEnabled)
		// Trigger persistence:
		if (newIsPersistenceEnabled && this.canBePersisted) {
			this.persist()
		}
	}

	private def findPersistedNonIntermediateObject() {
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

	private def getIntermediateCorrespondenceContainer() {
		val container = intermediateCorrespondence.eContainer
		if (container instanceof Intermediate) {
			return container
		} else {
			return null
		}
	}

	private def Set<Intermediate> getTransitiveIntermediateCorrespondences(Intermediate startIntermediate) {
		val existingIntermediate = new HashSet<Intermediate>
		existingIntermediate.add(startIntermediate)
		return existingIntermediate.transitiveIntermediateCorrespondences
	}

	private def Set<Intermediate> getTransitiveIntermediateCorrespondences(Set<Intermediate> foundIntermediates) {
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

	private def findIntermediateCorrespondence(EObject object) {
		if (correspondenceModel === null) return null; // TODO
		val result = ReactionsCorrespondenceHelper.getCorrespondingModelElements(object, Intermediate, null, null,
			correspondenceModel).head
		if (result === null) {
			throw new IllegalStateException('''Could not find the intermediate correspondence of ‹«object»›!''')
		}
		return result
	}

	private static def withoutFileExtension(String s) {
		val dotIndex = s.lastIndexOf('.')
		return if (dotIndex != -1) s.substring(0, dotIndex) else s
	}

	override initialiseForModelElement(EObject eObject) {
		val resource = eObject.eResource
		checkArgument(resource !== null, "The provided object must be in a resource!")
		val objectResourceUri = resource.URI
		baseURI = eObject.calculateBaseUri()
		path = SAME_FOLDER.resolve(objectResourceUri).deresolve(baseURI).toString
		fileExtension = objectResourceUri.fileExtension
		name = objectResourceUri.lastSegment.toString.withoutFileExtension
		content = eObject
		isPersisted = true
	}

	private def calculateBaseUri(EObject persistedObject) {
		assertTrue(persistedObject !== null)
		assertTrue(persistedObject.eResource !== null)
		// Get the project folder from the already persisted object:
		// Since the relative path cannot be empty, we append a dummy file and then trim it again afterwards.
		val projectFileUri = PersistenceHelper.getURIFromSourceProjectFolder(persistedObject, 'fake.ext')
		return SAME_FOLDER.resolve(projectFileUri)
	}
}
