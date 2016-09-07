package tools.vitruvius.applications.jmljava.helper.java.shadowcopy;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

/**
 * A shadow copy is a clone of a monitored project. All files are duplicated so changes can be
 * carried out without harm. Additionally implementors have to guarantee that there are no side
 * effects on the Vitruvius framework.
 */
public interface ShadowCopy {

    /**
     * Initializes the shadow copy with JML specifications. This is done by inserting statements
     * into the Java shadow copy, which contain all syntactically relevant expressions of the JML
     * specification. The files on the disc reflect this changes after returning from this method.
     * 
     * @param resolveAllReferences
     *            Indicates if all references shall be resolved.
     */
    public void setupShadowCopyWithJMLSpecifications(boolean resolveAllReferences);

    /**
     * Updates the original JML specifications by using the statements of the Java shadow copy.
     * 
     * @return The changed JML elements, which should be saved.
     */
    public Collection<EObject> updateOriginalJMLSpecifications();

    /**
     * @return The correspondence instance for the shadow copy.
     */
    public ShadowCopyCorrespondences getShadowCopyCorrespondences();

}
