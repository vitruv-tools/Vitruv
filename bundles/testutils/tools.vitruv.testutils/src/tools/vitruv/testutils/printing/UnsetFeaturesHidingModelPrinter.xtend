package tools.vitruv.testutils.printing

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import static tools.vitruv.testutils.printing.PrintResult.*

class UnsetFeaturesHidingModelPrinter implements ModelPrinter {
	override PrintResult printFeature(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature
	) {
		if (!object.eIsSet(feature)) 
			PRINTED_NO_OUTPUT
	 	else 
			NOT_RESPONSIBLE
	}
	
	override withSubPrinter(ModelPrinter subPrinter) {
		this
	}
}