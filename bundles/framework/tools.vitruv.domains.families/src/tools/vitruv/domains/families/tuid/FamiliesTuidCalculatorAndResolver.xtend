package tools.vitruv.domains.families.tuid

import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.metamodels.families.FamiliesPackage

class FamiliesTuidCalculatorAndResolver extends AttributeTuidCalculatorAndResolver {
	
	new(String nsPrefix) {
		super(nsPrefix, ""+FamiliesPackage.Literals.FAMILIES_ROOT.classifierID)
	}
	
	override calculateIndividualTuidDelegator(EObject eObject) {
		dispatchedCalculateIndividualTuidDelegator(eObject);	
	}
	
	def String dispatchedCalculateIndividualTuidDelegator(EObject eObject) {
		super.calculateIndividualTuidDelegator(eObject);	
	}
	
}
