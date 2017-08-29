package tools.vitruv.extensions.dslsruntime.mappings.registry

import java.util.List
import org.eclipse.emf.ecore.EObject

/**
 * Interface for data structures that group elements of a metamodel for an instantiation of a mapping, which relates instances of two metamodels.
 */
interface MappingInstanceHalf {

	def List<EObject> getElements()
	
	def boolean contains(EObject element) {
		return getElements().contains(element)
	}
}