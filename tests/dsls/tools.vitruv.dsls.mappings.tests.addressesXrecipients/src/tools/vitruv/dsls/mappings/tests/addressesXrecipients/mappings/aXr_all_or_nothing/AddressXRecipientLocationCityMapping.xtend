package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing

import edu.kit.ipd.sdq.metamodels.addresses.Address
import edu.kit.ipd.sdq.metamodels.addresses.Addresses
import edu.kit.ipd.sdq.metamodels.recipients.City
import edu.kit.ipd.sdq.metamodels.recipients.Location
import edu.kit.ipd.sdq.metamodels.recipients.Recipient
import edu.kit.ipd.sdq.metamodels.recipients.Recipients
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAddressXRecipientLocationCityInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAddressXRecipientLocationCityInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.AbstractMappingRuntime

class AddressXRecipientLocationCityMapping extends AbstractMappingRuntime<LeftAddressXRecipientLocationCityInstanceHalf,RightAddressXRecipientLocationCityInstanceHalf> {
	static val singleton = new AddressXRecipientLocationCityMapping
	
	val AdRootXReRootMapping rootXrootMapping = AdRootXReRootMapping.adRootXReRootMapping
	
	extension AddressXRecipientLocationCityCandidatesGenerator = new AddressXRecipientLocationCityCandidatesGenerator(rootXrootMapping.packageVisibleRegistry4DependingCandidatesGenerator, elementsRegistry)
	
	private new() {
		super("AddressXRecipientLocationCityMapping")
	}
	
	def static AddressXRecipientLocationCityMapping addressXRecipientLocationCityMapping() {
		return singleton
	}
		
	/********** BEGIN PUBLIC LEFT ELEMENT METHODS **********/
	def void addAddress(Address address) {
		elementsRegistry.addElement(Address, address)
		val newLeftCandidates = getNewCandidatesForAddress(address)
		leftAndRightRegistry.addLeftCandidates(newLeftCandidates)
	}
	
	def void removeAddress(Address address) {
		bothSidesCombiningRegistry.removeLeftElementAndCandidates(Address, address)
	}
	
	def void registerLeftElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		addAddress(a)
		bothSidesCombiningRegistry.promoteValidatedCandidatesToInstances(#[aRoot, a], #[rRoot, r, l, c])
	}
	
	/********** BEGIN PUBLIC RIGHT ELEMENT METHODS **********/
	def void addRecipient(Recipient recipient) {
		elementsRegistry.addElement(Recipient, recipient)
		val newRightCandidates = getNewCandidatesForRecipient(recipient)
		leftAndRightRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeRecipient(Recipient recipient) {
		bothSidesCombiningRegistry.removeRightElementAndCandidates(Recipient, recipient)
	}
	
	def void addLocation(Location location) {
		elementsRegistry.addElement(Location, location)
		val newRightCandidates = getNewCandidatesForLocation(location)
		leftAndRightRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeLocation(Location location) {
		bothSidesCombiningRegistry.removeRightElementAndCandidates(Location, location)
	}
	
	def void addCity(City city) {
		elementsRegistry.addElement(City, city)
		val newRightCandidates = getNewCandidatesForCity(city)
		leftAndRightRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeCity(City city) {
		bothSidesCombiningRegistry.removeRightElementAndCandidates(City, city)
	}
	
	def void registerRightElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		addRecipient(r)
		addLocation(l)
		addCity(c)
		bothSidesCombiningRegistry.promoteValidatedCandidatesToInstances(#[aRoot, a], #[rRoot, r, l, c])
	}
	
	/********** BEGIN PUBLIC INSTANCE METHODS **********/
	def void removeInvalidatedInstanceHalves(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		bothSidesCombiningRegistry.removeInvalidatedInstanceHalves(#[aRoot, a], #[rRoot, r, l, c])
	}
}