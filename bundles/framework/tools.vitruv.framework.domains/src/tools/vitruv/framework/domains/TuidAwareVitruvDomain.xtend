package tools.vitruv.framework.domains

import tools.vitruv.framework.tuid.TuidCalculator
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.tuid.Tuid

interface TuidAwareVitruvDomain extends VitruvDomain, TuidCalculator {
	def String calculateTuidFromEObject(EObject eObject, EObject virtualRootObject, String prefix);
	def boolean hasTuid(String tuid);
	def EObject resolveEObjectFromRootAndFullTuid(EObject root, Tuid tuid);
	def VURI getModelVURIContainingIdentifiedEObject(Tuid tuid);
	
	/**
	 * Must be called to register the domain at the TUID calculation and resolution system 
	 */
	def void registerAtTuidManagement();
}