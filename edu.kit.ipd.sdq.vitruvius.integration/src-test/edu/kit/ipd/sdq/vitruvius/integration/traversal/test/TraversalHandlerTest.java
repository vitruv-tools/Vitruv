package edu.kit.ipd.sdq.vitruvius.integration.traversal.test;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine.EMFModelPropagationEngineImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.synctransprovider.TransformationExecutingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.ITraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.repository.RepositoryTraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.util.RepositoryModelLoader;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

 
/**
 * The Class TraversalHandlerTest.
 */
public class TraversalHandlerTest {

    private VSUMImpl vsum;
    private SyncManagerImpl syncManager;

    private CorrespondenceInstance correspondenceInstance;
    protected ResourceSet resourceSet;

    /**
     * Sets the up all tests.
     */
    @BeforeClass
    public static void setUpAllTests() {
        TestUtil.initializeLogger();
    }

    /**
     * Sets the up test.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUpTest() throws Exception {

        final MetaRepositoryImpl metaRepository = PCMJavaUtils.createPCMJavaMetarepository();
        final VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository, metaRepository);
        final TransformationExecutingProvidingImpl syncTransformationProvider = new TransformationExecutingProvidingImpl();
        final EMFModelPropagationEngineImpl propagatingChange = new EMFModelPropagationEngineImpl(
                syncTransformationProvider);
        this.syncManager = new SyncManagerImpl(this.vsum, propagatingChange, this.vsum, metaRepository, this.vsum, null);
        this.resourceSet = new ResourceSetImpl();

    }

    /**
     * Test integration handler.
     */
    @Test
    public void testIntegrationHandler() {

        final String path = "JUnitTestmodels/basicTestcaseModel.repository";

        // load model
        final Resource r = RepositoryModelLoader.loadPCMResource(path);

        // traverse model and get ordered list of changes
        final Repository repo = (Repository) r.getContents().get(0);
        final ITraversalStrategy<Repository> traversal = new RepositoryTraversalStrategy();
        EList<Change> changes = null;

        try {
            changes = traversal.traverse(repo, URI.createPlatformResourceURI(path, true), null);
        } catch (final UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.syncManager.synchronizeChanges(changes);

    }

}
