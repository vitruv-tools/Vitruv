package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import java.util.HashMap
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.types.Boolean
import org.emftext.language.java.types.Byte
import org.emftext.language.java.types.Char
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.Double
import org.emftext.language.java.types.Float
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.Long
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.Short
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.Void
import org.emftext.language.java.types.impl.BooleanImpl
import org.emftext.language.java.types.impl.ByteImpl
import org.emftext.language.java.types.impl.CharImpl
import org.emftext.language.java.types.impl.DoubleImpl
import org.emftext.language.java.types.impl.FloatImpl
import org.emftext.language.java.types.impl.IntImpl
import org.emftext.language.java.types.impl.LongImpl
import org.emftext.language.java.types.impl.ShortImpl

/**
 * Helper to map type References to PCM data types
 * 
 */
class TypeReferenceCorrespondenceHelper {
	private new() {
	}

	private static final Logger logger = Logger.getLogger(TypeReferenceCorrespondenceHelper.simpleName)

	private static var ClaimableMap<Class<? extends PrimitiveType>, DataType> primitveTypeMappingMap;

	private def static initPrimitiveTypeMap() {
		primitveTypeMappingMap = new ClaimableHashMap
		primitveTypeMappingMap.put(Boolean,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("BOOL"))
		primitveTypeMappingMap.put(BooleanImpl,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("BOOL"))
		primitveTypeMappingMap.put(Byte,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("BYTE"))
			primitveTypeMappingMap.put(ByteImpl,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("BYTE"))
		primitveTypeMappingMap.put(Char,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("CHAR"))
		primitveTypeMappingMap.put(CharImpl,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("CHAR"))
		primitveTypeMappingMap.put(Double,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("DOUBLE"))
		primitveTypeMappingMap.put(DoubleImpl,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("DOUBLE"))
		primitveTypeMappingMap.put(Int,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("INT"))
		primitveTypeMappingMap.put(IntImpl,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("INT"))
		primitveTypeMappingMap.put(Long,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("INT"))
		primitveTypeMappingMap.put(LongImpl,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("INT"))
		primitveTypeMappingMap.put(Float,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("DOUBLE"))
		primitveTypeMappingMap.put(FloatImpl,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("DOUBLE"))
		primitveTypeMappingMap.put(Short,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("INT"))
		primitveTypeMappingMap.put(ShortImpl,
			TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypes.get("INT"))
	}

	private synchronized def static DataType claimPCMDataTypeForJaMoPPPrimitiveType(PrimitiveType primitiveType) {
		if(primitiveType instanceof Void){
			return null
		}
		if (null == primitveTypeMappingMap) {
			initPrimitiveTypeMap()
		}
		return primitveTypeMappingMap.claimValueForKey(primitiveType.class)
	}

	public def static DataType getCorrespondingPCMDataTypeForTypeReference(TypeReference typeReference,
		CorrespondenceInstance correspondenceInstance, UserInteracting userInteracting, Repository repo) {
		if (typeReference instanceof PrimitiveType) {
			return claimPCMDataTypeForJaMoPPPrimitiveType(typeReference as PrimitiveType)
		}
		if (typeReference instanceof ClassifierReference) {
			return getPCMDataTypeForClassifierReference(typeReference as ClassifierReference, correspondenceInstance,
				userInteracting, repo)
		}
		if (typeReference instanceof NamespaceClassifierReference) {
			return getPCMDataTypeForNamespaceClassifierReference(typeReference as NamespaceClassifierReference,
				correspondenceInstance, userInteracting, repo)
		}
		logger.error("Could not find a PCM data type for type reference " + typeReference)
		return null
	}

	def private static DataType getPCMDataTypeForNamespaceClassifierReference(NamespaceClassifierReference reference,
		CorrespondenceInstance correspondenceInstance, UserInteracting userInteracting, Repository repo) {
		if (!reference.classifierReferences.nullOrEmpty) {

			//just create the data type from the first classifier that is non null
			for (classifierRef : reference.classifierReferences) {
				if (null != classifierRef) {
					return getPCMDataTypeForClassifierReference(classifierRef, correspondenceInstance,
						userInteracting, repo)
				}
			}
		}
		logger.error(
			"Could not find a PCM data type for namespace classifier reference " + reference +
				" Possible reason: no classifierReference found inside namespace classifier reference.")
		return null
	}

