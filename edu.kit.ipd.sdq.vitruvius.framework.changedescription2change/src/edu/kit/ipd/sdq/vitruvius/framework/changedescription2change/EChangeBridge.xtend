package edu.kit.ipd.sdq.vitruvius.framework.changedescription2change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EAttribute

class EChangeBridge {
	def static EChange createAdditiveEChangeForReferencedObject(EObject referencingEObject, EReference reference) {
		// FIXME MK ChangeBridge
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def static EChange createAdditiveEChangeForAttributeValue(EObject object, EAttribute attribute) {
		// FIXME MK ChangeBridge
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def static EChange createSubtractiveEChangeForReferencedObject(EObject object, EReference reference) {
		// FIXME MK ChangeBridge
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}