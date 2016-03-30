package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper

import java.util.ArrayList
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.dsls.response.meta.correspondence.response.ResponseCorrespondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.dsls.response.meta.correspondence.response.ResponseFactory

final class CorrespondenceHelper {
	private new() {}

	private static def getResponseView(CorrespondenceInstance<Correspondence> correspondenceInstance) {
		return correspondenceInstance.getEditableView(ResponseCorrespondence, [
			ResponseFactory.eINSTANCE.createResponseCorrespondence()
		]);
	}

	private static def getTUID(CorrespondenceInstance<Correspondence> correspondenceInstance, EObject object,
		EObject parent) {
		if (parent == null) {
			return correspondenceInstance.calculateTUIDFromEObject(object);
		} else {
			val rootTUID = correspondenceInstance.calculateTUIDFromEObject(parent);
			val String prefix = rootTUID.toString;
			return correspondenceInstance.calculateTUIDFromEObject(object, object.eContainer(), prefix);
		}
	}

	public static def removeCorrespondence(CorrespondenceInstance<Correspondence> correspondenceInstance,
		EObject source, EObject sourceParent, EObject target, EObject targetParent) {
		val correspondenceInstanceView = correspondenceInstance.responseView;
		val sourceTUID = correspondenceInstance.getTUID(source, sourceParent);
		val targetTUID = correspondenceInstance.getTUID(target, targetParent);
		val correspondences = correspondenceInstanceView.getCorrespondencesForTUIDs(#[sourceTUID]);
		for (correspondence : correspondences.toList) {
			if ((correspondence.ATUIDs.contains(sourceTUID) && correspondence.BTUIDs.contains(targetTUID)) ||
				(correspondence.BTUIDs.contains(sourceTUID) && correspondence.ATUIDs.contains(targetTUID))) {
				correspondenceInstanceView.removeCorrespondencesAndDependendCorrespondences(correspondence);
			}
		}
	}

	public static def ResponseCorrespondence addCorrespondence(
		CorrespondenceInstance<Correspondence> correspondenceInstance, EObject source, EObject target, String tag) {
		val correspondence = correspondenceInstance.responseView.
			createAndAddCorrespondence(#[source], #[target]) as ResponseCorrespondence;
		correspondence.tag = tag ?: "";
		return correspondence;
	}

	public static def <T> Iterable<T> getCorrespondingObjectsOfType(
		CorrespondenceInstance<Correspondence> correspondenceInstance, EObject source, String expectedTag,
		Class<T> type) {
			val tuid = correspondenceInstance.getTUID(source, null);
			return correspondenceInstance.responseView.getCorrespondencesForTUIDs(#[tuid]).filter [
				expectedTag.nullOrEmpty || tag == expectedTag
			].map[it.getCorrespondingObjectsOfTypeInCorrespondence(tuid, type)].flatten;
		}

	public static def <T> Iterable<T> getCorrespondingObjectsOfType(
		CorrespondenceInstance<Correspondence> correspondenceInstance, EObject source, EObject sourceParent,
		Class<T> type) {
		val tuid = correspondenceInstance.getTUID(source, sourceParent);
		return correspondenceInstance.responseView.getCorrespondencesForTUIDs(#[tuid]).map [
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

	public static def <T> getCorrespondingModelElement(EObject sourceElement, Class<T> affectedElementClass,
		boolean optional, String expectedTag, Function1<T, Boolean> preconditionMethod, Blackboard blackboard) {
		val nonNullPreconditionMethod = if(preconditionMethod != null) preconditionMethod else [T input|true];
		val targetElements = new ArrayList<T>();
		try {
			val correspondingObjects = getCorrespondingObjectsOfType(blackboard.getCorrespondenceInstance(),
				sourceElement, expectedTag, affectedElementClass);
			targetElements += correspondingObjects.filterNull.filter(nonNullPreconditionMethod);
		} catch (RuntimeException ex) {
			if (!optional) {
				throw new RuntimeException("An error occured when retrieved the corresponding elements of: " +
					sourceElement);
			} else {
				// The element is optional so there can occur errors when trying to retrieve the corresponding element. Just catch it and go on.
			}
		}

		if (targetElements.size() != 1) {
			if (!optional || targetElements.size() > 1) {
				throw new IllegalArgumentException(
					"There were (" + targetElements.size() + ") corresponding elements of type " +
						affectedElementClass.getSimpleName() + " for " + (if(optional) "optional" else "") +
						": " + sourceElement);
			}
		}

		return if(targetElements.isEmpty()) null else targetElements.get(0);
	}
}
	