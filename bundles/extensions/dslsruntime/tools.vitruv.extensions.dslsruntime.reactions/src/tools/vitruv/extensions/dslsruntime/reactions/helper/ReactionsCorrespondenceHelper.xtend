package tools.vitruv.extensions.dslsruntime.reactions.helper

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsCorrespondence
import tools.vitruv.extensions.dslsruntime.reactions.ReactionsCorrespondenceModelViewFactory
import tools.vitruv.framework.correspondence.CorrespondenceModel

final class ReactionsCorrespondenceHelper {

	static Logger logger = Logger.getLogger(ReactionsCorrespondenceHelper)

	private new() {}

	private static def getReactionsView(CorrespondenceModel correspondenceModel) {
		return correspondenceModel.getEditableView(ReactionsCorrespondenceModelViewFactory.instance);
	}

	public static def removeCorrespondencesBetweenElements(CorrespondenceModel correspondenceModel,
		EObject source, EObject target, String tag) {
		logger.trace("Removing correspondence between " + source + " and " + target + " with tag: " + tag);
		val correspondenceModelView = correspondenceModel.reactionsView;
		correspondenceModelView.removeCorrespondencesBetween(#[source], #[target], tag);
	}

	public static def removeCorrespondencesOfObject(CorrespondenceModel correspondenceModel,
		EObject source) {
		logger.trace("Removing correspondences of object " + source);
		val correspondenceModelView = correspondenceModel.reactionsView;
		correspondenceModelView.removeCorrespondencesFor(#[source], null);
	}

	public static def ReactionsCorrespondence addCorrespondence(
		CorrespondenceModel correspondenceModel, EObject source, EObject target, String tag) {
		logger.trace("Adding correspondence between " + source + " and " + target + " with tag: " + tag);
		val correspondence = correspondenceModel.reactionsView.
			createAndAddCorrespondence(#[source], #[target]);
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
