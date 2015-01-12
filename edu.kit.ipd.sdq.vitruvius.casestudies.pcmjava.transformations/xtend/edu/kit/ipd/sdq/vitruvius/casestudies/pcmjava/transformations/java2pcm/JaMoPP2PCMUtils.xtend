package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import com.google.common.collect.Sets
import de.uka.ipd.sdq.pcm.core.entity.NamedElement
import de.uka.ipd.sdq.pcm.repository.Repository
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import de.uka.ipd.sdq.pcm.system.System
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import java.util.HashSet
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.emftext.language.java.classifiers.ClassifiersFactory

abstract class JaMoPP2PCMUtils extends PCMJaMoPPUtils {
	private new() {
	}

	private static Logger logger = Logger.getLogger(JaMoPP2PCMUtils.simpleName)

	def public static Repository getRepository(CorrespondenceInstance correspondenceInstance) {
		val Set<Repository> repos = correspondenceInstance.getAllEObjectsInCorrespondencesWithType(Repository)
		if (repos.nullOrEmpty) {
			throw new RuntimeException("Could not find a repository")
		}
		if (1 != repos.size) {
			logger.warn("found more than one repository. Retruning the first")
		}
		return repos.get(0)
	}

	def static addJaMoPP2PCMCorrespondenceToFeatureCorrespondenceMap(String jaMoPPFeatureName, String pcmFeatureName,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		var nameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(jaMoPPFeatureName)].iterator.next
		var entityNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(pcmFeatureName)].iterator.next
		featureCorrespondenceMap.put(nameAttribute, entityNameAttribute)
	}

	def static addName2EntityNameCorrespondence(Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		addJaMoPP2PCMCorrespondenceToFeatureCorrespondenceMap(PCMJaMoPPNamespace.JaMoPP::JAMOPP_ATTRIBUTE_NAME,
			PCMJaMoPPNamespace.PCM::PCM_ATTRIBUTE_ENTITY_NAME, featureCorrespondenceMap)
	}

	def static updateNameAttribute(
		Set<EObject> correspondingEObjects,
		Object newValue,
		EStructuralFeature affectedFeature,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceInstance correspondenceInstance,
		boolean markFilesOfChangedEObjectsAsFilesToSave
	) {
		val Set<Class<? extends EObject>> pcmRootClasses = Sets.newHashSet(Repository, System)
		updateNameAttribute(correspondingEObjects, newValue, affectedFeature, featureCorrespondenceMap,
			correspondenceInstance, markFilesOfChangedEObjectsAsFilesToSave, pcmRootClasses)
	}

	def static void updateNameAttributeForPCMRootObjects(Iterable<NamedElement> pcmRootElements,
		EStructuralFeature affectedFeature, Object newValue, TransformationChangeResult transformationChangeResult,
		CorrespondenceInstance correspondenceInstance) {
		for (pcmRoot : pcmRootElements) {
			if (!(pcmRoot instanceof Repository) && !(pcmRoot instanceof System)) {
				logger.warn(
					"EObject " + pcmRoot + " is not an instance of a PCM Root object - element" + pcmRoot +
						"will not be renamed")

			} else {
				val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(pcmRoot)

				//change name		
				pcmRoot.entityName = newValue.toString;

				val VURI oldVURI = VURI.getInstance(pcmRoot.eResource.getURI)
				transformationChangeResult.existingObjectsToDelete.add(oldVURI)
				transformationChangeResult.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, pcmRoot, null)
				transformationChangeResult.newRootObjectsToSave.add(pcmRoot)
			}
		}
	}

	def static TransformationChangeResult updateNameAsSingleValuedEAttribute(EObject eObject,
		EAttribute affectedAttribute, Object oldValue, Object newValue,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceInstance correspondenceInstance) {
		val correspondingEObjects = PCMJaMoPPUtils.checkKeyAndCorrespondingObjects(eObject, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance)
		if (correspondingEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val Set<NamedElement> rootPCMEObjects = new HashSet
		rootPCMEObjects.addAll(correspondingEObjects.filter(typeof(Repository)))
		rootPCMEObjects.addAll(correspondingEObjects.filter(typeof(System)))
		var boolean markFilesOfChangedEObjectsAsFilesToSave = true
		if (!rootPCMEObjects.nullOrEmpty) {
			markFilesOfChangedEObjectsAsFilesToSave = false
		}
		val tcr = JaMoPP2PCMUtils.updateNameAttribute(correspondingEObjects, newValue, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance, markFilesOfChangedEObjectsAsFilesToSave)
		if (!rootPCMEObjects.nullOrEmpty) {
			JaMoPP2PCMUtils.updateNameAttributeForPCMRootObjects(rootPCMEObjects, affectedAttribute, newValue, tcr,
				correspondenceInstance)
		}
		return tcr
	}

}
