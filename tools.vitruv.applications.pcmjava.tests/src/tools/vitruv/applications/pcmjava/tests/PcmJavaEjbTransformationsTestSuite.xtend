package tools.vitruv.applications.pcmjava.tests

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import tools.vitruv.applications.pcmjava.tests.ejbtransformations.java2pcm.EJBJava2PCMTransformationsTestSuite
import tools.vitruv.applications.pcmjava.tests.ejbtransformations.seffstatements.EJBJava2PCMSeffTestSuite

@RunWith(Suite)
@SuiteClasses(#[
	EJBJava2PCMTransformationsTestSuite,
	EJBJava2PCMSeffTestSuite
])
class PcmJavaEjbTransformationsTestSuite {
	
}