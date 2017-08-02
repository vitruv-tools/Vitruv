package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings

import tools.vitruv.extensions.dslsruntime.mappings.MappingRegistry
import tools.vitruv.extensions.dslsruntime.mappings.Mapping
import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import java.util.Set

class AdRootXReRootMapping implements Mapping {
	static val singleton = new AdRootXReRootMapping
	
	val MappingRegistry mappingRegistry
	
	private new() {
		this.mappingRegistry = new MappingRegistry(this)
	}
	
	def static AdRootXReRootMapping adRootXReRootMapping() {
		return singleton
	}
	
	override getMappingName() '''AdRootXReRootMapping'''
	
	/********** BEGIN ELEMENT METHODS **********/
	def void addAddresses(Addresses addresses) {
		mappingRegistry.addElement(Addresses, addresses)
		val newLeftCandidates = getNewCandidatesForAddresses(addresses)
		mappingRegistry.addLeftCandidates(newLeftCandidates)
	}
	
	def void removeAddresses(Addresses addresses) {
		mappingRegistry.removeLeftElementAndCandidatesAndInstances(Addresses, addresses)
	}
	
	def void addRecipients(Recipients recipients) {
		mappingRegistry.addElement(Recipients, recipients)
		val newRightCandidates = getNewCandidatesForRecipients(recipients)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeRecipients(Recipients recipients) {
		mappingRegistry.removeRightElementAndCandidatesAndInstances(Recipients, recipients)
	}
	
	/********** BEGIN CANDIDATE METHODS **********/
	def Iterable<Addresses> getLeftCandidates() {
		return mappingRegistry.getLeftCandidates().toLeftTypes
	}
	
	def private Iterable<Addresses> toLeftTypes(Iterable<Set<Object>> iterable) {
		return iterable.map[
			it.filter(Addresses).get(0)
		]
	}
		
	def Iterable<Recipients> getRightCandidates() {
		return mappingRegistry.getRightCandidates().toRightTypes
	}
	
	def private Iterable<Recipients> toRightTypes(Iterable<Set<Object>> iterable) {
		return iterable.map[
			it.filter(Recipients).get(0)
		]
	}
	
	/********** BEGIN INSTANCE METHODS **********/
	def Iterable<Addresses> getLeftInstances() {
		return mappingRegistry.getLeftInstances().toLeftTypes
	}
	
	def Iterable<Recipients> getRightInstances() {
		return mappingRegistry.getRightInstances().toRightTypes
	}
	
	def void addLeftInstance(Addresses aRoot) {
		mappingRegistry.addLeftInstance(#{aRoot})
	}
	
	def void addRightInstance(Recipients rRoot) {
		mappingRegistry.addRightInstance(#{rRoot})
	}
	
	def void removeLeftInstance(Addresses aRoot) {
		mappingRegistry.removeLeftInstance(#{aRoot})
	}
	
	def void removeRightInstance(Recipients rRoot) {
		mappingRegistry.removeLeftInstance(#{rRoot})
	}
	
	/********** BEGIN PRIVATE METHODS **********/
	def private Iterable<Set<Object>> getNewCandidatesForAddresses(Addresses aRoot) {
		return mappingRegistry.cartesianProduct(#{aRoot})
	}
	
	def private Iterable<Set<Object>> getNewCandidatesForRecipients(Recipients rRoot) {
		return mappingRegistry.cartesianProduct(#{rRoot})
	}
}