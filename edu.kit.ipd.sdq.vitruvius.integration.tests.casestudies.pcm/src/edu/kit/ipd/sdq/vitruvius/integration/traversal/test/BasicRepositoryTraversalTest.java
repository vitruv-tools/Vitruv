package edu.kit.ipd.sdq.vitruvius.integration.traversal.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.Signature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.root.InsertRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.ConcreteChange;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.traversal.repository.RepositoryTraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.ITraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.util.RepositoryModelLoader;

/**
 * This test case will be executed with a model which contains all basic elements from the
 * repository package (DataTypes, BasicComponents, Interfaces, Signatures, Parameters, Prov/Req
 * Roles).
 *
 * @author Sven Leonhardt, Benjamin Hettwer
 */

public class BasicRepositoryTraversalTest {

    private static Resource testmodel;
    private static final String path = "JUnitTestmodels/basicTestcaseModel.repository";

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
     * Basic traversal test.
     */
    @Test
    public void basicTraversalTest() {

        // traverse model and get a list of changes
        final Repository repo = (Repository) testmodel.getContents().get(0);
        final ITraversalStrategy<Repository> traversal = new RepositoryTraversalStrategy();
        EList<VitruviusChange> changes = null;

        try {
            changes = traversal.traverse(repo, URI.createPlatformResourceURI(path, true), null);
        } catch (final UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // get model elements
        final EList<DataType> dataTypes = repo.getDataTypes__Repository();
        final CollectionDataType collType = (CollectionDataType) dataTypes.get(0);
        final CompositeDataType compType = (CompositeDataType) dataTypes.get(1);
        final BasicComponent basicComp = (BasicComponent) repo.getComponents__Repository().get(0);
        final EList<Interface> interfaces = repo.getInterfaces__Repository();

        // first element of change list must be the root
        final ConcreteChange repository = (ConcreteChange) changes.get(0);
        final InsertRootEObject<EObject> repoChange = (InsertRootEObject<EObject>) repository.getEChanges().get(0);
        String id = ((Repository) repoChange.getNewValue()).getId();
        assertTrue(repo.getId().equals(id));

        // 2: Composite DataType
        final ConcreteChange compositeDataType = (ConcreteChange) changes.get(1);
        final InsertEReference<EObject, EObject> compositeDataChange = (InsertEReference<EObject, EObject>) compositeDataType
                .getEChanges().get(0);
        id = ((CompositeDataType) compositeDataChange.getNewValue()).getId();
        assertTrue(compType.getId().equals(id));

        // 3: Collection DataType
        final ConcreteChange collectionDataType = (ConcreteChange) changes.get(2);
        final InsertEReference<EObject, EObject> collectionDataTypeChange = (InsertEReference<EObject, EObject>) collectionDataType
                .getEChanges().get(0);
        id = ((CollectionDataType) collectionDataTypeChange.getNewValue()).getId();
        assertTrue(collType.getId().equals(id));

        // 4: BasicComponent
        final ConcreteChange basicComponent = (ConcreteChange) changes.get(3);
        final InsertEReference<EObject, EObject> basicComponentChange = (InsertEReference<EObject, EObject>) basicComponent
                .getEChanges().get(0);
        id = ((BasicComponent) basicComponentChange.getNewValue()).getId();
        assertTrue(basicComp.getId().equals(id));

        // 5: Interfaces (4x)
        final List<VitruviusChange> interfaceChanges = changes.subList(4, 7);
        for (int i = 0; i < interfaceChanges.size(); i++) {
            final InsertEReference<EObject, EObject> InterfaceChange = (InsertEReference<EObject, EObject>) ((ConcreteChange) interfaceChanges
                    .get(0)).getEChanges().get(0);
            final Interface inter = (Interface) InterfaceChange.getNewValue();
            // first must be top interface
            if (i == 0) {
                assertTrue(inter.getParentInterfaces__Interface().isEmpty());
            }
            // All interface changes must be in interface list of the model
            assertTrue(interfaces.contains(inter));
        }

        // 6: ProvidesComponent + Roles
        final CompositeChange providesComponentChange = (CompositeChange) changes.get(8);
        List<VitruviusChange> atomicChanges = providesComponentChange.getChanges();
        // At least 2 atomic changes must be aggregated (component +
        // providesRoles)
        assertTrue(atomicChanges.size() >= 2);
        // first must be the component, second the role
        final ProvidesComponentType provComponent = (ProvidesComponentType) ((InsertEReference<EObject, EObject>) ((ConcreteChange) atomicChanges
                .get(0)).getEChanges().get(0)).getNewValue();
        final OperationProvidedRole provRole = (OperationProvidedRole) ((InsertEReference<EObject, EObject>) ((ConcreteChange) atomicChanges
                .get(1)).getEChanges().get(0)).getNewValue();
        assertEquals("_mIhV8GQ6EeSiO4MX-GG07A", provComponent.getId());
        assertEquals("_sqDJQGQ6EeSiO4MX-GG07A", provRole.getId());

        // 7: CompleteComponent + Roles
        final CompositeChange completeComponentChange = (CompositeChange) changes.get(9);
        atomicChanges = completeComponentChange.getChanges();
        // At least 2 atomic changes must be aggregated (component +
        // providesRoles / requiredRoles)
        assertTrue(atomicChanges.size() >= 2);
        // first must be the completeComponent
        final CompleteComponentType compComp = (CompleteComponentType) ((InsertEReference<EObject, EObject>) ((ConcreteChange) atomicChanges
                .get(0)).getEChanges().get(0)).getNewValue();
        assertEquals("_tyXcMGQ6EeSiO4MX-GG07A", compComp.getId());

        // 8: remaining roles of the basic component (2x)
        final ConcreteChange basicRole1Change = (ConcreteChange) changes.get(10);
        final ConcreteChange basicRole2Change = (ConcreteChange) changes.get(11);
        final OperationProvidedRole basicRole1 = (OperationProvidedRole) ((InsertEReference<EObject, EObject>) basicRole1Change
                .getEChanges().get(0)).getNewValue();
        final OperationRequiredRole basicRole2 = (OperationRequiredRole) ((InsertEReference<EObject, EObject>) basicRole2Change
                .getEChanges().get(0)).getNewValue();
        assertEquals("_hWo7UGQ6EeSiO4MX-GG07A", basicRole1.getId());
        assertEquals("_f6_csGQ6EeSiO4MX-GG07A", basicRole2.getId());

        // 9: Signature + Parameter
        final ConcreteChange signatureChange = (ConcreteChange) changes.get(12);
        final ConcreteChange parameterChange = (ConcreteChange) changes.get(13);
        final Signature signature = (Signature) ((InsertEReference<EObject, EObject>) signatureChange.getEChanges().get(0))
                .getNewValue();
        final Parameter parameter = (Parameter) ((InsertEReference<EObject, EObject>) parameterChange.getEChanges().get(0))
                .getNewValue();
        assertEquals("_xX5_gGQ6EeSiO4MX-GG07A", signature.getId());
        assertEquals("IntParam", parameter.getParameterName());

        // 10: InnerDeclaration
        final ConcreteChange innerDecChange = (ConcreteChange) changes.get(14);
        final InnerDeclaration innerDeclaration = (InnerDeclaration) ((InsertEReference<EObject, EObject>) innerDecChange
                .getEChanges().get(0)).getNewValue();
        assertEquals("InnerType", innerDeclaration.getEntityName());

    }

}
