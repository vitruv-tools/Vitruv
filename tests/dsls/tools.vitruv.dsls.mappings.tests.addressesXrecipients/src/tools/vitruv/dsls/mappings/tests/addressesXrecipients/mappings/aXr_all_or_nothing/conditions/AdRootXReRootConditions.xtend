package tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.conditions

import edu.kit.ipd.sdq.metamodels.addresses.Addresses
import edu.kit.ipd.sdq.metamodels.recipients.Recipients
import tools.vitruv.extensions.dslsruntime.mappings.MappingsUtil

class AdRootXReRootConditions {
	static val singleton = new AdRootXReRootConditions
	
	private new() {
		// empty singleton constructor
	}
	
	static def adRootXReRootConditions() {
		return singleton
	}
	
	def boolean checkLeftConditions(Addresses aRoot) {
		return MappingsUtil.allElementsStillExisting(aRoot)
	}
		
//	private def void enforceLeftConditions(Addresses aRoot) {
//		// empty (no single-sided conditions for left side)
//	}
	
	def boolean checkRightConditions(Recipients rRoot) {
		return MappingsUtil.allElementsStillExisting(rRoot)
	}
	
//	private def void enforceRightConditions(Recipients rRoot) {
//		// empty (no single-sided conditions for right side)
//	}
//	
//	def void enforceConditionsFromLeftToRight(Addresses aRoot, Recipients rRoot) {
//		enforceRightConditions(rRoot)
//		// empty (no bidirectionalizable conditions)
//	}
//	
//	def void enforceConditionsFromRightToLeft(Addresses aRoot, Recipients rRoot) {
//		enforceLeftConditions(aRoot)
//		// empty (no bidirectionalizable conditions)
//	}
}