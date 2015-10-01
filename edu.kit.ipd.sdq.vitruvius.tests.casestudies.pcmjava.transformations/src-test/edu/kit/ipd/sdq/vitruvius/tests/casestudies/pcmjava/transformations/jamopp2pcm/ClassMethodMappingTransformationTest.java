package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;

import static org.junit.Assert.assertEquals;

import org.emftext.language.java.members.ClassMethod;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class ClassMethodMappingTransformationTest extends JaMoPP2PCMTransformationTest {

    @Test
    public void testAddClassMethodWithCorrespondence() throws Throwable {
        // create repo
        final Repository repo = super.addRepoContractsAndDatatypesPackage();
        this.testUserInteractor.addNextSelections(JaMoPP2PCMTransformationTest.SELECT_NOTHING_DECIDE_LATER);
        super.createPackageWithPackageInfo(repo.getEntityName(), "contracts");
        // create component implementing class
        super.addPackageAndImplementingClass(PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME);
        // create interface
        super.createInterfaceInPackage("contracts", true, PCM2JaMoPPTestUtils.INTERFACE_NAME);
        // create interface method
        super.addMethodToInterfaceWithCorrespondence(PCM2JaMoPPTestUtils.INTERFACE_NAME,
                PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME);
        // add implements/provided role
        super.addImplementsCorrespondingToOperationProvidedRoleToClass(
                PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Impl", PCM2JaMoPPTestUtils.INTERFACE_NAME);

        // actual test: add class method to implementing class that overrides the interface
        final ResourceDemandingSEFF correspondingSeff = super.addClassMethodToClassThatOverridesInterfaceMethod(
                PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Impl", PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME);

        // assert the correspondingSEFF
        final ClassMethod jaMoPPMethod = this.getCorrespondenceInstance()
                .claimUniqueCorrespondingEObjectByType(correspondingSeff, ClassMethod.class);
        assertEquals(jaMoPPMethod.getName(), PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME);
    }

    @Test
    public void testAddClassMethodWithoutCorrespondence() {

    }

}
