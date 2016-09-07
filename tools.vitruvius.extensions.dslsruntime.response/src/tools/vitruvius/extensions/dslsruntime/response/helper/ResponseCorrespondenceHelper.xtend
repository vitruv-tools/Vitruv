package tools.vitruvius.extensions.dslsruntime.response.helper

import java.util.ArrayList
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.eclipse.emf.ecore.EObject
import tools.vitruvius.framework.tuid.TUID
import tools.vitruvius.dsls.response.meta.correspondence.response.ResponseCorrespondence
import tools.vitruvius.dsls.response.meta.correspondence.response.ResponseFactory
import java.util.List
import tools.vitruvius.framework.correspondence.CorrespondenceModel

final class ResponseCorrespondenceHelper {
	private new() {}

	private static def getResponseView(CorrespondenceModel correspondenceModel) {
		return correspondenceModel.getEditableView(ResponseCorrespondence, [
			ResponseFactory.eINSTANCE.createResponseCorrespondence()
		]);
	}

	private static def getTUID(CorrespondenceModel correspondenceModel, EObject object,
		EObject parent) {
		if (parent == null) {
			return correspondenceModel.calculateTUIDFromEObject(object);
		} else {
			val rootTUID = correspondenceModel.calculateTUIDFromEObject(parent);
			val String prefix = rootTUID.toString;
			return correspondenceModel.calculateTUIDFromEObject(object, object.eContainer(), prefix);
		}
	}

	public static def removeCorrespondencesBetweenElements(CorrespondenceModel correspondenceModel,
		EObject source, EObject sourceParent, EObject target, EObject targetParent) {
		val correspondenceModelView = correspondenceModel.responseView;
		val sourceTUID = correspondenceModel.getTUID(source, sourceParent);
		val targetTUID = correspondenceModel.getTUID(target, targetParent);
		val correspondences = correspondenceModelView.getCorrespondencesForTUIDs(#[sourceTUID]);
		for (correspondence : correspondences.toList) {
			if ((correspondence.ATUIDs.contains(sourceTUID) && correspondence.BTUIDs.contains(targetTUID)) ||
				(correspondence.BTUIDs.contains(sourceTUID) && correspondence.ATUIDs.contains(targetTUID))) {
				correspondenceModelView.removeCorrespondencesAndDependendCorrespondences(correspondence);
			}
		}
	}
	
	public static def removeCorrespondencesOfObject(CorrespondenceModel correspondenceModel,
		EObject source, EObject sourceParent) {
		val sourceTUID = correspondenceModel.getTUID(source, sourceParent);
		val correspondenceModelView = correspondenceModel.responseView;
		val correspondences = correspondenceModelView.getCorrespondencesForTUIDs(#[sourceTUID]);
		for (correspondence : correspondences.toList) {
			correspondenceModelView.removeCorrespondencesAndDependendCorrespondences(correspondence);
		}
	}

	public static def ResponseCorrespondence addCorrespondence(
		CorrespondenceModel correspondenceModel, EObject source, EObject target, String tag) {
		val correspondence = correspondenceModel.responseView.
			createAndAddCorrespondence(#[source], #[target]) as ResponseCorrespondence;
		correspondence.tag = tag ?: "";
		return correspondence;
	}

	public static def <T> Iterable<T> getCorrespondingObjectsOfType(
		CorrespondenceModel correspondenceModel, EObject source, String expectedTag,
		Class<T> type) {
			val tuid = correspondenceModel.getTUID(source, null);
			return correspondenceModel.responseView.getCorrespondencesForTUIDs(#[tuid]).filter [
				expectedTag.nullOrEmpty || tag == expectedTag
			].map[it.getCorrespondingObjectsOfTypeInCorrespondence(tuid, type)].flatten;
		}

	public static def <T> Iterable<T> getCorrespondingObjectsOfType(
		CorrespondenceModel correspondenceModel, EObject source, EObject sourceParent,
		Class<T> type) {
		val tuid = correspondenceModel.getTUID(source, sourceParent);
		return correspondenceModel.responseView.getCorrespondencesForTUIDs(#[tuid]).map [
			it.getCorrespondingObjectsOfTypeInCorrespondence(tuid, type)
		].flatten;
	}

	private static def <T> Iterable<T> getCorrespondingObjectsOfTypeInCorrespondence(
		ResponseCorrespondence correspondence, TUID source, Class<T> type) {
		var Iterable<T> correspondences = if (correspondence.ATUIDs.contains(source)) {
				correspondence.^bs.filter(type);
			} else {
				correspondence.^as.filter(type);
			}
		return correspondences;
	}

	public static def <T> List<T> getCorrespondingModelElements(EObject sourceElement, Class<T> affectedElementClass,
		String expectedTag, Function1<T, Boolean> preconditionMethod, CorrespondenceModel correspondenceModel) {
		val nonNullPreconditionMethod = if(preconditionMethod != null) preconditionMethod else [T input|true];
		val targetElements = new ArrayList<T>();
		try {
			val correspondingObjects = getCorrespondingObjectsOfType(correspondenceModel,
				sourceElement, expectedTag, affectedElementClass);
			targetElements += correspondingObjects.filterNull.filter(nonNullPreconditionMethod);
		} catch (RuntimeException ex) {
		}

		return targetElements;
	}
}
	