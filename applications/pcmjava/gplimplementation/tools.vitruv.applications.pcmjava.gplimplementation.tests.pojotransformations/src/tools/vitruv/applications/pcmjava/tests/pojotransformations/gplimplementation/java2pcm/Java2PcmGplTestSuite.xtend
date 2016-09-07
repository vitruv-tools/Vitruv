package tools.vitruv.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm

import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses
import org.junit.runners.Suite

@RunWith(Suite)
@SuiteClasses(#[ClassMappingGplTransformationTest, ClassMethodMappingGplTransformationTest,
	FieldMappingGplTransformationTest, InterfaceMappingGplTransformationTest,
	JaMoPPParameterMappingGplTransformationTest, MethodMappingGplTransformationTest,
	PackageMappingGplTransformationTest, TypeReferenceMappingGplTransformationTest])
class Java2PcmGplTestSuite {
}
