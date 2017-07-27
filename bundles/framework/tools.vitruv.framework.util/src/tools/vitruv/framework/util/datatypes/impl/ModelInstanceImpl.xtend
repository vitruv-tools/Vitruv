package tools.vitruv.framework.util.datatypes.impl

import java.io.IOException
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.transaction.TransactionalEditingDomain
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.datatypes.AbstractURIHaving
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI

class ModelInstanceImpl extends AbstractURIHaving implements ModelInstance {
	static extension Logger = Logger::getLogger(ModelInstanceImpl.simpleName)
	@Accessors(PUBLIC_GETTER)
	Resource resource
	Map<Object, Object> lastUsedLoadOptions

	new(VURI uri, Resource resource) {
		super(uri)
		if (resource === null)
			throw new RuntimeException('''Cannot create a model instance at the URI '«»«uri»' for a null resource!''')
		this.resource = resource
	}

	override getMetamodeURI() {
		if (null !== resource && resource.contents.size === 0)
			throw new RuntimeException('''
				Cannot get the metamodel URI for the model instance at the URI '«»«URI»' because it has no root element!
			''')

		var String rootEObjectNamespace
		try {
			rootEObjectNamespace = uniqueRootEObject.eClass.EPackage.nsURI.toString
		} catch (RuntimeException e) {
			warn("A unique root object could not be determined. Trying the first root object instead.")
			rootEObjectNamespace = firstRootEObject.eClass.EPackage.nsURI.toString
		}

		return VURI::getInstance(rootEObjectNamespace)
	}

	override getUniqueRootEObject() {
		return EcoreResourceBridge::getUniqueContentRoot(resource, URI.toString)
	}

	/**
	 * Returns the root element of the model instance, which occurs first (depending on the order in
	 * the resource) and throws a {@link java.lang.RuntimeException RuntimeException} if there is no
	 * root element.
	 * @return the root element
	 */
	override getFirstRootEObject() {
		try {
			return EcoreResourceBridge::getFirstRootEObject(resource, URI.toString)
		} catch (RuntimeException re) {
			val boolean forceLoad = true
			load(lastUsedLoadOptions, forceLoad)
			return EcoreResourceBridge::getFirstRootEObject(resource, URI.toString)
		}

	}

	override <T extends EObject> T getUniqueRootEObjectIfCorrectlyTyped(Class<T> rootElementClass) {
		return EcoreResourceBridge::getUniqueContentRootIfCorrectlyTyped(resource, URI.toString, rootElementClass)
	}

	/**
	 * Returns the root element of the model instance if it is the only one with a compatible type.
	 * It is NOT necessary to have exactly one root element as long as only one of these element
	 * matches the given type. If there is not exactly one such element a{@link java.lang.RuntimeException RuntimeException} is thrown.
	 * @param rootElementClassthe class of which the root element has to be an instance of
	 * @return the root element
	 */
	override <T extends EObject> T getUniqueTypedRootEObject(Class<T> rootElementClass) {
		return EcoreResourceBridge::getUniqueTypedRootEObject(resource, URI.toString, rootElementClass)
	}

	override getRootElements() {
		return resource.contents
	}

	override load(
		Map<Object, Object> loadOptions,
		boolean forceLoadByDoingUnloadBeforeLoad
	) {
		EMFCommandBridge.createAndExecuteVitruviusRecordingCommand([
			{
				try {
					if (null !== loadOptions)
						lastUsedLoadOptions = loadOptions
					if (resource.modified || forceLoadByDoingUnloadBeforeLoad)
						resource.unload
					resource.load(lastUsedLoadOptions)
				} catch (IOException e) {
					// soften
					throw new RuntimeException(e)
				}

				return null
			}
		], getTransactionalEditingDomain())
	}

	override load(Map<Object, Object> loadOptions) {
		load(loadOptions, false)
	}

	// TODO HK This should be done differently: The VSUM provides the editing domain!
	private def synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
		if (null === TransactionalEditingDomain::Factory.INSTANCE.getEditingDomain(resource.resourceSet))
			TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(resource.resourceSet)
		return TransactionalEditingDomain::Factory.INSTANCE.getEditingDomain(resource.resourceSet)
	}
}
