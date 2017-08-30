package tools.vitruv.extensions.dslsruntime.mappings.interfaces

import java.util.Set
import org.eclipse.emf.ecore.EObject

/**
 * Interface for keeping track of all instances for certain metaclasses.
 * Used to restrict the checking of mapping conditions to instances 
 * of those metaclasses that appear as mapping parameters in the mapping signature. 
 */
interface IElementsRegistry {
	def <C extends EObject> Set<C> getElements(Class<C> clazz)
	def <C extends EObject> void addElement(Class<C> clazz, C element)
	def <C extends EObject> void removeElement(Class<C> clazz, C element)
}