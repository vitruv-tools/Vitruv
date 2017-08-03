package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.instances

import tools.vitruv.extensions.dslsruntime.mappings.MappingInstance
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.LeftAddressXRecipientLocationCityInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.RightAddressXRecipientLocationCityInstanceHalf

class AddressXRecipientLocationCityInstance implements MappingInstance {
	extension LeftAddressXRecipientLocationCityInstanceHalf leftHalf
	extension RightAddressXRecipientLocationCityInstanceHalf rightHalf
	
	new(LeftAddressXRecipientLocationCityInstanceHalf leftHalf, RightAddressXRecipientLocationCityInstanceHalf rightHalf) {
		this.leftHalf = leftHalf
		this.rightHalf = rightHalf
	}
	
	override checkLeftConditions() {
		return leftHalf.checkConditions()
	}

	override enforceLeftConditions() {
		leftHalf.enforceConditions()
	}
	
	override checkRightConditions() {
		return rightHalf.checkConditions()
	}
	
	override enforceRigthConditions() {
		rightHalf.enforceConditions()
	}
	
	// TODO KEEP ON WORKING HERE: private fields with protected getters for halves into mapping instance superclass
	
	override enforceFromLeft2Right() {
		// enforce inverse of:
		// a.number = l.number
		l.number = a.number
		// enforce inverse of:
		// a.street = l.street
		l.street = a.street
		// enforce inverse of:
		// a.zipCode = c.zipCode
		c.zipCode = a.zipCode
	}
	
	override enforceFromRight2Left() {
		a.number = l.number
		a.street = l.street
		a.zipCode = c.zipCode
	}
}