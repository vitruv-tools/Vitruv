import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.City
import edu.kit.ipd.sdq.commons.util.java.Triple

class AddressXRecipientLocationCityMapping {
	
	def static Iterable<Address> getLeftCandidates() {
		// FIXME MK
	}
	
	def static Iterable<Address> getLeftInstances() {
		// FIXME MK
	}
	
	def static Iterable<Triple<Recipient, Location, City>> getRightCandidates() {
		// FIXME MK
	}
	
	def static Iterable<Triple<Recipient, Location, City>> getRightInstances() {
		// FIXME MK
	}
	
	def static void registerLeftInstance(Address a) {
		// FIXME MK
	}
	
	def static void registerRightInstance(Recipient r, Location l, City c) {
		// FIXME MK
	}
	
	def static void deregisterLeftInstance(Address a) {
		// FIXME MK
	}
	
	def static void deregisterRightInstance(Recipient r, Location l, City c) {
		// FIXME MK
	}
	
	def static void registerAddress(Address address) {
		// FIXME MK
	}
	
	def static void deregisterAddress(Address address) {
		// FIXME MK
	}
	
	def static void registerRecipient(Recipient recipient) {
		// FIXME MK
	}
	
	def static void deregisterRecipient(Recipient recipient) {
		// FIXME MK
	}
	
	def static void registerLocation(Location location) {
		// FIXME MK
	}
	
	def static void deregisterLocation(Location location) {
		// FIXME MK
	}
	
	def static void registerCity(City city) {
		// FIXME MK
	}
	
	def static void deregisterCity(City city) {
		// FIXME MK
	}
	
	def static boolean isMappingApplicationForAddress(Recipient r, Location l, City c, Recipient correspondingRecipient, Location correspondingLocation, City correspondingCity) {
		return r == correspondingRecipient && l == correspondingLocation && c == correspondingCity
	}
}