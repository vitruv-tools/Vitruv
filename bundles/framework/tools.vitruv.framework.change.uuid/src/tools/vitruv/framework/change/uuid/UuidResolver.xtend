package tools.vitruv.framework.change.uuid

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.common.util.URI

interface UuidResolver {
	public static final UuidResolver EMPTY = new EmptyUuidResolver();
	
	def boolean hasUuid(EObject object);
	def String getUuid(EObject object);
	def EObject getEObject(String uuid);
	def void registerEObject(String uuid, EObject eObject);
	
	/**
	 * Registers the given UUID for the element at the given {@link URI} in the
	 * {@link ResourceSet} of this resolver.
	 * If the object cannot be resolved in the {@link ResourceSet} of this resolver,
	 * <code>false</code> is returned, otherwise <code>true</code>.
	 */
	def boolean registerUuidForGlobalUri(String uuid, URI uri);
	
	def ResourceSet getResourceSet();
	
}