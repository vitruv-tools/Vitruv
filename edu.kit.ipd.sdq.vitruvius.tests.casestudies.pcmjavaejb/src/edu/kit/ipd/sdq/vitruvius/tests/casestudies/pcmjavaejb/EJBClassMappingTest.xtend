package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjavaejb

import org.junit.Test

import static org.junit.Assert.assertEquals

class EJBClassMappingTest extends EJBJaMoPP2PCMTransformationTest{ 
	
	@Test
	def testCreateClassAndAddStatelessAnnotation(){ 
		super.addRepoContractsAndDatatypesPackage()
		
		val correspondingBasicComponent = createEJBClass(TEST_CLASS_NAME)
		
		assertEquals("Created component has different name as added class", correspondingBasicComponent.entityName, TEST_CLASS_NAME)
	}
	
}