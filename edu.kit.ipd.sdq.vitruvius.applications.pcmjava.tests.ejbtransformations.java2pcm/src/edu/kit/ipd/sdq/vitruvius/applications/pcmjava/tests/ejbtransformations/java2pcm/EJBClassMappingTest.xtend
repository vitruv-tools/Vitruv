package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.ejbtransformations.java2pcm

import org.junit.Test

import static org.junit.Assert.assertEquals

class EJBClassMappingTest extends EJBJaMoPP2PCMTransformationTest{ 
	
	@Test
	def testCreateClassAndAddStatelessAnnotation(){ 
		super.addRepoContractsAndDatatypesPackage()
		
		val correspondingBasicComponent = createEJBClass(TEST_CLASS_NAME)
		
		assertEquals("Created component has different name as added class", correspondingBasicComponent.entityName, TEST_CLASS_NAME)
	}
	
	@Test
	def testCreateMethodThatOverridesInterfaceMethod(){
		super.createPackageEJBClassAndInterface()
		super.addImplementsCorrespondingToOperationProvidedRoleToClass(TEST_CLASS_NAME, TEST_INTERFACE_NAME)
		val correspondingOpSignature = super.addMethodToInterfaceWithCorrespondence(TEST_INTERFACE_NAME)
		
		val rdSEFF = super.addClassMethodToClassThatOverridesInterfaceMethod(TEST_CLASS_NAME, correspondingOpSignature.entityName)
		
		assertEquals("RDSEFF describes wrong service", rdSEFF.describedService__SEFF.id, correspondingOpSignature.id)
	}
	
}