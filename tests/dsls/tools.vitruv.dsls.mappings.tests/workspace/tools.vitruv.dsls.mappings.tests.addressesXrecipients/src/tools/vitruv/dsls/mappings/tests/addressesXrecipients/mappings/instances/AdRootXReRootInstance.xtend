package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.instances

import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.LeftAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.RightAdRootXReRootInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.MappingInstance

class AdRootXReRootInstance extends MappingInstance<LeftAdRootXReRootInstanceHalf,RightAdRootXReRootInstanceHalf> {
	extension LeftAdRootXReRootInstanceHalf = leftHalf
	extension RightAdRootXReRootInstanceHalf = rightHalf	
	
	new(LeftAdRootXReRootInstanceHalf leftHalf, RightAdRootXReRootInstanceHalf rightHalf) {
		super(leftHalf,rightHalf)
	}
}
