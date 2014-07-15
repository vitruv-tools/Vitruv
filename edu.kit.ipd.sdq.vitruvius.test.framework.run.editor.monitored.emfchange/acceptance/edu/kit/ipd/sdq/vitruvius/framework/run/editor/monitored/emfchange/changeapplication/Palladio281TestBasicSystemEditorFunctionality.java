package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.net.URL;

import org.junit.Test;

import de.uka.ipd.sdq.pcm.core.composition.AssemblyConnector;
import de.uka.ipd.sdq.pcm.core.composition.AssemblyContext;
import de.uka.ipd.sdq.pcm.core.composition.AssemblyInfrastructureConnector;
import de.uka.ipd.sdq.pcm.core.composition.CompositionFactory;
import de.uka.ipd.sdq.pcm.core.composition.ProvidedDelegationConnector;
import de.uka.ipd.sdq.pcm.core.composition.RequiredDelegationConnector;
import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.CompositeComponent;
import de.uka.ipd.sdq.pcm.repository.InfrastructureInterface;
import de.uka.ipd.sdq.pcm.repository.InfrastructureProvidedRole;
import de.uka.ipd.sdq.pcm.repository.InfrastructureRequiredRole;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationProvidedRole;
import de.uka.ipd.sdq.pcm.repository.OperationRequiredRole;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Metamodels;

/**
 * This acceptance test is derived from
 * https://sdqbuild.ipd.kit.edu/jira/si/jira.issueviews:issue-html/PALLADIO-281/PALLADIO-281.html
 */
