package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.ejbmapping.java2pcm

import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationProvidedRole

import static org.junit.Assert.assertEquals

class EJBImplementsMappingTest extends EJBJaMoPP2PCMTransformationTest{
	
	@Test
	def testAddImplementsToComponentClass(){
		super.createPackageEJBClassAndInterface()
		
		val OperationProvidedRole opr = super.addImplementsCorrespondingToOperationProvidedRoleToClass(TEST_CLASS_NAME, TEST_INTERFACE_NAME)
		
		assertEquals( "OperationProvidedRole has wrong name", TEST_CLASS_NAME + "_provides_" + TEST_INTERFACE_NAME, opr.entityName)
	}
	
}