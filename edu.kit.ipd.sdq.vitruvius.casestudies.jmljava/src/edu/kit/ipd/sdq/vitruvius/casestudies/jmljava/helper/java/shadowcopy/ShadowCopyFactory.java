package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

/**
 * Factory for a {@link ShadowCopy}. This interface can be used in conjunction with Google Guice.
 */
public interface ShadowCopyFactory {

    /**
     * Creates a new instance of {@link ShadowCopy}.
     * 
     * @param ci
     *            The correspondence instance to use for the initialization.
     * @return The new instance.
     */
    ShadowCopy create(CorrespondenceInstance ci);

    /**
     * Creates a new instance of {@link ShadowCopy}.
     * 
     * @param ci
     *            The correspondence instance to use for the initialization.
     * @param useJMLCopy
     *            for copying JML models too.
     * @return The new instance.
     */
    ShadowCopy create(CorrespondenceInstance ci, boolean useJMLCopy);

}