public class Palladio281TestBasicSystemEditorFunctionality extends
        AbstractInterleavedChangeApplicatorTests<Repository, de.uka.ipd.sdq.pcm.system.System> {

    private Repository repositoryRoot;
    private de.uka.ipd.sdq.pcm.system.System systemRoot;

    @Override
    public void setUp() {
        super.setUp();
        repositoryRoot = testA.sourceRoot;
        systemRoot = testB.sourceRoot;
    }

    @Override
    protected void registerMetaModels() {
        Metamodels.registerRepositoryMetamodel();
    }

    @Override
    protected URL getModelA() {
        return Files.EMPTY_REPOSITORY;
    }

    @Override
    protected URL getModelB() {
        return Files.EMPTY_SYSTEM;
    }

    @Test
    public void palladio281() {
        // Create a new Basic Component in the Repository Diagram Editor. Name it with a
        // recognizable name.
        // (I will refer to this component as "BC1".)
        BasicComponent bc1 = RepositoryFactory.eINSTANCE.createBasicComponent();
        bc1.setEntityName("BC1");
        repositoryRoot.getComponents__Repository().add(bc1);

        // Create a new Interface in the Repository Diagram Editor. Name it with a recognizable
        // name.
        // (I will refer to this component as "IP1".)
        OperationInterface ip1 = RepositoryFactory.eINSTANCE.createOperationInterface();
        repositoryRoot.getInterfaces__Repository().add(ip1);
        ip1.setEntityName("IP1");

        // Connect IP1 to BC1 using a Provided Role.
        OperationProvidedRole ip1toBc1 = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        bc1.getProvidedRoles_InterfaceProvidingEntity().add(ip1toBc1);
        ip1toBc1.setProvidedInterface__OperationProvidedRole(ip1);

        // Create a new Interface in the Repository Diagram Editor. Name it with a recognizable
        // name.
        // (I will refer to this component as "IR1".)
        OperationInterface ir1 = RepositoryFactory.eINSTANCE.createOperationInterface();
        repositoryRoot.getInterfaces__Repository().add(ir1);
        ir1.setEntityName("IR1");

        // Connect IR1 to BC1 using a Required Role.
        OperationRequiredRole ir1toBc1 = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        ir1toBc1.setRequiredInterface__OperationRequiredRole(ir1);
        bc1.getRequiredRoles_InterfaceRequiringEntity().add(ir1toBc1);

        // Save the Repository Diagram File.
        synchronizeChangesAndAssertEqualityA();

        // Create a new InfrastructureInterface in the Repository Diagram Editor. Name it with a
        // recognizable name.
        // (I will refer to this component as "IR2".)
        InfrastructureInterface ir2 = RepositoryFactory.eINSTANCE.createInfrastructureInterface();
        repositoryRoot.getInterfaces__Repository().add(ir2);
        ir2.setEntityName("IR2");

        // Connect IR2 to BC1 using an Infrastructure Required Role.
        InfrastructureRequiredRole ir2ToBc1 = RepositoryFactory.eINSTANCE.createInfrastructureRequiredRole();
        ir2ToBc1.setRequiredInterface__InfrastructureRequiredRole(ir2);
        bc1.getRequiredRoles_InterfaceRequiringEntity().add(ir2ToBc1);

        // Create a new Composite Component in the Repository Diagram Editor. Name it with a
        // recognizable name.
        // (I will refer to this component as "CC1".)
        CompositeComponent cc1 = RepositoryFactory.eINSTANCE.createCompositeComponent();
        repositoryRoot.getComponents__Repository().add(cc1);
        cc1.setEntityName("CC1");

        // Connect IP1 to CC1 using a Required Role.
        OperationRequiredRole ip1toCc1 = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        ip1toCc1.setRequiredInterface__OperationRequiredRole(ip1);
        cc1.getRequiredRoles_InterfaceRequiringEntity().add(ip1toCc1);

        // Connect IR2 to CC1 using an Infrastructure Provided Role.
        InfrastructureProvidedRole ir2toCc1 = RepositoryFactory.eINSTANCE.createInfrastructureProvidedRole();
        cc1.getProvidedRoles_InterfaceProvidingEntity().add(ir2toCc1);
        ir2toCc1.setProvidedInterface__InfrastructureProvidedRole(ir2);

        // Save the Repository Diagram File.
        synchronizeChangesAndAssertEqualityA();

        // Create an initial system diagram.

        // Create an Assembly Context in the pre-existing defaultSystem. Select the existing
        // repository model from step 1 when prompted by the wizard. Then select the Basic Component
        // BC1 by clicking on it once and then pressing the "OK" button.
        // Name the resulting Assembly Context.
        // Name the resulting AssemblyContext (I will refer to it as AC1).
        AssemblyContext ac1 = CompositionFactory.eINSTANCE.createAssemblyContext();
        systemRoot.getAssemblyContexts__ComposedStructure().add(ac1);
        ac1.setEncapsulatedComponent__AssemblyContext(bc1);

        // Add a SystemOperationRequiredRole to the defaultSystem by choosing it from the tools
        // palette and then clicking on the defaultSystem name compartment.
        OperationRequiredRole ir1ToSys = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        systemRoot.getRequiredRoles_InterfaceRequiringEntity().add(ir1ToSys);

        // Select IR1 as the Role's Interface when prompted.
        ir1ToSys.setRequiredInterface__OperationRequiredRole(ir1);

        // Connect the Assembly Context's Requires Role and the System's Requires Role using an
        // OperationRequiredDelegationConnector.
        RequiredDelegationConnector ac1ToSys = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();
        ac1ToSys.setAssemblyContext_RequiredDelegationConnector(ac1);
        systemRoot.getConnectors__ComposedStructure().add(ac1ToSys);

        // Add a second Assembly Context for CC1. Name it (I will refer to it as AC2).
        AssemblyContext ac2 = CompositionFactory.eINSTANCE.createAssemblyContext();
        systemRoot.getAssemblyContexts__ComposedStructure().add(ac2);
        ac2.setEntityName("AC2");
        ac2.setEncapsulatedComponent__AssemblyContext(cc1);

        // Connect AC2's Required Role with AC1's Provided Role using an AssemblyConnector.
        AssemblyConnector ac1ToAc2 = CompositionFactory.eINSTANCE.createAssemblyConnector();
        ac1ToAc2.setProvidingAssemblyContext_AssemblyConnector(ac1);
        ac1ToAc2.setRequiringAssemblyContext_AssemblyConnector(ac2);
        systemRoot.getConnectors__ComposedStructure().add(ac1ToAc2);

        // Connect AC1's Infrastructure Required Role with AC2's Infrastructure Provided Role using
        // an AssemblyInfrastructureConnector.
        AssemblyInfrastructureConnector ac1iToac2I = CompositionFactory.eINSTANCE
                .createAssemblyInfrastructureConnector();
        systemRoot.getConnectors__ComposedStructure().add(ac1iToac2I);
        ac1iToac2I.setProvidingAssemblyContext__AssemblyInfrastructureConnector(ac2);
        ac1iToac2I.setRequiringAssemblyContext__AssemblyInfrastructureConnector(ac1);

        synchronizeChangesAndAssertEqualityB();

        // Switch back to the Repository Diagram Editor. Open CC1's Composite System Editor by
        // double clicking on it.

        // Create an Assembly Context in the pre-existing CC1 System. Select the existing repository
        // model from step 1 if prompted by the wizard. Then select the Basic Component BC1 by
        // clicking on it once and then pressing the "OK" button.
        AssemblyContext ac3 = CompositionFactory.eINSTANCE.createAssemblyContext();
        ac3.setEncapsulatedComponent__AssemblyContext(bc1);
        cc1.getAssemblyContexts__ComposedStructure().add(ac3);

        // Name the resulting Assembly Context.

        // Name the resulting AssemblyContext (I will refer to it as AC3).
        ac3.setEntityName("AC3");

        // Add a SystemOperationProvidedRole to the CC1 System by choosing it from the tools palette
        // and then clicking on the defaultSystem name compartment.
        OperationProvidedRole ip1toCc1Prov = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        cc1.getProvidedRoles_InterfaceProvidingEntity().add(ip1toCc1Prov);

        // Select IP1 as the Role's Interface when prompted.
        ip1toCc1Prov.setProvidedInterface__OperationProvidedRole(ip1);

        // Connect CC1's new Provided Role with AC3's Provided Role using an
        // OperationProvidedDelegationConnector.
        ProvidedDelegationConnector pdc = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();
        pdc.setInnerProvidedRole_ProvidedDelegationConnector(ip1toCc1Prov);
        pdc.setAssemblyContext_ProvidedDelegationConnector(ac3);
        cc1.getConnectors__ComposedStructure().add(pdc);

        synchronizeChangesAndAssertEqualityA();
        synchronizeChangesAndAssertEqualityB();
    }
}
