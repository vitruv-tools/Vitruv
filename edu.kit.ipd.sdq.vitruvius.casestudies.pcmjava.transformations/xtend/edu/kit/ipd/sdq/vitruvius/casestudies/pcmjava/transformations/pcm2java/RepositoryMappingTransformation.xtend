package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.repository.Repository
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMNamespace
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult

class RepositoryMappingTransformation extends edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation {

	val private static final Logger logger = Logger.getLogger(RepositoryMappingTransformation.name)

	override getClassOfMappedEObject() {
		return typeof(Repository)
	}

	override void setCorrespondenceForFeatures() {
		var repositoryNameAttribute = RepositoryFactory.eINSTANCE.createRepository.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPPCMNamespace::PCM_ATTRIBUTE_ENTITY_NAME)].iterator.next
		var packageNameAttribute = ContainersFactory.eINSTANCE.createPackage.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPPCMNamespace::JAMOPP_ATTRIBUTE_NAME)].iterator.next
		featureCorrespondenceMap.put(repositoryNameAttribute, packageNameAttribute)
	}

	override addEObject(EObject eObject) {
		val Repository repository = eObject as Repository
		val Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage
		jaMoPPPackage.name = repository.entityName
		jaMoPPPackage.namespaces.add(jaMoPPPackage.name)
		val EObjectCorrespondence eObjectCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence
		eObjectCorrespondence.setElementA(repository)
		eObjectCorrespondence.setElementB(jaMoPPPackage)
		correspondenceInstance.addSameTypeCorrespondence(eObjectCorrespondence)
		//we do not save packages --> return empty transformationChangeResult
		return new TransformationChangeResult
	}

	override removeEObject(EObject eObject) {
		val Repository repository = eObject as Repository
		//Remove corresponding package
		val Package jaMoPPPackage = correspondenceInstance.claimUniqueCorrespondingEObjectByType(repository, Package)
		EcoreUtil.remove(jaMoPPPackage)
		//remove corresponding instance
		correspondenceInstance.removeAllCorrespondences(repository)
		return new TransformationChangeResult
	}

	override updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		val Repository repository = eObject as Repository
		//val EStructuralFeature jaMoPPNameAttribute = correspondenceInstance.claimCorrespondingEObjectByTypeIfUnique(affectedAttribute, EStructuralFeature)
		val EStructuralFeature jaMoPPNameAttribute = featureCorrespondenceMap.claimValueForKey(affectedAttribute) 
		val Package jaMoPPPackage = correspondenceInstance.claimUniqueCorrespondingEObjectByType(repository, Package)
		jaMoPPPackage.eSet(jaMoPPNameAttribute, newValue);
		//we do not save packages
		return new TransformationChangeResult
	}

	override updateEReference(EObject eObject, EReference affectedEReference, Object newValue) {
		/*val Repository repository = eObject as Repository
		val Object valueOfReferene = repository.eGet(affectedEReference)
		val EStructuralFeature jaMoPPNameAttribute = TransformationUtils.
			findCorrespondingEObjectIfUnique(correspondenceInstance, affectedEReference) as EStructuralFeature*/
		//Not implemented (yet) for Repository(?)
		return null
	}

	override updateEContainmentReference(EObject eObject, EReference afffectedEReference, Object newValue) {
		val Repository repository = eObject as Repository

		//Not implemented (yet) for Repository(?)
		return null
	}

}
