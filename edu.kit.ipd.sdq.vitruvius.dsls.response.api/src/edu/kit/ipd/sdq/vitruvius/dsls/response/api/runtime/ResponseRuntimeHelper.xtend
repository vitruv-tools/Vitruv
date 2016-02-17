package edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID

public final class ResponseRuntimeHelper {
	public static def EObject getModelRoot(EObject modelObject) {
		var result = modelObject;
		while (result.eContainer() != null) {
			result = result.eContainer();
		}
		return result;
	}
	
	private static def getTUID(CorrespondenceInstance correspondenceInstance, EObject object, EObject parent) {
		if (parent == null) {
			return correspondenceInstance.calculateTUIDFromEObject(object);
		} else {
			val rootTUID = correspondenceInstance.calculateTUIDFromEObject(parent);
			val String prefix = rootTUID.toString;
			return correspondenceInstance.calculateTUIDFromEObject(object, object.eContainer(), prefix);
		}
	}
	
	public static def removeCorrespondence(CorrespondenceInstance correspondenceInstance, EObject source, EObject sourceParent, EObject target, EObject targetParent) {
		val sourceTUID = correspondenceInstance.getTUID(source, sourceParent);
		val targetTUID = correspondenceInstance.getTUID(source, targetParent);
		val correspondences = correspondenceInstance.getCorrespondencesForTUIDs(#[sourceTUID]);
		for (correspondence : correspondences) {
			if ((correspondence.ATUIDs.contains(sourceTUID)
				&& correspondence.BTUIDs.contains(targetTUID))
				|| (correspondence.BTUIDs.contains(sourceTUID)
				&& correspondence.ATUIDs.contains(targetTUID))) {
				correspondenceInstance.removeCorrespondencesAndDependendCorrespondences(correspondence);		
			}
		}
	}
	
	public static def addCorrespondence(CorrespondenceInstance correspondenceInstance, EObject source, EObject target) {
		correspondenceInstance.createAndAddCorrespondence(#[source], #[target]);
	}
	
	public static def <T> Iterable<T> getCorrespondingObjectsOfType(CorrespondenceInstance correspondenceInstance, EObject source, EObject sourceParent,
			Class<T> type) {
		val tuid = correspondenceInstance.getTUID(source, sourceParent);
		return correspondenceInstance.getCorrespondencesForTUIDs(#[tuid]).map[it.getCorrespondingObjectsOfTypeInCorrespondence(tuid, type)].flatten;
	}
	
	private static def <T> Iterable<T> getCorrespondingObjectsOfTypeInCorrespondence(Correspondence correspondence, TUID source, Class<T> type) {
		var Iterable<T> correspondences = 
			if (correspondence.ATUIDs.contains(source)) {
				correspondence.^bs.filter(type);
			} else {
				correspondence.^as.filter(type);
			}
		return correspondences;
	}
	
}
