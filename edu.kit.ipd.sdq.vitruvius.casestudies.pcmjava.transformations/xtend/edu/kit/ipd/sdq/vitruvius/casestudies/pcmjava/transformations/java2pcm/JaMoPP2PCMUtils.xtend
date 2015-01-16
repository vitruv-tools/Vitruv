package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import com.google.common.collect.Sets
import de.uka.ipd.sdq.pcm.core.entity.NamedElement
import de.uka.ipd.sdq.pcm.repository.CollectionDataType
import de.uka.ipd.sdq.pcm.repository.CompositeDataType
import de.uka.ipd.sdq.pcm.repository.Interface
import de.uka.ipd.sdq.pcm.repository.OperationInterface
import de.uka.ipd.sdq.pcm.repository.Repository
import de.uka.ipd.sdq.pcm.repository.RepositoryComponent
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import de.uka.ipd.sdq.pcm.system.System
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
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
import org.emftext.language.java.containers.Package

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

	/**
	 * Tries to find the correct (main) correspondence for the given repository.
	 * Since the main repository has at least three corresponding packages (the main package as well as the datatypes
	 * and contracts packages) we try to find the corresondence that is the one to the main package - if it can not
	 * be found we return the first parent correspondence   
	 */
	def static public findMainParrentCorrepsondenceForRepository(Repository repo,
		CorrespondenceInstance correspondenceInstance) {
		val correspondences = correspondenceInstance.getAllCorrespondences(repo)
		if (!correspondences.nullOrEmpty) {
			for (correspondence : correspondences) {
				if (correspondence instanceof EObjectCorrespondence) {
					val eoc = correspondence as EObjectCorrespondence
					if (isTUIDMainPackageCorrespondence(eoc.elementBTUID, repo, correspondenceInstance) ||
						isTUIDMainPackageCorrespondence(eoc.elementATUID, repo, correspondenceInstance)) {
						return correspondence
					}
				}
			}
			return correspondences.get(0)
		}
		logger.info(
			"Could not find any correspondence for repository " + repo +
				". The newly created correspondence will not have a parent correspondence")
		return null
	}

	def static public dispatch findMainParrentCorrepsondenceForPCMElement(RepositoryComponent repoComponent,
		CorrespondenceInstance correspondenceInstance) {
		findMainParrentCorrepsondenceForRepository(repoComponent.repository__RepositoryComponent, correspondenceInstance)
	}

	def static public dispatch findMainParrentCorrepsondenceForPCMElement(Repository repo,
		CorrespondenceInstance correspondenceInstance) {
		findMainParrentCorrepsondenceForRepository(repo, correspondenceInstance)
	}

	def static public dispatch findMainParrentCorrepsondenceForPCMElement(Interface pcmInterface,
		CorrespondenceInstance correspondenceInstance) {
		findMainParrentCorrepsondenceForRepository(pcmInterface.repository__Interface, correspondenceInstance)
	}

	def static public dispatch findMainParrentCorrepsondenceForPCMElement(System pcmSystem,
		CorrespondenceInstance correspondenceInstance) {
		return null
	}

	def static public dispatch findMainParrentCorrepsondenceForPCMElement(NamedElement pcmNamedElement,
		CorrespondenceInstance correspondenceInstance) {
		logger.info(
			"No specific parrent correspondence found for PCM Element " + pcmNamedElement.entityName + " - use null")
		return null
	}

	def static public dispatch findMainParrentCorrepsondenceForPCMElement(EObject eObject,
		CorrespondenceInstance correspondenceInstance) {
		logger.warn(
			"EObject " + eObject +
				" is not a PCM element. No parrent correspondence can be found for the Object - returning null")
		return null
	}

	def static private boolean isTUIDMainPackageCorrespondence(TUID tuid, Repository repo,
		CorrespondenceInstance correspondenceInstance) {
		val EObject eObject = correspondenceInstance.resolveEObjectFromTUID(tuid)
		if (eObject instanceof Package) {
			val jaMoPPPackage = eObject as Package
			if (jaMoPPPackage.name.equals(repo.entityName)) {

				// found correspondence for main package
				return true
			}
		}
		return false
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

	def static TransformationChangeResult createTransformationChangeResultForNewCorrespondingEObjects(EObject newEObject,
		EObject[] newCorrespondingEObjects, CorrespondenceInstance correspondenceInstance) {
		if (newCorrespondingEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val tcr = new TransformationChangeResult
		var Correspondence parrentCorrespondence = null
		for (pcmElement : newCorrespondingEObjects) {
			parrentCorrespondence = null
			if (pcmElement instanceof Repository || pcmElement instanceof System) {
				tcr.newRootObjectsToSave.add(pcmElement)
			} else{ 
				parrentCorrespondence = JaMoPP2PCMUtils.
					findMainParrentCorrepsondenceForRepositoryFromPCMElement(pcmElement,
						correspondenceInstance)
				tcr.existingObjectsToSave.add(pcmElement)
			} 
			tcr.addNewCorrespondence(correspondenceInstance, pcmElement, newEObject, parrentCorrespondence)
		}
		tcr
	}
	
	def dispatch private static Correspondence findMainParrentCorrepsondenceForRepositoryFromPCMElement(EObject object, CorrespondenceInstance correspondenceInstance){
		return null
	}
	
	def dispatch private static Correspondence findMainParrentCorrepsondenceForRepositoryFromPCMElement(RepositoryComponent repoComponent, CorrespondenceInstance correspondenceInstance){
		return findMainParrentCorrepsondenceForRepository(repoComponent.repository__RepositoryComponent, correspondenceInstance)
	}
	
	def dispatch private static Correspondence findMainParrentCorrepsondenceForRepositoryFromPCMElement(CompositeDataType cdt, CorrespondenceInstance correspondenceInstance){
		return findMainParrentCorrepsondenceForRepository(cdt.repository__DataType, correspondenceInstance)
	}
	
	def dispatch private static Correspondence findMainParrentCorrepsondenceForRepositoryFromPCMElement(CollectionDataType cdt, CorrespondenceInstance correspondenceInstance){
		return findMainParrentCorrepsondenceForRepository(cdt.repository__DataType, correspondenceInstance)
	}
	
	def dispatch private static Correspondence findMainParrentCorrepsondenceForRepositoryFromPCMElement(OperationInterface opInterface, CorrespondenceInstance correspondenceInstance){
		return findMainParrentCorrepsondenceForRepository(opInterface.repository__Interface, correspondenceInstance)
	}

}
