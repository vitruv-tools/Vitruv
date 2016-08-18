package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.jamoppuidcalculatorandresolver.JaMoPPTUIDCalculatorAndResolverTestSuite;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.seffstatements.PCMJavaSeffstatementsTestSuite;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.PCMJavaTransformationsTestSuite;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.PCM2JavaTestSuite;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.java2pcm.Java2PCMTestSuite;
import edu.kit.ipd.sdq.vitruvius.tests.framework.run.SynchronisationTest.SynchronizationTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ JaMoPPTUIDCalculatorAndResolverTestSuite.class, PCMJavaTransformationsTestSuite.class,
	SynchronizationTestSuite.class, PCM2JavaTestSuite.class, Java2PCMTestSuite.class,
	PCMJavaSeffstatementsTestSuite.class })
public class PCMJavaTestSuite {

}
