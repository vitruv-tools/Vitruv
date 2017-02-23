package tools.vitruv.framework.change.echange.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * Static utility class for the EChange package and subpackages.
 */
class EChangeUtil {
	
	/**
	 * Wrapper method for {@link EcoreUtil.resolve} which is null-safe.
	 * @param proxy The EObject which should be resolved.
	 * @param resourceSet The resource set which contains the resource with 
	 * 		the concrete EObject of the proxy.
	 * @return The resolved EObject. If resolving the proxy fails, the proxy object itself.
	 */
	public static def resolveProxy(EObject proxy, ResourceSet resourceSet) {
		if (proxy != null) {
			return EcoreUtil.resolve(proxy, resourceSet)
		}
		return proxy
	}
}