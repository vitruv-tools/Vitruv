package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.ejbtransformations.java2pcm

import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationRequiredRole

import static org.junit.Assert.*

class EJBFieldMappingTest extends EJBJaMoPP2PCMTransformationTest {
	
	@Test
	def void annotateField() {
		val basicComponent = super.createPackageEJBClassAndInterface()
		
		super.addFieldToClassWithName(TEST_CLASS_NAME, TEST_INTERFACE_NAME, TEST_FIELD_NAME, null)
		val opRequiredRole = super.addAnnotationToField(TEST_FIELD_NAME, EJB_FIELD_ANNOTATION_NAME, OperationRequiredRole, TEST_CLASS_NAME)
		
		assertEquals("BasicComponent of required role is wrong", basicComponent.id, opRequiredRole.requiringEntity_RequiredRole.id)
		assertEquals("Required Interface has wrong name", TEST_INTERFACE_NAME, opRequiredRole.requiredInterface__OperationRequiredRole.entityName)
	}
}
