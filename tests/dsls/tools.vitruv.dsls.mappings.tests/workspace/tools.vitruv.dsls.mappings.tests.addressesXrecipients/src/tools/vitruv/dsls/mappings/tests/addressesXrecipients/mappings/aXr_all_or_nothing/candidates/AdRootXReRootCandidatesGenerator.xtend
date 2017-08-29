package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.candidates

import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAdRootXReRootInstanceHalf
import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import tools.vitruv.extensions.dslsruntime.mappings.MappingsUtil
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAdRootXReRootInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.ElementRuntime

class AdRootXReRootCandidatesGenerator {
	val ElementRuntime candidateElementsRuntime
	
	new(ElementRuntime candidateElementsRuntime) {
		this.candidateElementsRuntime = candidateElementsRuntime
	}
	
	def Iterable<LeftAdRootXReRootInstanceHalf> getNewCandidatesForAddresses(Addresses aRoot) {
		val aRootSet = #{aRoot}
		val cartesianProduct = MappingsUtil.cartesianProduct(aRootSet)
		return cartesianProduct.map[new LeftAdRootXReRootInstanceHalf(
			it.get(0) as Addresses
		)]
	}
	
	def Iterable<RightAdRootXReRootInstanceHalf> getNewCandidatesForRecipients(Recipients rRoot) {
		val rRootSet = #{rRoot}
		val cartesianProduct = MappingsUtil.cartesianProduct(rRootSet)
		return cartesianProduct.map[new RightAdRootXReRootInstanceHalf(
			it.get(0) as Recipients
		)]
	}
}