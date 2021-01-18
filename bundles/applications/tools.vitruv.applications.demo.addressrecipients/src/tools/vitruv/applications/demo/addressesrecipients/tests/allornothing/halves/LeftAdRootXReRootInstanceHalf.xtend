package tools.vitruv.applications.demo.addressesrecipients.tests.allornothing.halves

import edu.kit.ipd.sdq.metamodels.addresses.Addresses
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingInstanceHalf
import org.eclipse.xtend.lib.annotations.Data

@Data 
class LeftAdRootXReRootInstanceHalf implements IMappingInstanceHalf {
	// no dependencies to other mappings
	Addresses aRoot
	
	override getElements() {
		return #[aRoot]
	}	
}
