package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.ejbmapping.java2pcm

import org.junit.runners.Suite.SuiteClasses
import org.junit.runners.Suite
import org.junit.runner.RunWith
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.ejbmapping.java2pcm.seff.EJBJava2PCMTransformationsSeffTestSuite

@RunWith(Suite)

@SuiteClasses( #[
	EJBJava2PCMTransformationsTestSuite,
	EJBJava2PCMTransformationsSeffTestSuite
])
class EJBJava2PCMTransformationsWithSeffTestSuite {
	
}