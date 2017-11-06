package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves

import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingInstanceHalf
import edu.kit.ipd.sdq.metamodels.recipients.Recipients
import org.eclipse.xtend.lib.annotations.Data

@Data 
class RightAdRootXReRootInstanceHalf implements IMappingInstanceHalf {
	// no dependencies to other mappings
	Recipients rRoot
	
	override getElements() {
		return #[rRoot]
	}
}