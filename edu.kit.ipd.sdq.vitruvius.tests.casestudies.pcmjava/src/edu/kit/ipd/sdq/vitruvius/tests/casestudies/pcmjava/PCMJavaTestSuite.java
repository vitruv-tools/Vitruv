package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.PCMJavaPackageMappingTestSuite;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.jamoppuidcalculatorandresolver.JaMoPPTUIDCalculatorAndResolverTestSuite;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.PCMJavaTransformationsTestSuite;
import edu.kit.ipd.sdq.vitruvius.tests.framework.run.SynchronisationTest.SynchronizationTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ JaMoPPTUIDCalculatorAndResolverTestSuite.class, PCMJavaTransformationsTestSuite.class,
	SynchronizationTestSuite.class, PCMJavaPackageMappingTestSuite.class })
public class PCMJavaTestSuite {

}
