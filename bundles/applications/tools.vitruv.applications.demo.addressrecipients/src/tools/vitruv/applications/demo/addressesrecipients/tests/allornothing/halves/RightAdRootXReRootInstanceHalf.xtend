package tools.vitruv.applications.demo.addressesrecipients.tests.allornothing.halves

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
