package edu.kit.ipd.sdq.vitruvius.dsls.response.executor

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance

public final class ResponseRuntimeHelper {
	public static def EObject getModelRoot(EObject modelObject) {
		var result = modelObject;
		while (result.eContainer() != null) {
			result = result.eContainer();
		}
		return result;
	}
	
	public static def Iterable<? extends EObject> getCorrespondingObjects(CorrespondenceInstance correspondenceInstance, EObject source) {
		val correspondences = correspondenceInstance.getCorrespondences(#[source])
		return correspondences.map[correspondence |
			if (correspondence.^as.contains(source)) {
				return correspondence.^bs;
			} else {
				return correspondence.^as;
			}
		].flatten
	}
	
	public static def <T> Iterable<T> getCorrespondingObjectsOfType(CorrespondenceInstance correspondenceInstance, EObject source,
			Class<T> type) {
		return correspondenceInstance.getCorrespondingObjects(source).filter(type);
	}
}
