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

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
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
        EList<Change> changes = null;

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
        final EMFModelChange repository = (EMFModelChange) changes.get(0);
        final CreateRootEObject<EObject> repoChange = (CreateRootEObject<EObject>) repository.getEChange();
        String id = ((Repository) repoChange.getNewValue()).getId();
        assertTrue(repo.getId().equals(id));

        // 2: Composite DataType
        final EMFModelChange compositeDataType = (EMFModelChange) changes.get(1);
        final CreateNonRootEObjectInList<EObject> compositeDataChange = (CreateNonRootEObjectInList<EObject>) compositeDataType
                .getEChange();
        id = ((CompositeDataType) compositeDataChange.getNewValue()).getId();
        assertTrue(compType.getId().equals(id));

        // 3: Collection DataType
        final EMFModelChange collectionDataType = (EMFModelChange) changes.get(2);
        final CreateNonRootEObjectInList<EObject> collectionDataTypeChange = (CreateNonRootEObjectInList<EObject>) collectionDataType
                .getEChange();
        id = ((CollectionDataType) collectionDataTypeChange.getNewValue()).getId();
        assertTrue(collType.getId().equals(id));

        // 4: BasicComponent
        final EMFModelChange basicComponent = (EMFModelChange) changes.get(3);
        final CreateNonRootEObjectInList<EObject> basicComponentChange = (CreateNonRootEObjectInList<EObject>) basicComponent
                .getEChange();
        id = ((BasicComponent) basicComponentChange.getNewValue()).getId();
        assertTrue(basicComp.getId().equals(id));

        // 5: Interfaces (4x)
        final List<Change> interfaceChanges = changes.subList(4, 7);
        for (int i = 0; i < interfaceChanges.size(); i++) {
            final CreateNonRootEObjectInList<EObject> InterfaceChange = (CreateNonRootEObjectInList<EObject>) ((EMFModelChange) interfaceChanges
                    .get(0)).getEChange();
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
        List<Change> atomicChanges = providesComponentChange.getChanges();
        // At least 2 atomic changes must be aggregated (component +
        // providesRoles)
        assertTrue(atomicChanges.size() >= 2);
        // first must be the component, second the role
        final ProvidesComponentType provComponent = (ProvidesComponentType) ((CreateNonRootEObjectInList<EObject>) ((EMFModelChange) atomicChanges
                .get(0)).getEChange()).getNewValue();
        final OperationProvidedRole provRole = (OperationProvidedRole) ((CreateNonRootEObjectInList<EObject>) ((EMFModelChange) atomicChanges
                .get(1)).getEChange()).getNewValue();
        assertEquals("_mIhV8GQ6EeSiO4MX-GG07A", provComponent.getId());
        assertEquals("_sqDJQGQ6EeSiO4MX-GG07A", provRole.getId());

        // 7: CompleteComponent + Roles
        final CompositeChange completeComponentChange = (CompositeChange) changes.get(9);
        atomicChanges = completeComponentChange.getChanges();
        // At least 2 atomic changes must be aggregated (component +
        // providesRoles / requiredRoles)
        assertTrue(atomicChanges.size() >= 2);
        // first must be the completeComponent
        final CompleteComponentType compComp = (CompleteComponentType) ((CreateNonRootEObjectInList<EObject>) ((EMFModelChange) atomicChanges
                .get(0)).getEChange()).getNewValue();
        assertEquals("_tyXcMGQ6EeSiO4MX-GG07A", compComp.getId());

        // 8: remaining roles of the basic component (2x)
        final EMFModelChange basicRole1Change = (EMFModelChange) changes.get(10);
        final EMFModelChange basicRole2Change = (EMFModelChange) changes.get(11);
        final OperationProvidedRole basicRole1 = (OperationProvidedRole) ((CreateNonRootEObjectInList<EObject>) basicRole1Change
                .getEChange()).getNewValue();
        final OperationRequiredRole basicRole2 = (OperationRequiredRole) ((CreateNonRootEObjectInList<EObject>) basicRole2Change
                .getEChange()).getNewValue();
        assertEquals("_hWo7UGQ6EeSiO4MX-GG07A", basicRole1.getId());
        assertEquals("_f6_csGQ6EeSiO4MX-GG07A", basicRole2.getId());

        // 9: Signature + Parameter
        final EMFModelChange signatureChange = (EMFModelChange) changes.get(12);
        final EMFModelChange parameterChange = (EMFModelChange) changes.get(13);
        final Signature signature = (Signature) ((CreateNonRootEObjectInList<EObject>) signatureChange.getEChange())
                .getNewValue();
        final Parameter parameter = (Parameter) ((CreateNonRootEObjectInList<EObject>) parameterChange.getEChange())
                .getNewValue();
        assertEquals("_xX5_gGQ6EeSiO4MX-GG07A", signature.getId());
        assertEquals("IntParam", parameter.getParameterName());

        // 10: InnerDeclaration
        final EMFModelChange innerDecChange = (EMFModelChange) changes.get(14);
        final InnerDeclaration innerDeclaration = (InnerDeclaration) ((CreateNonRootEObjectInList<EObject>) innerDecChange
                .getEChange()).getNewValue();
        assertEquals("InnerType", innerDeclaration.getEntityName());

    }

}
