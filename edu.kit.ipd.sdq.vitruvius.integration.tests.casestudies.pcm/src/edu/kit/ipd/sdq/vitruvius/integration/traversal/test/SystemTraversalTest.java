package edu.kit.ipd.sdq.vitruvius.integration.traversal.test;

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

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.traversal.composite.SystemTraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.ITraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.util.RepositoryModelLoader;

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
    @Test
    public void systemTraversalTest() {

        // traverse model and get a list of changes
        final System system = (System) testmodel.getContents().get(0);
        final ITraversalStrategy<System> repoTraversal = new SystemTraversalStrategy();

        EList<Change> changes = null;
        final URI uri = URI.createPlatformResourceURI(path, true);

        try {
            changes = repoTraversal.traverse(system, uri, null);
        } catch (final UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        EMFModelChange basicChange = null;
        CompositeChange compChange = null;

        System changeSystem = null;
        AssemblyContext assemblyContext = null;
        ProvidedRole provRole = null;
        RequiredRole reqRole = null;
        Connector connector = null;

        basicChange = (EMFModelChange) changes.get(0);
        changeSystem = (System) ((CreateRootEObject<EObject>) basicChange.getEChange()).getNewValue();
        assertEquals("_aER8QKYKEeS1R4FUSvxJvA", changeSystem.getId());

        // AssemblyContexts (2x)
        basicChange = (EMFModelChange) changes.get(1);
        assemblyContext = (AssemblyContext) ((CreateNonRootEObjectInList<EObject>) basicChange.getEChange())
                .getNewValue();
        assertEquals("_s8ta0KYKEeS1R4FUSvxJvA", assemblyContext.getId());

        basicChange = (EMFModelChange) changes.get(2);
        assemblyContext = (AssemblyContext) ((CreateNonRootEObjectInList<EObject>) basicChange.getEChange())
                .getNewValue();
        assertEquals("_ufjGkKYKEeS1R4FUSvxJvA", assemblyContext.getId());

        // CompositeChange: provided role + delegation
        compChange = (CompositeChange) changes.get(3);
        basicChange = (EMFModelChange) compChange.getChanges().get(0);
        provRole = (ProvidedRole) ((CreateNonRootEObjectInList<EObject>) basicChange.getEChange()).getNewValue();
        assertEquals("_zTNC0KYKEeS1R4FUSvxJvA", provRole.getId());

        basicChange = (EMFModelChange) compChange.getChanges().get(1);
        connector = (Connector) ((CreateNonRootEObjectInList<EObject>) basicChange.getEChange()).getNewValue();
        assertEquals("_5V0FgKYKEeS1R4FUSvxJvA", connector.getId());

        // CompositeChange: required role + delegation
        compChange = (CompositeChange) changes.get(4);
        basicChange = (EMFModelChange) compChange.getChanges().get(0);
        reqRole = (RequiredRole) ((CreateNonRootEObjectInList<EObject>) basicChange.getEChange()).getNewValue();
        assertEquals("_04atIKYKEeS1R4FUSvxJvA", reqRole.getId());

        basicChange = (EMFModelChange) compChange.getChanges().get(1);
        connector = (Connector) ((CreateNonRootEObjectInList<EObject>) basicChange.getEChange()).getNewValue();
        assertEquals("_8WTbAKYKEeS1R4FUSvxJvA", connector.getId());

        // Assembly Connector
        basicChange = (EMFModelChange) changes.get(5);
        connector = (Connector) ((CreateNonRootEObjectInList<EObject>) basicChange.getEChange()).getNewValue();
        assertEquals("_9MuFoKYKEeS1R4FUSvxJvA", connector.getId());

        return;
    }

}
