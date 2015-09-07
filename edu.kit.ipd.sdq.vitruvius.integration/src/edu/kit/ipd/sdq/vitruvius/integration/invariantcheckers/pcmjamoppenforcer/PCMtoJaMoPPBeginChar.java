package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.pcmjamoppenforcer;

import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.PCMtoJaMoPPRenameInvariantEnforcer;

/**
 * The Class PCMtoJaMoPPBeginChar.
 */
public class PCMtoJaMoPPBeginChar extends PCMtoJaMoPPRenameInvariantEnforcer {

    // TODO: check for all name conflicts: JLS allowed/Eclipse allowed (a vs A)
    // etc...
    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.PCMJaMoPPEnforcer.
     * PCMtoJaMoPPRenameInvariantEnforcer#checkForNameConflict(java.lang.String)
     */
    @Override
    protected boolean checkForNameConflict(final String a) {
        return !Character.isJavaIdentifierStart(a.charAt(0));

        // return !a.matches("^[a-zA-Z_$]*");
        // Character.isJavaIdentifierPart(arg0)
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.PCMJaMoPPEnforcer.
     * PCMtoJaMoPPRenameInvariantEnforcer#renameElement(java.lang.String)
     */
    @Override
    protected String renameElement(final String element) {

        logger.info("Detected Invalid First Character: " + element);

        final String ret = "RN" + this.renameCtr + "_" + element.substring(1);
        this.renameCtr++;
        return ret;
    }

}
