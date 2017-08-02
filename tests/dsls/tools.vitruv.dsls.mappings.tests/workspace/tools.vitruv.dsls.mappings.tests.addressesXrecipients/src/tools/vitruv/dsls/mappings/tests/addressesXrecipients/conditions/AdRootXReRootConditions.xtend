package tools.vitruv.dsls.mappings.tests.addressesXrecipients.conditions

import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.Recipients

class AdRootXReRootConditions {
	static val singleton = new AdRootXReRootConditions
	
	private new() {
		// empty
	}
	
	def static AdRootXReRootConditions adRootXReRootConditions() {
		return singleton
	}
	
	def boolean checkLeftPreconditions(Addresses aRoot) {
		return true
	}
	
	def boolean checkRightPreconditions(Recipients rRoot) {
		return true
	}
	
	def void enforceLeftPreconditions(Addresses aRoot) {
		// empty
	}
	
	def void enforceRigthPostconditions(Recipients rRoot) {
		// empty
	}
	
	def void enforceFromLeft2Right(Addresses aRoot, Recipients rRoot) {
		// empty
	}
	
	def void enforceFromRight2Left(Addresses aRoot, Recipients rRoot) {
		// empty
	}
}