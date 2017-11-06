package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.conditions

import edu.kit.ipd.sdq.metamodels.addresses.Addresses
import edu.kit.ipd.sdq.metamodels.addresses.Address
import edu.kit.ipd.sdq.metamodels.recipients.Recipients
import edu.kit.ipd.sdq.metamodels.recipients.Recipient
import edu.kit.ipd.sdq.metamodels.recipients.Location
import edu.kit.ipd.sdq.metamodels.recipients.City
import tools.vitruv.extensions.dslsruntime.mappings.MappingsUtil

class AddressXRecipientLocationCityConditions {
	static val singleton = new AddressXRecipientLocationCityConditions
	
	private new() {
		// empty singleton constructor
	}
	
	static def addressXRecipientLocationCityConditions() {
		return singleton
	}
	
	def boolean checkLeftConditions(Addresses aRoot, Address a) {
		return MappingsUtil.allElementsStillExisting(aRoot, a)
		&& aRoot.addresses.contains(a)
		&& a.number > 0
		&& a.zipCode !== null
	}
		
//	private def void enforceLeftConditions(Addresses aRoot, Address a) {
//		// enforce a in rootXroot:aRoot.addresses
//		aRoot.addresses.add(a)
//		// enforce a.number > 0
//      	if (a.number <= 0) {
//			a.number = 0
//		}
//      	// enforce a.zipCode != null
//      	if (a.zipCode == null) {
//			a.zipCode == ""
//		}
//	}
	
	def boolean checkRightConditions(Recipients rRoot, Recipient r, Location l, City c) {
		return MappingsUtil.allElementsStillExisting(rRoot, r, l, c)
		&& rRoot.recipients.contains(r) 
		&& r.business == true 
		&& r.locatedAt == l
		&& l.number > 0
		&& r.locatedIn == c 
		&& c.zipCode !== null
	}
	
//	private def void enforceRightConditions(Recipients rRoot, Recipient r, Location l, City c) {
//		// enforce r in rootXroot:rRoot.recipients
//		rRoot.recipients.add(r)
//		// enforce r.business == true
//		r.business = true
//		// enforce r.locatedAt == l
//		r.locatedAt = l
//		// enforce l.number > 0
//		if (l.number <= 0) {
//			l.number = 0
//		}
//		// enforce r.locatedIn == c
//		r.locatedIn = c
//		// enforce c.zipCode != null
//		if (c.zipCode == null) {
//			c.zipCode == ""
//		}
//	}
//	
//	def void enforceConditionsFromLeftToRight(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
//		enforceRightConditions(rRoot, r, l, c)
//		// enforce inverse of:
//		// a.number = l.number
//		l.number = a.number
//		// enforce inverse of:
//		// a.street = l.street
//		l.street = a.street
//		// enforce inverse of:
//		// a.zipCode = c.zipCode
//		c.zipCode = a.zipCode
//	}
//	
//	def void enforceConditionsFromRightToLeft(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
//		enforceLeftConditions(aRoot, a)
//		a.number = l.number
//		a.street = l.street
//		a.zipCode = c.zipCode
//	}
}