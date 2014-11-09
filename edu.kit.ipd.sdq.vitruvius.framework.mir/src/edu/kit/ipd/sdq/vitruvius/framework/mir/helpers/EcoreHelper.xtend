package edu.kit.ipd.sdq.vitruvius.framework.mir.helpers

import java.util.List
import org.eclipse.emf.ecore.EObject
import java.util.ArrayList

class EcoreHelper {
	/**
	 * returns a list of all ancestors of obj, starting with
	 * the object itself
	 */
	static def getContainerHierarchy(EObject obj) {
		val List<EObject> result = new ArrayList<EObject>();
		var EObject iterator = obj;
		while (iterator != null) {
			result.add(iterator)
			iterator = iterator.eContainer
		}
		return result
	}
}