package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMNamespace
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.CompilationUnit
import org.eclipse.emf.ecore.EAttribute
import org.apache.log4j.Logger

abstract class PCM2JaMoPPUtils {
	private static val Logger logger = Logger.getLogger("PCM2JaMoPPUtils")

	def private PCM2JaMoPPUtils() {
	}

	def static addEntityName2NameCorrespondence(Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		var entityNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPPCMNamespace::PCM_ATTRIBUTE_ENTITY_NAME)].iterator.next
		var nameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPPCMNamespace::JAMOPP_ATTRIBUTE_NAME)].iterator.next
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

		// we need to know whether a CompilationUnit is affected. Reason: if a CompilationUnit is changed
		// we have to delete the old one and save the new one. However, if more than one changes in the same Compilation Unit
		// occur we would recreate the old CompilationUnit if we save it. 
		val boolean compilationUnitAffected = correspondingEObjects.exists[eObject|eObject instanceof CompilationUnit]
		for (EObject correspondingObject : correspondingEObjects) {
			val oldObject = EcoreUtil.copy(correspondingObject)

			// compilationUnit was renamed: Delete old one and save new one
			if (correspondingObject instanceof CompilationUnit) {
				transformationChangeResult.existingObjectsToDelete.add(oldObject)
			}
			correspondingObject.eClass.eSet(eStructuralFeature, newValue)
			correspondenceInstance.update(oldObject, correspondingObject)
			if (correspondingObject instanceof CompilationUnit) {
				transformationChangeResult.newRootObjectsToSave.add(correspondingObject)
			} else if (!compilationUnitAffected) {
				transformationChangeResult.existingObjectsToSave.add(correspondingObject)
			}
		}
		transformationChangeResult
	}

	def static checkKeyAndCorrespondingObjects(EObject eObject, EAttribute affectedAttribute,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceInstance correspondenceInstance) {
		if (!featureCorrespondenceMap.containsKey(affectedAttribute)) {
			logger.info("no feature correspondence found for affected Attribute: " + affectedAttribute)
			return null
		}
		var correspondingEObjects = correspondenceInstance.getAllCorrespondingEObjects(eObject)
		if (correspondingEObjects.nullOrEmpty) {
			logger.info("No corresponding objects found for " + eObject)
		}
		return correspondingEObjects
	}

}
