package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves

import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import tools.vitruv.extensions.dslsruntime.mappings.MappingInstanceHalf
import org.eclipse.xtend.lib.annotations.Data

@Data class LeftAdRootXReRootInstanceHalf implements MappingInstanceHalf {
	Addresses aRoot
	
	override getElements() {
		return #[aRoot]
	}	
}