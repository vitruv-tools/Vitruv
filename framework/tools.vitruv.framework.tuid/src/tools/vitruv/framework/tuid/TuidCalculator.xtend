package tools.vitruv.framework.tuid

import org.eclipse.emf.ecore.EObject

interface TuidCalculator {
	def boolean canCalculateTuid(EObject object);
	def TUID calculateTuid(EObject object);
}