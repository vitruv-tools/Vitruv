package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves

import tools.vitruv.extensions.dslsruntime.mappings.MappingInstanceHalf
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.City
import org.eclipse.xtend.lib.annotations.Data

@Data class RightAddressXRecipientLocationCityInstanceHalf extends MappingInstanceHalf {
	RightAdRootXReRootInstanceHalf rootXroot
	Recipient r
	Location l
	City c
	
	override getElements() {
		return #[r,l,c]
	}
	
	override checkConditions() {
		return super.checkConditions() && 
		rootXroot.RRoot.recipients.contains(r) && 
		r.business == true && 
		r.locatedAt == l &&
		l.number > 0 && 
		r.locatedIn == c &&
		c.zipCode != null
	}
	
	override enforceConditions() {
		// enforce r in rootXroot:rRoot.recipients
		rootXroot.RRoot.recipients.add(r)
		// enforce r.business == true
		r.business = true
		// enforce r.locatedAt == l
		r.locatedAt = l
		// enforce l.number > 0
		if (l.number <= 0) {
			l.number = 0
		}
		// enforce r.locatedIn == c
		r.locatedIn = c
		// enforce c.zipCode != null
		if (c.zipCode == null) {
			c.zipCode == ""
		}
	}
}