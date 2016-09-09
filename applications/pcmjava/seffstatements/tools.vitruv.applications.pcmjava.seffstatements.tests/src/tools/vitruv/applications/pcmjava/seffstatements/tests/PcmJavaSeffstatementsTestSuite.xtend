package tools.vitruv.applications.pcmjava.seffstatements.tests

import org.junit.runners.Suite
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses
import tools.vitruv.applications.pcmjava.seffstatements.tests.pojotransformations.PcmJavaSeffstatementsPojoTransformationsTestSuite
import tools.vitruv.applications.pcmjava.seffstatements.tests.ejbtransformations.PcmJavaSeffstatementsEjbTransformationsTestSuite

@RunWith(Suite)
@SuiteClasses(#[
	PcmJavaSeffstatementsPojoTransformationsTestSuite,
	PcmJavaSeffstatementsEjbTransformationsTestSuite
])
class PcmJavaSeffstatementsTestSuite {
	
}