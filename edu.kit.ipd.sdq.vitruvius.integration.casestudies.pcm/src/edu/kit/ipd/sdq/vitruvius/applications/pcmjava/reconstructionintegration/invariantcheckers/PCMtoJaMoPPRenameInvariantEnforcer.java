package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers;

import org.apache.log4j.Logger;

/**
 * Base class for all simple "rename invariant enforcers". Relies on an underlying traversal
 * strategy for different model types.
 *
 * @author johannes hoor
 *
 */
public abstract class PCMtoJaMoPPRenameInvariantEnforcer {
    protected int renameCtr;
    protected Logger logger = Logger.getRootLogger();

    /**
     * Check for name conflict.
     *
     * @param a
     *            the a
     * @return true, if successful
     */
    protected abstract boolean checkForNameConflict(String a);

    /**
     * Rename element.
     *
     * @param element
     *            the element
     * @return the string
     */
    protected abstract String renameElement(String element);
}
