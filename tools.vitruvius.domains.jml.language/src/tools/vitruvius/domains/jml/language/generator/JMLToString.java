package tools.vitruvius.domains.jml.language.generator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.impl.Serializer;

import com.google.inject.Guice;
import com.google.inject.Injector;

import tools.vitruvius.domains.jml.language.JMLRuntimeModule;

/**
 * Serializer for JML objects.
 */
public class JMLToString {

    private static final Injector INJECTOR = Guice.createInjector(new JMLRuntimeModule());

    /**
     * Converts the given object to its concrete syntax representation.
     * 
     * @param eobj
     *            JML object, which shall be converted to concrete syntax.
     * @return The concrete syntax of the object or a generic identifier string in case of an error.
     */
    public static String valueOf(EObject eobj) {
        if (eobj == null) {
            return "null";
        }
        try {
            return INJECTOR.getInstance(Serializer.class).serialize(eobj);
        } catch (Exception ex) { // fall back:
            return eobj.getClass().getSimpleName() + '@' + eobj.hashCode();
        }
    }

}
