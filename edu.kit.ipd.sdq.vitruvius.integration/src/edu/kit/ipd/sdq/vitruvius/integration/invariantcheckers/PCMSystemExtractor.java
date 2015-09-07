package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers;

import org.eclipse.emf.ecore.resource.Resource;

import de.uka.ipd.sdq.pcm.system.System;

/**
 * returns System-"Root".
 *
 * @author Sven Leonhardt
 */

public class PCMSystemExtractor implements IMModelImplExtractor<System> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.IMModelImplExtractor#getImpl(org.eclipse
     * .emf.ecore.resource.Resource)
     */
    @Override
    public System getImpl(final Resource model) {

        final System system = (System) model.getContents().get(0);

        return system;

    }

}
