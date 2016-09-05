package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation

import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses
import org.junit.runners.Suite
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm.ClassMappingGplTransformationTest
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm.ClassMethodMappingGplTransformationTest
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm.FieldMappingGplTransformationTest
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm.InterfaceMappingGplTransformationTest
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm.JaMoPPParameterMappingGplTransformationTest
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm.MethodMappingGplTransformationTest
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm.TypeReferenceMappingGplTransformationTest
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm.PackageMappingGplTransformationTest

@RunWith(Suite)
@SuiteClasses(#[ClassMappingGplTransformationTest, ClassMethodMappingGplTransformationTest,
	FieldMappingGplTransformationTest, InterfaceMappingGplTransformationTest,
	JaMoPPParameterMappingGplTransformationTest, MethodMappingGplTransformationTest,
	PackageMappingGplTransformationTest, TypeReferenceMappingGplTransformationTest])
class Java2PcmGplTestSuite {
}
