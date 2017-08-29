package tools.vitruv.extensions.dslsruntime.mappings

import com.google.common.collect.SetMultimap
import org.eclipse.emf.ecore.EObject
import com.google.common.collect.HashMultimap
import java.util.Set

abstract class AbstractElementRuntime implements ElementRuntime {
	val SetMultimap<Class<?>, EObject> elementsMap
	val String mappingName

	new(String mappingName) {
		this.elementsMap = HashMultimap.create()
		this.mappingName = mappingName
	}
	
	override <C> Set<?> getElements(Class<C> clazz) {
		return elementsMap.get(clazz)
	}
	
	override <C extends EObject> void addElement(Class<C> clazz, C element) {
		val elementIsNew = elementsMap.put(clazz, element)
		if (!elementIsNew) {
			throw new IllegalStateException('''Cannot register the element '«element»' for the mapping '«mappingName»'
			and the class '«clazz»' because it is already registered for them!''')
		}
	}
	
	protected def <C> void removeElement(Class<C> clazz, C element) {
		val wasMapped = elementsMap.remove(clazz, element)
		if (!wasMapped) {
			throw new IllegalStateException('''Cannot deregister the element '«element»' for the mapping '«mappingName»'
			and the class '«clazz»' because it is not registered for them!''')
		}
	}
}