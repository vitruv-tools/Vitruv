package tools.vitruv.extensions.dslsruntime.mappings.impl

import com.google.common.collect.SetMultimap
import org.eclipse.emf.ecore.EObject
import com.google.common.collect.HashMultimap
import java.util.Set
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IElementsRegistry

/**
 * An object of this class keeps track of all instances of all metaclasses that appear as a 
 * mapping parameter on at least one side of a mapping signature for a certain mapping. 
 * Realized by mapping every relevant metaclass to a set that contains all instances of the metaclass.
 */
class SetMultimapElementsRegistry implements IElementsRegistry {
	/**
	 * Behaves like a {@code Map<Class<?...>, Set<EObject>>} but creates 
	 * and maintains the necessary sets for us.
	 */
	val SetMultimap<Class<? extends EObject>, EObject> elementsMap
	val String mappingName

	new(String mappingName) {
		this.elementsMap = HashMultimap.create()
		this.mappingName = mappingName
	}
	
	override <C extends EObject> Set<C> getElements(Class<C> clazz) {
		return elementsMap.get(clazz) as Set<C>
	}
	
	override <C extends EObject> void addElement(Class<C> clazz, C element) {
		val elementIsNew = elementsMap.put(clazz, element)
		if (!elementIsNew) {
			throw new IllegalStateException('''Cannot register the element '«element»' for the mapping '«mappingName»'
			and the class '«clazz»' because it is already registered for them!''')
		}
	}
	
	override def <C extends EObject> void removeElement(Class<C> clazz, C element) {
		val wasMapped = elementsMap.remove(clazz, element)
		if (!wasMapped) {
			throw new IllegalStateException('''Cannot deregister the element '«element»' for the mapping '«mappingName»'
			and the class '«clazz»' because it is not registered for them!''')
		}
	}
}