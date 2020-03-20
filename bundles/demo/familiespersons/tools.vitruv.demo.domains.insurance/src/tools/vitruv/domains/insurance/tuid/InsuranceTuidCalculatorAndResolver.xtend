package tools.vitruv.domains.insurance.tuid

import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver

class InsuranceTuidCalculatorAndResolver extends AttributeTuidCalculatorAndResolver {
	
	new(String nsPrefix) {
		super(nsPrefix, "name")
	}
	
}
