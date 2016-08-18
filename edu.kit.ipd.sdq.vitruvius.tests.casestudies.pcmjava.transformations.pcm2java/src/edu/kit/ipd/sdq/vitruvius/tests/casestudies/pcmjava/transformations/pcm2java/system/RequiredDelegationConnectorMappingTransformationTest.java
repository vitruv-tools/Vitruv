package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.system;

import static org.junit.Assert.fail;

import org.junit.Test;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class RequiredDelegationConnectorMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddRequireDelegationConnector() throws Throwable {
        final Repository repo = super.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComp = super.addBasicComponentAndSync(repo, PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME);
        final OperationInterface opInterface = super.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);
        final System system = super.createAndSyncSystem(PCM2JaMoPPTestUtils.SYSTEM_NAME);
        final AssemblyContext assemblyContext = super.createAndSyncAssemblyContext(system, basicComp);
        final OperationRequiredRole basicComponentRequiredRole = super.createAndSyncOperationRequiredRole(opInterface,
                basicComp);
        final OperationRequiredRole systemRequiredRole = super.createAndSyncOperationRequiredRole(opInterface, system);

        final RequiredDelegationConnector requiredDelegationConnector = CompositionFactory.eINSTANCE
                .createRequiredDelegationConnector();
        requiredDelegationConnector.setAssemblyContext_RequiredDelegationConnector(assemblyContext);
        requiredDelegationConnector.setInnerRequiredRole_RequiredDelegationConnector(basicComponentRequiredRole);
        requiredDelegationConnector.setOuterRequiredRole_RequiredDelegationConnector(systemRequiredRole);
        requiredDelegationConnector.setParentStructure__Connector(system);
        super.triggerSynchronization(system);

        this.assertRequiredDelegationConnector(requiredDelegationConnector);
    }

    private void assertRequiredDelegationConnector(final RequiredDelegationConnector requiredDelegationConnector) {
        fail("no assert yet");
    }

}
