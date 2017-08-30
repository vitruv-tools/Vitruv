package tools.vitruv.extensions.dslsruntime.mappings.interfaces

import org.eclipse.emf.ecore.EObject
import java.util.List

/**
 * Interface for updating runtime information when an instance of a metaclass that appears as a mapping parameter
 * in a mapping signature is removed or when candidate tuples of such instances no longer or newly fulfill 
 * the conditions of the mapping (i.e. they are invalidated or new mapping instantiations).
 */
interface IBothSidesCombiningRegistry<L extends IMappingInstanceHalf, R extends IMappingInstanceHalf> {
	/**
	 * Removes the passed element, which has to be an instance of a metaclass that 
	 * appears as a mapping parameter on the left side of a mapping signature, and all candidate tuples 
	 * that involve the element from the runtime registry for the mapping.
	 */
	def <C extends EObject> void removeLeftElementAndCandidates(Class<C> clazz, C element)
	/**
	 * Removes the passed element, which has to be an instance of a metaclass that 
	 * appears as a mapping parameter on the right side of a mapping signature, and all candidate tuples 
	 * that involve the element from the runtime registry for the mapping.
	 */
	def <C extends EObject> void removeRightElementAndCandidates(Class<C> clazz, C element)	
	def void removeInvalidatedInstanceHalves(List<Object> leftElements, List<Object> rightElements)
	def void promoteValidatedCandidatesToInstances(List<Object> leftElements, List<Object> rightElements)
}