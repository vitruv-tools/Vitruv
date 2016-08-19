package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory

import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil.*
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils

class RepositoryMappingTransformation extends EmptyEObjectMappingTransformation {

	val private static final Logger logger = Logger.getLogger(RepositoryMappingTransformation.name)

	override getClassOfMappedEObject() {
		return typeof(Repository)
	}

	override void setCorrespondenceForFeatures() {
		var repositoryNameAttribute = RepositoryFactory.eINSTANCE.createRepository.eClass.getEAllAttributes.filter [ attribute |
			attribute.name.equalsIgnoreCase(PCMNamespace.PCM_ATTRIBUTE_ENTITY_NAME)
		].iterator.next
		var packageNameAttribute = ContainersFactory.eINSTANCE.createPackage.eClass.getEAllAttributes.filter [ attribute |
			attribute.name.equalsIgnoreCase(JaMoPPNamespace.JAMOPP_ATTRIBUTE_NAME)
		].iterator.next
		featureCorrespondenceMap.put(repositoryNameAttribute, packageNameAttribute)
	}

	/**
	 * called when a repository is created
	 * creates following corresponding objects
	 * 1) main-package for repository
	 * 2) contracts package for interfaces in main-package
	 * 3) datatypes package for datatypes in main-package
	 */
	override createEObject(EObject eObject) {
		val Repository repository = eObject as Repository
		val Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage
		jaMoPPPackage.name = repository.entityName
		val Package contractsPackage = ContainersFactory.eINSTANCE.createPackage
		contractsPackage.namespaces.add(jaMoPPPackage.name)
		contractsPackage.name = "contracts"
		val Package datatypesPackage = ContainersFactory.eINSTANCE.createPackage
		datatypesPackage.namespaces.add(jaMoPPPackage.name)
		datatypesPackage.name = "datatypes"
		return #[jaMoPPPackage, contractsPackage, datatypesPackage]
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		val transformationResult = new TransformationResult
		if (newCorrespondingEObjects.nullOrEmpty) {
			return transformationResult
		}
		newCorrespondingEObjects.forEach [ newCorrespondingEObject |
			PCMJaMoPPUtils.addRootChangeToTransformationResult(newCorrespondingEObject, blackboard,
				PCMJaMoPPUtils.getSourceModelVURI(newRootEObject), transformationResult)
		]
		for (correspondingEObject : newCorrespondingEObjects) {
			blackboard.correspondenceModel.createAndAddCorrespondence(newRootEObject, correspondingEObject)
		}
		return transformationResult
	}

	override removeEObject(EObject eObject) {
		return null
	}

	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete) {
		val transformationResult = new TransformationResult
		PCMJaMoPPUtils.removeCorrespondenceAndAllObjects(oldRootEObject, null, blackboard)
		return transformationResult 
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		return PCMJaMoPPUtils.removeCorrespondenceAndAllObjects(oldValue, oldAffectedEObject, blackboard)
	}

	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		PCM2JaMoPPUtils.updateNameAsSingleValuedEAttribute(eObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, blackboard)
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		val transformationResult = new TransformationResult
		if(newCorrespondingEObjects.nullOrEmpty){
			return transformationResult
		}
		val javaRoots = newCorrespondingEObjects.filter(typeof(JavaRoot))
		javaRoots.forEach [ javaRoot |
			PCMJaMoPPUtils.addRootChangeToTransformationResult(javaRoot, blackboard,
				PCMJaMoPPUtils.getSourceModelVURI(newAffectedEObject), transformationResult)
		]

		for (jaMoPPElement : newCorrespondingEObjects) {
			blackboard.correspondenceModel.createAndAddCorrespondence(newValue, jaMoPPElement)
		}
		return transformationResult
	}

	def findPackageWithName(String packageName, Iterable<Correspondence> correspondences) {
		for (correspondence : correspondences) {
			if (correspondence.ATUIDs.exists[it.toString.contains(packageName)] ||
				correspondence.BTUIDs.exists[it.toString.contains(packageName)]) {
				return correspondence
			}
		}
		correspondences.get(0)
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		val transformationResult = new TransformationResult
		logger.warn(
			"method createNonRootEObjectSingle should not be called for " + RepositoryMappingTransformation.simpleName +
				" transformation")
				return transformationResult
			}

			override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
				EObject[] oldCorrespondingEObjectsToDelete) {

				// Called everytime a BasicComponent is removed - does nothing because the actual removing is already done in deleteNonRootEObjectInList
				return new TransformationResult
			}

		}
