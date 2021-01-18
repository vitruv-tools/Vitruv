package tools.vitruv.applications.demo.addressesrecipients.tests.allornothing.halves

import edu.kit.ipd.sdq.metamodels.addresses.Address
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingInstanceHalf

@Data 
class LeftAddressXRecipientLocationCityInstanceHalf implements IMappingInstanceHalf {
	LeftAdRootXReRootInstanceHalf rootXroot
	Address a
	
	override getElements() {
		return com.google.common.collect.Iterables.concat(rootXroot.elements, #[a]).toList
	}
}
