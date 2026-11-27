package tools.vitruv.framework.remote.common.util;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emfcloud.jackson.resource.JsonResourceFactory;

/**
 * Contains utility functions to work with {@link Resource}s.
 */
public class ResourceUtil {
    private ResourceUtil() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
    }

    /**
     * Creates a {@link Resource} with the given {@link URI} and given content.
     *
     * @param uri       The URI of the resource.
     * @param content   The content of the resource.
     * @param parentSet The parent {@link ResourceSet} of the resource.
     * @return The created {@link Resource}.
     */
    public static Resource createResourceWith(URI uri, Collection<? extends EObject> content, ResourceSet parentSet) {
        var resource = parentSet.createResource(uri);
        resource.getContents().addAll(content);
        return resource;
    }

    /**
     * Creates a {@link Resource} with the given {@link URI} and given content.
     * Uses a new {@link ResourceSet} as parent set.
     *
     * @param uri     The URI of the resource.
     * @param content The content of the resource.
     * @return The created {@link Resource}.
     */
    public static Resource createResourceWith(URI uri, Collection<? extends EObject> content) {
        return createResourceWith(uri, content, createJsonResourceSet());
    }
    
    public static Resource createEmptyResource(URI uri) {
		return createResourceWith(uri, Collections.emptyList());
	}
    
    /**
     * Returns a {@link ResourceSet} and registers a {@link JsonResourceFactory} as default factory.
     * 
     * @return The created {@link ResourceSet}.
     */
    public static ResourceSet createJsonResourceSet() {
    	var set = new ResourceSetImpl();
    	set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new JsonResourceFactory());
    	return set;
    }
}
