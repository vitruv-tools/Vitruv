package tools.vitruv.framework.domains

import tools.vitruv.framework.tuid.TuidCalculator
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.URIHaving
import java.util.Set
import java.util.Collection
import tools.vitruv.framework.tuid.Tuid

interface TuidAwareVitruvDomain extends URIHaving, Comparable<URIHaving>, TuidCalculator {
	def String calculateTuidFromEObject(EObject eObject, EObject virtualRootObject, String prefix);
	def boolean hasTuid(String tuid);
	def EObject resolveEObjectFromRootAndFullTuid(EObject root, Tuid tuid);
	def VURI getModelVURIContainingIdentifiedEObject(Tuid tuid);
	
	def boolean isInstanceOfDomainMetamodel(EObject object);
	def Set<String> getNsUris();
	def Collection<String> getFileExtensions();
}