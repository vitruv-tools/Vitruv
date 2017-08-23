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
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.instances.AddressXRecipientLocationCityInstance
import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.Recipients

class AddressXRecipientLocationCityMapping extends Mapping<LeftAddressXRecipientLocationCityInstanceHalf,RightAddressXRecipientLocationCityInstanceHalf,AddressXRecipientLocationCityInstance> {
	static val singleton = new AddressXRecipientLocationCityMapping
	
	val AdRootXReRootMapping rootXrootMapping = AdRootXReRootMapping.adRootXReRootMapping
	
	def static AddressXRecipientLocationCityMapping addressXRecipientLocationCityMapping() {
		return singleton
	}
	
	override getMappingName() '''AddressXRecipientLocationCity'''
		
	/********** BEGIN PUBLIC ELEMENT METHODS **********/
	def void addAddress(Address address) {
		mappingRegistry.addElement(Address, address)
		val newLeftCandidates = getNewCandidatesForAddress(address)
		mappingRegistry.addLeftCandidates(newLeftCandidates)
	}
	
	def void removeAddress(Address address) {
		mappingRegistry.removeLeftElementCandidatesHalvesAndFullInstances(Address, address)
	}
	
	def void addRecipient(Recipient recipient) {
		mappingRegistry.addElement(Recipient, recipient)
		val newRightCandidates = getNewCandidatesForRecipient(recipient)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeRecipient(Recipient recipient) {
		mappingRegistry.removeRightElementCandidatesHalvesAndFullInstances(Recipient, recipient)
	}
	
	def void addLocation(Location location) {
		mappingRegistry.addElement(Location, location)
		val newRightCandidates = getNewCandidatesForLocation(location)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeLocation(Location location) {
		mappingRegistry.removeRightElementCandidatesHalvesAndFullInstances(Location, location)
	}
	
	def void addCity(City city) {
		mappingRegistry.addElement(City, city)
		val newRightCandidates = getNewCandidatesForCity(city)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeCity(City city) {
		mappingRegistry.removeRightElementCandidatesHalvesAndFullInstances(City, city)
	}
	
	/********** BEGIN PUBLIC ENFORCEMENT METHODS **********/
	def void enforceConditionsFromLeft2Right(Address a, Recipient r, Location l, City c) {
		val instance = getFullInstance(a, r, l, c)
		enforceConditionsFromLeft2Right(instance)
	}
	
	def void enforceConditionsFromRight2Left(Address a, Recipient r, Location l, City c) {
		val instance = getFullInstance(a, r, l, c)
		enforceConditionsFromRight2Left(instance)
	}
	
	/********** BEGIN PUBLIC INSTANCE METHODS **********/
	def void registerLeftAndFullInstance(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		val rootXroot = rootXrootMapping.getFullInstance(aRoot,rRoot)
		val leftInstance = new LeftAddressXRecipientLocationCityInstanceHalf(rootXroot.leftHalf, a)
		mappingRegistry.addLeftInstance(leftInstance)
		registerFullInstance(a, r, l, c)
	}
	
	def void registerRightAndFullInstance(Addresses aRoot, Recipients rRoot, Address a, Recipient r, Location l, City c) {
		val rootXroot = rootXrootMapping.getFullInstance(aRoot,rRoot)
		val rightInstance = new RightAddressXRecipientLocationCityInstanceHalf(rootXroot.rightHalf, r, l, c)
		mappingRegistry.addRightInstance(rightInstance)
		registerFullInstance(a, r, l, c)
	}
	
	def AddressXRecipientLocationCityInstance getFullInstance(Address a, Recipient r, Location l, City c) {
		return mappingRegistry.getFullInstance(#[a], #[r, l, c])
	}
	
	/********** BEGIN PRIVATE INSTANCE METHODS **********/
	private	def void registerFullInstance(Address a, Recipient r, Location l, City c) {
		val leftInstance = mappingRegistry.getLeftInstance(#[a])
		val rightInstance = mappingRegistry.getRightInstance(#[r, l, c])
		val fullInstance = new AddressXRecipientLocationCityInstance(leftInstance, rightInstance)
		mappingRegistry.addFullInstance(fullInstance)
	}
	
	/********** BEGIN PRIVATE CANDIDATE METHODS **********/
	def private Iterable<LeftAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForAddress(Address address) {
		val aRootSet = rootXrootMapping.getLeftCandidatesAndInstances()
		val aSet = #{address}
		val cartesianProduct = mappingRegistry.cartesianProduct(aRootSet, aSet)
		return cartesianProduct.map[new LeftAddressXRecipientLocationCityInstanceHalf(
			it.get(0) as LeftAdRootXReRootInstanceHalf,
			it.get(1) as Address
		)]
	}
	
	def private Iterable<RightAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForRecipient(Recipient recipient) {
		val rRootSet = rootXrootMapping.getRightCandidatesAndInstances()
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
		val rRootSet = rootXrootMapping.getRightCandidatesAndInstances()
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
		val rRootSet = rootXrootMapping.getRightCandidatesAndInstances()
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