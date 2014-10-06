package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import com.google.common.collect.Sets
import de.uka.ipd.sdq.pcm.repository.DataType
import de.uka.ipd.sdq.pcm.repository.InnerDeclaration
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMUtils
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.ArrayList
import java.util.Comparator
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.Void

class InnerDeclarationMappingTransforamtion extends EmptyEObjectMappingTransformation {

	private static val Logger logger = Logger.getLogger(InnerDeclarationMappingTransforamtion.simpleName)

	override getClassOfMappedEObject() {
		return InnerDeclaration
	}

	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

	/**
	 * inner Declaration created. 
	 * Create field with name of inner declaration and + getter + setter for each inner declaration 
	 */
	override createEObject(EObject eObject) {
		val InnerDeclaration innerDec = eObject as InnerDeclaration
		val DataType innerType = innerDec.compositeDataType_InnerDeclaration
		val TypeReference typeRef = DataTypeCorrespondenceHelper.
			claimUniqueCorrespondingJaMoPPDataType(innerType, correspondenceInstance)
		val members = addFieldGetterAndSetterToClassifier(typeRef, innerDec.entityName)
		members.sort(
			new Comparator<EObject> {

				override compare(EObject o1, EObject o2) {
					if (o1 instanceof Field && o2 instanceof Method) {
						return 1
					} else if (o1 instanceof Method && o2 instanceof Field) {
						return -1
					}
					return 0;
				}

				override equals(Object obj) {
					return this == obj;
				}

			})
		return members
	}

	def private List<EObject> addFieldGetterAndSetterToClassifier(TypeReference reference, String name) {
		val List<EObject> ret = new ArrayList<EObject>()
		val Field field = createField(reference, name)
		ret.add(field)
		ret.addAll(createGetterAndSetter(field, reference))
		return ret
	}

	def private List<EObject> createGetterAndSetter(Field field, TypeReference reference) {
		val List<EObject> ret = new ArrayList<EObject>()
		val String commonContent = '''private «JaMoPPPCMUtils.getNameFromJaMoPPType(reference)» «field.name»;
			'''
		val String getterContent = commonContent + '''
			public «JaMoPPPCMUtils.getNameFromJaMoPPType(reference)» get«field.name.toFirstUpper»(){
				return «field.name»;
			}
		'''
		val String setterContent = commonContent + '''
			public void set«field.name.toFirstUpper»(«JaMoPPPCMUtils.getNameFromJaMoPPType(reference)» «field.name»){
				this.«field.name» = «field.name»;
			}
		'''
		val getter = JaMoPPPCMUtils.createJaMoPPMethod(getterContent)
		val setter = JaMoPPPCMUtils.createJaMoPPMethod(setterContent)
		ret.add(getter)
		ret.add(setter)
		return ret
	}

	def private createField(TypeReference reference, String name) {
		val Field field = MembersFactory.eINSTANCE.createField
		field.typeReference = reference
		var String fieldName = name
		if (fieldName.nullOrEmpty) {
			fieldName = JaMoPPPCMUtils.getNameFromJaMoPPType(reference)
		}
		if (Character.isUpperCase(fieldName.charAt(0))) {
			fieldName = fieldName.toFirstLower
		} else {
			fieldName = "f_" + fieldName
		}
		if (fieldName.nullOrEmpty) {
			fieldName = "field_" + JaMoPPPCMUtils.getNameFromJaMoPPType(reference)
		}
		field.name = fieldName
		return field
	}

	/**
	 * Called when a InnerDeclaration has been renamed.
	 * Rename field and getter and setters accordingly
	 * 
	 */
	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val affectedEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(eObject, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance)
		if (affectedEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val field = affectedEObjects.filter(typeof(Field))
		val tcr = PCM2JaMoPPUtils.updateNameAttribute(Sets.newHashSet(field), newValue, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance, true)
		val methods = affectedEObjects.filter(typeof(Method))
		for (method : methods) {
			val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(method)
			if (isGetter(method)) {
				method.name = "get" + newValue.toString.toFirstUpper
			}
			if (isSetter(method)) {
				method.name = "set" + newValue.toString.toFirstUpper
			}
			tcr.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, method, null)
			tcr.existingObjectsToSave.add(method)
		}
		return tcr
	}

	/**
	 * called when the type of an InnerDeclaration has been changed
	 */
	override insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index) {
		val affectedEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(affectedEObject, affectedReference,
			featureCorrespondenceMap, correspondenceInstance)
		if (affectedEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		if (false == newValue instanceof DataType) {
			logger.warn("NewValue is not an instance of DataType: " + newValue + " - change not synchronized")
		}
		val newDataType = newValue as DataType
		val field = affectedEObjects.filter(typeof(Field))
		val tcr = PCM2JaMoPPUtils.updateNameAttribute(Sets.newHashSet(field), newValue, affectedReference,
			featureCorrespondenceMap, correspondenceInstance, true)
		val methods = affectedEObjects.filter(typeof(Method))
		for (method : methods) {
			val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(method)
			val typeRef = DataTypeCorrespondenceHelper.
				claimUniqueCorrespondingJaMoPPDataType(newDataType, correspondenceInstance)
			if (isGetter(method)) {
				method.typeReference = typeRef
			} else if (isSetter(method)) {
				if (!method.parameters.nullOrEmpty) {
					val parameter = method.parameters.get(0)
					parameter.typeReference = typeRef
				}
			}
			tcr.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, method, null)
			tcr.existingObjectsToSave.add(method)
		}
		return tcr
	}

	def isSetter(Method method) {
		return method.name.startsWith("set")
	}

	def isGetter(Method method) {
		return method.name.startsWith("get") && method.typeReference instanceof Void
	}

}
