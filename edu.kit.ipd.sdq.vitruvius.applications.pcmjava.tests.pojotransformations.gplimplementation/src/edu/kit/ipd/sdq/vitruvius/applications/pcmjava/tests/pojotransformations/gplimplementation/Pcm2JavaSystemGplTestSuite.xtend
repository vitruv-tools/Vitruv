package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.pcm2java.system.AssemblyContextMappingGplTransformationTest
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.pcm2java.system.RequiredDelegationConnectorMappingGplTransformationTest
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.pcm2java.system.SystemMappingGplTransformationTest

@RunWith(Suite)
@SuiteClasses(AssemblyContextMappingGplTransformationTest,
	RequiredDelegationConnectorMappingGplTransformationTest,
	SystemMappingGplTransformationTest
)
class Pcm2JavaSystemGplTestSuite {
	
}