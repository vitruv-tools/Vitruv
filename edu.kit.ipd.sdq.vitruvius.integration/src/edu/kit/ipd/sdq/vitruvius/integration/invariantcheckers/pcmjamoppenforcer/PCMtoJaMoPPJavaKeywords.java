package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.pcmjamoppenforcer;

import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.PCMtoJaMoPPRenameInvariantEnforcer;

/**
 * Solves conflict: PCM elements have Java Keywords as identifier.
 *
 * @author Johannes Hoor
 *
 */
public class PCMtoJaMoPPJavaKeywords extends PCMtoJaMoPPRenameInvariantEnforcer {

    private final String[] javaKeywords = { "abstract", "continue", "for", "new", "switch", "assert", "default",
            "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double",
            "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum",
            "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final",
            "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float",
            "native", "super", "while",

    };

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
        for (final String keyword : this.javaKeywords) {
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

        logger.info("Detected Java Keyword: " + element);
        final String newElementName = "RN" + this.renameCtr + "_" + element;

        this.renameCtr++;
        return newElementName;
    }

}
