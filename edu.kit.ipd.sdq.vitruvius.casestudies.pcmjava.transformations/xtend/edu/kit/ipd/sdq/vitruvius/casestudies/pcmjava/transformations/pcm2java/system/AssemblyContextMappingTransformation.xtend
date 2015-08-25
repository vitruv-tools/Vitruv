package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system

import com.google.common.collect.Lists
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.palladiosimulator.pcm.repository.RepositoryComponent

class AssemblyContextMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return AssemblyContext
	}

	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

	/**
	 * called when a assembly context has been added
	 * creates a private field with type of enclosed component and name of the assembly context.
	 * 
	 */
	override createEObject(EObject eObject) {
		val AssemblyContext assemblyContext = eObject as AssemblyContext
		val component = assemblyContext.encapsulatedComponent__AssemblyContext
		if (null == component) {

			// we are not able to create a field if the component in the assembly context is null
			// the following methods have to be aware of that
			return null
		}
		return createFieldForAssemblyContext(assemblyContext, component)
	}

	/**
	 * called when a assembly context has been renamed --> rename the field
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		PCM2JaMoPPUtils.updateNameAsSingleValuedEAttribute(affectedEObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, blackboard)
	}

	/**
	 * called when the component within the assembly context has been changed.
	 * Creates a new field2AssemblyCorrespondence if no correspondence exists. 
	 * If a correspondence already exists the TypeReference of the field will be updated
	 */
	override void replaceNonContainmentEReference(EObject affectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue, int index) {
		if(oldValue == newValue){
			// if the object has not changed we do not do anything
			return 
		}
		if (affectedReference.name.equals(PCMJaMoPPNamespace.PCM.ASSEMBLY_CONTEXT_ENCAPSULATED_COMPONENT) &&
			newValue instanceof RepositoryComponent && affectedEObject instanceof AssemblyContext) {
			val typedElementCorrespondences = blackboard.correspondenceInstance.
				getCorrespondingEObjectsByType(affectedEObject as AssemblyContext, Field)
			if (typedElementCorrespondences.nullOrEmpty) {
				val assemblyContext = affectedEObject as AssemblyContext

				//create new correspondences
				val newEObjects = this.createFieldForAssemblyContext(assemblyContext,
					newValue as RepositoryComponent);
				val composedProvidingRequiringEntity = assemblyContext.parentStructure__AssemblyContext
				PCM2JaMoPPUtils.
					handleAssemblyContextAddedAsNonRootEObjectInList(composedProvidingRequiringEntity, assemblyContext,
						newEObjects, blackboard)

			} else {
				val jaMoPPClass = blackboard.correspondenceInstance.
					claimUniqueCorrespondingEObjectByType(newValue as RepositoryComponent, Class)

				//update existing correspondence
				for (typedElement : typedElementCorrespondences) {
					val oldTUID = blackboard.correspondenceInstance.calculateTUIDFromEObject(typedElement)
					typedElement.typeReference = PCM2JaMoPPUtils.createNamespaceClassifierReference(jaMoPPClass)
					blackboard.correspondenceInstance.update(oldTUID, typedElement)
					TransformationUtils.saveNonRootEObject(typedElement)
				}
			}
		}
	}

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		replaceNonContainmentEReference(affectedEObject, affectedReference, oldValue, newValue, 0)
	}

	def private EObject[] createFieldForAssemblyContext(AssemblyContext assemblyContext, RepositoryComponent component) {
		var Class jaMoPPClass = null
		var Class jaMoPPCompositeClass = null
		try {
			jaMoPPClass = blackboard.correspondenceInstance.claimUniqueCorrespondingEObjectByType(component, Class)
			jaMoPPCompositeClass = blackboard.correspondenceInstance.
				claimUniqueCorrespondingEObjectByType(assemblyContext.parentStructure__AssemblyContext, Class)

		} catch (RuntimeException e) {
			val String msg = "Can not create field for component " + component.entityName +
				" because the component does not have a corresponding class yet."
			userInteracting.showMessage(UserInteractionType.MODELESS, msg)
			return Lists.newArrayList
		}

		val TypeReference typeRef = PCM2JaMoPPUtils.createNamespaceClassifierReference(jaMoPPClass)
		val String name = assemblyContext.entityName

		val List<EObject> newEObjects = new ArrayList<EObject>()

		val field = PCM2JaMoPPUtils.createPrivateField(typeRef, name)
		jaMoPPCompositeClass.members.add(field)

		newEObjects.add(field)

		val constructors = jaMoPPCompositeClass.members.filter(typeof(Constructor))

		if (constructors.nullOrEmpty) {
			newEObjects.add(PCM2JaMoPPUtils.addConstructorToClass(jaMoPPCompositeClass))
		} else {
			newEObjects.addAll(constructors)
		}

		newEObjects.add(PCM2JaMoPPUtils.addImportToCompilationUnitOfClassifier(jaMoPPCompositeClass, jaMoPPClass))

		// add creation of EObject to each constructor
		for (ctor : jaMoPPCompositeClass.members.filter(typeof(Constructor))) {
			newEObjects.addAll(PCM2JaMoPPUtils.createNewForFieldInConstructor(field))
		}

		newEObjects
	}

}
