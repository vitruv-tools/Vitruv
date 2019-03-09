package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil

// TODO TS temporary workaround for problem with invalid IDs
class EqualityHelper extends EcoreUtil.EqualityHelper {
	override protected haveEqualFeature(EObject eObject1, EObject eObject2, EStructuralFeature feature) {
		if (feature.name == "affectedEObjectID" || feature.name == "newValueID") {
			return true
		}
		return super.haveEqualFeature(eObject1, eObject2, feature)
	}

}
