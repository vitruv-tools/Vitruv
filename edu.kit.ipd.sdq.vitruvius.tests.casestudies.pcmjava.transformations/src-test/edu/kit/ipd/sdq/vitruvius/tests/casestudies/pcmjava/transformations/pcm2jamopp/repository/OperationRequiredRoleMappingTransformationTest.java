package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.statements.Statement;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationRequiredRole;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.system.System;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class OperationRequiredRoleMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddOperationRequiredRole() throws Throwable, Throwable {
        final OperationRequiredRole operationRequiredRole = this
                .createAndSyncRepoBasicCompInterfaceAndOperationReqiredRole();

        this.assertOperationRequiredRole(operationRequiredRole);
    }

    @Test
    public void testAddOperationRequiredToSystem() throws Throwable {
        final System system = super.createAndSyncSystem(PCM2JaMoPPTestUtils.SYSTEM_NAME);
        final Repository repo = super.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);

        final OperationRequiredRole operationRequiredRole = this
                .createAndSyncOperationRequiredRole(opInterface, system);

        this.assertOperationRequiredRole(operationRequiredRole);
    }

    @Test
    public void testChangeOperationRequiredRole() throws Throwable, Throwable {
        final OperationRequiredRole opr = this.createAndSyncRepoBasicCompInterfaceAndOperationReqiredRole();
        final Repository repo = opr.getRequiredInterface__OperationRequiredRole().getRepository__Interface();

        final OperationInterface newInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME + PCM2JaMoPPTestUtils.RENAME);
        final BasicComponent newBasicComponent = this.addBasicComponentAndSync(repo,
                PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + PCM2JaMoPPTestUtils.RENAME);
        opr.setRequiredInterface__OperationRequiredRole(newInterface);
        opr.setRequiringEntity_RequiredRole(newBasicComponent);
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));

        this.assertOperationRequiredRole(opr);
    }

    @Test
    public void testChangeNameOfOperationRequiredRole() throws Throwable {
        final OperationRequiredRole opr = this.createAndSyncRepoBasicCompInterfaceAndOperationReqiredRole();

        opr.setEntityName("operationReqRoleNameChange");
        super.triggerSynchronization(VURI.getInstance(opr.eResource()));

        this.assertOperationRequiredRole(opr);
    }

    @Test
    public void testCangeTypeOfOperationRequiredRole() throws Throwable {
        final OperationRequiredRole opr = this.createAndSyncRepoBasicCompInterfaceAndOperationReqiredRole();
        final Repository repo = opr.getRequiredInterface__OperationRequiredRole().getRepository__Interface();

        final OperationInterface newInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME + PCM2JaMoPPTestUtils.RENAME);
        opr.setRequiredInterface__OperationRequiredRole(newInterface);
        super.triggerSynchronization(VURI.getInstance(opr.eResource()));

        this.assertOperationRequiredRole(opr);
    }

    @Test
    public void testAddOperationRequiredRoleToSystem() throws Throwable {
        final Repository repo = super.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final System system = super.createAndSyncSystem(PCM2JaMoPPTestUtils.SYSTEM_NAME);
        final OperationInterface opInterface = super.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);

        final OperationRequiredRole orr = super.createAndSyncOperationRequiredRole(opInterface, system);

        this.assertOperationRequiredRole(orr);
    }

    /**
     * ap operation required role is represented by one constructor parameter (per constructor), one
     * assignment in the constructor (per constructor) and a field with the type of the interface as
     * well as the import of the required interface in the components main class
     *
     * @param operationRequiredRole
     * @throws Throwable
     */
    private void assertOperationRequiredRole(final OperationRequiredRole operationRequiredRole) throws Throwable {
        final Set<EObject> correspondingEObjects = this.getCorrespondenceInstance().getAllCorrespondingEObjects(
                operationRequiredRole);
        int importFounds = 0;
        int constructorParameterFound = 0;
        int assignmentOperatorsFound = 0;
        int fieldsFound = 0;
        int expectedConstrucotrParameters = 0;
        for (final EObject correspondingEObj : correspondingEObjects) {
            if (correspondingEObj instanceof Import) {
                importFounds++;
            } else if (correspondingEObj instanceof org.emftext.language.java.parameters.Parameter) {
                constructorParameterFound++;
                final org.emftext.language.java.parameters.Parameter param = (org.emftext.language.java.parameters.Parameter) correspondingEObj;
                assertTrue("Corresponding parameter has wrong name",
                        param.getName().equalsIgnoreCase(operationRequiredRole.getEntityName()));

            } else if (correspondingEObj instanceof Statement) {
                assignmentOperatorsFound++;
            } else if (correspondingEObj instanceof Field) {
                fieldsFound++;
                final Field field = (Field) correspondingEObj;
                final Class jaMoPPClass = (Class) field.getContainingConcreteClassifier();
                for (final Member member : jaMoPPClass.getMembers()) {
                    if (member instanceof Constructor) {
                        expectedConstrucotrParameters++;
                    }
                }
                assertTrue("Corresponding field has wrong name",
                        field.getName().equalsIgnoreCase(operationRequiredRole.getEntityName()));

            } else {
                fail("operation required role corresponds to unexpected object: " + correspondingEObj);
            }
        }
        assertEquals("Unexpected number of imports found", 1, importFounds);
        assertEquals("Unexpected number of constructorParameters found", expectedConstrucotrParameters,
                constructorParameterFound);
        assertEquals("Unexpected number of fields found", 1, fieldsFound);
        // we currently do not synchronize the assignment statements
        // assertEquals("Unexpected number of imports found", assignmentOperatorsFound,
        // constructorParameterFound);
    }

}
