package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings

import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.LeftAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.RightAdRootXReRootInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.Mapping

class AdRootXReRootMapping extends Mapping<LeftAdRootXReRootInstanceHalf,RightAdRootXReRootInstanceHalf> {
	static val singleton = new AdRootXReRootMapping

	private new() {
		super()
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
	def private Iterable<LeftAdRootXReRootInstanceHalf> getNewCandidatesForAddresses(Addresses aRoot) {
		val aRootSet = #{aRoot}
		val cartesianProduct = mappingRegistry.cartesianProduct(aRootSet)
		return cartesianProduct.map[new LeftAdRootXReRootInstanceHalf(
			it.get(0) as Addresses
		)]
	}
	
	def private Iterable<RightAdRootXReRootInstanceHalf> getNewCandidatesForRecipients(Recipients rRoot) {
		val rRootSet = #{rRoot}
		val cartesianProduct = mappingRegistry.cartesianProduct(rRootSet)
		return cartesianProduct.map[new RightAdRootXReRootInstanceHalf(
			it.get(0) as Recipients
		)]
	}
}