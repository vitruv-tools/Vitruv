package tools.vitruv.framework.util.datatypes

import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.util.datatypes.impl.ModelInstanceImpl

interface ModelInstance extends URIHaving {
	static def ModelInstance createModelInstance(VURI uri, Resource resource) {
		new ModelInstanceImpl(uri, resource)
	}

	def Resource getResource()

	def VURI getMetamodeURI()

	/** 
	 * Returns the root element of the model instance if it is unique (exactly one root element) and
	 * throws a {@link java.lang.RuntimeException RuntimeException} otherwise.
	 * @return the root element
	 */
	def EObject getUniqueRootEObject()

	/** 
	 * Returns the root element of the model instance, which occurs first (depending on the order in
	 * the resource) and throws a {@link java.lang.RuntimeException RuntimeException} if there is no
	 * root element.
	 * @return the root element
	 */
	def EObject getFirstRootEObject()

	/** 
	 * Returns the root element of the model instance if it is unique (exactly one root element) and
	 * has the type of the given class and throws a {@link java.lang.RuntimeExceptionRuntimeException} otherwise.
	 * @param rootElementClassthe class of which the root element has to be an instance of
	 * @return the root element
	 */
	def <T extends EObject> T getUniqueRootEObjectIfCorrectlyTyped(Class<T> rootElementClass)

	/** 
	 * Returns the root element of the model instance if it is the only one with a compatible type.
	 * It is NOT necessary to have exactly one root element as long as only one of these element
	 * matches the given type. If there is not exactly one such element a{@link java.lang.RuntimeException RuntimeException} is thrown.
	 * @param rootElementClassthe class of which the root element has to be an instance of
	 * @return the root element
	 */
	def <T extends EObject> T getUniqueTypedRootEObject(Class<T> rootElementClass)

	def List<EObject> getRootElements()

	/** 
	 * Loads the resource into memory. The load can be forced by setting
	 * forceLoadByDoingUnloadBeforeLoad to true, which means that the resource will be unloaded
	 * before we load it again.
	 */
	def void load(Map<Object, Object> loadOptions, boolean forceLoadByDoingUnloadBeforeLoad)

	def void load(Map<Object, Object> loadOptions)
}
