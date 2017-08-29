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
	
	extension AddressXRecipientLocationCityCandidatesGenerator = new AddressXRecipientLocationCityCandidatesGenerator(rootXrootMapping, this)
	
	private new() {
		super("AddressXRecipientLocationCityMapping")
	}
	
	def static AddressXRecipientLocationCityMapping addressXRecipientLocationCityMapping() {
		return singleton
	}
		
	/********** BEGIN PUBLIC ELEMENT METHODS **********/
	def void addAddress(Address address) {
		mappingRegistry.addElement(Address, address)
		val newLeftCandidates = getNewCandidatesForAddress(address)
		mappingRegistry.addLeftCandidates(newLeftCandidates)
	}
	
	def void removeAddress(Address address) {
		mappingRegistry.removeLeftCandidatesForElement(Address, address)
	}
	
	def void addRecipient(Recipient recipient) {
		mappingRegistry.addElement(Recipient, recipient)
		val newRightCandidates = getNewCandidatesForRecipient(recipient)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeRecipient(Recipient recipient) {
		mappingRegistry.removeRightCandidatesForElement(Recipient, recipient)
	}
	
	def void addLocation(Location location) {
		mappingRegistry.addElement(Location, location)
		val newRightCandidates = getNewCandidatesForLocation(location)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeLocation(Location location) {
		mappingRegistry.removeRightCandidatesForElement(Location, location)
	}
	
	def void addCity(City city) {
		mappingRegistry.addElement(City, city)
		val newRightCandidates = getNewCandidatesForCity(city)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeCity(City city) {
		mappingRegistry.removeRightCandidatesForElement(City, city)
	}
	
	def void registerLeftElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		addAddress(a)
		mappingRegistry.promoteValidatedCandidatesToInstances(#[aRoot, a], #[rRoot, r, l, c])
	}
	
	def void registerRightElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		addRecipient(r)
		addLocation(l)
		addCity(c)
		mappingRegistry.promoteValidatedCandidatesToInstances(#[aRoot, a], #[rRoot, r, l, c])
	}
	
	/********** BEGIN PUBLIC INSTANCE METHODS **********/
	def void registerLeftInstanceHalf(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		val leftRootXrootHalf = rootXrootMapping.getLeftInstanceHalf(#[aRoot])
		val leftInstance = new LeftAddressXRecipientLocationCityInstanceHalf(leftRootXrootHalf, a)
		mappingRegistry.addLeftInstanceHalf(leftInstance)
	}
	
	def void registerRightInstanceHalf(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		val rightRootXrootHalf = rootXrootMapping.getRightInstanceHalf(#[rRoot])
		val rightInstance = new RightAddressXRecipientLocationCityInstanceHalf(rightRootXrootHalf, r, l, c)
		mappingRegistry.addRightInstanceHalf(rightInstance)
	}
	
	def void removeInvalidatedInstanceHalves(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		mappingRegistry.removeInvalidatedInstanceHalves(#[aRoot, a], #[rRoot, r, l, c])
	}
}