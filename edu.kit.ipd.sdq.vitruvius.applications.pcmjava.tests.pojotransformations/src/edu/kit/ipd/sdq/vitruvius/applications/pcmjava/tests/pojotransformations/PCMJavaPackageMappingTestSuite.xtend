package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.pcm2java.PCM2JavaTestSuite
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.java2pcm.Java2PCMTestSuite
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.seffstatements.PCMJavaSeffstatementsTestSuite

@RunWith(Suite)
@SuiteClasses(#[
	PCM2JavaTestSuite,
	Java2PCMTestSuite,
	PCMJavaSeffstatementsTestSuite
])
class PCMJavaPackageMappingTestSuite {
}
