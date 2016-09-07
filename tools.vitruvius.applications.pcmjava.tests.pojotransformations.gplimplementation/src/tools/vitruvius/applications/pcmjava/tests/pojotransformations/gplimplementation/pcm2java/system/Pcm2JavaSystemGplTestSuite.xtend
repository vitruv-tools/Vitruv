package tools.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.pcm2java.system

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite)
@SuiteClasses(#[
	AssemblyContextMappingGplTransformationTest,
	RequiredDelegationConnectorMappingGplTransformationTest,
	SystemMappingGplTransformationTest
])
class Pcm2JavaSystemGplTestSuite {
	
}
