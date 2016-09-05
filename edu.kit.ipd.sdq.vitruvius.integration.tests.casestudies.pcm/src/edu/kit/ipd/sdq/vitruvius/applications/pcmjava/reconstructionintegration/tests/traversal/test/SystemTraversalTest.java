package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.tests.traversal.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.system.System;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.root.InsertRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.ConcreteChange;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.composite.SystemTraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.RepositoryModelLoader;
import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.traversal.ITraversalStrategy;

/**
 * The Class SystemTraversalTest.
 */
public class SystemTraversalTest {

    private static Resource testmodel;
    private static final String path = "JUnitTestmodels/systemTestcaseModel.system";

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        testmodel = RepositoryModelLoader.loadPCMResource(path);
    }

    /**
     * Tear down after class.
     *
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        testmodel = null;
    }

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * System traversal test.
     */
    @SuppressWarnings("unchecked")
	@Test
    public void systemTraversalTest() {

        // traverse model and get a list of changes
        final System system = (System) testmodel.getContents().get(0);
        final ITraversalStrategy<System> repoTraversal = new SystemTraversalStrategy();

        EList<VitruviusChange> changes = null;
        final URI uri = URI.createPlatformResourceURI(path, true);

        try {
            changes = repoTraversal.traverse(system, uri, null);
        } catch (final UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ConcreteChange basicChange = null;
        CompositeChange compChange = null;

        System changeSystem = null;
        AssemblyContext assemblyContext = null;
        ProvidedRole provRole = null;
        RequiredRole reqRole = null;
        Connector connector = null;

        basicChange = (ConcreteChange) changes.get(0);
        changeSystem = (System) ((InsertRootEObject<EObject>) basicChange.getEChanges().get(0)).getNewValue();
        assertEquals("_aER8QKYKEeS1R4FUSvxJvA", changeSystem.getId());

        // AssemblyContexts (2x)
        basicChange = (ConcreteChange) changes.get(1);
        assemblyContext = (AssemblyContext) ((InsertEReference<EObject, EObject>) basicChange.getEChanges().get(0))
                .getNewValue();
        assertEquals("_s8ta0KYKEeS1R4FUSvxJvA", assemblyContext.getId());

        basicChange = (ConcreteChange) changes.get(2);
        assemblyContext = (AssemblyContext) ((InsertEReference<EObject, EObject>) basicChange.getEChanges().get(0))
                .getNewValue();
        assertEquals("_ufjGkKYKEeS1R4FUSvxJvA", assemblyContext.getId());

        // CompositeChange: provided role + delegation
        compChange = (CompositeChange) changes.get(3);
        basicChange = (ConcreteChange) compChange.getChanges().get(0);
        provRole = (ProvidedRole) ((InsertEReference<EObject, EObject>) basicChange.getEChanges().get(0)).getNewValue();
        assertEquals("_zTNC0KYKEeS1R4FUSvxJvA", provRole.getId());

        basicChange = (ConcreteChange) compChange.getChanges().get(1);
        connector = (Connector) ((InsertEReference<EObject, EObject>) basicChange.getEChanges().get(0)).getNewValue();
        assertEquals("_5V0FgKYKEeS1R4FUSvxJvA", connector.getId());

        // CompositeChange: required role + delegation
        compChange = (CompositeChange) changes.get(4);
        basicChange = (ConcreteChange) compChange.getChanges().get(0);
        reqRole = (RequiredRole) ((InsertEReference<EObject, EObject>) basicChange.getEChanges().get(0)).getNewValue();
        assertEquals("_04atIKYKEeS1R4FUSvxJvA", reqRole.getId());

        basicChange = (ConcreteChange) compChange.getChanges().get(1);
        connector = (Connector) ((InsertEReference<EObject, EObject>) basicChange.getEChanges().get(0)).getNewValue();
        assertEquals("_8WTbAKYKEeS1R4FUSvxJvA", connector.getId());

        // Assembly Connector
        basicChange = (ConcreteChange) changes.get(5);
        connector = (Connector) ((InsertEReference<EObject, EObject>) basicChange.getEChanges().get(0)).getNewValue();
        assertEquals("_9MuFoKYKEeS1R4FUSvxJvA", connector.getId());

        return;
    }

}
