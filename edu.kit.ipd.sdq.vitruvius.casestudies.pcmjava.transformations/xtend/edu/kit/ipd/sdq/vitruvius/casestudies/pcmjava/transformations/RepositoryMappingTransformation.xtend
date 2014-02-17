package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import de.uka.ipd.sdq.pcm.repository.Repository
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import java.util.Collection
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import org.eclipse.emf.ecore.EStructuralFeature
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import org.eclipse.emf.ecore.util.EcoreUtil

class RepositoryMappingTransformation extends EObjectMappingTransformation {

	val private static final Logger logger = Logger.getLogger(RepositoryMappingTransformation.name)

	override getClassOfMappedEObject() {
		return typeof(Repository)
	}

	override void setCorrespondenceForFeatures() {
		val nameAttributeCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence
		var repositoryNameAttribute = RepositoryFactory.eINSTANCE.createRepository.eClass.EAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("entityName")].iterator.next
		var packageNameAttribute = ContainersFactory.eINSTANCE.createPackage.eClass.EAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("name")].iterator.next
		nameAttributeCorrespondence.setElementA(repositoryNameAttribute)
		nameAttributeCorrespondence.setElementB(packageNameAttribute);
		correspondenceInstance.addCorrespondence(nameAttributeCorrespondence)
	}

	//TODO: write test cases + how will the models be saved?
	override addEObject(EObject eObject) {
		val Repository repository = eObject as Repository
		val Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage
		jaMoPPPackage.name = repository.entityName
		val EObjectCorrespondence eObjectCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence
		eObjectCorrespondence.setElementA(repository)
		eObjectCorrespondence.setElementB(jaMoPPPackage)
		correspondenceInstance.addCorrespondence(eObjectCorrespondence)
		return jaMoPPPackage
	}

	override removeEObject(EObject eObject) {
		val Repository repository = eObject as Repository
		//Remove corresponding package
		val Package jaMoPPPackage = correspondenceInstance.claimCorrespondingEObjectByTypeIfUnique(repository, Package)
		EcoreUtil.remove(jaMoPPPackage)
		//remove corresponding instance
		correspondenceInstance.removeAllCorrespondingInstances(repository)
		return null
	}

	override updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		val Repository repository = eObject as Repository
		val Object valueOfAttribute = repository.eGet(affectedAttribute)
		val EStructuralFeature jaMoPPNameAttribute = correspondenceInstance.claimCorrespondingEObjectByTypeIfUnique(eObject, EStructuralFeature) 
		val Package jaMoPPPackage = correspondenceInstance.claimCorrespondingEObjectByTypeIfUnique(eObject, Package)
		jaMoPPPackage.eSet(jaMoPPNameAttribute, valueOfAttribute);
		return null
	}

	override updateEReference(EObject eObject, EReference affectedEReference, EObject newValue) {
		/*val Repository repository = eObject as Repository
		val Object valueOfReferene = repository.eGet(affectedEReference)
		val EStructuralFeature jaMoPPNameAttribute = TransformationUtils.
			findCorrespondingEObjectIfUnique(correspondenceInstance, affectedEReference) as EStructuralFeature*/
		//Not implemented (yet) for Repository(?)
		return null
	}

	override updateEContainmentReference(EObject eObject, EReference afffectedEReference, EObject newValue) {
		val Repository repository = eObject as Repository

		//Not implemented (yet) for Repository(?)
		return null
	}

}
