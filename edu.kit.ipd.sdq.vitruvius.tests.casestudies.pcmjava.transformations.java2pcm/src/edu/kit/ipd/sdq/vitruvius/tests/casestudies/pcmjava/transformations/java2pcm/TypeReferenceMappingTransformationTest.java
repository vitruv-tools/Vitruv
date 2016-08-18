package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.java2pcm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.java2pcm.ClassMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class TypeReferenceMappingTransformationTest extends JaMoPP2PCMTransformationTest {

    @Test
    public void testAddImplementsToClassWithCorrespondingComponent() throws Throwable {
        // crete repo
        final Repository repo = this.addRepoContractsAndDatatypesPackage();
        // create class
        this.addSecondPackageCorrespondsWithoutCorrespondences();
        this.testUserInteractor.addNextSelections(ClassMappingTransformation.SELECT_CREATE_BASIC_COMPONENT);
        final BasicComponent basicComponent = this.addClassInSecondPackage(BasicComponent.class);
        this.createPackageWithPackageInfo(repo.getEntityName(), "contracts");
        // create interface
        final OperationInterface opInterface = this.createInterfaceInPackage("contracts", true,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);

        // add the implement relation
        final OperationProvidedRole opr = super.addImplementsCorrespondingToOperationProvidedRoleToClass(
                basicComponent.getEntityName(), opInterface.getEntityName());

        assertEquals("The interface proivieded by the provided role is not the expected interface",
                opr.getProvidedInterface__OperationProvidedRole().getId(), opInterface.getId());
        assertEquals("The component that provides the interface is not the expected component",
                opr.getProvidingEntity_ProvidedRole().getId(), basicComponent.getId());
    }

}
