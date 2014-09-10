package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.repository.Repository
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.containers.ContainersFactory
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

	override createEObject(EObject eObject) {
		val Repository repository = eObject as Repository
		val Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage
		jaMoPPPackage.name = repository.entityName
		jaMoPPPackage.namespaces.add(jaMoPPPackage.name)
		return jaMoPPPackage.toArray
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		if (newCorrespondingEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		for (correspondingEObject : newCorrespondingEObjects) {
			correspondenceInstance.createAndAddEObjectCorrespondence(newRootEObject, correspondingEObject)
		}
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(newCorrespondingEObjects)
	}

	override removeEObject(EObject eObject) {
		val Repository repository = eObject as Repository

		//Remove corresponding package
		val Package jaMoPPPackage = correspondenceInstance.claimUniqueCorrespondingEObjectByType(repository, Package)
		EcoreUtil.remove(jaMoPPPackage)

		//remove corresponding instance
		correspondenceInstance.removeAllCorrespondences(repository)
		return null
	}

	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete) {
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	override deleteNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {

		//FIXME: add useful code here
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val Repository repository = eObject as Repository

		//val EStructuralFeature jaMoPPNameAttribute = correspondenceInstance.claimCorrespondingEObjectByTypeIfUnique(affectedAttribute, EStructuralFeature)
		val EStructuralFeature jaMoPPNameAttribute = featureCorrespondenceMap.claimValueForKey(affectedAttribute)
		val Package jaMoPPPackage = correspondenceInstance.claimUniqueCorrespondingEObjectByType(repository, Package)
		jaMoPPPackage.eSet(jaMoPPNameAttribute, newValue);

		//we do not save packages
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(jaMoPPPackage.toArray)
	}

	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index, EObject[] newCorrespondingEObjects) {
		val parrentCorrespondence = correspondenceInstance.claimUniqueOrNullCorrespondenceForEObject(affectedEObject);
		for (jaMoPPElement : newCorrespondingEObjects) {
			correspondenceInstance.createAndAddEObjectCorrespondence(newValue, jaMoPPElement, parrentCorrespondence)
		}
		return TransformationUtils.createTransformationChangeResultForNewRootEObjects(newCorrespondingEObjects)
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method createNonRootEObjectSingle should not be called for " + RepositoryMappingTransformation.simpleName +
				" transformation")
		return null
	}

}
