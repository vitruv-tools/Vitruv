package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import edu.kit.ipd.sdq.commons.util.java.Triple
import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.recipients.City
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import java.util.Set
import tools.vitruv.extensions.dslsruntime.mappings.Mapping
import tools.vitruv.extensions.dslsruntime.mappings.MappingRegistry

class AddressXRecipientLocationCityMapping implements Mapping {
	static val singleton = new AddressXRecipientLocationCityMapping
	
	val MappingRegistry mappingRegistry
	
	private new() {
		this.mappingRegistry = new MappingRegistry(this)
	}
	
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
	def Iterable<Address> getLeftCandidates() {
		// FIXME MK IMPROVE THIS
		return mappingRegistry.getLeftCandidates().map[it.get(0) as Address]
	}
		
	def Iterable<Triple<Recipient, Location, City>> getRightCandidates() {
		// FIXME MK
	}
	
	/********** BEGIN INSTANCE METHODS **********/
	def Iterable<Address> getLeftInstances() {
		// FIXME MK
	}
	
	def Iterable<Triple<Recipient, Location, City>> getRightInstances() {
		// FIXME MK
	}
	
	def void addLeftInstance(Address a) {
		// FIXME MK
	}
	
	def void addRightInstance(Recipient r, Location l, City c) {
		// FIXME MK
	}
	
	def void removeLeftInstance(Address a) {
		// FIXME MK
	}
	
	def void removeRightInstance(Recipient r, Location l, City c) {
		// FIXME MK
	}
	
	/********** BEGIN PRIVATE METHODS **********/
	def private Iterable<Set<Object>> getNewCandidatesForAddress(Address address) {
		return #[#{address}]
	}
	
	def private Iterable<Set<Object>> getNewCandidatesForRecipient(Recipient recipient) {
		val ls = mappingRegistry.getElements(Location)
		val cs = mappingRegistry.getElements(City)
		return mappingRegistry.cartesianProduct(#{recipient},ls,cs)
	}
	
	def private Iterable<Set<Object>> getNewCandidatesForLocation(Location location) {
		val rs = mappingRegistry.getElements(Recipient)
		val cs = mappingRegistry.getElements(City)
		return mappingRegistry.cartesianProduct(#{location},rs,cs)
	}
	
	def private Iterable<Set<Object>> getNewCandidatesForCity(City city) {
		val rs = mappingRegistry.getElements(Recipient)
		val ls = mappingRegistry.getElements(Location)
		return mappingRegistry.cartesianProduct(#{city},rs,ls)
	}
}