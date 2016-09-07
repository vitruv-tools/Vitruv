package tools.vitruvius.applications.pcmjava.reconstructionintegration.repository;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruvius.framework.change.description.VitruviusChangeFactory;
import tools.vitruvius.framework.change.processing.impl.Change2CommandTransformingProvidingImpl;
import tools.vitruvius.applications.pcmjava.util.PCMJavaRepositoryCreationUtil;
import tools.vitruvius.domains.pcm.util.RepositoryModelLoader;
import tools.vitruvius.extensions.constructionsimulation.traversal.ITraversalStrategy;
import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.metarepository.MetaRepositoryImpl;
import tools.vitruvius.framework.modelsynchronization.ChangeSynchronizerImpl;
import tools.vitruvius.framework.vsum.VSUMImpl;

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
