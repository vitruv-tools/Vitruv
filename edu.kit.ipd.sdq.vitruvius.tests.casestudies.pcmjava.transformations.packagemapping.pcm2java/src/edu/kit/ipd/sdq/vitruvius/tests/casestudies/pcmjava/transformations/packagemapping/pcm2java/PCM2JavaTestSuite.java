package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.pcm2java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.pcm2java.repository.PCM2JavaRepositoryTestSuite;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.pcm2java.system.PCM2JavaSystemTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ DataTypeCorrespondenceHelperTest.class,
	PCM2JavaRepositoryTestSuite.class, PCM2JavaSystemTestSuite.class })
public class PCM2JavaTestSuite {

}
