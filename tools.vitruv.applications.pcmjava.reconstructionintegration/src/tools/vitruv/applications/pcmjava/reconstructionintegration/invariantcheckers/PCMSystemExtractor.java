package tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers;

import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.extensions.constructionsimulation.invariantcheckers.IMModelImplExtractor;

/**
 * returns System-"Root".
 *
 * @author Sven Leonhardt
 */

public class PCMSystemExtractor implements IMModelImplExtractor<System> {

    /*
     * (non-Javadoc)
     *
     * @see tools.vitruv.integration.invariantChecker.IMModelImplExtractor#getImpl(org.
     * eclipse .emf.ecore.resource.Resource)
     */
    @Override
    public System getImpl(final Resource model) {

        final System system = (System) model.getContents().get(0);

        return system;

    }

}
