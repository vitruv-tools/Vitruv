package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer;

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers.PCMtoJaMoPPRenameInvariantEnforcer;

/**
 * Solves conflict: PCM elements have Java Keywords as identifier.
 *
 * @author Johannes Hoor
 *
 */
public class PCMtoJaMoPPVitruviusKeywords extends PCMtoJaMoPPRenameInvariantEnforcer {

    private final String[] vitruviusKeywords = { "contract", "datatypes" };

    /**
     * Simple String comparison.
     *
     * @param a
     *            Str
     * @param b
     *            Str
     * @return t/f
     */
    private boolean checkNameIdentity(final String a, final String b) {
        return a.equals(b);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.PCMJaMoPPEnforcer.
     * PCMtoJaMoPPRenameInvariantEnforcer#checkForNameConflict(java.lang.String)
     */
    @Override
    protected boolean checkForNameConflict(final String a) {
        for (final String keyword : this.vitruviusKeywords) {
            if (this.checkNameIdentity(a, keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Rename Strings with specific settings (unique rename).
     *
     * @param element
     *            the element
     * @return renamed String
     */
    @Override
    protected String renameElement(final String element) {

        logger.info("Detected Vitruvius Keyword: " + element);
        final String newElementName = "RN" + this.renameCtr + "_" + element;

        this.renameCtr++;
        return newElementName;
    }

}
