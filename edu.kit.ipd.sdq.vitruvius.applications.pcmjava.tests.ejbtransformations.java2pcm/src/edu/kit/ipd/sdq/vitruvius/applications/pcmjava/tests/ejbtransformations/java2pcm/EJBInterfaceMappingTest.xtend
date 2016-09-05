package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.ejbtransformations.java2pcm

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.util.PCM2JaMoPPTestUtils
import org.junit.Test
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum

import static org.junit.Assert.*
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.util.CompilationUnitManipulatorHelper
import org.eclipse.jdt.core.IMethod

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
		
		val pcmParam = super.addParameterToSignature(TEST_INTERFACE_NAME, correspondingOpSignature.entityName, "byte[]", "data", null)
		
		assertPCMParam(pcmParam, "data", PrimitiveTypeEnum.BYTE)
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
	
	@Test
	def testCreateMultipleParametersInMethodInEJBInterface(){
		val correspondingOpSignature = createPackageEJBInterrfaceAndInterfaceMethod()
		
		val name = "data"
		val typeName = "byte[]"
		val pcmParam = super.addParameterToSignature(TEST_INTERFACE_NAME, correspondingOpSignature.entityName, typeName, name, null)
		assertPCMParam(pcmParam, name, PrimitiveTypeEnum.BYTE)
		
		val icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(TEST_INTERFACE_NAME,
                this.currentTestProject);
        val IMethod iMethod = icu.getType(TEST_INTERFACE_NAME).methods.get(0)
        val secondParamname = "additionalData"
        val typeStringName = "String[]"
        val String secondParameterStr = ", " + typeStringName + " " + secondParamname ;
		val secondPcmParam = super.insertParameterIntoSignature(correspondingOpSignature.entityName, secondParamname, icu, iMethod, secondParameterStr)
		assertPCMParam(secondPcmParam, secondParamname, PrimitiveTypeEnum.STRING)
		
	}
	
	private def createPackageEJBInterrfaceAndInterfaceMethod() {
		super.createPackageAndEJBInterface()
		val correspondingOpSignature = super.addMethodToInterfaceWithCorrespondence(TEST_INTERFACE_NAME)
		correspondingOpSignature
	}
	
	private def assertPCMParam(Parameter pcmParam, String expectedName, PrimitiveTypeEnum expectedPrimiteveTypeEnum) {
		assertEquals("PCM Parameter has not the expected name ", expectedName, pcmParam.entityName)
		//expecting type byte[]	
		assertTrue("PCM Parameter Type is not a collection Data type", pcmParam.dataType__Parameter instanceof CollectionDataType)
		val CollectionDataType cdt = pcmParam.dataType__Parameter as CollectionDataType
		assertTrue("PCM Parameter Type: InnerDatatype is not a primitive type", cdt.innerType_CollectionDataType instanceof PrimitiveDataType)
		val primitiveDataType = cdt.innerType_CollectionDataType as PrimitiveDataType
		assertEquals("PCM Parameter Type: wrong InnerDatatype", expectedPrimiteveTypeEnum, primitiveDataType.type)
	}
	
}