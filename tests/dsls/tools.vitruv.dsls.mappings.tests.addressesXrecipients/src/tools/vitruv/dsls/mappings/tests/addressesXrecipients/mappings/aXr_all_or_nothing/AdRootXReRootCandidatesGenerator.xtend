package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing

import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAdRootXReRootInstanceHalf
import edu.kit.ipd.sdq.metamodels.addresses.Addresses
import edu.kit.ipd.sdq.metamodels.recipients.Recipients
import tools.vitruv.extensions.dslsruntime.mappings.MappingsUtil
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAdRootXReRootInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IElementsRegistry

class AdRootXReRootCandidatesGenerator {
	// no dependencies to other mappings
	val IElementsRegistry candidateElementsRuntime
	
	new(IElementsRegistry candidateElementsRuntime) {
		// no dependencies to other mappings
		this.candidateElementsRuntime = candidateElementsRuntime
	}
	
	def Iterable<LeftAdRootXReRootInstanceHalf> getNewCandidatesForAddresses(Addresses aRoot) {
		// no dependencies to other mappings
		val aRootSet = #{aRoot}
		val cartesianProduct = MappingsUtil.typedCartesianProduct(aRootSet)
		return cartesianProduct.map[new LeftAdRootXReRootInstanceHalf(
			it.get0
		)]
	}
	
	def Iterable<RightAdRootXReRootInstanceHalf> getNewCandidatesForRecipients(Recipients rRoot) {
		// no dependencies to other mappings
		val rRootSet = #{rRoot}
		val cartesianProduct = MappingsUtil.typedCartesianProduct(rRootSet)
		return cartesianProduct.map[new RightAdRootXReRootInstanceHalf(
			it.get0
		)]
	}
}