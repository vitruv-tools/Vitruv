package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings

import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.LeftAdRootXReRootInstanceHalf
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.halves.RightAdRootXReRootInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.Mapping
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.instances.AdRootXReRootInstance

class AdRootXReRootMapping extends Mapping<LeftAdRootXReRootInstanceHalf,RightAdRootXReRootInstanceHalf,AdRootXReRootInstance> {
	static val singleton = new AdRootXReRootMapping

	private new() {
		super()
	}

	def static AdRootXReRootMapping adRootXReRootMapping() {
		return singleton
	}
	
	override getMappingName() '''AdRootXReRootMapping'''
	
	/********** BEGIN PUBLIC ELEMENT METHODS **********/
	def void addAddresses(Addresses addresses) {
		mappingRegistry.addElement(Addresses, addresses)
		val newLeftCandidates = getNewCandidatesForAddresses(addresses)
		mappingRegistry.addLeftCandidates(newLeftCandidates)
	}
	
	def void removeAddresses(Addresses addresses) {
		mappingRegistry.removeLeftElementCandidatesHalvesAndFullInstances(Addresses, addresses)
	}
	
	def void addRecipients(Recipients recipients) {
		mappingRegistry.addElement(Recipients, recipients)
		val newRightCandidates = getNewCandidatesForRecipients(recipients)
		mappingRegistry.addRightCandidates(newRightCandidates)
	}
	
	def void removeRecipients(Recipients recipients) {
		mappingRegistry.removeRightElementCandidatesHalvesAndFullInstances(Recipients, recipients)
	}
	
	/********** BEGIN PUBLIC ENFORCEMENT METHODS **********/
	def void enforceConditionsFromLeft2Right(Addresses aRoot, Recipients rRoot) {
		val instance = getFullInstance(aRoot, rRoot)
		enforceConditionsFromLeft2Right(instance)
	}
	
	def void enforceConditionsFromRight2Left(Addresses aRoot, Recipients rRoot) {
		val instance = getFullInstance(aRoot, rRoot)
		enforceConditionsFromRight2Left(instance)
	}
	
	/********** BEGIN PUBLIC INSTANCE METHODS **********/
	def void registerLeftAndFullInstance(Addresses aRoot, Recipients rRoot) {
		val leftInstance = new LeftAdRootXReRootInstanceHalf(aRoot)
		mappingRegistry.addLeftInstance(leftInstance)
		registerFullInstance(aRoot, rRoot)
	}
	
	def void registerRightAndFullInstance(Addresses aRoot, Recipients rRoot) {
		val rightInstance = new RightAdRootXReRootInstanceHalf(rRoot)
		mappingRegistry.addRightInstance(rightInstance)
		registerFullInstance(aRoot, rRoot)
	}
	
	def AdRootXReRootInstance getFullInstance(Addresses aRoot, Recipients rRoot) {
		return mappingRegistry.getFullInstance(#[aRoot], #[rRoot])
	}

	/********** BEGIN PRIVATE INSTANCE METHODS **********/	
	private def void registerFullInstance(Addresses aRoot, Recipients rRoot) {
		val leftInstance = mappingRegistry.getLeftInstance(#[aRoot])
		val rightInstance = mappingRegistry.getRightInstance(#[rRoot])
		val fullInstance = new AdRootXReRootInstance(leftInstance, rightInstance)
		mappingRegistry.addFullInstance(fullInstance)
	}
	
	/********** BEGIN PRIVATE CANDIDATE METHODS **********/
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