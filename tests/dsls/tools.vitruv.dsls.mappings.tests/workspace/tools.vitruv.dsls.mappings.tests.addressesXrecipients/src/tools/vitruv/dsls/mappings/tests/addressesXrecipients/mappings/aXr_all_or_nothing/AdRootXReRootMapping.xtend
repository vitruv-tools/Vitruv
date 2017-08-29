package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing

import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.candidates.AdRootXReRootCandidatesGenerator
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAdRootXReRootInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.AbstractMappingRuntime

class AdRootXReRootMapping extends AbstractMappingRuntime<LeftAdRootXReRootInstanceHalf,RightAdRootXReRootInstanceHalf> {
	static val singleton = new AdRootXReRootMapping
	
	extension AdRootXReRootCandidatesGenerator = new AdRootXReRootCandidatesGenerator(this)

	private new() {
		super("AdRootXReRootMapping")
	}

	def static AdRootXReRootMapping adRootXReRootMapping() {
		return singleton
	}
	
	/********** BEGIN PUBLIC ELEMENT METHODS **********/
	def void addAddresses(Addresses addresses) {
		mappingRegistry.addElement(Addresses, addresses)
		val newLeftCandidates = getNewCandidatesForAddresses(addresses)
		mappingRegistry.addLeftCandidates(newLeftCandidates)
	}
	
	def void removeAddresses(Addresses addresses) {
		mappingRegistry.removeLeftCandidatesForElement(Addresses, addresses)
	}
	
	def void addRecipients(Recipients recipients) {
		mappingRegistry.addElement(Recipients, recipients)
		val newRightCandidates = getNewCandidatesForRecipients(recipients)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeRecipients(Recipients recipients) {
		mappingRegistry.removeRightCandidatesForElement(Recipients, recipients)
	}
	
	def void registerLeftElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot) {
		addAddresses(aRoot)
		mappingRegistry.promoteValidatedCandidatesToInstances(#[aRoot], #[rRoot])
	}
	
	def void registerRightElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot) {
		addRecipients(rRoot)
		mappingRegistry.promoteValidatedCandidatesToInstances(#[aRoot], #[rRoot])
	}
	
	/********** BEGIN PUBLIC INSTANCE METHODS **********/
	def void registerLeftInstanceHalf(Addresses aRoot, Recipients rRoot) {
		val leftInstance = new LeftAdRootXReRootInstanceHalf(aRoot)
		mappingRegistry.addLeftInstanceHalf(leftInstance)
	}
	
	def void registerRightInstanceHalf(Addresses aRoot, Recipients rRoot) {
		val rightInstance = new RightAdRootXReRootInstanceHalf(rRoot)
		mappingRegistry.addRightInstanceHalf(rightInstance)
	}
	
	def void removeInvalidatedInstanceHalves(Addresses aRoot, Recipients rRoot) {
		mappingRegistry.removeInvalidatedInstanceHalves(#[aRoot], #[rRoot])
	}
}