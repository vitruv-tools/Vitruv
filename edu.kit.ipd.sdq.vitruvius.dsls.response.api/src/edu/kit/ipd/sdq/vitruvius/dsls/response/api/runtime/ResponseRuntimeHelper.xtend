package edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence

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
	
	public static def <T> Iterable<Correspondence> getCorrespondencesWithTargetType(CorrespondenceInstance correspondenceInstance, EObject source,
			Class<T> type) {
		val correspondences = correspondenceInstance.getCorrespondences(#[source])
		return correspondences.filter[correspondence |
			if (correspondence.^as.contains(source)) {
				return !correspondence.bs.filter(type).empty;
			}
			if (correspondence.^bs.contains(source)) {
				return !correspondence.^as.filter(type).empty;
			}
			return false;
		]
	}
	
	public static def <T> Iterable<T> getCorrespondingObjectsOfTypeInCorrespondence(Correspondence correspondence, EObject source, Class<T> type) {
		var Iterable<T> correspondences = 
			if (correspondence.^as.contains(source)) {
				correspondence.^bs.filter(type);
			} else {
				correspondence.^as.filter(type);
			}
		return correspondences;
	}
	
}
