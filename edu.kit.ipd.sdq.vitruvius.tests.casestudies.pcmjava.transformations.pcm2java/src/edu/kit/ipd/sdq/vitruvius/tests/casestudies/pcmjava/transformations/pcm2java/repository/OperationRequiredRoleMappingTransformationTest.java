package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository;

import java.util.concurrent.Callable;

import org.junit.Test;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.PCM2JaMoPPTestUtils;

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
    public void testAddOperationRequiredRoleToCompositeComponent() throws Throwable {
        final Repository repo = super.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);
        final CompositeComponent compositeComponent = super.createAndSyncCompositeComponent(repo,
                PCM2JaMoPPTestUtils.COMPOSITE_COMPONENT_NAME);

        // test: add the requried Role
        final OperationRequiredRole operationRequiredRole = this.createAndSyncOperationRequiredRole(opInterface,
                compositeComponent);

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

        EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                	assertOperationRequiredRole(opr);
                } catch (final Throwable e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        }, this.vsum);
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

}
