package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.types.Boolean
import org.emftext.language.java.types.Byte
import org.emftext.language.java.types.Char
import org.emftext.language.java.types.Double
import org.emftext.language.java.types.Float
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.Long
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.Short
import org.emftext.language.java.types.TypesFactory

abstract class PCM2JaMoPPUtils {
	private static val Logger logger = Logger.getLogger(PCM2JaMoPPUtils.simpleName)

	private new() {
	}

	def static addEntityName2NameCorrespondence(Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		addCorrespondenceToFeatureCorrespondenceMap(PCMJaMoPPNamespace.PCM::PCM_ATTRIBUTE_ENTITY_NAME,
			PCMJaMoPPNamespace.JaMoPP::JAMOPP_ATTRIBUTE_NAME, featureCorrespondenceMap)
	}

	def static addCorrespondenceToFeatureCorrespondenceMap(String featureNameA, String featureNameB,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		var entityNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(featureNameA)].iterator.next
		var nameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(featureNameB)].iterator.next
		featureCorrespondenceMap.put(entityNameAttribute, nameAttribute)
	}

	def static updateNameAttribute(
		Set<EObject> correspondingEObjects,
		Object newValue,
		EStructuralFeature affectedFeature,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceInstance correspondenceInstance
	) {
		val EStructuralFeature eStructuralFeature = featureCorrespondenceMap.claimValueForKey(affectedFeature)
		var transformationChangeResult = new TransformationChangeResult

		val boolean javaRootAffected = correspondingEObjects.exists[eObject|eObject instanceof JavaRoot]
		if (javaRootAffected) {
			logger.error("The method updateNameattribut is not able to rename java root objects")
		}
		for (EObject correspondingObject : correspondingEObjects) {
			val Resource resource = correspondingObject.eResource
			val boolean isProxy = correspondingObject.eIsProxy
			if (null == correspondingObject || null == correspondingObject.eResource) {
				logger.error(
					"corresponding object is null or correspondingObject is not contained in a resource!: " +
						correspondingObject + " (object.isProxy=" + isProxy + ")")
			} else {
				val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(correspondingObject)
				correspondingObject.eSet(eStructuralFeature, newValue)
				transformationChangeResult.addCorrespondenceToUpdate(correspondenceInstance, oldTUID,
					correspondingObject, null)
				//transformationChangeResult.existingObjectsToSave.add(correspondingObject)
			}
		}
		transformationChangeResult
	}

	/**
	 * Checks whether the affectedAttribute is in the featureCorrespondenceMap and returns all corresponding objects, 
	 * if any. otherwise null is returned.
	 */
	def static checkKeyAndCorrespondingObjects(EObject eObject, EStructuralFeature affectedEFeature,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceInstance correspondenceInstance) {
		if (!featureCorrespondenceMap.containsKey(affectedEFeature)) {
			logger.debug("no feature correspondence found for affectedEeature: " + affectedEFeature)
			return null
		}
		var correspondingEObjects = correspondenceInstance.getAllCorrespondingEObjects(eObject)
		if (correspondingEObjects.nullOrEmpty) {
			logger.info("No corresponding objects found for " + eObject)
		}
		return correspondingEObjects
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(EObject eObject) {
		throw new RuntimeException("eObject " + eObject + " is not a primitve datatype")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Boolean bool) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Boolean")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Int integer) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Integer")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Short shortInt) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Short")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Byte b) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Byte")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Long l) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Long")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Float f) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Float")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Double d) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Double")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Char c) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Char")
	}

	def static NamespaceClassifierReference createAndReturnNamespaceClassifierReferenceForName(String namespace,
		String name) {
		val classifier = ClassifiersFactory.eINSTANCE.createClass
		classifier.setName(name)
		val classifierReference = TypesFactory.eINSTANCE.createClassifierReference
		classifierReference.setTarget(classifier)
		val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
		namespaceClassifierReference.classifierReferences.add(classifierReference)
		namespaceClassifierReference.namespaces.addAll(namespace.split("."))
		return namespaceClassifierReference
	}
}
