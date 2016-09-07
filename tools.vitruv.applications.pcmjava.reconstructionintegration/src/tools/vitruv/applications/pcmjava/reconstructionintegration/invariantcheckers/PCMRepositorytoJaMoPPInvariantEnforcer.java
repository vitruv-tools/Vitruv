package tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers;

import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.extensions.constructionsimulation.invariantcheckers.StandardInvariantEnforcer;

/**
 * base class for all PCMRepositorytoJaMoPP-Invar-Enforcer.
 *
 * QVTo-based enforcers also inherit from this class.
 *
 * @author Johannes Hoor
 */
public abstract class PCMRepositorytoJaMoPPInvariantEnforcer extends StandardInvariantEnforcer<Repository>
        implements IJaMoPPInvariant {

    /**
     * Instantiates a new PCM repositoryto ja mo pp invariant enforcer.
     */
    public PCMRepositorytoJaMoPPInvariantEnforcer() {
        this.extractor = new PCMRepositoryExtractor();

    }

    // TODO maybe add functionality to serialize the model (for debugging purposes)
    /*
     * (non-Javadoc)
     *
     * @see
     * tools.vitruv.integration.invariantChecker.FixedInvariantEnforcer#returnModel()
     */
    @Override
    public Resource returnModel() {

        return this.model;

    }

}
