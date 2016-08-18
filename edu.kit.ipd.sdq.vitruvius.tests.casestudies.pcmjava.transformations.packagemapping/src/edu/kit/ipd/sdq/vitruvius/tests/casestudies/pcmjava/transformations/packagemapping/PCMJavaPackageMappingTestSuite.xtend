package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.pcm2java.PCM2JavaTestSuite
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.java2pcm.Java2PCMTestSuite
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.seffstatements.PCMJavaSeffstatementsTestSuite

@RunWith(Suite)
@SuiteClasses(#[
	PCM2JavaTestSuite,
	Java2PCMTestSuite,
	PCMJavaSeffstatementsTestSuite
])
class PCMJavaPackageMappingTestSuite {
}
