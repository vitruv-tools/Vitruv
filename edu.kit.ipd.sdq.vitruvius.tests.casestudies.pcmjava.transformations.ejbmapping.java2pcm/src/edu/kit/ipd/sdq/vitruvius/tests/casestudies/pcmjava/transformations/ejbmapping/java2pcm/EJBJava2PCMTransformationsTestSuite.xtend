package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.ejbmapping.java2pcm

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