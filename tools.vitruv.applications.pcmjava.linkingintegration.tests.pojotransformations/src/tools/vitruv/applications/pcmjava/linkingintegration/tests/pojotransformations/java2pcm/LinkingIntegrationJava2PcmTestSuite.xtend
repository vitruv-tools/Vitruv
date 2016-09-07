package tools.vitruv.applications.pcmjava.linkingintegration.tests.pojotransformations.java2pcm

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite)
@SuiteClasses(IntegrationClassMappingTransformationTest,
	IntegrationInterfaceMappingTransformationTest,
	IntegrationMethodMappingTransformationTest,
	IntegrationPackageMappingTransformationTest
)
class LinkingIntegrationJava2PcmTestSuite {
	
}