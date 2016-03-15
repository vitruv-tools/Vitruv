package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import org.eclipse.emf.ecore.EObject
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import java.util.Set
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.emf.ecore.resource.Resource

class CorrespondenceInstanceView<T extends Correspondence> implements CorrespondenceInstance<T> {
	private final CorrespondenceInstance<Correspondence> correspondenceInstanceDelegate;
	private final Class<T> correspondenceType;
	
	public new(VURI vuri, Resource resource, Class<T> correspondenceType, CorrespondenceInstance<Correspondence> correspondenceInstance) {
		this.correspondenceType = correspondenceType;
		this.correspondenceInstanceDelegate = correspondenceInstance;
	}
	
	override addCorrespondence(T correspondence) {
		correspondenceInstanceDelegate.addCorrespondence(correspondence);
	}
	
	override calculateTUIDFromEObject(EObject eObject) {
		correspondenceInstanceDelegate.calculateTUIDFromEObject(eObject);
	}
	
	override calculateTUIDFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		correspondenceInstanceDelegate.calculateTUIDFromEObject(eObject, virtualRootObject, prefix);
	}
	
	override calculateTUIDsFromEObjects(List<EObject> eObjects) {
		correspondenceInstanceDelegate.calculateTUIDsFromEObjects(eObjects);
	}
	
	// Re-implement this method, because we cannot claim a unique generic correspondence and restrict it to the given type afterwards 
	override claimUniqueCorrespondence(List<EObject> aEObjects, List<EObject> bEObjects) {
		val correspondences = getCorrespondences(aEObjects)
		for (T correspondence : correspondences) {
			val correspondingBs = correspondence.bs
			if (correspondingBs != null && correspondingBs.equals(bEObjects)) {
				return correspondence;
			}
		}
		throw new RuntimeException("No correspondence for '" + aEObjects + "' and '" + bEObjects + "' was found!");
	}
	
	override createAndAddManualCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2) {
		correspondenceInstanceDelegate.createAndAddManualCorrespondence(eObjects1, eObjects2);
	}
	
	override getAllCorrespondencesWithoutDependencies() {
		correspondenceInstanceDelegate.getAllCorrespondencesWithoutDependencies().filter(correspondenceType).toSet();
	}
	
	override getCorrespondences(List<EObject> eObjects) {
		correspondenceInstanceDelegate.getCorrespondences(eObjects).filter(correspondenceType).toSet();
	}
	
	override getCorrespondencesForTUIDs(List<TUID> tuids) {
		correspondenceInstanceDelegate.getCorrespondencesForTUIDs(tuids).filter(correspondenceType).toSet();
	}
	
	override getCorrespondencesThatInvolveAtLeast(Set<EObject> eObjects) {
		correspondenceInstanceDelegate.getCorrespondencesThatInvolveAtLeast(eObjects).filter(correspondenceType).toSet();
	}
	
	override getCorrespondencesThatInvolveAtLeastTUIDs(Set<TUID> tuids) {
		correspondenceInstanceDelegate.getCorrespondencesThatInvolveAtLeastTUIDs(tuids).filter(correspondenceType).toSet();
	}
	
	override getMapping() {
		correspondenceInstanceDelegate.getMapping();
	}
	
	override getTUIDsForMetamodel(T correspondence, String metamodelNamespaceUri) {
		correspondenceInstanceDelegate.getTUIDsForMetamodel(correspondence, metamodelNamespaceUri);
	}
	
	override <U extends Correspondence> getView(Class<U> correspondenceType) {
		correspondenceInstanceDelegate.getView(correspondenceType);
	}
	
	override hasCorrespondences(List<EObject> eObjects) {
		var Set<T> correspondences = this.getCorrespondences(eObjects);
		return correspondences != null && correspondences.size() > 0
	}
	
	override hasCorrespondences() {
		correspondenceInstanceDelegate.hasCorrespondences();
	}
	
	override removeCorrespondencesAndDependendCorrespondences(T correspondence) {
		correspondenceInstanceDelegate.removeCorrespondencesAndDependendCorrespondences(correspondence).filter(correspondenceType).toSet();
	}
	
	override removeCorrespondencesThatInvolveAtLeastAndDependend(Set<EObject> eObjects) {
		correspondenceInstanceDelegate.removeCorrespondencesThatInvolveAtLeastAndDependend(eObjects);
	}
	
	override removeCorrespondencesThatInvolveAtLeastAndDependendForTUIDs(Set<TUID> tuids) {
		correspondenceInstanceDelegate.removeCorrespondencesThatInvolveAtLeastAndDependendForTUIDs(tuids);
	}
	
	override resolveEObjectFromRootAndFullTUID(EObject root, String tuidString) {
		correspondenceInstanceDelegate.resolveEObjectFromRootAndFullTUID(root, tuidString);
	}
	
	override resolveEObjectFromTUID(TUID tuid) {
		correspondenceInstanceDelegate.resolveEObjectFromTUID(tuid);
	}
	
	override resolveEObjectsFromTUIDs(List<TUID> tuids) {
		correspondenceInstanceDelegate.resolveEObjectsFromTUIDs(tuids);
	}
	
	override resolveEObjectsSetsFromTUIDsSets(Set<List<TUID>> tuidLists) {
		correspondenceInstanceDelegate.resolveEObjectsSetsFromTUIDsSets(tuidLists);
	}
	
	override updateTUID(EObject oldEObject, EObject newEObject) {
		correspondenceInstanceDelegate.updateTUID(oldEObject, newEObject);
	}
	
	override updateTUID(TUID oldTUID, EObject newEObject) {
		correspondenceInstanceDelegate.updateTUID(oldTUID, newEObject);
	}
	
	override updateTUID(TUID oldTUID, TUID newTUID) {
		correspondenceInstanceDelegate.updateTUID(oldTUID, newTUID);
	}
	
	// TODO re-design the CorrespondenceInstance to avoid a functionality depending on the correpondenceType
	override getCorrespondingEObjects(List<EObject> eObjects) {
		correspondenceInstanceDelegate.getCorrespondingEObjects(correspondenceType, eObjects);
	}
	
	override getCorrespondingEObjects(Class<? extends Correspondence> correspondenceType, List<EObject> eObjects) {
		correspondenceInstanceDelegate.getCorrespondingEObjects(correspondenceType, eObjects);
	}
	
	override createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2) {
		throw new UnsupportedOperationException("Do not use this")
	}
	
}