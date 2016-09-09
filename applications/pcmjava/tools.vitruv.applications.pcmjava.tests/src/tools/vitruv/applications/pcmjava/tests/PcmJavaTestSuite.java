package tools.vitruv.applications.pcmjava.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruv.applications.pcmjava.tests.ejbtransformations.java2pcm.Java2PcmEjbTransformationsTestSuite;
import tools.vitruv.applications.pcmjava.tests.transformations.PCMJavaTransformationsTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ //JaMoPPTUIDCalculatorAndResolverTestSuite.class, 
	PCMJavaTransformationsTestSuite.class,
	PcmJavaPojoTransformationsTestSuite.class,
	Java2PcmEjbTransformationsTestSuite.class
	})
public class PcmJavaTestSuite {

}
