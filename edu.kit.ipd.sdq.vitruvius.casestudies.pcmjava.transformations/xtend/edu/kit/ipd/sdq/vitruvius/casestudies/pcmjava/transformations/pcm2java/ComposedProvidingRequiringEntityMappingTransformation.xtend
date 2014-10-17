package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.core.entity.ComposedProvidingRequiringEntity
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.containers.Package
import org.eclipse.emf.ecore.EAttribute
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.eclipse.emf.ecore.EStructuralFeature
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import org.emftext.language.java.containers.JavaRoot
import com.google.common.collect.Sets
import org.eclipse.emf.ecore.EReference
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace

/**
 * base class for RepositoryComponentMappingTransformation and SystemMappingTransformation
 */
abstract class ComposedProvidingRequiringEntityMappingTransformation extends EmptyEObjectMappingTransformation {

	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

	def Package getParentPackage(EObject eObject)

	/**
	 * called when a ComposedProvidingRequiriungEntity has been created
	 * --> create a package, a compilation unit and a class for the entity
	 */
	override createEObject(EObject eObject) {
		val ComposedProvidingRequiringEntity composedEntity = eObject as ComposedProvidingRequiringEntity
		val Package parrentPackage = getParentPackage(eObject)

		//create all elements
		val createdEObjects = PCM2JaMoPPUtils.createPackageCompilationUnitAndJaMoPPClass(composedEntity, parrentPackage)

		return createdEObjects
	}

	override removeEObject(EObject eObject) {
		return correspondenceInstance.getAllCorrespondingEObjects(eObject)
	}

	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		return PCM2JaMoPPUtils.updateNameAsSingleValuedEAttribute(eObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, correspondenceInstance)
	}
	
	/**
	 * called when a AssemblyContext has been added
	 */
	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index, EObject[] newCorrespondingEObjects) {
		val tcr = TransformationUtils.createEmptyTransformationChangeResult
		if(affectedReference.name.equals(PCMJaMoPPNamespace.PCM.SYSTEM_ASSEMBLY_CONTEXTS__COMPOSED_STRUCTURE)){
			
		}
		return tcr
	}

}
