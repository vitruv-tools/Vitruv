package tools.vitruv.framework.vsum.modelsynchronization

import org.eclipse.emf.ecore.resource.Resource
import java.util.Set
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import org.eclipse.emf.ecore.EObject

package class ChangedResourcesTracker {
	final Set<Resource> sourceModelResources;
	final Set<Resource> involvedModelResources;
	
	new() {
		this.sourceModelResources = newHashSet();
		this.involvedModelResources = newHashSet();
	}


	def void addSourceResourceOfChange(TransactionalChange change) {
		val atomicChanges = change.getEChanges
		val involvedObjects = atomicChanges.map[
			if (it instanceof FeatureEChange<?,?>) it.affectedEObject 
				else if (it instanceof EObjectAddedEChange<?>) it.newValue 
				else if (it instanceof EObjectSubtractedEChange<?>) it.oldValue].filter(EObject);
		sourceModelResources += involvedObjects.map[eResource].filterNull.toList;
	}
	
	def void addInvolvedModelResource(Resource resource) {
		if (resource === null) {
			return;
		}
		this.involvedModelResources += resource;
	}
	
	def void markNonSourceResourceAsChanged() {
		// Some models are not marked as modified although they were actually modified by the consistency preservation
		// Therefore, we have to mark them as modified manually
		
		// FIXME HK We remove only objects that were not in one of the change source models. This is especially necessary 
		// to avoid overwriting Java files that were modified by the user in an editor (and changes recorded by the Java AST)
		// but whose JaMoPP VSUM model is not up to date because changes were not made to the EMF Model but in the text editor
		// Therefore, saving those models would overwrite the changed ones.
		// This will hopefully not be necessary anymore if we replay recorded changes in the VSUM isolated from their
		// recording in editors.
		// We recently disabled the filtering, as it is necessary for transitive change propagation, which can again
		// modify the source model, which has to be reloaded in the view afterwards.
		// TODO HK Remove this tracker and directly mark modified models when it operates properly in tests.
		val changedNonSourceResources = involvedModelResources
			//.filter[!sourceModelResources.exists[sourceResource | sourceResource.getURI.equals(it.getURI)]];
		changedNonSourceResources.forEach[modified = true];
	}
}
