package tools.vitruv.applications.pcmjava.tests

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.PCM2JavaTestSuite
import tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm.Java2PCMTestSuite
import tools.vitruv.applications.pcmjava.seffstatements.tests.pojotransformations.PCMJavaSeffstatementsTestSuite

@RunWith(Suite)
@SuiteClasses(#[
	PCM2JavaTestSuite,
	Java2PCMTestSuite,
	PCMJavaSeffstatementsTestSuite
])
class PcmJavaPojoTransformationsTestSuite {
}
