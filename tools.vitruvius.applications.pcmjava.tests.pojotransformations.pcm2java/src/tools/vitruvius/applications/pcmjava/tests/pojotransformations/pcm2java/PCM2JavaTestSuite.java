package tools.vitruvius.applications.pcmjava.tests.pojotransformations.pcm2java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruvius.applications.pcmjava.tests.pojotransformations.pcm2java.repository.PCM2JavaRepositoryTestSuite;
import tools.vitruvius.applications.pcmjava.tests.pojotransformations.pcm2java.system.PCM2JavaSystemTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ DataTypeCorrespondenceHelperTest.class,
	PCM2JavaRepositoryTestSuite.class, PCM2JavaSystemTestSuite.class })
public class PCM2JavaTestSuite {

}
