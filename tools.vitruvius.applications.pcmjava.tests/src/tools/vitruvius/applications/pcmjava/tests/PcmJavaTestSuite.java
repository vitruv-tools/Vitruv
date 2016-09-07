package tools.vitruvius.applications.pcmjava.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruvius.applications.pcmjava.tests.jamoppuidcalculatorandresolver.JaMoPPTUIDCalculatorAndResolverTestSuite;
import tools.vitruvius.applications.pcmjava.tests.transformations.PCMJavaTransformationsTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ JaMoPPTUIDCalculatorAndResolverTestSuite.class, 
	PCMJavaTransformationsTestSuite.class,
	PcmJavaPojoTransformationsTestSuite.class,
	PcmJavaEjbTransformationsTestSuite.class
	})
public class PcmJavaTestSuite {

}
