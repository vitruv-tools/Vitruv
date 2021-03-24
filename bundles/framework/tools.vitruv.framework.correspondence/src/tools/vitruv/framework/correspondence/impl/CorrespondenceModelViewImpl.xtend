package tools.vitruv.framework.correspondence.impl

import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import java.util.function.Supplier
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.correspondence.CorrespondenceModelView
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelViewFactory
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.function.Predicate

class CorrespondenceModelViewImpl<T extends Correspondence> implements CorrespondenceModelView<T> {
	@Accessors(PROTECTED_GETTER)
	final InternalCorrespondenceModel correspondenceModelDelegate;
	@Accessors(PROTECTED_GETTER)
	final Class<T> correspondenceType;
	final Supplier<T> correspondenceCreator
	
	@Accessors(PROTECTED_GETTER)
	final Predicate<T> defaultCorrespondenceFilter;
	 
	new(Class<T> correspondenceType, InternalCorrespondenceModel correspondenceModel) {
		this(correspondenceType, correspondenceModel, null)
	}

	new(Class<T> correspondenceType, InternalCorrespondenceModel correspondenceModel,
		Supplier<T> correspondenceCreator) {
		this.correspondenceType = correspondenceType;
		this.defaultCorrespondenceFilter =  [true];
		this.correspondenceModelDelegate = correspondenceModel;
		this.correspondenceCreator = correspondenceCreator
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
		correspondenceModelDelegate.getCorrespondences(correspondenceType, defaultCorrespondenceFilter, eObjects, tag).toSet();
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
	
	override getCorrespondingEObjects(Correspondence correspondence, List<EObject> eObjects) {
		correspondenceModelDelegate.getCorrespondingEObjects(correspondence, eObjects);
	}

	override getCorrespondingEObjects(List<EObject> eObjects) {
		correspondenceModelDelegate.getCorrespondingEObjects(correspondenceType, defaultCorrespondenceFilter, eObjects, null);
	}

	override getCorrespondingEObjects(List<EObject> eObjects, String tag) {
		correspondenceModelDelegate.getCorrespondingEObjects(correspondenceType, defaultCorrespondenceFilter, eObjects, tag);
	}

	override removeCorrespondencesBetween(List<EObject> aEObjects, List<EObject> bEObjects, String tag) {
		correspondenceModelDelegate.removeCorrespondencesBetween(correspondenceType, defaultCorrespondenceFilter, aEObjects, bEObjects, tag);
	}

	override removeCorrespondencesFor(List<EObject> eObjects, String tag) {
		correspondenceModelDelegate.removeCorrespondencesFor(correspondenceType, defaultCorrespondenceFilter, eObjects, tag);
	}
	
	override <V extends CorrespondenceModelView<?>> getView(CorrespondenceModelViewFactory<V> correspondenceModelViewFactory) {
		correspondenceModelDelegate.getView(correspondenceModelViewFactory);
	}
	
	override <V extends CorrespondenceModelView<?>> getEditableView(CorrespondenceModelViewFactory<V> correspondenceModelViewFactory) {
		correspondenceModelDelegate.getEditableView(correspondenceModelViewFactory)
	}
	
	override getGenericView() {
		correspondenceModelDelegate.genericView;
	}
		
}
