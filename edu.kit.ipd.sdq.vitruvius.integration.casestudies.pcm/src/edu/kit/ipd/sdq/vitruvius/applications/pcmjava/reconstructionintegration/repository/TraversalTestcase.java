package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.repository;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.Repository;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.PCMJavaRepositoryCreationUtil;
import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.RepositoryModelLoader;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.traversal.ITraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

/**
 * Test class for the usage of traversal strategies.
 *
 * @author Sven Leonhardt, Benjamin Hettwer
 */

public class TraversalTestcase {

    private static Logger logger = Logger.getLogger(TraversalTestcase.class);

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {

        // load model
        final String path = "Testmodels/small_example.repository";
        // String path = "Testmodels/interface_inheritance.repository";
        final Resource r = RepositoryModelLoader.loadPCMResource(path);

        // traverse model and get ordered list of changes
        final Repository repo = (Repository) r.getContents().get(0);
        final ITraversalStrategy<Repository> traversal = new RepositoryTraversalStrategy();
        EList<VitruviusChange> changes = null;

        try {
            changes = traversal.traverse(repo, URI.createPlatformResourceURI(path, true), null);
        } catch (final UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // create syncManager
        final MetaRepositoryImpl metaRepository = PCMJavaRepositoryCreationUtil.createPCMJavaMetarepository();
        final VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository);

        final Change2CommandTransformingProvidingImpl change2CommandTransformingProviding = new Change2CommandTransformingProvidingImpl();
        final ChangeSynchronizerImpl changeSynchronizing = new ChangeSynchronizerImpl(vsum,
                change2CommandTransformingProviding, vsum, null);
        
        final VitruviusChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
        // propagate changes
        changeSynchronizing.synchronizeChange(compositeChange);

        logger.info("Integration done");

    }
}
