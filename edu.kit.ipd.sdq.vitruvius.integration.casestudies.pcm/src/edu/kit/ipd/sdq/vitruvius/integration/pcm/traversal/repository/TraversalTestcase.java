package edu.kit.ipd.sdq.vitruvius.integration.pcm.traversal.repository;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.Repository;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils;
import edu.kit.ipd.sdq.vitruvius.commandexecutor.CommandExecutingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.VitruviusChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.changes.changepreparer.ChangePreparingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.changesynchronizer.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.ITraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.util.RepositoryModelLoader;

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
        final MetaRepositoryImpl metaRepository = PCMJavaUtils.createPCMJavaMetarepository();
        final VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository);

        final Change2CommandTransformingProvidingImpl change2CommandTransformingProviding = new Change2CommandTransformingProvidingImpl();
        final ChangePreparing changePreparing = new ChangePreparingImpl(vsum);
        final CommandExecuting commandExecuting = new CommandExecutingImpl();

        final ChangeSynchronizerImpl changeSynchronizing = new ChangeSynchronizerImpl(vsum,
                change2CommandTransformingProviding, vsum, null, changePreparing,
                commandExecuting);
        
        final VitruviusChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
        // propagate changes
        changeSynchronizing.synchronizeChange(compositeChange);

        logger.info("Integration done");

    }
}
