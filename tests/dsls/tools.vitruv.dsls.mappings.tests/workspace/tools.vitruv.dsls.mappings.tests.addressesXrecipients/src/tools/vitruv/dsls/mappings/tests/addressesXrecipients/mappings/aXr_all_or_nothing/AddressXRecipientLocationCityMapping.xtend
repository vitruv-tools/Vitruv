package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing

import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.City
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.candidates.AddressXRecipientLocationCityCandidatesGenerator
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAddressXRecipientLocationCityInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAddressXRecipientLocationCityInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.AbstractMappingRuntime

class AddressXRecipientLocationCityMapping extends AbstractMappingRuntime<LeftAddressXRecipientLocationCityInstanceHalf,RightAddressXRecipientLocationCityInstanceHalf> {
	static val singleton = new AddressXRecipientLocationCityMapping
	
	val AdRootXReRootMapping rootXrootMapping = AdRootXReRootMapping.adRootXReRootMapping
	
	extension AddressXRecipientLocationCityCandidatesGenerator = new AddressXRecipientLocationCityCandidatesGenerator(rootXrootMapping, registry.elementsRegistry)
	
	private new() {
		super("AddressXRecipientLocationCityMapping")
	}
	
	def static AddressXRecipientLocationCityMapping addressXRecipientLocationCityMapping() {
		return singleton
	}
		
	/********** BEGIN PUBLIC LEFT ELEMENT METHODS **********/
	def void addAddress(Address address) {
		registry.elementsRegistry.addElement(Address, address)
		val newLeftCandidates = getNewCandidatesForAddress(address)
		registry.leftAndRightRegistry.addLeftCandidates(newLeftCandidates)
	}
	
	def void removeAddress(Address address) {
		registry.removeLeftElementAndCandidates(Address, address)
	}
	
	def void registerLeftElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		addAddress(a)
		registry.promoteValidatedCandidatesToInstances(#[aRoot, a], #[rRoot, r, l, c])
	}
	
	/********** BEGIN PUBLIC RIGHT ELEMENT METHODS **********/
	def void addRecipient(Recipient recipient) {
		registry.elementsRegistry.addElement(Recipient, recipient)
		val newRightCandidates = getNewCandidatesForRecipient(recipient)
		registry.leftAndRightRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeRecipient(Recipient recipient) {
		registry.removeRightElementAndCandidates(Recipient, recipient)
	}
	
	def void addLocation(Location location) {
		registry.elementsRegistry.addElement(Location, location)
		val newRightCandidates = getNewCandidatesForLocation(location)
		registry.leftAndRightRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeLocation(Location location) {
		registry.removeRightElementAndCandidates(Location, location)
	}
	
	def void addCity(City city) {
		registry.elementsRegistry.addElement(City, city)
		val newRightCandidates = getNewCandidatesForCity(city)
		registry.leftAndRightRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeCity(City city) {
		registry.removeRightElementAndCandidates(City, city)
	}
	
	def void registerRightElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		addRecipient(r)
		addLocation(l)
		addCity(c)
		registry.promoteValidatedCandidatesToInstances(#[aRoot, a], #[rRoot, r, l, c])
	}
	
	/********** BEGIN PUBLIC INSTANCE METHODS **********/
	def void removeInvalidatedInstanceHalves(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		registry.removeInvalidatedInstanceHalves(#[aRoot, a], #[rRoot, r, l, c])
	}
}