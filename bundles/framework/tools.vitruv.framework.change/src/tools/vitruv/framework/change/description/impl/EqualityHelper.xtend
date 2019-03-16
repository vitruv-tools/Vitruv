package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil

// TODO TS (LOW) temporary workaround for problem with invalid IDs
class EqualityHelper extends EcoreUtil.EqualityHelper {
	override protected haveEqualFeature(EObject eObject1, EObject eObject2, EStructuralFeature feature) {
		// TODO TS (MEDIUM) use constant from Ecore API
		if (feature.name == "affectedEObjectID" || feature.name == "newValueID") {
			return true
		}
		return super.haveEqualFeature(eObject1, eObject2, feature)
	}

}
