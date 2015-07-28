package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import com.google.common.collect.Sets
import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.Interface
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.system.System
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.containers.Package
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.Type
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypedElement

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
			} else {
				parrentCorrespondence = JaMoPP2PCMUtils.
					findParrentCorrepsondenceForPCMElement(pcmElement, correspondenceInstance)
				tcr.existingObjectsToSave.add(pcmElement)
			}
			tcr.addNewCorrespondence(correspondenceInstance, pcmElement, newEObject, parrentCorrespondence)
		}
		tcr
	}

	def dispatch public static Correspondence findParrentCorrepsondenceForPCMElement(EObject object,
		CorrespondenceInstance correspondenceInstance) {
		return null
	}

	def dispatch public static Correspondence findParrentCorrepsondenceForPCMElement(
		RepositoryComponent repoComponent, CorrespondenceInstance correspondenceInstance) {
		return findMainParrentCorrepsondenceForRepository(repoComponent.repository__RepositoryComponent,
			correspondenceInstance)
	}

	def dispatch public static Correspondence findParrentCorrepsondenceForPCMElement(
		InnerDeclaration innerDeclaration, CorrespondenceInstance correspondenceInstance) {
		val correspondences = correspondenceInstance.getAllCorrespondences(
			innerDeclaration.compositeDataType_InnerDeclaration)
		if (correspondences.nullOrEmpty) {
			return null
		}
		return correspondences.iterator.next
	}

	def dispatch public static Correspondence findParrentCorrepsondenceForPCMElement(CompositeDataType cdt,
		CorrespondenceInstance correspondenceInstance) {
		return findMainParrentCorrepsondenceForRepository(cdt.repository__DataType, correspondenceInstance)
	}

	def dispatch public static Correspondence findParrentCorrepsondenceForPCMElement(CollectionDataType cdt,
		CorrespondenceInstance correspondenceInstance) {
		return findMainParrentCorrepsondenceForRepository(cdt.repository__DataType, correspondenceInstance)
	}

	def dispatch public static Correspondence findParrentCorrepsondenceForPCMElement(OperationSignature opSig,
		CorrespondenceInstance correspondenceInstance) {
		val correspondences = correspondenceInstance.getAllCorrespondences(opSig.interface__OperationSignature)
		if (correspondences.nullOrEmpty) {
			return null
		}
		return correspondences.iterator.next
	}

	def dispatch public static Correspondence findParrentCorrepsondenceForPCMElement(OperationInterface opInterface,
		CorrespondenceInstance correspondenceInstance) {
		return findMainParrentCorrepsondenceForRepository(opInterface.repository__Interface, correspondenceInstance)
	}

	def dispatch public static findParrentCorrepsondenceForPCMElement(Parameter pcmParameter,
		CorrespondenceInstance correspondenceInstance) {
		val correspondences = correspondenceInstance.getAllCorrespondences(pcmParameter.operationSignature__Parameter)
		if (correspondences.nullOrEmpty) {
			return null
		}
		return correspondences.iterator.next
	}

	def dispatch static Classifier getTargetClassifierFromTypeReference(TypeReference reference) {
		return null
	}

	def dispatch static Classifier getTargetClassifierFromTypeReference(NamespaceClassifierReference reference) {
		if (reference.classifierReferences.nullOrEmpty) {
			return null
		}
		return getTargetClassifierFromTypeReference(reference.classifierReferences.get(0))
	}

	def dispatch static Classifier getTargetClassifierFromTypeReference(ClassifierReference reference) {
		return reference.target
	}

	def dispatch static Classifier getTargetClassifierFromTypeReference(PrimitiveType reference) {
		return null
	}

	/**
	 * Try to automatically find the corresponding repository component for a given classifier by 
	 * a) looking for a direct component
	 * a2) looking for a direct corresponding System-->it has no corresponding component in that case
	 * b) looking for the a component class in the same package
	 * c) asking the user in which component the classifier is
	 * @param classifier the classifier
	 */
	def static RepositoryComponent getComponentOfConcreteClassifier(ConcreteClassifier classifier,
		CorrespondenceInstance ci, UserInteracting userInteracting) {

		//a)
		var correspondingComponents = ci.getCorrespondingEObjectsByType(classifier, RepositoryComponent)
		if (!correspondingComponents.nullOrEmpty) {
			return correspondingComponents.get(0)
		}
		
		//a2)
		var correspondingComposedProvidingRequiringEntitys = ci.getCorrespondingEObjectsByType(classifier, System)
		if(!correspondingComposedProvidingRequiringEntitys.nullOrEmpty){
			return null
		}			

		//b)
		for (Classifier classifierInSamePackage : classifier.containingCompilationUnit.classifiersInSamePackage) {
			correspondingComponents = ci.getCorrespondingEObjectsByType(classifierInSamePackage, RepositoryComponent)
			if (!correspondingComponents.nullOrEmpty) {
				return correspondingComponents.get(0)
			}
		}

		//c)
		val repo = getRepository(ci)
		val String msg = "Please specify the component for class: " +
			classifier.containingCompilationUnit.namespacesAsString + classifier.name
		val List<String> selections = new ArrayList<String>
		repo.components__Repository.forEach[comp|selections.add(comp.entityName)]
		selections.add("Class is not in any component")
		val int selection = userInteracting.selectFromMessage(UserInteractionType.MODAL, msg, selections)
		if (selection == selections.size) {
			return null
		}
		return repo.components__Repository.get(selection)
	}

	def public static EObject[] checkAndAddOperationRequiredRole(TypedElement typedElement,
		CorrespondenceInstance correspondenceInstance, UserInteracting userInteracting) {
		val Type type = getTargetClassifierFromImplementsReferenceAndNormalizeURI(typedElement.typeReference)
		if (null == type) {
			return null
		}
		val Set<EObject> newCorrespondingEObjects = new HashSet
		val fieldTypeCorrespondences = correspondenceInstance.getAllCorrespondingEObjects(type)
		val correspondingInterfaces = fieldTypeCorrespondences.filter(typeof(OperationInterface))
		var RepositoryComponent repoComponent = null
		if (!correspondingInterfaces.nullOrEmpty) {
			for (correspondingInterface : correspondingInterfaces) {

				//ii)a)
				repoComponent = JaMoPP2PCMUtils.getComponentOfConcreteClassifier(
					typedElement.containingConcreteClassifier, correspondenceInstance, userInteracting)
				if (null == repoComponent) {
					return null
				}
				val OperationRequiredRole operationRequiredRole = RepositoryFactory.eINSTANCE.
					createOperationRequiredRole
				operationRequiredRole.requiredInterface__OperationRequiredRole = correspondingInterface
				operationRequiredRole.requiringEntity_RequiredRole = repoComponent
				operationRequiredRole.entityName = "Component_" + repoComponent.entityName + "_requires_" +
					correspondingInterface.entityName
				newCorrespondingEObjects.add(operationRequiredRole)
				userInteracting.showMessage(UserInteractionType.MODELESS,
					"An OperationRequiredRole (from component " + repoComponent.entityName + " to interface " +
						correspondingInterface.entityName + ") for the element: " + typedElement + " has been created.")
			}
		}

		val correspondingComponents = fieldTypeCorrespondences.filter(typeof(RepositoryComponent))
		if (!correspondingComponents.nullOrEmpty) {
			if (null == repoComponent) {
				repoComponent = JaMoPP2PCMUtils.getComponentOfConcreteClassifier(
					typedElement.containingConcreteClassifier, correspondenceInstance, userInteracting)
			}
			if (null == repoComponent) {
				return null
			}

			//ii)b)
			for (correspondingComponent : correspondingComponents) {
				for (OperationProvidedRole operationProvidedRole : correspondingComponent.
					providedRoles_InterfaceProvidingEntity.filter(typeof(OperationProvidedRole))) {
					var operationInterface = operationProvidedRole.providedInterface__OperationProvidedRole
					val OperationRequiredRole operationRequiredRole = RepositoryFactory.eINSTANCE.
						createOperationRequiredRole
					operationRequiredRole.requiredInterface__OperationRequiredRole = operationInterface
					operationRequiredRole.requiringEntity_RequiredRole = repoComponent
					operationRequiredRole.entityName = "Component_" + repoComponent.entityName + "_requires_" +
						operationInterface.entityName
					userInteracting.showMessage(UserInteractionType.MODELESS,
						"An OperationRequiredRole (from component " + repoComponent.entityName + " to interface " +
							operationInterface.entityName + ") for the element: " + typedElement + " has been created.")
					newCorrespondingEObjects.add(operationRequiredRole)
				}
			}
		}
		return newCorrespondingEObjects
	}
	
	def public static Classifier getTargetClassifierFromImplementsReferenceAndNormalizeURI(TypeReference reference) {
		val interfaceClassifier = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(reference)
		if (null == interfaceClassifier) {
			return null
		}
		normalizeURI(interfaceClassifier)
		return interfaceClassifier
	}
	
	def public static normalizeURI(EObject eObject){
		val resource = eObject.eResource
		val resourceSet = resource.resourceSet
		val uri = resource.URI
		val uriConverter = resourceSet.URIConverter
		val normalizedURI = uriConverter.normalize(uri)
		resource.URI = normalizedURI
	}
	
}
