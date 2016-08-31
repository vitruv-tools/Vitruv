package edu.kit.ipd.sdq.vitruvius.integration.traversal.test;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.Repository;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.traversal.repository.RepositoryTraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.ITraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.util.RepositoryModelLoader;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

/**
 * The Class TraversalHandlerTest.
 */
public class TraversalHandlerTest {

    private VSUMImpl vsum;
    private ChangeSynchronizing changeSynchronizing;

    private CorrespondenceModel correspondenceModel;
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
        this.vsum = new VSUMImpl(metaRepository, metaRepository);
        final Change2CommandTransformingProviding change2CommandTransformingProviding = new Change2CommandTransformingProvidingImpl();
        this.changeSynchronizing = new ChangeSynchronizerImpl(this.vsum, change2CommandTransformingProviding, this.vsum, null);
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
        EList<VitruviusChange> changes = null;

        try {
            changes = traversal.traverse(repo, URI.createPlatformResourceURI(path, true), null);
        } catch (final UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        VitruviusChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
        this.changeSynchronizing.synchronizeChange(compositeChange);

    }

}
