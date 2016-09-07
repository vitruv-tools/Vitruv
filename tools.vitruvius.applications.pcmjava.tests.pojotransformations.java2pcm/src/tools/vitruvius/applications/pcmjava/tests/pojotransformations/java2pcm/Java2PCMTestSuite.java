package tools.vitruvius.applications.pcmjava.tests.pojotransformations.java2pcm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({ ClassMappingTransformationTest.class, ClassMethodMappingTransformationTest.class,
        FieldMappingTransformationTest.class, InterfaceMappingTransformationTest.class,
        JaMoPPParameterMappingTransformationTest.class, MethodMappingTransformationTest.class,
        PackageMappingTransformationTest.class, TypeReferenceMappingTransformationTest.class })
public class Java2PCMTestSuite {

}
