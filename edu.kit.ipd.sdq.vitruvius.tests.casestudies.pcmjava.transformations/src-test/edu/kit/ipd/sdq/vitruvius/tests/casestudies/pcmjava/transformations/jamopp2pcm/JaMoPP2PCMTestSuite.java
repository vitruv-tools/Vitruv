package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({ ClassMappingTransformationTest.class, ClassMethodMappingTransformationTest.class,
        FieldMappingTransformationTest.class, InterfaceMappingTransformationTest.class,
        JaMoPPParameterMappingTransformationTest.class, MethodMappingTransformationTest.class,
        PackageMappingTransformationTest.class, TypeReferenceMappingTransformationTest.class })
public class JaMoPP2PCMTestSuite {

}
