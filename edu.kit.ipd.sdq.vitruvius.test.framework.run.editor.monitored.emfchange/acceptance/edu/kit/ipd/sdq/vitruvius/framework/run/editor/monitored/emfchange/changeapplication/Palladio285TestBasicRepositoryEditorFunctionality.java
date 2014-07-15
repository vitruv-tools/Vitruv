package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.net.URL;

import org.junit.Test;

import de.uka.ipd.sdq.pcm.core.CoreFactory;
import de.uka.ipd.sdq.pcm.core.PCMRandomVariable;
import de.uka.ipd.sdq.pcm.parameter.ParameterFactory;
import de.uka.ipd.sdq.pcm.parameter.VariableCharacterisation;
import de.uka.ipd.sdq.pcm.parameter.VariableCharacterisationType;
import de.uka.ipd.sdq.pcm.parameter.VariableUsage;
import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.InfrastructureInterface;
import de.uka.ipd.sdq.pcm.repository.InfrastructureProvidedRole;
import de.uka.ipd.sdq.pcm.repository.InfrastructureRequiredRole;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationProvidedRole;
import de.uka.ipd.sdq.pcm.repository.OperationRequiredRole;
import de.uka.ipd.sdq.pcm.repository.OperationSignature;
import de.uka.ipd.sdq.pcm.repository.PassiveResource;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import de.uka.ipd.sdq.pcm.seff.ResourceDemandingSEFF;
import de.uka.ipd.sdq.pcm.seff.SeffFactory;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Metamodels;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.pcm.PCMTestUtils;

/**
 * This acceptance test is derived from
 * https://sdqbuild.ipd.kit.edu/jira/si/jira.issueviews:issue-html/PALLADIO-285/PALLADIO-285.html
 */
public class Palladio285TestBasicRepositoryEditorFunctionality extends AbstractChangeApplicatorTests<Repository> {

    @Override
    protected void registerMetamodels() {
        Metamodels.registerRepositoryMetamodel();
    }

    @Override
    protected URL getModel() {
        return Files.EMPTY_REPOSITORY;
    }

    @Test
    public void palladio285() {
        // Create a new Basic Component in the Repository Diagram Editor. Name it with a
        // recognizable name. (I will refer to this component as "BC1".)
        BasicComponent bc1 = RepositoryFactory.eINSTANCE.createBasicComponent();
        sourceRoot.getComponents__Repository().add(bc1);
        bc1.setEntityName("BC1");

        // Create a new Interface in the Repository Diagram Editor. Name it with a recognizable
        // name. (I will refer to this component as "IP1".)
        OperationInterface ip1 = RepositoryFactory.eINSTANCE.createOperationInterface();
        sourceRoot.getInterfaces__Repository().add(ip1);
        ip1.setEntityName("IP1");

        // Connect BC1 to IP1 using a ProvidedRole.
        OperationProvidedRole bc1ProvidedIP1 = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        bc1.getProvidedRoles_InterfaceProvidingEntity().add(bc1ProvidedIP1);
        bc1ProvidedIP1.setProvidedInterface__OperationProvidedRole(ip1);

        // Add a Signature to IP1. Name it with a recognizable name. (I will refer to it as "Sig1")
        OperationSignature sig1 = RepositoryFactory.eINSTANCE.createOperationSignature();
        ip1.getSignatures__OperationInterface().add(sig1);
        sig1.setEntityName("Sig1");

        // Change the return type of Sig1 to bool (in the properties view).
        sig1.setReturnType__OperationSignature(PCMTestUtils.getBoolType(sourceRoot.eResource()));

        // Delete BC1 by selecting it and then pressing the Del key.
        sourceRoot.getComponents__Repository().remove(bc1);

        // Undo the deletion by pressing ctrl + z.
        sourceRoot.getComponents__Repository().add(bc1);

        // Delete IP1 by selecting it and then pressing the Del key.
        sourceRoot.getInterfaces__Repository().remove(ip1);

        // Undo the deletion by pressing ctrl + z.
        sourceRoot.getInterfaces__Repository().add(ip1);

        // Add a SEFF to BC1. Select Sig1 as its Signature, when prompted to do so by a dialog.
        // Double click the SEFF. The SEFF editor should open. Close it again.
        ResourceDemandingSEFF seff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
        seff.setDescribedService__SEFF(sig1);
        bc1.getServiceEffectSpecifications__BasicComponent().add(seff);

        // Add a Passive Resource to BC1's PassiveResourceCompartment. Name it with a recognizable
        // name.
        PassiveResource passiveResource = RepositoryFactory.eINSTANCE.createPassiveResource();
        bc1.getPassiveResource_BasicComponent().add(passiveResource);
        passiveResource.setEntityName("passiveResource");

        // Add a Component Parameter to the ComponentParameterCompartment. A Dialog for the name
        // opens. Enter a recognizable name.
        VariableUsage vu = ParameterFactory.eINSTANCE.createVariableUsage();
        // TODO: set name?
        bc1.getComponentParameterUsage_ImplementationComponentType().add(vu);

        // Add a Variable Characterisation to the Component Parameter. A Dialog should open. Enter a
        // stochastic expression (e.g. a number).
        VariableCharacterisation vc = ParameterFactory.eINSTANCE.createVariableCharacterisation();
        vc.setType(VariableCharacterisationType.STRUCTURE);
        PCMRandomVariable pcmr = CoreFactory.eINSTANCE.createPCMRandomVariable();
        pcmr.setSpecification("1");
        vc.setSpecification_VariableCharacterisation(pcmr);

        vu.getVariableCharacterisation_VariableUsage().add(vc);

        // Add an InfrastructureInterface. Give it a recognizable name. (I will refer to it as
        // "IP2")
        InfrastructureInterface ip2 = RepositoryFactory.eINSTANCE.createInfrastructureInterface();
        ip2.setEntityName("IP2");
        sourceRoot.getInterfaces__Repository().add(ip2);

        // Connect BC1 to IP2 using an InfrastructureProvidedRole.
        InfrastructureProvidedRole ipr = RepositoryFactory.eINSTANCE.createInfrastructureProvidedRole();
        ipr.setProvidedInterface__InfrastructureProvidedRole(ip2);
        bc1.getProvidedRoles_InterfaceProvidingEntity().add(ipr);

        // Create a new Basic Component in the Repository Diagram Editor. Name it with a
        // recognizable name. (I will refer to this component as "BC2".)
        BasicComponent bc2 = RepositoryFactory.eINSTANCE.createBasicComponent();
        sourceRoot.getComponents__Repository().add(bc2);
        bc2.setEntityName("BC2");

        // Connect BC2 with IP1 using a RequiredRole.
        OperationRequiredRole rr = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        bc2.getRequiredRoles_InterfaceRequiringEntity().add(rr);
        rr.setRequiredInterface__OperationRequiredRole(ip1);

        // Connect BC2 with IP2 using an InfrastructureRequiredRole.
        InfrastructureRequiredRole ir = RepositoryFactory.eINSTANCE.createInfrastructureRequiredRole();
        ir.setRequiredInterface__InfrastructureRequiredRole(ip2);
        bc2.getRequiredRoles_InterfaceRequiringEntity().add(ir);

        synchronizeChangesAndAssertEquality();
    }
}
