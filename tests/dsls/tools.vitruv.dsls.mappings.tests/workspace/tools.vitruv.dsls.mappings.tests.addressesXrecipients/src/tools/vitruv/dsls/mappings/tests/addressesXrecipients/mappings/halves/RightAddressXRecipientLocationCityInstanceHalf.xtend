package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves

import tools.vitruv.extensions.dslsruntime.mappings.MappingInstanceHalf
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.City
import org.eclipse.xtend.lib.annotations.Data

@Data class RightAddressXRecipientLocationCityInstanceHalf extends MappingInstanceHalf {
	RightAdRootXReRootInstanceHalf rRoot
	Recipient r
	Location l
	City c
	
	override getElements() {
		return #[r,l,c]
	}
	
	override checkConditions() {
		return r.business == true && l.number > 0 && c.zipCode != null
	}
	
	override enforceConditions() {
		// enforce r.business == true
		r.business = true
		// enforce l.number > 0
		if (l.number <= 0) {
			l.number = 0
		}
		// enforce c.zipCode != null
		if (c.zipCode == null) {
			c.zipCode == ""
		}
	}
}