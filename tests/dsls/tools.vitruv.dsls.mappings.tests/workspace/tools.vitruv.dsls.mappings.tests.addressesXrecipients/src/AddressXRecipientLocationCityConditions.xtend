import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.City

class AddressXRecipientLocationCityConditions {
	def static boolean checkLeftPreconditions(Address a) {
		return a.number > 0 && a.zipCode != ""
	}
	
	def static boolean checkRightPreconditions(Recipient r, Location l, City c) {
		// FIXME MK
		return false
	}
	
	def static void enforceLeftPreconditions(Address a) {
		// FIXME MK
	}
	
	def static void enforceRigthPostconditions(Recipient r, Location l, City c) {
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
	
	def static void enforceFromLeft2Right(Address a, Recipient r, Location l, City c) {
		// enforce a.street = l.street
		l.street = a.street
		// enforce a.zipCode = c.zipCode
		c.zipCode = a.zipCode
	}
	
	def static void enforceFromRight2Left(Address a, Recipient r, Location l, City c) {
		// FIXE MK
	}
}