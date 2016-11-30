package tools.vitruv.extensions.dslsruntime.reactions.helper

import java.util.ArrayList
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.tuid.TUID
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsCorrespondence
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsFactory
import java.util.List
import tools.vitruv.framework.correspondence.CorrespondenceModel

final class ReactionsCorrespondenceHelper {
	private new() {}

	private static def getReactionsView(CorrespondenceModel correspondenceModel) {
		return correspondenceModel.getEditableView(ReactionsCorrespondence, [
			ReactionsFactory.eINSTANCE.createReactionsCorrespondence()
		]);
	}

	private static def getTUID(CorrespondenceModel correspondenceModel, EObject object) {
		return correspondenceModel.calculateTUIDFromEObject(object);
	}

	public static def removeCorrespondencesBetweenElements(CorrespondenceModel correspondenceModel,
		EObject source, EObject target) {
		val correspondenceModelView = correspondenceModel.reactionsView;
		val sourceTUID = correspondenceModel.getTUID(source);
		val targetTUID = correspondenceModel.getTUID(target);
		val correspondences = correspondenceModelView.getCorrespondencesForTUIDs(#[sourceTUID]);
		for (correspondence : correspondences.toList) {
			if ((correspondence.ATUIDs.contains(sourceTUID) && correspondence.BTUIDs.contains(targetTUID)) ||
				(correspondence.BTUIDs.contains(sourceTUID) && correspondence.ATUIDs.contains(targetTUID))) {
				correspondenceModelView.removeCorrespondencesAndDependendCorrespondences(correspondence);
			}
		}
	}
	
	public static def removeCorrespondencesOfObject(CorrespondenceModel correspondenceModel,
		EObject source) {
		val sourceTUID = correspondenceModel.getTUID(source);
		val correspondenceModelView = correspondenceModel.reactionsView;
		val correspondences = correspondenceModelView.getCorrespondencesForTUIDs(#[sourceTUID]);
		for (correspondence : correspondences.toList) {
			correspondenceModelView.removeCorrespondencesAndDependendCorrespondences(correspondence);
		}
	}

	public static def ReactionsCorrespondence addCorrespondence(
		CorrespondenceModel correspondenceModel, EObject source, EObject target, String tag) {
		val correspondence = correspondenceModel.reactionsView.
			createAndAddCorrespondence(#[source], #[target]) as ReactionsCorrespondence;
		correspondence.tag = tag ?: "";
		return correspondence;
	}

	public static def <T> Iterable<T> getCorrespondingObjectsOfType(
		CorrespondenceModel correspondenceModel, EObject source, String expectedTag,
		Class<T> type) {
			val tuid = correspondenceModel.getTUID(source);
			return correspondenceModel.reactionsView.getCorrespondencesForTUIDs(#[tuid]).filter [
				expectedTag.nullOrEmpty || tag == expectedTag
			].map[it.getCorrespondingObjectsOfTypeInCorrespondence(tuid, type)].flatten;
		}

	private static def <T> Iterable<T> getCorrespondingObjectsOfTypeInCorrespondence(
		ReactionsCorrespondence correspondence, TUID source, Class<T> type) {
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
	