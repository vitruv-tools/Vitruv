package tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer;

import org.eclipse.emf.ecore.EObject;

 
/**
 * This class provides basic tracability for any name change to pcm elements. This is needed for
 * renaming roles in components correctly. Needed in PCMtoJaMoPPNameConflicts.
 *
 * @author Johannes Hoor
 *
 * @param <T>
 *            the class of the PCM element
 */
public class PCMNameChangeTrace<T extends EObject> {
    private T target;
    private String previousName;
    private String currentName;

    /**
     * Instantiates a new PCM name change trace.
     *
     * @param object
     *            the object
     */
    public PCMNameChangeTrace(final T object) {
        this.target = object;
    }

    /**
     * Instantiates a new PCM name change trace.
     *
     * @param object
     *            the object
     * @param previousName
     *            the previous name
     */
    public PCMNameChangeTrace(final T object, final String previousName) {
        this(object);
        this.previousName = previousName;
    }

    /**
     * Gets the target.
     *
     * @return the target
     */
    public T getTarget() {
        return this.target;
    }

    /**
     * Sets the target.
     *
     * @param target
     *            the new target
     */
    public void setTarget(final T target) {
        this.target = target;
    }

    /**
     * Gets the current name.
     *
     * @return the current name
     */
    public String getCurrentName() {
        return this.currentName;
    }

    /**
     * Sets the current name.
     *
     * @param currentName
     *            the new current name
     */
    public void setCurrentName(final String currentName) {
        this.currentName = currentName;
    }

    /**
     * Gets the previous name.
     *
     * @return the previous name
     */
    public String getPreviousName() {
        return this.previousName;
    }

    /**
     * Sets the previous name.
     *
     * @param previousName
     *            the new previous name
     */
    public void setPreviousName(final String previousName) {
        this.previousName = previousName;
    }

}
