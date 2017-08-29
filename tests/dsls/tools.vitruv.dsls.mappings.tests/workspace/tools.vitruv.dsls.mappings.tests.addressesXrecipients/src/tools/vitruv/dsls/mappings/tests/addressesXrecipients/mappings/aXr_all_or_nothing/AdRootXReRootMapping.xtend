package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing

import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.candidates.AdRootXReRootCandidatesGenerator
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAdRootXReRootInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.AbstractMappingRuntime

class AdRootXReRootMapping extends AbstractMappingRuntime<LeftAdRootXReRootInstanceHalf,RightAdRootXReRootInstanceHalf> {
	static val singleton = new AdRootXReRootMapping
	
	// no dependencies to other mappings
	
	extension AdRootXReRootCandidatesGenerator = new AdRootXReRootCandidatesGenerator(registry.elementsRegistry)

	private new() {
		super("AdRootXReRootMapping")
	}

	def static AdRootXReRootMapping adRootXReRootMapping() {
		return singleton
	}
	
	/********** BEGIN PUBLIC LEFT ELEMENT METHODS **********/
	def void addAddresses(Addresses addresses) {
		registry.elementsRegistry.addElement(Addresses, addresses)
		val newLeftCandidates = getNewCandidatesForAddresses(addresses)
		registry.leftAndRightRegistry.addLeftCandidates(newLeftCandidates)
	}
	
	def void removeAddresses(Addresses addresses) {
		registry.removeLeftElementAndCandidates(Addresses, addresses)
	}
	
	def void registerLeftElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot) {
		addAddresses(aRoot)
		registry.promoteValidatedCandidatesToInstances(#[aRoot], #[rRoot])
	}
	
	/********** BEGIN PUBLIC RIGHT ELEMENT METHODS **********/
	def void addRecipients(Recipients recipients) {
		registry.elementsRegistry.addElement(Recipients, recipients)
		val newRightCandidates = getNewCandidatesForRecipients(recipients)
		registry.leftAndRightRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeRecipients(Recipients recipients) {
		registry.removeRightElementAndCandidates(Recipients, recipients)
	}
	
	def void registerRightElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot) {
		addRecipients(rRoot)
		registry.promoteValidatedCandidatesToInstances(#[aRoot], #[rRoot])
	}
	
	/********** BEGIN PUBLIC INSTANCE METHODS **********/
	def void removeInvalidatedInstanceHalves(Addresses aRoot, Recipients rRoot) {
		registry.removeInvalidatedInstanceHalves(#[aRoot], #[rRoot])
	}
}