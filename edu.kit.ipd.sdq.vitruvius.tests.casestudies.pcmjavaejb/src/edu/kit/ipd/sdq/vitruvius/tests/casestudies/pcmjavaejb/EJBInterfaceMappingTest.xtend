package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjavaejb

import org.junit.Test

import static org.junit.Assert.assertEquals

class EJBInterfaceMappingTest extends EJBJaMoPP2PCMTransformationTest{
	
	@Test
	def testCreateInterfaceAndAddRemoteAnnotation(){ 
		super.addRepoContractsAndDatatypesPackage()
		
		val correspondingOpInterface = createEJBInterface(TEST_INTERFACE_NAME)
		
		assertEquals("Created component has different name as added class", correspondingOpInterface.entityName, TEST_INTERFACE_NAME)
	}
}