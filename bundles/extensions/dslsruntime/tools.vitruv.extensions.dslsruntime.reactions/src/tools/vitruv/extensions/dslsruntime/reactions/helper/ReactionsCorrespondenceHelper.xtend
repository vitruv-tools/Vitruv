package tools.vitruv.extensions.dslsruntime.reactions.helper

import java.util.ArrayList
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.tuid.Tuid
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

	private static def getTuid(CorrespondenceModel correspondenceModel, EObject object) {
		return correspondenceModel.calculateTuidFromEObject(object);
	}

	public static def removeCorrespondencesBetweenElements(CorrespondenceModel correspondenceModel,
		EObject source, EObject target, String tag) {
		val correspondenceModelView = correspondenceModel.reactionsView;
		val sourceTuid = correspondenceModel.getTuid(source);
		val targetTuid = correspondenceModel.getTuid(target);
		val correspondences = correspondenceModelView.getCorrespondencesForTuids(#[sourceTuid]).filter[it.tag == tag];
		for (correspondence : correspondences.toList) {
			if ((correspondence.ATuids.contains(sourceTuid) && correspondence.BTuids.contains(targetTuid)) ||
				(correspondence.BTuids.contains(sourceTuid) && correspondence.ATuids.contains(targetTuid))) {
				correspondenceModelView.removeCorrespondencesAndDependendCorrespondences(correspondence);
			}
		}
	}
	
	public static def removeCorrespondencesOfObject(CorrespondenceModel correspondenceModel,
		EObject source) {
		val sourceTuid = correspondenceModel.getTuid(source);
		val correspondenceModelView = correspondenceModel.reactionsView;
		val correspondences = correspondenceModelView.getCorrespondencesForTuids(#[sourceTuid]);
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
			val tuid = correspondenceModel.getTuid(source);
			return correspondenceModel.reactionsView.getCorrespondencesForTuids(#[tuid]).filter [
				expectedTag.nullOrEmpty || tag == expectedTag
			].map[it.getCorrespondingObjectsOfTypeInCorrespondence(tuid, type)].flatten;
		}

	private static def <T> Iterable<T> getCorrespondingObjectsOfTypeInCorrespondence(
		ReactionsCorrespondence correspondence, Tuid source, Class<T> type) {
		var Iterable<T> correspondences = if (correspondence.ATuids.contains(source)) {
				correspondence.^bs.filter(type);
			} else {
				correspondence.^as.filter(type);
			}
		return correspondences;
	}

	public static def <T> List<T> getCorrespondingModelElements(EObject sourceElement, Class<T> affectedElementClass,
		String expectedTag, Function1<T, Boolean> preconditionMethod, CorrespondenceModel correspondenceModel) {
		val nonNullPreconditionMethod = if(preconditionMethod !== null) preconditionMethod else [T input|true];
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
	