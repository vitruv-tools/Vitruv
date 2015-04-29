package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import com.google.common.collect.Sets
import de.uka.ipd.sdq.pcm.repository.OperationRequiredRole
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.types.TypeReference
import org.apache.log4j.Logger

class OperationRequiredRoleMappingTransformation extends EmptyEObjectMappingTransformation {

	private val Logger logger = Logger.getLogger(OperationRequiredRoleMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return OperationRequiredRole
	}

	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

	/**
	 * called when a operation Required role has been created
	 * Following things are done:
	 * 1) create field with interface type and name of required role in main class of the component/system which corresponds to the newly created interface
	 * 2) create constructor parameter in all constructors of the class
	 * 3) add assignment from contructor to newly created field
	 */
	override createEObject(EObject eObject) {
		val OperationRequiredRole operationRequiredRole = eObject as OperationRequiredRole
		val interfaceRequiringEntity = operationRequiredRole.requiringEntity_RequiredRole
		val jaMoPPClass = correspondenceInstance.claimUniqueCorrespondingEObjectByType(interfaceRequiringEntity, Class)
		val opInterface = operationRequiredRole.requiredInterface__OperationRequiredRole
		val jaMoPPInterface = correspondenceInstance.claimUniqueCorrespondingEObjectByType(opInterface, Interface)
		val List<EObject> newEObjects = new ArrayList

		if (null == jaMoPPInterface) {
			logger.info(
				"No corresponding Java Interface found for OperationInterface " + opInterface +
					" not created an field for the operation required role (yet)")
			return null
		}

		val name = operationRequiredRole.entityName
		val TypeReference type = PCM2JaMoPPUtils.createNamespaceClassifierReference(jaMoPPInterface)

		//create import
		val import = PCM2JaMoPPUtils.addImportToCompilationUnitOfClassifier(jaMoPPClass, jaMoPPInterface)
		newEObjects.add(import)

		//create field
		val field = PCM2JaMoPPUtils.createPrivateField(EcoreUtil.copy(type), name)
		jaMoPPClass.members.add(field)
		newEObjects.add(field)

		//create constructor if none exists
		if (jaMoPPClass.members.filter(typeof(Constructor)).nullOrEmpty) {
			PCM2JaMoPPUtils.addConstructorToClass(jaMoPPClass)
		}

		//add param to contructor
		for (ctor : jaMoPPClass.members.filter(typeof(Constructor))) {
			val Parameter newParam = PCM2JaMoPPUtils.createOrdinaryParameter(EcoreUtil.copy(type), name)
			ctor.parameters.add(newParam)
			val Statement asssignment = PCM2JaMoPPUtils.createAssignmentFromParameterToField(field, newParam)
			ctor.statements.add(asssignment)
			newEObjects.add(newParam)
		}
		return newEObjects
	}

	override removeEObject(EObject eObject) {
		return correspondenceInstance.getAllCorrespondingEObjects(eObject)
	}

	/**
	 * called when a operation required role has been changed.
	 */
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val tcr = TransformationUtils.createEmptyTransformationChangeResult
		val orr = affectedEObject as OperationRequiredRole
		val orrInterface = orr.requiredInterface__OperationRequiredRole
		val orrComponent = orr.requiringEntity_RequiredRole
		if(oldValue == newValue || orrInterface == newValue || orrComponent == newValue){
			// if the value has not changed we do nothing
			return tcr
		}
		
		val EObject[] oldEObjects = removeEObject(affectedEObject)
		for (oldEObject : oldEObjects) {
			val tuidToRemove = correspondenceInstance.calculateTUIDFromEObject(oldEObject)
			tcr.addCorrespondenceToDelete(correspondenceInstance, tuidToRemove)
			if (null != oldEObject.eContainer) {
				tcr.existingObjectsToSave.add(oldEObject.eContainer)
			}
			EcoreUtil.remove(oldEObject)
		}
		val EObject[] newEObjects = createEObject(affectedEObject)
		val opr = affectedEObject as OperationRequiredRole
		val interfaceRequiringEntity = opr.requiringEntity_RequiredRole
		val parrentCorrespondences = correspondenceInstance.getAllCorrespondences(interfaceRequiringEntity)
		var Correspondence parrentCorrespondence = null
		if (!parrentCorrespondences.nullOrEmpty) {
			parrentCorrespondence = parrentCorrespondences.get(0)
		}
		if (null != newEObjects) {
			for (newEObject : newEObjects) {
				tcr.addNewCorrespondence(correspondenceInstance, newEObject, affectedEObject, parrentCorrespondence)
				tcr.existingObjectsToSave.add(newEObject)
			}
		}

		return tcr
	}

	/**
	 * called when the name or ID of a OperationRequiredRole has been changed - rename parameter
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val affectedEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(affectedEObject, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance)
		if (affectedEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val affectedField = affectedEObjects.filter(typeof(Field))
		val tcr = PCM2JaMoPPUtils.updateNameAttribute(Sets.newHashSet(affectedField), newValue, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance, true)
		val affectedParam = affectedEObjects.filter(typeof(Parameter))
		tcr.addChangeResult(
			PCM2JaMoPPUtils.updateNameAttribute(Sets.newHashSet(affectedParam), newValue, affectedAttribute,
				featureCorrespondenceMap, correspondenceInstance, true))
		return tcr
	}
}
	