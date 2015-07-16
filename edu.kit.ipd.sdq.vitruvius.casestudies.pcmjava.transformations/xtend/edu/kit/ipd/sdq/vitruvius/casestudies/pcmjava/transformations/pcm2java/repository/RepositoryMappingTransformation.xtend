package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.Interface
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package

class RepositoryMappingTransformation extends EmptyEObjectMappingTransformation {

	val private static final Logger logger = Logger.getLogger(RepositoryMappingTransformation.name)

	override getClassOfMappedEObject() {
		return typeof(Repository)
	}

	override void setCorrespondenceForFeatures() {
		var repositoryNameAttribute = RepositoryFactory.eINSTANCE.createRepository.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(PCMJaMoPPNamespace.PCM::PCM_ATTRIBUTE_ENTITY_NAME)].iterator.next
		var packageNameAttribute = ContainersFactory.eINSTANCE.createPackage.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(PCMJaMoPPNamespace.JaMoPP::JAMOPP_ATTRIBUTE_NAME)].iterator.next
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
		if (newCorrespondingEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val transResult = TransformationUtils.
			createTransformationChangeResultForNewRootEObjects(newCorrespondingEObjects)
		for (correspondingEObject : newCorrespondingEObjects) {
			transResult.addNewCorrespondence(correspondenceInstance, newRootEObject, correspondingEObject, null)
		}
		return transResult
	}

	override removeEObject(EObject eObject) {
		val Repository repository = eObject as Repository

		//Remove corresponding packages
		val jaMoPPPackages = correspondenceInstance.getCorrespondingEObjectsByType(repository, Package)
		for (jaMoPPPackage : jaMoPPPackages) {
			EcoreUtil.remove(jaMoPPPackage)
		}

		//remove corresponding instance
		correspondenceInstance.removeAllCorrespondences(repository)
		return null
	}

	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete) {
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {
		return PCM2JaMoPPUtils.
			deleteCorrespondingEObjectsAndGetTransformationChangeResult(oldCorrespondingEObjectsToDelete,
				correspondenceInstance)
	}

	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		return PCM2JaMoPPUtils.updateNameAsSingleValuedEAttribute(eObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, correspondenceInstance)
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		val Iterable<EObjectCorrespondence> correspondenceCandidates = correspondenceInstance.
			getAllCorrespondences(newAffectedEObject).filter(typeof(EObjectCorrespondence))
		val transformationResult = TransformationUtils.
			createTransformationChangeResultForNewRootEObjects(newCorrespondingEObjects.filter(typeof(JavaRoot)))
		for (jaMoPPElement : newCorrespondingEObjects) {
			val parrentCorrespondence = getParrentCorrespondence(newValue, correspondenceCandidates)
			transformationResult.addNewCorrespondence(correspondenceInstance, newValue, jaMoPPElement,
				parrentCorrespondence)
		}
		return transformationResult
	}

	def dispatch getParrentCorrespondence(EObject object, Iterable<EObjectCorrespondence> correspondences) {
		return null
	}

	def dispatch getParrentCorrespondence(RepositoryComponent component, Iterable<EObjectCorrespondence> correspondences) {
		for (correspondence : correspondences) {
			if (correspondence.elementATUID.equals(
				correspondenceInstance.calculateTUIDFromEObject(component.repository__RepositoryComponent)) || correspondence.
				elementBTUID.equals(
					correspondenceInstance.calculateTUIDFromEObject(component.repository__RepositoryComponent))) {
				return correspondence
			}
		}
		return null;
	}

	// package with name "contracts"
	def dispatch getParrentCorrespondence(Interface pcmIf, Iterable<EObjectCorrespondence> correspondences) {
		return findPackageWithName("contracts", correspondences)
	}

	//package with name "datatypes"
	def dispatch getParrentCorrespondence(DataType dataType, Iterable<EObjectCorrespondence> correspondences) {
		findPackageWithName("datatypes", correspondences)
	}

	def findPackageWithName(String packageName, Iterable<EObjectCorrespondence> correspondences) {
		for (correspondence : correspondences) {
			if (correspondence.elementATUID.toString.contains(packageName) ||
				correspondence.elementBTUID.toString.contains(packageName)) {
				return correspondence
			}
		}
		correspondences.get(0)
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method createNonRootEObjectSingle should not be called for " + RepositoryMappingTransformation.simpleName +
				" transformation")
		return null
	}

	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] oldCorrespondingEObjectsToDelete) {

		//Called everytime a BasicComponent is removed - does nothing because the actual removing is already don e in deleteNonRootEObjectInList
		return TransformationUtils.createEmptyTransformationChangeResult
	}

}
