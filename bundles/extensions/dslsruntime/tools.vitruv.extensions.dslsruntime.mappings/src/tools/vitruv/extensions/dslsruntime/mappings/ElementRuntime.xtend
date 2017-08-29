package tools.vitruv.extensions.dslsruntime.mappings

import java.util.Set
import org.eclipse.emf.ecore.EObject

interface ElementRuntime {
	def <C> Set<?> getElements(Class<C> clazz)
	def <C extends EObject> void addElement(Class<C> clazz, C element)
}