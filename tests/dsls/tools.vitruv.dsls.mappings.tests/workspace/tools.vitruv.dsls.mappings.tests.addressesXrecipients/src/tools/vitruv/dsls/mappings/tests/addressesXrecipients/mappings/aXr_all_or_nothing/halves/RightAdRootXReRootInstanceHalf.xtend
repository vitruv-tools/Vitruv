package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves

import tools.vitruv.extensions.dslsruntime.mappings.MappingInstanceHalf
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import org.eclipse.xtend.lib.annotations.Data

@Data class RightAdRootXReRootInstanceHalf implements MappingInstanceHalf {
	Recipients rRoot
	
	override getElements() {
		return #[rRoot]
	}
}