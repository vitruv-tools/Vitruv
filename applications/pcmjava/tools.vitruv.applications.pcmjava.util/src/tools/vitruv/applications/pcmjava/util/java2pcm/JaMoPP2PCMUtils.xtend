package tools.vitruv.applications.pcmjava.util.java2pcm

import com.google.common.collect.Sets
import tools.vitruv.framework.userinteraction.UserInteractionType
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.tuid.TUID
import tools.vitruv.framework.util.datatypes.ClaimableMap
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
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.Type
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypedElement
import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.system.System

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.applications.pcmjava.util.PCMJaMoPPUtils
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.domains.pcm.PcmNamespace
import tools.vitruv.domains.java.JavaNamespace

abstract class JaMoPP2PCMUtils extends PCMJaMoPPUtils {
	private new() {
	}

	private static Logger logger = Logger.getLogger(JaMoPP2PCMUtils.simpleName)

	def public static Repository getRepository(CorrespondenceModel correspondenceModel) {
		val Set<Repository> repos = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Repository)
		if (repos.nullOrEmpty) {
			return null
		}
		if (1 != repos.size) {
			logger.warn("found more than one repository. Retruning the first")
		}
		return repos.get(0)
	}

	def static addJaMoPP2PCMCorrespondenceToFeatureCorrespondenceMap(String jaMoPPFeatureName, String pcmFeatureName,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		var nameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter [ attribute |
			attribute.name.equalsIgnoreCase(jaMoPPFeatureName)
		].iterator.next
		var entityNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter [ attribute |
			attribute.name.equalsIgnoreCase(pcmFeatureName)
		].iterator.next
		featureCorrespondenceMap.put(nameAttribute, entityNameAttribute)
	}

	def static addName2EntityNameCorrespondence(Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		addJaMoPP2PCMCorrespondenceToFeatureCorrespondenceMap(JavaNamespace.JAMOPP_ATTRIBUTE_NAME,
			PcmNamespace.PCM_ATTRIBUTE_ENTITY_NAME, featureCorrespondenceMap)
	}

	def static updateNameAttribute(
		Set<EObject> correspondingEObjects,
		Object newValue,
		EStructuralFeature affectedFeature,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceModel correspondenceModel,
		boolean saveFilesOfChangedEObjects
	) {
		val Set<Class<? extends EObject>> pcmRootClasses = Sets.newHashSet(Repository, System)
		updateNameAttribute(correspondingEObjects, newValue, affectedFeature, featureCorrespondenceMap,
			correspondenceModel, saveFilesOfChangedEObjects, pcmRootClasses)
	}

	def static void updateNameAttributeForPCMRootObjects(Iterable<NamedElement> pcmRootElements,
		EStructuralFeature affectedFeature, Object newValue, CorrespondenceModel correspondenceModel,
		ChangePropagationResult transformationResult) {
			for (pcmRoot : pcmRootElements) {
				if (!(pcmRoot instanceof Repository) && !(pcmRoot instanceof System)) {
					logger.warn("EObject " + pcmRoot + " is not an instance of a PCM Root object - element" + pcmRoot +
						"will not be renamed")

			} else {
				val TUID oldTUID = correspondenceModel.calculateTUIDFromEObject(pcmRoot)
	
				// change name		
				pcmRoot.entityName = newValue.toString;
	
				val VURI oldVURI = VURI.getInstance(pcmRoot.eResource.getURI)
				PCMJaMoPPUtils.handleRootChanges(pcmRoot, correspondenceModel, oldVURI, transformationResult, oldVURI, oldTUID)
				transformationResult.addVuriToDeleteIfNotNull(oldVURI)
			}
		}
	}
	
	def static void updateNameAsSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap, CorrespondenceModel correspondenceModel, 
		ChangePropagationResult transformationResult) {
		val correspondingEObjects = PCMJaMoPPUtils.checkKeyAndCorrespondingObjects(eObject, affectedAttribute,
			featureCorrespondenceMap, correspondenceModel)
		if (correspondingEObjects.nullOrEmpty) {
			return
		}
		val Set<NamedElement> rootPCMEObjects = new HashSet
		rootPCMEObjects.addAll(correspondingEObjects.filter(typeof(Repository)))
		rootPCMEObjects.addAll(correspondingEObjects.filter(typeof(System)))
		var boolean saveFilesOfChangedEObjects = true
		if (!rootPCMEObjects.nullOrEmpty) {
			saveFilesOfChangedEObjects = false
		}
		JaMoPP2PCMUtils.updateNameAttribute(correspondingEObjects, newValue, affectedAttribute,
			featureCorrespondenceMap, correspondenceModel, saveFilesOfChangedEObjects)
		if (!rootPCMEObjects.nullOrEmpty) {
			JaMoPP2PCMUtils.updateNameAttributeForPCMRootObjects(rootPCMEObjects, affectedAttribute, newValue,
				correspondenceModel, transformationResult)
		}
		return
	}
	
	def static createNewCorrespondingEObjects(EObject newEObject, EObject[] newCorrespondingEObjects,
		CorrespondenceModel correspondenceModel, ChangePropagationResult transformationResult) {
		if (newCorrespondingEObjects.nullOrEmpty) {
			return
		}
		for (pcmElement : newCorrespondingEObjects) {
			if (pcmElement instanceof Repository || pcmElement instanceof System) {
				PCMJaMoPPUtils.addRootChangeToTransformationResult(pcmElement, correspondenceModel,
					PCMJaMoPPUtils.getSourceModelVURI(newEObject), transformationResult)
			} else {
				// do nothing. save will be done later
			}
			correspondenceModel.createAndAddCorrespondence(pcmElement, newEObject)
		}
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
		CorrespondenceModel ci, UserInteracting userInteracting) {
	
		// a)
		var correspondingComponents = ci.getCorrespondingEObjectsByType(classifier, RepositoryComponent)
		if (!correspondingComponents.nullOrEmpty) {
			return correspondingComponents.get(0)
		}
	
		// a2)
		var correspondingComposedProvidingRequiringEntitys = ci.getCorrespondingEObjectsByType(classifier, System)
		if (!correspondingComposedProvidingRequiringEntitys.nullOrEmpty) {
			return null
		}
	
		// b)
		for (Classifier classifierInSamePackage : classifier.containingCompilationUnit.classifiersInSamePackage) {
			correspondingComponents = ci.getCorrespondingEObjectsByType(classifierInSamePackage,
				RepositoryComponent)
			if (!correspondingComponents.nullOrEmpty) {
				return correspondingComponents.get(0)
			}
		}
	
		// c)
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
		CorrespondenceModel correspondenceModel, UserInteracting userInteracting) {
		val Type type = getTargetClassifierFromImplementsReferenceAndNormalizeURI(typedElement.typeReference)
		if (null == type) {
			return null
		}
		val Set<EObject> newCorrespondingEObjects = new HashSet
		val fieldTypeCorrespondences = correspondenceModel.getCorrespondingEObjects(type)
		val correspondingInterfaces = fieldTypeCorrespondences.filter(typeof(OperationInterface))
		var RepositoryComponent repoComponent = null
		if (!correspondingInterfaces.nullOrEmpty) {
			for (correspondingInterface : correspondingInterfaces) {
	
				// ii)a)
				repoComponent = JaMoPP2PCMUtils.getComponentOfConcreteClassifier(
					typedElement.containingConcreteClassifier, correspondenceModel, userInteracting)
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
						correspondingInterface.entityName + ") for the element: " + typedElement +
						" has been created.")
					}
				}
	
				val correspondingComponents = fieldTypeCorrespondences.filter(typeof(RepositoryComponent))
				if (!correspondingComponents.nullOrEmpty) {
					if (null == repoComponent) {
						repoComponent = JaMoPP2PCMUtils.getComponentOfConcreteClassifier(
							typedElement.containingConcreteClassifier, correspondenceModel, userInteracting)
					}
					if (null == repoComponent) {
						return null
					}
	
					// ii)b)
					for (correspondingComponent : correspondingComponents) {
						for (OperationProvidedRole operationProvidedRole : correspondingComponent.
							providedRoles_InterfaceProvidingEntity.filter(typeof(OperationProvidedRole))) {
							var operationInterface = operationProvidedRole.providedInterface__OperationProvidedRole
							val OperationRequiredRole operationRequiredRole = RepositoryFactory.eINSTANCE.
								createOperationRequiredRole
							operationRequiredRole.requiredInterface__OperationRequiredRole = operationInterface
							operationRequiredRole.requiringEntity_RequiredRole = repoComponent
							operationRequiredRole.entityName = "Component_" + repoComponent.entityName +
								"_requires_" + operationInterface.entityName
							userInteracting.showMessage(UserInteractionType.MODELESS,
								"An OperationRequiredRole (from component " + repoComponent.entityName +
									" to interface " + operationInterface.entityName + ") for the element: " +
									typedElement + " has been created.")
							newCorrespondingEObjects.add(operationRequiredRole)
						}
					}
				}
				return newCorrespondingEObjects
			}
	
	def public static Classifier getTargetClassifierFromImplementsReferenceAndNormalizeURI(
		TypeReference reference) {
		var interfaceClassifier = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(reference)
		if (null == interfaceClassifier) {
			return null
		}
		
		if(interfaceClassifier.eIsProxy){
			val resSet = reference.eResource.resourceSet
			interfaceClassifier = EcoreUtil.resolve(interfaceClassifier, resSet) as Classifier			
		}
		normalizeURI(interfaceClassifier)
		return interfaceClassifier
	}

	def public static normalizeURI(EObject eObject) {
		if (null == eObject.eResource || null == eObject.eResource.resourceSet) {
			return false
		}
		val resource = eObject.eResource
		val resourceSet = resource.resourceSet
		val uri = resource.getURI
		val uriConverter = resourceSet.getURIConverter
		val normalizedURI = uriConverter.normalize(uri)
		resource.URI = normalizedURI
		return true
	}

}
