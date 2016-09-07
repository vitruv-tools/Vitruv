package tools.vitruv.applications.pcmjava.util.pcm2java

import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.framework.util.datatypes.ClaimableHashMap
import tools.vitruv.framework.util.datatypes.ClaimableMap
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.types.Type
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory

import static extension tools.vitruv.framework.util.bridges.CollectionBridge.*
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.framework.correspondence.CorrespondenceModel

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
class DataTypeCorrespondenceHelper {

	private new() {
	}

	private static final Logger logger = Logger.getLogger(DataTypeCorrespondenceHelper.simpleName)

	private static var ClaimableMap<PrimitiveTypeEnum, Type> primitveTypeMappingMap;

	private def static initPrimitiveTypeMap() {
		primitveTypeMappingMap = new ClaimableHashMap<PrimitiveTypeEnum, Type>()
		val stringClassifier = ClassifiersFactory.eINSTANCE.createClass
		stringClassifier.setName("String")
		primitveTypeMappingMap.put(PrimitiveTypeEnum.BOOL, TypesFactory.eINSTANCE.createBoolean)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.BYTE, TypesFactory.eINSTANCE.createByte)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.CHAR, TypesFactory.eINSTANCE.createChar)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.DOUBLE, TypesFactory.eINSTANCE.createDouble)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.INT, TypesFactory.eINSTANCE.createInt)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.LONG, TypesFactory.eINSTANCE.createLong)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.STRING, stringClassifier)
	}

	public synchronized def static Type claimJaMoPPTypeForPrimitiveDataType(PrimitiveDataType pdt) {
		if (null == primitveTypeMappingMap) {
			initPrimitiveTypeMap()
		}
		return EcoreUtil.copy(primitveTypeMappingMap.claimValueForKey(pdt.type))
	}

	public static def TypeReference claimUniqueCorrespondingJaMoPPDataTypeReference(DataType dataType,
		CorrespondenceModel ci) {
		if (null == dataType) {
			return TypesFactory.eINSTANCE.createVoid
		}
		val Type type = claimUniqueCorrespondingType(dataType, ci)
		if (type instanceof TypeReference) {
			return type
		} else if (type instanceof ConcreteClassifier) {
			return PCM2JaMoPPUtils.createNamespaceClassifierReference(type as ConcreteClassifier)
		}
		logger.warn(
			"found type " + type +
				"is neither a TypeReference nor a ConcreteClassifier - could not create and return TypeReference")
		return TypesFactory.eINSTANCE.createClassifierReference
	}

	public static def Type claimUniqueCorrespondingJaMoPPDataType(DataType dataType, CorrespondenceModel ci) {
		if (null == dataType) {
			return TypesFactory.eINSTANCE.createVoid
		}
		return claimUniqueCorrespondingType(dataType, ci)
	}

	private static def dispatch Type claimUniqueCorrespondingType(CollectionDataType cdt, CorrespondenceModel ci) {
		return ci.getCorrespondingEObjectsByType(cdt, ConcreteClassifier).claimOne
	}

	private static def dispatch Type claimUniqueCorrespondingType(PrimitiveDataType pdt, CorrespondenceModel ci) {
		return claimJaMoPPTypeForPrimitiveDataType(pdt)
	}

	private static def dispatch Type claimUniqueCorrespondingType(CompositeDataType cdt, CorrespondenceModel ci) {
		return ci.getCorrespondingEObjectsByType(cdt, ConcreteClassifier).claimOne
	}
}
