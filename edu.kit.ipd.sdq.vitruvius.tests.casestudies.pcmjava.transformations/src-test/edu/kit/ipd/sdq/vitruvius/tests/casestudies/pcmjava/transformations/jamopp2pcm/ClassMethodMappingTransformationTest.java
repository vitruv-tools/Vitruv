package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;

import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class ClassMethodMappingTransformationTest extends JaMoPP2PCMTransformationTest {

    @Test
    public void testAddClassMethodWithCorrespondence() throws Throwable {
        // create repo
        super.addFirstPackage();
        // create component implementing class
        super.addPackageAndImplementingClass(PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME);
        // create interface
        super.createInterfaceInPackage("contracts", true, PCM2JaMoPPTestUtils.INTERFACE_NAME);
        // create interface method
        super.addMethodToInterfaceWithCorrespondence(PCM2JaMoPPTestUtils.INTERFACE_NAME,
                PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME);
        // add implements/provided role

        // actual test: add class method to implementing class that overrides the interface method

        // assert the correspondence of the class method
        this.assertClassMethod();
    }

    @Test
    public void testAddClassMethodWithoutCorrespondence() {

    }

    private void assertClassMethod() {
        // TODO Auto-generated method stub

    }
}
