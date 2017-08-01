package tools.vitruv.framework.vsum.modelsynchronization

import java.util.Set

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange

package class ChangedResourcesTracker {
	val Set<Resource> involvedModelResources
	val Set<Resource> sourceModelResources

	new() {
		involvedModelResources = newHashSet
		sourceModelResources = newHashSet
	}

	def void addSourceResourceOfChange(TransactionalChange change) {
		val atomicChanges = change.getEChanges.map [
			if (it instanceof CompoundEChange) it.atomicChanges else #[it]
		].flatten
		val involvedObjects = atomicChanges.map [
			if (it instanceof FeatureEChange<?, ?>)
				it.affectedEObject
			else if (it instanceof EObjectAddedEChange<?>)
				it.newValue
			else if (it instanceof EObjectSubtractedEChange<?>) it.oldValue
		].filter(EObject)
		sourceModelResources += involvedObjects.map[eResource].filterNull.toList
	}

	def void addInvolvedModelResource(Resource resource) {
		if (resource === null) {
			return
		}
		involvedModelResources += resource
	}

	def void markNonSourceResourceAsChanged() {
		// Some models are not marked as modified although they were actually modified by the consistency preservation
		// Therefore, we have to mark them as modified manually
		// FIXME HK We remove only objects that were not in one of the change source models. This is especially necessary
		// to avoid overwriting Java files that were modified by the user in an editor (and changes recorded by the JAva AST)
		// but whose JaMoPP VSUM model is not up to date because changes were not made to the EMF Model but in the text editor
		// Therefore, saving those models would overwrite the changed ones.
		// This will hopefully not be necessary anymore if we replay recorded changes in the VSUM isolated from their
		// recording in editors.
		val changedNonSourceResources = involvedModelResources.filter [
			!sourceModelResources.exists[sourceResource|sourceResource.URI.equals(URI)]
		]
		changedNonSourceResources.forEach[modified = true]
	}
}
