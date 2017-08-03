package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.instances

import tools.vitruv.extensions.dslsruntime.mappings.MappingInstance
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.LeftAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.RightAdRootXReRootInstanceHalf

class AdRootXReRootInstance implements MappingInstance {
	extension LeftAdRootXReRootInstanceHalf leftHalf
	extension RightAdRootXReRootInstanceHalf rightHalf	
	
	new(LeftAdRootXReRootInstanceHalf leftHalf, RightAdRootXReRootInstanceHalf rightHalf) {
		this.leftHalf = leftHalf
		this.rightHalf = rightHalf
	}
}
