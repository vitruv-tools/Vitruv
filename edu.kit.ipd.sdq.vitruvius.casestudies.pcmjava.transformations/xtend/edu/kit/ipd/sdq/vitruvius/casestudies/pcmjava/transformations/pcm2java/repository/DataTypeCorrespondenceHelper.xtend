package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.CollectionDataType
import de.uka.ipd.sdq.pcm.repository.CompositeDataType
import de.uka.ipd.sdq.pcm.repository.DataType
import de.uka.ipd.sdq.pcm.repository.PrimitiveDataType
import de.uka.ipd.sdq.pcm.repository.PrimitiveTypeEnum
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory
import org.eclipse.xtend.lib.macro.declaration.PrimitiveType.Kind

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
class DataTypeCorrespondenceHelper{ 

	private new(){
	}

	private static var ClaimableMap<PrimitiveTypeEnum, TypeReference> primitveTypeMappingMap;

	private def static initPrimitiveTypeMap() {
		primitveTypeMappingMap = new ClaimableHashMap<PrimitiveTypeEnum, TypeReference>()
		val stringReference = PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("", "String")
		primitveTypeMappingMap.put(PrimitiveTypeEnum.BOOL, TypesFactory.eINSTANCE.createBoolean)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.BYTE, TypesFactory.eINSTANCE.createByte)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.CHAR, TypesFactory.eINSTANCE.createChar)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.DOUBLE, TypesFactory.eINSTANCE.createDouble)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.INT, TypesFactory.eINSTANCE.createInt)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.LONG, TypesFactory.eINSTANCE.createLong)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.STRING, stringReference)
	}

	public synchronized def static TypeReference claimJaMoPPTypeForPrimitiveDataType(PrimitiveDataType pdt) {
		if (null == primitveTypeMappingMap) {
			initPrimitiveTypeMap()
		}
		return primitveTypeMappingMap.claimValueForKey(pdt.type)
	}
	
	public static def TypeReference claimUniqueCorrespondingJaMoPPDataType(DataType dataType, CorrespondenceInstance ci){
		if(null == dataType){
			return TypesFactory.eINSTANCE.createVoid
		}
		return claimUniqueCorrespondingType(dataType, ci)
	}
	
	private static def dispatch TypeReference claimUniqueCorrespondingType(CollectionDataType cdt, CorrespondenceInstance ci){
		return ci.claimUniqueCorrespondingEObjectByType(cdt, TypeReference)
	}
	
	private static def dispatch TypeReference claimUniqueCorrespondingType(PrimitiveDataType pdt, CorrespondenceInstance ci){
		return claimJaMoPPTypeForPrimitiveDataType(pdt)
	}
	
	private static def dispatch TypeReference claimUniqueCorrespondingType(CompositeDataType cdt, CorrespondenceInstance ci){
		return ci.claimUniqueCorrespondingEObjectByType(cdt, TypeReference)
	}
	
}
