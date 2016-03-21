package edu.kit.ipd.sdq.vitruvius.framework.changedescription2change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EAttribute
import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.TypeInferringAtomicEChangeFactory.*
import org.eclipse.emf.ecore.change.ChangeDescription

class EChangeBridge {
	def static EChange createAdditiveEChangeForReferencedObject(EObject referencingEObject, EReference reference) {
		// FIXME MK ChangeBridge
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def static EChange createAdditiveEChangeForAttributeValue(EObject eObject, EAttribute attribute) {
		return createAdditiveAttributeChange(eObject, attribute)
	}
	
	def static EChange createSubtractiveEChangeForReferencedObject(EObject eObject, EReference reference) {
		// FIXME MK ChangeBridge
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def static EChange createAdditiveEChangeForEObject(EObject eObject) {
		if (eObject.eContainer instanceof ChangeDescription) {
			return createInsertRootChange(eObject, true)
		}
		// FIXME MK ChangeBridge
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def static EChange createSubtractiveEChangeForEObject(EObject eObject) {
		// FIXME MK ChangeBridge
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}