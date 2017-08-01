package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.City

class AddressXRecipientLocationCityConditions {
	static val singleton = new AddressXRecipientLocationCityConditions
	
	private new() {
		// empty
	}
	
	def static AddressXRecipientLocationCityConditions addressXRecipientLocationCityConditions() {
		return singleton
	}
	
	def boolean checkLeftPreconditions(Address a) {
		return a.number > 0 && a.zipCode != null
	}
	
	def boolean checkRightPreconditions(Recipient r, Location l, City c) {
		return r.business == true && l.number > 0 && c.zipCode != null
	}
	
	def void enforceLeftPreconditions(Address a) {
		// enforce a.number > 0
      	if (a.number <= 0) {
			a.number = 0
		}
      	// enforce a.zipCode != null
      	if (a.zipCode == null) {
			a.zipCode == ""
		}
	}
	
	def void enforceRigthPostconditions(Recipient r, Location l, City c) {
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
	
	def void enforceFromLeft2Right(Address a, Recipient r, Location l, City c) {
		// enforce inverse of:
		// a.number = l.number
		l.number = a.number
		// enforce inverse of:
		// a.street = l.street
		l.street = a.street
		// enforce inverse of:
		// a.zipCode = c.zipCode
		c.zipCode = a.zipCode
	}
	
	def void enforceFromRight2Left(Address a, Recipient r, Location l, City c) {
		a.number = l.number
		a.street = l.street
		a.zipCode = c.zipCode
	}
}