package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.conditions

import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import tools.vitruv.extensions.dslsruntime.mappings.MappingsUtil

class AdRootXReRootConditions {
	static val singleton = new AdRootXReRootConditions
	
	private new() {
		// empty
	}
	
	static def adRootXReRootConditions() {
		return singleton
	}
	
	def boolean checkLeftConditions(Addresses aRoot) {
		return MappingsUtil.allElementsStillExisting(aRoot)
	}
		
	def void enforceLeftConditions(Addresses aRoot) {
		// empty
	}
	
	def boolean checkRightConditions(Recipients rRoot) {
		return MappingsUtil.allElementsStillExisting(rRoot)
	}
	
	def void enforceRightConditions(Recipients rRoot) {
		// empty
	}
	
	def void enforceConditionsFromLeftToRight(Addresses aRoot, Recipients rRoot) {
		enforceRightConditions(rRoot)
	}
	
	def void enforceConditionsFromRightToLeft(Addresses aRoot, Recipients rRoot) {
		enforceLeftConditions(aRoot)
	}
}