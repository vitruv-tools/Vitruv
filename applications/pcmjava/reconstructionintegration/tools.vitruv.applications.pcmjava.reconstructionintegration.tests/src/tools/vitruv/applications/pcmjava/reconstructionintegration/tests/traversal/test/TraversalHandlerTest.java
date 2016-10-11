package tools.vitruv.applications.pcmjava.reconstructionintegration.tests.traversal.test;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.framework.change.description.VitruviusChangeFactory;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.applications.pcmjava.reconstructionintegration.repository.RepositoryTraversalStrategy;
import tools.vitruv.applications.pcmjava.util.PCMJavaRepositoryCreationUtil;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import tools.vitruv.domains.pcm.util.RepositoryModelLoader;
import tools.vitruv.extensions.constructionsimulation.traversal.ITraversalStrategy;

/**
 * The Class TraversalHandlerTest.
 */
public class TraversalHandlerTest {

    private InternalVirtualModel vsum;

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

        final Iterable<Metamodel> metamodels = PCMJavaRepositoryCreationUtil.createPcmJamoppMetamodels();
        // TODO Use the correct change2command transformings here
//        final Change2CommandTransformingProviding change2CommandTransformingProviding = new Change2CommandTransformingProvidingImpl();
        this.vsum = TestUtil.createVSUM(metamodels);
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
        this.vsum.propagateChange(compositeChange);

    }

}
