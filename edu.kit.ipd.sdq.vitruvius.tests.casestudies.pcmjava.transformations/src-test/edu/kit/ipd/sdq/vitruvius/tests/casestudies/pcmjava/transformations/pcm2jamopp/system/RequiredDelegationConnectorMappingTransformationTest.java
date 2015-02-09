package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.system;

import static org.junit.Assert.fail;

import org.junit.Test;

import de.uka.ipd.sdq.pcm.core.composition.AssemblyContext;
import de.uka.ipd.sdq.pcm.core.composition.CompositionFactory;
import de.uka.ipd.sdq.pcm.core.composition.RequiredDelegationConnector;
import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationRequiredRole;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.system.System;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
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
