package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.pcm2java.Pcm2JavaGplTestSuite
import tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.java2pcm.Java2PcmGplTestSuite

@RunWith(Suite)
@SuiteClasses(Pcm2JavaGplTestSuite,
	Java2PcmGplTestSuite)
class PcmJavaGplTestSuite {
	
}