package tools.vitruvius.applications.jmljava.vitruvius.utils;

import tools.vitruvius.framework.util.VitruviusConstants;
import tools.vitruvius.framework.util.bridges.EclipseBridge;

/**
 * Collection of utility methods to work with the Eclipse plugin environment.
 */
public class EclipseUtilities {

    /**
     * Finds all registered extensions with the same name as the full qualified name of the given
     * class and the type of it. So the extensions for a class a.b.c.Class would be registered at
     * the extension point a.b.c.Class and implement the interface Class.
     * 
     * @param clazz
     *            The class of the registered extension.
     * @param <T>
     *            the type of the registered extension.
     * @return A set of registered extensions.
     */
    public static <T> Iterable<T> getRegisteredExtensions(Class<T> clazz) {
        return EclipseBridge.getRegisteredExtensions(clazz.getCanonicalName().toLowerCase(),
                VitruviusConstants.getExtensionPropertyName(), clazz);
    }

}
