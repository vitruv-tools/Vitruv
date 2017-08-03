package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings

import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.recipients.City
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.LeftAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.LeftAddressXRecipientLocationCityInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.RightAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.RightAddressXRecipientLocationCityInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.Mapping

class AddressXRecipientLocationCityMapping extends Mapping<LeftAddressXRecipientLocationCityInstanceHalf,RightAddressXRecipientLocationCityInstanceHalf> {
	static val singleton = new AddressXRecipientLocationCityMapping
	
	val AdRootXReRootMapping rootXroot = AdRootXReRootMapping.adRootXReRootMapping
	
	def static AddressXRecipientLocationCityMapping addressXRecipientLocationCityMapping() {
		return singleton
	}
	
	override getMappingName() '''AddressXRecipientLocationCity'''
		
	/********** BEGIN ELEMENT METHODS **********/
	def void addAddress(Address address) {
		mappingRegistry.addElement(Address, address)
		val newLeftCandidates = getNewCandidatesForAddress(address)
		mappingRegistry.addLeftCandidates(newLeftCandidates)
	}
	
	def void removeAddress(Address address) {
		mappingRegistry.removeLeftElementAndCandidatesAndInstances(Address, address)
	}
	
	def void addRecipient(Recipient recipient) {
		mappingRegistry.addElement(Recipient, recipient)
		val newRightCandidates = getNewCandidatesForRecipient(recipient)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeRecipient(Recipient recipient) {
		mappingRegistry.removeRightElementAndCandidatesAndInstances(Recipient, recipient)
	}
	
	def void addLocation(Location location) {
		mappingRegistry.addElement(Location, location)
		val newRightCandidates = getNewCandidatesForLocation(location)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeLocation(Location location) {
		mappingRegistry.removeRightElementAndCandidatesAndInstances(Location, location)
	}
	
	def void addCity(City city) {
		mappingRegistry.addElement(City, city)
		val newRightCandidates = getNewCandidatesForCity(city)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeCity(City city) {
		mappingRegistry.removeRightElementAndCandidatesAndInstances(City, city)
	}
	
	/********** BEGIN CANDIDATE METHODS **********/
	def private Iterable<LeftAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForAddress(Address address) {
		val aRootSet = rootXroot.getLeftCandidates()
		val aSet = #{address}
		val cartesianProduct = mappingRegistry.cartesianProduct(aRootSet, aSet)
		return cartesianProduct.map[new LeftAddressXRecipientLocationCityInstanceHalf(
			it.get(0) as LeftAdRootXReRootInstanceHalf,
			it.get(1) as Address
		)]
	}
	
	def private Iterable<RightAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForRecipient(Recipient recipient) {
		val rRootSet = rootXroot.getRightCandidates()
		val rSet = #{recipient}
		val lSet = mappingRegistry.getElements(Location)
		val cSet = mappingRegistry.getElements(City)
		val cartesianProduct = mappingRegistry.cartesianProduct(rRootSet, rSet, lSet, cSet)
		return cartesianProduct.map[new RightAddressXRecipientLocationCityInstanceHalf(
			it.get(0) as RightAdRootXReRootInstanceHalf,
			it.get(1) as Recipient,
			it.get(2) as Location,
			it.get(3) as City
		)]
	}
	
	def private Iterable<RightAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForLocation(Location location) {
		val rRootSet = rootXroot.getRightCandidates()
		val rSet = mappingRegistry.getElements(Recipient)
		val lSet = #{location}
		val cSet = mappingRegistry.getElements(City)
		val cartesianProduct = mappingRegistry.cartesianProduct(rRootSet, rSet, lSet, cSet)
		return cartesianProduct.map[new RightAddressXRecipientLocationCityInstanceHalf(
			it.get(0) as RightAdRootXReRootInstanceHalf,
			it.get(1) as Recipient,
			it.get(2) as Location,
			it.get(3) as City
		)]
	}
	
	def private Iterable<RightAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForCity(City city) {
		val rRootSet = rootXroot.getRightCandidates()
		val rSet = mappingRegistry.getElements(Recipient)
		val lSet = mappingRegistry.getElements(Location)
		val cSet = #{city}
		val cartesianProduct = mappingRegistry.cartesianProduct(rRootSet, rSet, lSet, cSet)
		return cartesianProduct.map[new RightAddressXRecipientLocationCityInstanceHalf(
			it.get(0) as RightAdRootXReRootInstanceHalf,
			it.get(1) as Recipient,
			it.get(2) as Location,
			it.get(3) as City
		)]
	}
}