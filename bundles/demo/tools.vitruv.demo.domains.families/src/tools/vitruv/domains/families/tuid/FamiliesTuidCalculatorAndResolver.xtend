package tools.vitruv.domains.families.tuid

import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver
import org.eclipse.emf.ecore.EObject

class FamiliesTuidCalculatorAndResolver extends AttributeTuidCalculatorAndResolver {
	
	new(String nsPrefix) {
		super(nsPrefix, "id", "firstName", "lastName")
	}
	
	override calculateIndividualTuidDelegator(EObject eObject) {
		dispatchedCalculateIndividualTuidDelegator(eObject);	
	}
	
	def String dispatchedCalculateIndividualTuidDelegator(EObject eObject) {
		super.calculateIndividualTuidDelegator(eObject);	
	}
	
}
