package tools.vitruvius.applications.pcmjava.tests.ejbtransformations.java2pcm

import org.junit.runners.Suite.SuiteClasses
import org.junit.runners.Suite
import org.junit.runner.RunWith

@RunWith(Suite)

@SuiteClasses( #[
	EJBClassMappingTest, 
	EJBFieldMappingTest,
	EJBImplementsMappingTest,
	EJBInterfaceMappingTest,
	EJBPackageMappingTest
])
class EJBJava2PCMTransformationsTestSuite {
	
}