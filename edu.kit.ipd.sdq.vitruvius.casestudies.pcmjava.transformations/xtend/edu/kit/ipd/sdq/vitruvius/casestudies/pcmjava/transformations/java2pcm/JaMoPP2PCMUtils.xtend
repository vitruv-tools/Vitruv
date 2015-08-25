package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import com.google.common.collect.Sets
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
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
		boolean saveFilesOfChangedEObjects
	) {
		val Set<Class<? extends EObject>> pcmRootClasses = Sets.newHashSet(Repository, System)
		updateNameAttribute(correspondingEObjects, newValue, affectedFeature, featureCorrespondenceMap,
			correspondenceInstance, saveFilesOfChangedEObjects, pcmRootClasses)
	}

	def static void updateNameAttributeForPCMRootObjects(Iterable<NamedElement> pcmRootElements,
		EStructuralFeature affectedFeature, Object newValue,
		Blackboard blackboard) {
		for (pcmRoot : pcmRootElements) {
			if (!(pcmRoot instanceof Repository) && !(pcmRoot instanceof System)) {
				logger.warn(
					"EObject " + pcmRoot + " is not an instance of a PCM Root object - element" + pcmRoot +
						"will not be renamed")

			} else {
				val TUID oldTUID = blackboard.correspondenceInstance.calculateTUIDFromEObject(pcmRoot)

				//change name		
				pcmRoot.entityName = newValue.toString;

				val VURI oldVURI = VURI.getInstance(pcmRoot.eResource.getURI)
				TransformationUtils.deleteFile(oldVURI)
				blackboard.correspondenceInstance.update(oldTUID, pcmRoot)
				PCMJaMoPPUtils.saveEObject(pcmRoot, blackboard, oldVURI)
			}
		}
	}

	def static void updateNameAsSingleValuedEAttribute(EObject eObject,
		EAttribute affectedAttribute, Object oldValue, Object newValue,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		Blackboard blackboard) {
		val correspondingEObjects = PCMJaMoPPUtils.checkKeyAndCorrespondingObjects(eObject, affectedAttribute,
			featureCorrespondenceMap,blackboard.correspondenceInstance)
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
			featureCorrespondenceMap, blackboard.correspondenceInstance, saveFilesOfChangedEObjects)
		if (!rootPCMEObjects.nullOrEmpty) {
			JaMoPP2PCMUtils.updateNameAttributeForPCMRootObjects(rootPCMEObjects, affectedAttribute, newValue,
				blackboard)
		}
		return
	}

	def static createNewCorrespondingEObjects(EObject newEObject,
		EObject[] newCorrespondingEObjects, Blackboard blackboard) {
		if (newCorrespondingEObjects.nullOrEmpty) {
			return 
		}
		for (pcmElement : newCorrespondingEObjects) {
			if (pcmElement instanceof Repository || pcmElement instanceof System) {
				PCMJaMoPPUtils.saveEObject(pcmElement, blackboard, PCMJaMoPPUtils.getSourceModelVURI(newEObject))
			} else {
				TransformationUtils.saveNonRootEObject(pcmElement)
			}
			blackboard.correspondenceInstance.createAndAddEObjectCorrespondence(pcmElement, newEObject)
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
		if(null == eObject.eResource || null == eObject.eResource.resourceSet){
			return false
		}
		val resource = eObject.eResource
		val resourceSet = resource.resourceSet
		val uri = resource.URI
		val uriConverter = resourceSet.URIConverter
		val normalizedURI = uriConverter.normalize(uri)
		resource.URI = normalizedURI
		return true
	}
	
}
