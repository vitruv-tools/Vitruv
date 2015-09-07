package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.pcmjamoppenforcer;

import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.PCMtoJaMoPPRenameInvariantEnforcer;

/**
 * The Class PCMtoJaMoPPSpecialChars.
 */
public class PCMtoJaMoPPSpecialChars extends PCMtoJaMoPPRenameInvariantEnforcer {

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.PCMJaMoPPEnforcer.
     * PCMtoJaMoPPRenameInvariantEnforcer#checkForNameConflict(java.lang.String)
     */
    @Override
    protected boolean checkForNameConflict(final String a) {

        for (int i = 1; i < a.length(); i++) {
            if (!Character.isJavaIdentifierPart(a.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.PCMJaMoPPEnforcer.
     * PCMtoJaMoPPRenameInvariantEnforcer#renameElement(java.lang.String)
     */
    @Override
    protected String renameElement(final String element) {

        logger.info("Detected Special Character: " + element);
        final StringBuilder sb = new StringBuilder();
        sb.append(element.charAt(0)); // PreRequisite: BeginChar already done
        for (int i = 1; i < element.length(); i++) {
            if (!Character.isJavaIdentifierPart(element.charAt(i))) {
                sb.append("_" + this.renameCtr);

            } else {
                sb.append(element.charAt(i));
            }
        }
        this.renameCtr++;
        return sb.toString();
    }

}
