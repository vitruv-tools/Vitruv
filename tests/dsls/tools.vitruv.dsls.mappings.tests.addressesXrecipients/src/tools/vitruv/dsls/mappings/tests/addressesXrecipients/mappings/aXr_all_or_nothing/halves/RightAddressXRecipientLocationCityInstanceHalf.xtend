package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves

import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingInstanceHalf
import edu.kit.ipd.sdq.metamodels.recipients.Recipient
import edu.kit.ipd.sdq.metamodels.recipients.Location
import edu.kit.ipd.sdq.metamodels.recipients.City
import org.eclipse.xtend.lib.annotations.Data

@Data 
class RightAddressXRecipientLocationCityInstanceHalf implements IMappingInstanceHalf {
	RightAdRootXReRootInstanceHalf rootXroot
	Recipient r
	Location l
	City c
	
	override getElements() {
		return com.google.common.collect.Iterables.concat(rootXroot.elements, #[r, l, c]).toList
	}
}