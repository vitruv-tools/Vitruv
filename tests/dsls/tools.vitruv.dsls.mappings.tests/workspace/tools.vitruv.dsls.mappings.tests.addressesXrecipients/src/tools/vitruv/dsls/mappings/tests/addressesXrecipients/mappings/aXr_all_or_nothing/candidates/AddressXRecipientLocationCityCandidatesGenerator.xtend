package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.candidates

import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAddressXRecipientLocationCityInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAddressXRecipientLocationCityInstanceHalf
import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.City
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AdRootXReRootMapping
import tools.vitruv.extensions.dslsruntime.mappings.MappingsUtil
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAdRootXReRootInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.ElementRuntime
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAdRootXReRootInstanceHalf

class AddressXRecipientLocationCityCandidatesGenerator {
	val AdRootXReRootMapping rootXrootMapping
	val ElementRuntime candidateElementsRuntime
	
	new(AdRootXReRootMapping rootXrootMapping, ElementRuntime candidateElementsRuntime) {
		this.rootXrootMapping = rootXrootMapping
		this.candidateElementsRuntime = candidateElementsRuntime
	}
	
	def Iterable<LeftAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForAddress(Address address) {
		val aRootSet = rootXrootMapping.getLeftCandidatesAndInstances()
		val aSet = #{address}
		val cartesianProduct = MappingsUtil.cartesianProduct(aRootSet, aSet)
		return cartesianProduct.map[new LeftAddressXRecipientLocationCityInstanceHalf(
			it.get(0) as LeftAdRootXReRootInstanceHalf,
			it.get(1) as Address
		)]
	}
	
	def Iterable<RightAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForRecipient(Recipient recipient) {
		val rRootSet = rootXrootMapping.getRightCandidatesAndInstances()
		val rSet = #{recipient}
		val lSet = candidateElementsRuntime.getElements(Location)
		val cSet = candidateElementsRuntime.getElements(City)
		val cartesianProduct = MappingsUtil.cartesianProduct(rRootSet, rSet, lSet, cSet)
		return cartesianProduct.map[new RightAddressXRecipientLocationCityInstanceHalf(
			it.get(0) as RightAdRootXReRootInstanceHalf,
			it.get(1) as Recipient,
			it.get(2) as Location,
			it.get(3) as City
		)]
	}
	
	def Iterable<RightAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForLocation(Location location) {
		val rRootSet = rootXrootMapping.getRightCandidatesAndInstances()
		val rSet = candidateElementsRuntime.getElements(Recipient)
		val lSet = #{location}
		val cSet = candidateElementsRuntime.getElements(City)
		val cartesianProduct = MappingsUtil.cartesianProduct(rRootSet, rSet, lSet, cSet)
		return cartesianProduct.map[new RightAddressXRecipientLocationCityInstanceHalf(
			it.get(0) as RightAdRootXReRootInstanceHalf,
			it.get(1) as Recipient,
			it.get(2) as Location,
			it.get(3) as City
		)]
	}
	
	def Iterable<RightAddressXRecipientLocationCityInstanceHalf> getNewCandidatesForCity(City city) {
		val rRootSet = rootXrootMapping.getRightCandidatesAndInstances()
		val rSet = candidateElementsRuntime.getElements(Recipient)
		val lSet = candidateElementsRuntime.getElements(Location)
		val cSet = #{city}
		val cartesianProduct = MappingsUtil.cartesianProduct(rRootSet, rSet, lSet, cSet)
		return cartesianProduct.map[new RightAddressXRecipientLocationCityInstanceHalf(
			it.get(0) as RightAdRootXReRootInstanceHalf,
			it.get(1) as Recipient,
			it.get(2) as Location,
			it.get(3) as City
		)]
	}
}