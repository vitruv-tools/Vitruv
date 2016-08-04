package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjavaejb

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils
import org.junit.Test

import static org.junit.Assert.*
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum

class EJBInterfaceMappingTest extends EJBJaMoPP2PCMTransformationTest{
	
	@Test
	def testCreateInterfaceAndAddRemoteAnnotation(){ 
		super.addRepoContractsAndDatatypesPackage()
		
		val correspondingOpInterface = createEJBInterface(TEST_INTERFACE_NAME)
		
		assertEquals("Created component has different name as added class", correspondingOpInterface.entityName, TEST_INTERFACE_NAME)
	}
	
	@Test
	def testCreateMethodInEJBInterface(){
		val correspondingOpSignature = createPackageEJBInterrfaceAndInterfaceMethod()
		
		assertEquals("OperationSiganture has not the expected name ", correspondingOpSignature.entityName, 
				PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME)
	}
	
	@Test
	def testCreateParameterInMethodInEJBInterface(){
		val correspondingOpSignature = createPackageEJBInterrfaceAndInterfaceMethod()
		
		val pcmParam = super.addParameterToSignature(TEST_INTERFACE_NAME, correspondingOpSignature.entityName, "byte[]", "data")
		
		assertEquals("PCM Parameter has not the expected name ", "data", pcmParam.entityName)
		assertTrue("PCM Parameter Type is not a collection Data type", pcmParam.dataType__Parameter instanceof CollectionDataType)
		val CollectionDataType cdt = pcmParam.dataType__Parameter as CollectionDataType
		assertTrue("PCM Parameter Type: InnerDatatype is not a primitive type", cdt.innerType_CollectionDataType instanceof PrimitiveDataType)
		val primitiveDataType = cdt.innerType_CollectionDataType as PrimitiveDataType
		assertEquals("PCM Parameter Type: InnerDatatype is from type BYTE", PrimitiveTypeEnum.BYTE, primitiveDataType.type)
	}
	
	@Test
	def testSetReturnTypeInMethodInEJBInterface(){
		val correspondingOpSignature = createPackageEJBInterrfaceAndInterfaceMethod()
		
		val opSignature = addReturnTypeToSignature(TEST_INTERFACE_NAME, correspondingOpSignature.entityName, "byte[]", null)
		
		assertEquals("Wrong signature changed", opSignature.entityName, correspondingOpSignature.entityName)
		assertTrue("OpSignature returnType is not a collection Data type", opSignature.returnType__OperationSignature instanceof CollectionDataType)
		val CollectionDataType cdt = opSignature.returnType__OperationSignature as CollectionDataType
		assertTrue("OpSignature returnType: InnerDatatype is not a primitive type", cdt.innerType_CollectionDataType instanceof PrimitiveDataType)
		val primitiveDataType = cdt.innerType_CollectionDataType as PrimitiveDataType
		assertEquals("OpSignature returnType : InnerDatatype is from type BYTE", PrimitiveTypeEnum.BYTE, primitiveDataType.type)
	}
	
	private def createPackageEJBInterrfaceAndInterfaceMethod() {
		super.createPackageAndEJBInterface()
		val correspondingOpSignature = super.addMethodToInterfaceWithCorrespondence(TEST_INTERFACE_NAME)
		correspondingOpSignature
	}
	
}