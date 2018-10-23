package tools.vitruv.framework.correspondence.impl

import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import java.util.function.Supplier
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.tuid.Tuid
import tools.vitruv.framework.correspondence.CorrespondenceModelView
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel

class CorrespondenceModelViewImpl<T extends Correspondence> implements CorrespondenceModelView<T> {
	private final InternalCorrespondenceModel correspondenceModelDelegate;
	private final Class<T> correspondenceType;
	private final Supplier<T> correspondenceCreator

	public new(Class<T> correspondenceType, InternalCorrespondenceModel correspondenceModel) {
		this(correspondenceType, correspondenceModel, null)
	}

	public new(Class<T> correspondenceType, InternalCorrespondenceModel correspondenceModel,
		Supplier<T> correspondenceCreator) {
		this.correspondenceType = correspondenceType;
		this.correspondenceModelDelegate = correspondenceModel;
		this.correspondenceCreator = correspondenceCreator
	}
	
	override getURI() {
		return correspondenceModelDelegate.getURI;
	}

	override createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2) {
		createAndAddCorrespondence(eObjects1, eObjects2, null)
	}

	override createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2, String tag) {
		if (null === this.correspondenceCreator) {
			throw new RuntimeException("The current view is not able to create new correspondences")
		}
		correspondenceModelDelegate.createAndAddCorrespondence(eObjects1, eObjects2, tag, this.correspondenceCreator)
	}

	override createAndAddManualCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2, String tag) {
		correspondenceModelDelegate.createAndAddManualCorrespondence(eObjects1, eObjects2, tag);
	}

	override hasCorrespondences(List<EObject> eObjects) {
		var Set<T> correspondences = this.getCorrespondences(eObjects);
		return correspondences !== null && correspondences.size() > 0
	}

	override hasCorrespondences() {
		correspondenceModelDelegate.hasCorrespondences();
	}
	
	override getCorrespondences(List<EObject> eObjects) {
		getCorrespondences(eObjects, null);
	}
	
	override getCorrespondences(List<EObject> eObjects, String tag) {
		correspondenceModelDelegate.getCorrespondences(correspondenceType, eObjects, tag).toSet();
	}

	override claimUniqueCorrespondence(List<EObject> aEObjects, List<EObject> bEObjects) {
		val correspondencesA = getCorrespondences(aEObjects)
		val correspondencesB = getCorrespondences(bEObjects)
		correspondencesA.retainAll(correspondencesB);
		if (correspondencesA.size > 1) {
			throw new IllegalStateException("Only one correspondence for " + aEObjects + " and " + bEObjects + " expected, but found more");
		} else if (correspondencesA.size == 0) {
			throw new IllegalStateException("No correspondence for '" + aEObjects + "' and '" + bEObjects + "' was found!");
		}
		return correspondencesA.get(0);
	}

	override getCorrespondingEObjects(List<EObject> eObjects) {
		correspondenceModelDelegate.getCorrespondingEObjects(correspondenceType, eObjects, null);
	}

	override getCorrespondingEObjects(List<EObject> eObjects, String tag) {
		correspondenceModelDelegate.getCorrespondingEObjects(correspondenceType, eObjects, tag);
	}

	override <E> getAllEObjectsOfTypeInCorrespondences(Class<E> type) {
		correspondenceModelDelegate.getAllEObjectsOfTypeInCorrespondences(correspondenceType, type);
	}
	
	override removeCorrespondencesBetween(List<EObject> aEObjects, List<EObject> bEObjects, String tag) {
		correspondenceModelDelegate.removeCorrespondencesBetween(correspondenceType, aEObjects, bEObjects, tag);
	}

	override removeCorrespondencesFor(List<EObject> eObjects, String tag) {
		correspondenceModelDelegate.removeCorrespondencesFor(correspondenceType, eObjects, tag);
	}
	
	override <U extends Correspondence> getView(Class<U> correspondenceType) {
		correspondenceModelDelegate.getView(correspondenceType);
	}
	
	override <U extends Correspondence> getEditableView(Class<U> correspondenceType,
		Supplier<U> correspondenceCreator) {
		correspondenceModelDelegate.getEditableView(correspondenceType, correspondenceCreator)
	}
	
	override getGenericView() {
		correspondenceModelDelegate.genericView;
	}
		
	override calculateTuidFromEObject(EObject eObject) {
		correspondenceModelDelegate.calculateTuidFromEObject(eObject);
	}

	override calculateTuidFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		correspondenceModelDelegate.calculateTuidFromEObject(eObject, virtualRootObject, prefix);
	}

	override resolveEObjectFromTuid(Tuid tuid) {
		correspondenceModelDelegate.resolveEObjectFromTuid(tuid);
	}

}
