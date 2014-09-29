package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.PrimitiveDataType
import de.uka.ipd.sdq.pcm.repository.PrimitiveTypeEnum
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import org.emftext.language.java.types.TypesFactory

/**
 * Mapping transformation for primitive data types
 * Mapping is as follows:
 * PCM	     JaMoPP		
 * int 		 int
 * string    java.lang.String
 * bool	     boolean
 * byte		 byte
 * char		 char
 * long      long		
 */
class PrimitiveDataTypeMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return PrimitiveDataType
	}

	/**
	 * just creates the correspondence in correspondence instance
	 */
	override setCorrespondenceForFeatures() {
		val PrimitiveDataType primitiveTypeBool = RepositoryFactory.eINSTANCE.createPrimitiveDataType
		primitiveTypeBool.setType(PrimitiveTypeEnum.BOOL)
		val PrimitiveDataType primitiveTypeByte = RepositoryFactory.eINSTANCE.createPrimitiveDataType
		primitiveTypeByte.setType(PrimitiveTypeEnum.BYTE)
		val PrimitiveDataType primitiveTypeChar = RepositoryFactory.eINSTANCE.createPrimitiveDataType
		primitiveTypeChar.setType(PrimitiveTypeEnum.CHAR)
		val PrimitiveDataType primitiveTypeDouble = RepositoryFactory.eINSTANCE.createPrimitiveDataType
		primitiveTypeDouble.setType(PrimitiveTypeEnum.DOUBLE)
		val PrimitiveDataType primitiveTypeInt = RepositoryFactory.eINSTANCE.createPrimitiveDataType
		primitiveTypeInt.setType(PrimitiveTypeEnum.INT)
		val PrimitiveDataType primitiveTypeLong = RepositoryFactory.eINSTANCE.createPrimitiveDataType
		primitiveTypeLong.setType(PrimitiveTypeEnum.LONG)
		val PrimitiveDataType primitiveTypeString = RepositoryFactory.eINSTANCE.createPrimitiveDataType
		primitiveTypeString.setType(PrimitiveTypeEnum.STRING)
		val jaMoPPStringReference = PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("", "String")
		this.correspondenceInstance.createAndAddEObjectCorrespondence(primitiveTypeBool,
			TypesFactory.eINSTANCE.createBoolean)
		this.correspondenceInstance.createAndAddEObjectCorrespondence(primitiveTypeByte,
			TypesFactory.eINSTANCE.createByte)
		this.correspondenceInstance.createAndAddEObjectCorrespondence(primitiveTypeChar,
			TypesFactory.eINSTANCE.createChar)
		this.correspondenceInstance.createAndAddEObjectCorrespondence(primitiveTypeDouble,
			TypesFactory.eINSTANCE.createDouble)
		this.correspondenceInstance.createAndAddEObjectCorrespondence(primitiveTypeInt, TypesFactory.eINSTANCE.createInt)
		this.correspondenceInstance.createAndAddEObjectCorrespondence(primitiveTypeLong,
			TypesFactory.eINSTANCE.createLong)
		this.correspondenceInstance.createAndAddEObjectCorrespondence(primitiveTypeLong, jaMoPPStringReference)

	//could not be done here: void has to be used when  set to null or unset is called
	//this.correspondenceInstance.createAndAddEObjectCorrespondence(, TypesFactory.eINSTANCE.createVoid)
	}

}