	def private static DataType getPCMDataTypeForClassifierReference(ClassifierReference classifierReference,
		CorrespondenceInstance correspondenceInstance, UserInteracting userInteracting, Repository repo) {
		val Classifier classifier = classifierReference.target
		if (null != classifier) {

			//if classifier is string return primitive type string
			if (classifier.name.equalsIgnoreCase("String")) {
				return TypeReferenceCorrespondenceHelper.PrimitiveTypeCorrespondenceHelper.getPrimitiveDataTypeString()
			}
			var Set<DataType> dataTypes = null
			try {
				dataTypes = correspondenceInstance.getCorrespondingEObjectsByType(classifier, DataType)
			} catch (Throwable t) {
				logger.info("No correspondence found for classifier")
				return null
			}
			if (!dataTypes.nullOrEmpty) {
				if (1 == dataTypes.size) {

					// found only datatype
					return dataTypes.get(0)
				} else {
					logger.warn("more than one datatype correspondences found: returning the first");
					return dataTypes.get(0)
				}
			}

			// no data type found -->create one from the class
			val DataType newDataType = createDataTypeForClassifier(classifier, correspondenceInstance,
				userInteracting, repo)
			return newDataType
		}
		return null
	}

	def private static DataType createDataTypeForClassifier(Classifier classifier,
		CorrespondenceInstance correspondenceInstance, UserInteracting userInteracting, Repository repo) {
		if (null == classifier) {
			logger.warn("Classifier is null! Can not create a data type for the classifier")
			return null
		}
		val correspondingPCMEObjects = correspondenceInstance.getCorrespondingEObjectsByType(classifier, NamedElement)
		var String correspondingWarning = ""
		if (!correspondingPCMEObjects.nullOrEmpty) {
			correspondingWarning = System.getProperty("line.seperator") + "Warning: the classifier " +
				classifier.name + " already correspond to the PCM Elements: "
			for (namedElement : correspondingPCMEObjects) {
				correspondingWarning = correspondingWarning + System.getProperty("line.seperator") + namedElement.class.
					simpleName + ": " + namedElement.entityName
			}
		}

		//create a composite data type
		val CompositeDataType cdt = RepositoryFactory.eINSTANCE.createCompositeDataType
		cdt.entityName = classifier.name
		cdt.repository__DataType = repo
		correspondenceInstance.createAndAddEObjectCorrespondence(cdt, classifier)
		
		/*val String message = "Automatically created the corresponding composite data type " + cdt.entityName +
			" for classifier " + classifier.name + correspondingWarning
		userInteracting.showMessage(UserInteractionType.MODELESS, message)*/
		return cdt
	}

	/**
	 * loads the primitive PCM types from standard primitive types URI
	 * (partially) copied from DefaultResourceEnvironment from SoMoX
	 */
	private static class PrimitiveTypeCorrespondenceHelper {
		private new() {
		}

		val private static String PRIMITIVETYPES_URI = "platform:/plugin/org.palladiosimulator.pcm.resources/defaultModels/PrimitiveTypes.repository";

		var private static Repository primitiveTypesRepository;
		var private static Map<String, PrimitiveDataType> primitives = new HashMap<String, PrimitiveDataType>()
		/**
	     * Retrieves a map of {@link PrimitiveDataType}s as defined in the
	     * standard PCM resource repository.
	     *
	     * @return A cached map of primitive data types.
     	*/
		def public static Map<String, PrimitiveDataType> getPrimitiveDataTypes() {
			if (primitiveTypesRepository == null) {
				primitiveTypesRepository = getPrimitiveTypesRepository();
				for (DataType d : primitiveTypesRepository.getDataTypes__Repository()) {
					if (d instanceof PrimitiveDataType) {
						val PrimitiveDataType pdt = d as PrimitiveDataType;
						primitives.put(pdt.getType().getName(), pdt);
					}
				}
			}
			return primitives;
		}

		def private static Repository getPrimitiveTypesRepository() {
			if (primitiveTypesRepository != null) {
				return primitiveTypesRepository;
			}

			val Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			val Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("repository", new XMIResourceFactoryImpl());

			val URI uri = URI.createURI(PRIMITIVETYPES_URI);

			val ResourceSet resSet = new ResourceSetImpl();
			val Resource resource = resSet.getResource(uri, true);

			primitiveTypesRepository = resource.getContents().get(0) as Repository;
			return primitiveTypesRepository;
		}
	
		def static getPrimitiveDataTypeString() {
			return getPrimitiveDataTypes().get("STRING")
		}

	}

}
