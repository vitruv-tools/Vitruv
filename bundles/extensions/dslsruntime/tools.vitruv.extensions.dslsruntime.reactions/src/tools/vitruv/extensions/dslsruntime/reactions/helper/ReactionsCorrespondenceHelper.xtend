package tools.vitruv.extensions.dslsruntime.reactions.helper

import java.util.ArrayList
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.eclipse.emf.ecore.EObject
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

	public static def removeCorrespondencesBetweenElements(CorrespondenceModel correspondenceModel,
		EObject source, EObject target, String tag) {
		val correspondenceModelView = correspondenceModel.reactionsView;
		correspondenceModelView.removeCorrespondencesBetween(#[source], #[target], tag);
	}
	
	public static def removeCorrespondencesOfObject(CorrespondenceModel correspondenceModel,
		EObject source) {
		val correspondenceModelView = correspondenceModel.reactionsView;
		val correspondences = correspondenceModelView.getCorrespondences(#[source]);
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
			return correspondenceModel.reactionsView.getCorrespondingEObjects(#[source], expectedTag).flatten.filter(type);
	}
		
	public static def <T> List<T> getCorrespondingModelElements(EObject sourceElement, Class<T> affectedElementClass,
		String expectedTag, Function1<T, Boolean> preconditionMethod, CorrespondenceModel correspondenceModel) {
		if (sourceElement === null) {
			return #[];	
		}
		val nonNullPreconditionMethod = if (preconditionMethod !== null) preconditionMethod else [T input|true];
		val targetElements = new ArrayList<T>();
		val correspondingObjects = getCorrespondingObjectsOfType(correspondenceModel, sourceElement, expectedTag,
			affectedElementClass);
		targetElements += correspondingObjects.filterNull.filter(nonNullPreconditionMethod);

		return targetElements;
	}
}
	