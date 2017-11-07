package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing

import edu.kit.ipd.sdq.metamodels.addresses.Address
import edu.kit.ipd.sdq.metamodels.recipients.City
import edu.kit.ipd.sdq.metamodels.recipients.Location
import edu.kit.ipd.sdq.metamodels.recipients.Recipient
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAddressXRecipientLocationCityInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAddressXRecipientLocationCityInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.MappingsUtil
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IElementsRegistry
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingRegistry4DependingCandidatesGenerators

class AddressXRecipientLocationCityCandidatesGenerator {
	val IMappingRegistry4DependingCandidatesGenerators<LeftAdRootXReRootInstanceHalf, RightAdRootXReRootInstanceHalf> rootXrootRegistry
	val IElementsRegistry candidateElementsRuntime
	
	new(IMappingRegistry4DependingCandidatesGenerators<LeftAdRootXReRootInstanceHalf, RightAdRootXReRootInstanceHalf> rootXrootRegistry, IElementsRegistry candidateElementsRegistry) {
		this.rootXrootRegistry = rootXrootRegistry
		this.candidateElementsRuntime = candidateElementsRegistry
	}
	
	def Iterable<LeftAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForAddress(Address address) {
		val aRootSet = rootXrootRegistry.getLeftCandidatesAndInstances()
		val aSet = #{address}
		val cartesianProduct = MappingsUtil.typedCartesianProduct(aRootSet, aSet)
		return cartesianProduct.map[new LeftAddressXRecipientLocationCityInstanceHalf(
			it.get0,
			it.get1
		)]
	}
	
	def Iterable<RightAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForRecipient(Recipient recipient) {
		val rRootSet = rootXrootRegistry.getRightCandidatesAndInstances()
		val rSet = #{recipient}
		val lSet = candidateElementsRuntime.getElements(Location)
		val cSet = candidateElementsRuntime.getElements(City)
		val cartesianProduct = MappingsUtil.typedCartesianProduct(rRootSet, rSet, lSet, cSet)
		return cartesianProduct.map[new RightAddressXRecipientLocationCityInstanceHalf(
			it.get0,
			it.get1,
			it.get2,
			it.get3
		)]
	}
	
	def Iterable<RightAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForLocation(Location location) {
		val rRootSet = rootXrootRegistry.getRightCandidatesAndInstances()
		val rSet = candidateElementsRuntime.getElements(Recipient)
		val lSet = #{location}
		val cSet = candidateElementsRuntime.getElements(City)
		val cartesianProduct = MappingsUtil.typedCartesianProduct(rRootSet, rSet, lSet, cSet)
		return cartesianProduct.map[new RightAddressXRecipientLocationCityInstanceHalf(
			it.get0,
			it.get1,
			it.get2,
			it.get3
		)]
	}
	
	def Iterable<RightAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForCity(City city) {
		val rRootSet = rootXrootRegistry.getRightCandidatesAndInstances()
		val rSet = candidateElementsRuntime.getElements(Recipient)
		val lSet = candidateElementsRuntime.getElements(Location)
		val cSet = #{city}
		val cartesianProduct = MappingsUtil.typedCartesianProduct(rRootSet, rSet, lSet, cSet)
		return cartesianProduct.map[new RightAddressXRecipientLocationCityInstanceHalf(
			it.get0,
			it.get1,
			it.get2,
			it.get3
		)]
	}
}