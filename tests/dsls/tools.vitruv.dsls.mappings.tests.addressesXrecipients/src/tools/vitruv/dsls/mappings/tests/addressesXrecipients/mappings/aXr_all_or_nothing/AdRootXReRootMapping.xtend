package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing

import edu.kit.ipd.sdq.metamodels.addresses.Addresses
import edu.kit.ipd.sdq.metamodels.recipients.Recipients
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAdRootXReRootInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.AbstractMappingRuntime
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingRegistry4DependingCandidatesGenerators

class AdRootXReRootMapping extends AbstractMappingRuntime<LeftAdRootXReRootInstanceHalf,RightAdRootXReRootInstanceHalf> {
	static val singleton = new AdRootXReRootMapping
	
	// no dependencies to other mappings
	
	extension AdRootXReRootCandidatesGenerator = new AdRootXReRootCandidatesGenerator(elementsRegistry)

	private new() {
		super("AdRootXReRootMapping")
	}

	def static AdRootXReRootMapping adRootXReRootMapping() {
		return singleton
	}
	
	package def IMappingRegistry4DependingCandidatesGenerators<LeftAdRootXReRootInstanceHalf, RightAdRootXReRootInstanceHalf> getPackageVisibleRegistry4DependingCandidatesGenerator() {
		return registry4DependingCandidatesGenerator
	}
	
	/********** BEGIN PUBLIC LEFT ELEMENT METHODS **********/
	def void addAddresses(Addresses addresses) {
		elementsRegistry.addElement(Addresses, addresses)
		val newLeftCandidates = getNewCandidatesForAddresses(addresses)
		leftAndRightRegistry.addLeftCandidates(newLeftCandidates)
	}
	
	def void removeAddresses(Addresses addresses) {
		bothSidesCombiningRegistry.removeLeftElementAndCandidates(Addresses, addresses)
	}
	
	def void registerLeftElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot) {
		addAddresses(aRoot)
		bothSidesCombiningRegistry.promoteValidatedCandidatesToInstances(#[aRoot], #[rRoot])
	}
	
	/********** BEGIN PUBLIC RIGHT ELEMENT METHODS **********/
	def void addRecipients(Recipients recipients) {
		elementsRegistry.addElement(Recipients, recipients)
		val newRightCandidates = getNewCandidatesForRecipients(recipients)
		leftAndRightRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeRecipients(Recipients recipients) {
		bothSidesCombiningRegistry.removeRightElementAndCandidates(Recipients, recipients)
	}
	
	def void registerRightElementsAndPromoteCandidates(Addresses aRoot, Recipients rRoot) {
		addRecipients(rRoot)
		bothSidesCombiningRegistry.promoteValidatedCandidatesToInstances(#[aRoot], #[rRoot])
	}
	
	/********** BEGIN PUBLIC INSTANCE METHODS **********/
	def void removeInvalidatedInstanceHalves(Addresses aRoot, Recipients rRoot) {
		bothSidesCombiningRegistry.removeInvalidatedInstanceHalves(#[aRoot], #[rRoot])
	}
}