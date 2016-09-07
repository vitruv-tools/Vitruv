package tools.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import tools.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.pcm2java.Pcm2JavaGplTestSuite
import tools.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm.Java2PcmGplTestSuite

@RunWith(Suite)
@SuiteClasses(Pcm2JavaGplTestSuite,
	Java2PcmGplTestSuite)
class PcmJavaGplTestSuite {
	
}