package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves

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