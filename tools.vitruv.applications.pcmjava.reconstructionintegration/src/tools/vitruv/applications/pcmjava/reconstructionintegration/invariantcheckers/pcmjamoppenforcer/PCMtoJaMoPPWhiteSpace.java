package tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer;

import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.PCMtoJaMoPPRenameInvariantEnforcer;

/**
 * The Class PCMtoJaMoPPWhiteSpace.
 */
public class PCMtoJaMoPPWhiteSpace extends PCMtoJaMoPPRenameInvariantEnforcer {

    /*
     * (non-Javadoc)
     * 
     * @see tools.vitruv.integration.invariantChecker.PCMJaMoPPEnforcer.
     * PCMtoJaMoPPRenameInvariantEnforcer#checkForNameConflict(java.lang.String)
     */
    @Override
    protected boolean checkForNameConflict(final String a) {
        if (a.contains(" ")) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see tools.vitruv.integration.invariantChecker.PCMJaMoPPEnforcer.
     * PCMtoJaMoPPRenameInvariantEnforcer#renameElement(java.lang.String)
     */
    @Override
    protected String renameElement(final String element) {

        logger.info("Detected Whitespace: " + element);
        return element.replace(" ", "_");
    }

}
