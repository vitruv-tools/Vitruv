package tools.vitruv.framework.correspondence

import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import java.util.function.Supplier
import tools.vitruv.framework.correspondence.GenericCorrespondenceModel
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.tuid.Tuid

class CorrespondenceModelView<T extends Correspondence> implements GenericCorrespondenceModel<T> {
	private final GenericCorrespondenceModel<Correspondence> correspondenceModelDelegate;
	private final Class<T> correspondenceType;
	private final Supplier<T> correspondenceCreator

	public new(Class<T> correspondenceType, GenericCorrespondenceModel<Correspondence> correspondenceModel) {
		this(correspondenceType, correspondenceModel, null)
	}

	override getCorrespondencesForTuids(List<Tuid> tuids) {
		correspondenceModelDelegate.getCorrespondencesForTuids(tuids).filter(correspondenceType).toSet();
	}
	
	public new(Class<T> correspondenceType, GenericCorrespondenceModel<Correspondence> correspondenceModel,
		Supplier<T> correspondenceCreator) {
		this.correspondenceType = correspondenceType;
		this.correspondenceModelDelegate = correspondenceModel;
		this.correspondenceCreator = correspondenceCreator
	}

	override addCorrespondence(T correspondence) {
		correspondenceModelDelegate.addCorrespondence(correspondence);
	}

	override calculateTuidFromEObject(EObject eObject) {
		correspondenceModelDelegate.calculateTuidFromEObject(eObject);
	}

	override calculateTuidFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		correspondenceModelDelegate.calculateTuidFromEObject(eObject, virtualRootObject, prefix);
	}

	override calculateTuidsFromEObjects(List<EObject> eObjects) {
		correspondenceModelDelegate.calculateTuidsFromEObjects(eObjects);
	}

	// Re-implement this method, because we cannot claim a unique generic correspondence and restrict it to the given type afterwards 
	override claimUniqueCorrespondence(List<EObject> aEObjects, List<EObject> bEObjects) {
		val correspondences = getCorrespondences(aEObjects)
		for (T correspondence : correspondences) {
			val correspondingBs = correspondence.bs
			if (correspondingBs !== null && correspondingBs.equals(bEObjects)) {
				return correspondence;
			}
		}
		throw new RuntimeException("No correspondence for '" + aEObjects + "' and '" + bEObjects + "' was found!");
	}

	override createAndAddManualCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2) {
		correspondenceModelDelegate.createAndAddManualCorrespondence(eObjects1, eObjects2);
	}

	override getAllCorrespondences() {
		correspondenceModelDelegate.getAllCorrespondences().filter(correspondenceType).toList;
	}
	
	override getAllCorrespondencesWithoutDependencies() {
		correspondenceModelDelegate.getAllCorrespondencesWithoutDependencies().filter(correspondenceType).toSet();
	}

	override getCorrespondences(List<EObject> eObjects) {
		correspondenceModelDelegate.getCorrespondences(eObjects).filter(correspondenceType).toSet();
	}

	override getCorrespondencesThatInvolveAtLeast(Set<EObject> eObjects) {
		correspondenceModelDelegate.getCorrespondencesThatInvolveAtLeast(eObjects).filter(correspondenceType).
			toSet();
	}

	override <U extends Correspondence> getView(Class<U> correspondenceType) {
		correspondenceModelDelegate.getView(correspondenceType);
	}

	override hasCorrespondences(List<EObject> eObjects) {
		var Set<T> correspondences = this.getCorrespondences(eObjects);
		return correspondences !== null && correspondences.size() > 0
	}

	override hasCorrespondences() {
		correspondenceModelDelegate.hasCorrespondences();
	}

	override removeCorrespondencesAndDependendCorrespondences(T correspondence) {
		correspondenceModelDelegate.removeCorrespondencesAndDependendCorrespondences(correspondence).filter(
			correspondenceType).toSet();
	}

	override removeCorrespondencesThatInvolveAtLeastAndDependend(Set<EObject> eObjects) {
		correspondenceModelDelegate.removeCorrespondencesThatInvolveAtLeastAndDependend(eObjects);
	}

	override resolveEObjectFromTuid(Tuid tuid) {
		correspondenceModelDelegate.resolveEObjectFromTuid(tuid);
	}

	// TODO re-design the CorrespondenceModel to avoid a functionality depending on the correpondenceType
	override getCorrespondingEObjects(List<EObject> eObjects) {
		correspondenceModelDelegate.getCorrespondingEObjects(correspondenceType, eObjects, "");
	}

	override getCorrespondingEObjects(List<EObject> eObjects, String tag) {
		correspondenceModelDelegate.getCorrespondingEObjects(correspondenceType, eObjects, tag);
	}

	override getCorrespondingEObjects(Class<? extends Correspondence> correspondenceType, List<EObject> eObjects, String tag) {
		correspondenceModelDelegate.getCorrespondingEObjects(correspondenceType, eObjects, tag);
	}

	override createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2) {
		if (null === this.correspondenceCreator) {
			throw new RuntimeException("The current view is not able to create new correspondences")
		}
		correspondenceModelDelegate.createAndAddCorrespondence(eObjects1, eObjects2, this.correspondenceCreator as Supplier<Correspondence>)
	}

	override createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2,
		Supplier<Correspondence> correspondenceCreator) {
		correspondenceModelDelegate.createAndAddCorrespondence(eObjects1, eObjects2,
			correspondenceCreator)
	}
	
	override <U extends Correspondence> getEditableView(Class<U> correspondenceType,
		Supplier<U> correspondenceCreator) {
		correspondenceModelDelegate.getEditableView(correspondenceType, correspondenceCreator)
	}
	
	override getURI() {
		return correspondenceModelDelegate.getURI;
	}
	
	override resolveEObjectsFromUuids(List<String> uuids) {
		return correspondenceModelDelegate.resolveEObjectsFromUuids(uuids);
	}

}
