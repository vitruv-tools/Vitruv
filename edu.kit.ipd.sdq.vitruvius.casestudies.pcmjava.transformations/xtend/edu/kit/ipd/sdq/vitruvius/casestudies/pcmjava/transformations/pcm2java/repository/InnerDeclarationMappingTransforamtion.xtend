package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import com.google.common.collect.Sets
import de.uka.ipd.sdq.pcm.repository.DataType
import de.uka.ipd.sdq.pcm.repository.InnerDeclaration
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.members.ClassMethod
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
		var innerDatatypeAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration.eClass.EAllReferences.filter[attribute|
			attribute.name.equals(PCMJaMoPPNamespace.PCM.DATATYPE_INNERDECLARATION)].iterator.next
		var typeRefAttribute = MembersFactory.eINSTANCE.createClassMethod.eClass.EAllReferences.filter[attribute|
			attribute.name.equals(PCMJaMoPPNamespace.JaMoPP.JAMOPP_PARAMETER_ATTRIBUTE_TYPE_REFERENCE)].iterator.next
		featureCorrespondenceMap.put(innerDatatypeAttribute, typeRefAttribute)
	}

	/**
	 * inner Declaration created. 
	 * Create field with name of inner declaration and + getter + setter for each inner declaration 
	 */
	override createEObject(EObject eObject) {
		val InnerDeclaration innerDec = eObject as InnerDeclaration
		val DataType innerType = innerDec.datatype_InnerDeclaration
		val TypeReference typeRef = DataTypeCorrespondenceHelper.
			claimUniqueCorrespondingJaMoPPDataTypeReference(innerType, correspondenceInstance)
		val members = addFieldGetterAndSetterToClassifier(typeRef, innerDec.entityName)
		PCM2JaMoPPUtils.sortMembers(members)
		return members
	}

	def private List<EObject> addFieldGetterAndSetterToClassifier(TypeReference reference, String name) {
		val List<EObject> ret = new ArrayList<EObject>()
		val Field field = PCM2JaMoPPUtils.createPrivateField(reference, name)
		ret.add(field)
		ret.addAll(createGetterAndSetter(field, reference))
		return ret
	}

	def private List<EObject> createGetterAndSetter(Field field, TypeReference reference) {
		val List<EObject> ret = new ArrayList<EObject>()

		//ret.add(createGetter(field, reference))
		//ret.add(createSetter(field, reference))
		val String commonContent = '''private «PCM2JaMoPPUtils.getNameFromJaMoPPType(reference)» «field.name»;
			''' + "\n"
		val String getterContent = commonContent + '''
			public «PCM2JaMoPPUtils.getNameFromJaMoPPType(reference)» get«field.name.toFirstUpper»(){
			   return «field.name»;
			}
		'''
		val String setterContent = commonContent + '''
			public void set«field.name.toFirstUpper»(«PCM2JaMoPPUtils.getNameFromJaMoPPType(reference)» «field.name»){
			   this.«field.name» = «field.name»;
			}
		'''
		val getter = PCM2JaMoPPUtils.createJaMoPPMethod(getterContent)
		val setter = PCM2JaMoPPUtils.createJaMoPPMethod(setterContent)
		ret.add(getter)
		ret.add(setter)
		return ret
	}

//	def private createSetter(Field field, TypeReference reference) {
//		val method = MembersFactory.eINSTANCE.createClassMethod
//		method.name = "set" + field.name.toFirstUpper
//		method.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
//		method.typeReference = TypesFactory.eINSTANCE.createVoid
//		val parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter
//		parameter.name = field.name
//		parameter.typeReference = reference
//		method.parameters.add(parameter)
//		val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
//		val assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression
//
//		//this.
//		val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
//		assigmentExpression.child = selfReference
//
//		//.fieldname
//		val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
//		fieldReference.target = field
//		selfReference.next = fieldReference
//
//		//=
//		assigmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment
//
//		//name		
//		val identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
//		identifierReference.target = parameter
//
//		assigmentExpression.value = identifierReference
//		expressionStatement.expression = assigmentExpression
//		method.statements.add(expressionStatement)
//		return method
//	}
//
//	def private createGetter(Field field, TypeReference reference) {
//		val method = MembersFactory.eINSTANCE.createClassMethod
//		method.name = "get" + field.name.toFirstUpper
//		method.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
//		method.typeReference = reference
//
//		//this.fieldname
//		val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
//		val identifierRef = ReferencesFactory.eINSTANCE.createIdentifierReference
//		identifierRef.target = field
//		selfReference.next = identifierRef
//
//		// return
//		val ret = StatementsFactory.eINSTANCE.createReturn
//		ret.returnValue = selfReference
//		return method
//	}

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
		val fields = affectedEObjects.filter(typeof(Field))
		if(fields.nullOrEmpty){
			logger.error("No field found in corresponding EObjects for PCM Type " + eObject + " Change not sznchronized!")
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val field = fields.get(0)
		val tcr = PCM2JaMoPPUtils.updateNameAttribute(Sets.newHashSet(field), newValue, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance, true)
		val methods = affectedEObjects.filter(typeof(ClassMethod))
		for (method : methods) {
			val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(method)
			if (isGetter(method)) {
				method.name = "get" + newValue.toString.toFirstUpper

			//TODO: change access in return
			}
			if (isSetter(method)) {
				method.name = "set" + newValue.toString.toFirstUpper
				if (!method.parameters.nullOrEmpty) {
					val firstParam = method.parameters.get(0)
					firstParam.name = newValue.toString

				//TODO: change assignemnt
				}
			}
			tcr.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, method, null)
			tcr.existingObjectsToSave.add(method)
		}
		return tcr
	}

	/**
	 * called when the type of an InnerDeclaration has been changed
	 */
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val affectedEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(affectedEObject, affectedReference,
			featureCorrespondenceMap, correspondenceInstance)
		if (affectedEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		if (false == newValue instanceof DataType) {
			logger.warn("NewValue is not an instance of DataType: " + newValue + " - change not synchronized")
		}
		val tcr = new TransformationChangeResult
		val newDataType = newValue as DataType
		val newJaMoPPType = DataTypeCorrespondenceHelper.
			claimUniqueCorrespondingJaMoPPDataTypeReference(newDataType, correspondenceInstance)

		//Change field Type
		val fields = affectedEObjects.filter(typeof(Field))
		if(fields.nullOrEmpty){
			logger.error("No field found in corresponding EObjects for PCM Type " + affectedEObject + " Change not sznchronized!")
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val field = fields.get(0)

		val oldFieldTUID = correspondenceInstance.calculateTUIDFromEObject(field)
		field.typeReference = EcoreUtil.copy(newJaMoPPType)
		tcr.addCorrespondenceToUpdate(correspondenceInstance, oldFieldTUID, field, null)

		//Change method type/parameter
		val methods = affectedEObjects.filter(typeof(Method))
		for (method : methods) {
			val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(method)
			if (isGetter(method)) {
				method.typeReference = EcoreUtil.copy(newJaMoPPType)
			} else if (isSetter(method)) {
				if (!method.parameters.nullOrEmpty) {
					val parameter = method.parameters.get(0)
					parameter.typeReference = EcoreUtil.copy(newJaMoPPType)
				}
			}
			tcr.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, method, null)
			tcr.existingObjectsToSave.add(method)
		}
		return tcr
	}
	
	

	def isSetter(Method method) {
		return method.name.startsWith("set") && method.typeReference instanceof Void
	}

	def isGetter(Method method) {
		return method.name.startsWith("get")
	}

}
