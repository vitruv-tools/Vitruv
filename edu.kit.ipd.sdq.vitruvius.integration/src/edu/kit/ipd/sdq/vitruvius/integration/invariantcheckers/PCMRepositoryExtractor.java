package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers;

import org.eclipse.emf.ecore.resource.Resource;

import org.palladiosimulator.pcm.repository.Repository;

/**
 * returns Repository-"Root"
 *
 * @author Johannes Hoor
 */
public class PCMRepositoryExtractor implements IMModelImplExtractor<Repository> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.IMModelImplExtractor#getImpl(org.eclipse
     * .emf.ecore.resource.Resource)
     */
    @Override
    public Repository getImpl(final Resource model) {

        final Repository repo = (Repository) model.getContents().get(0);

        return repo;
    }

}
