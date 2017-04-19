package tools.vitruv.framework.domains

import tools.vitruv.framework.tuid.TuidCalculator
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.URIHaving
import java.util.Set
import java.util.Collection

interface TuidAwareVitruvDomain extends URIHaving, Comparable<URIHaving>, TuidCalculator {
	def String calculateTuidFromEObject(EObject eObject, EObject virtualRootObject, String prefix);
	def boolean hasTuid(String tuid);
	def EObject resolveEObjectFromRootAndFullTuid(EObject root, String tuid);
	def VURI getModelVURIContainingIdentifiedEObject(String tuid);
	
	def boolean isInstanceOfDomainMetamodel(EObject object);
	def Set<String> getNsUris();
	def Collection<String> getFileExtensions();
}