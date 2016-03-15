package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel

import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import org.eclipse.xtext.common.types.JvmGenericType
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import org.eclipse.emf.ecore.EObject
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorUtils.*;
import org.eclipse.xtext.common.types.JvmVisibility
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseRealization
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.JvmTypesBuilderWithoutAssociations
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import org.eclipse.xtext.common.types.JvmVisibility
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import org.eclipse.xtext.common.types.JvmOperation
import static edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.ResponseLanguageGeneratorConstants.*;
import org.eclipse.emf.ecore.EObject
import java.util.ArrayList
import org.eclipse.xtext.common.types.JvmFormalParameter
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import java.util.List
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseRuntimeHelper
import java.util.Map
import java.util.HashMap
import org.eclipse.emf.common.util.URI
import java.util.Collections
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl.SimpleTextXBlockExpression
import java.io.IOException
import org.eclipse.xtend2.lib.StringConcatenationClient
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementSpecification
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementDelete
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelPathCodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.PreconditionCodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExecutionCodeBlock
import org.eclipse.xtext.common.types.JvmGenericType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementRetrieve
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementRetrieveOrDelete
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementCreate
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.TargetChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.EObjectContainerMock
import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation

class ResponseClassGenerator extends ClassGenerator {
	protected extension final ResponseParameterGenerator _parameterGenerator;
	protected final Response response;
	protected final JvmGenericType implicitEffectClass;
	protected final Class<? extends EChange> change;
	protected final boolean hasPreconditionBlock;
	
	new(Response response, JvmGenericType implicitEffectClass, JvmTypesBuilderWithoutAssociations typesBuilder, JvmTypeReferenceBuilder typeReferenceBuilder) {
		super(typesBuilder, typeReferenceBuilder);
		if (response?.trigger == null || response?.effect == null) {
			throw new IllegalArgumentException();
		}
		this._parameterGenerator = new ResponseParameterGenerator(response, typeReferenceBuilder, typesBuilder);
		this.response = response;
		this.implicitEffectClass = implicitEffectClass;
		this.hasPreconditionBlock = response.trigger.precondition != null;
		this.change = response.trigger.generateEChangeInstanceClass();
	}
	
	public def JvmGenericType generateClass() {
		generateMethodGetTrigger();
		generateMethodApplyChange();
		
		response.toClass(response.responseQualifiedName) [
			visibility = JvmVisibility.DEFAULT;
			superTypes += typeRef(AbstractResponseRealization);
			addLoggerMethods(it);
			addEffectFieldAndConstructor(it);
			members += generatedMethods;
		];
	}
	
	protected def void addEffectFieldAndConstructor(JvmGenericType clazz) {
		clazz.members += clazz.toConstructor [
			parameters += generateParameter("userInteracting", UserInteracting);
			visibility = JvmVisibility.PUBLIC;
			body = '''
			super(userInteracting);'''
		]
	}
	
	public def JvmGenericType generateMockType() {
		var JvmGenericType result;
		if (!(response.trigger instanceof ConcreteModelElementChange && DeleteRootEObject.equals(change))) {
			if (change.methods.exists[it.name == "setOldValue"]) {
				val affectedElementClass = (response.trigger as AtomicFeatureChange).changedFeature.feature.EType;
				if (EObject.isAssignableFrom(affectedElementClass.instanceClass)) {
					result = affectedElementClass.toClass(affectedElementClass.instanceClass.name + "ContainerMock") [
						superTypes += typeRef(affectedElementClass.instanceClass);
						members += toConstructor() [
							parameters += toParameter("containedObject", typeRef(affectedElementClass.instanceClass));
							parameters += toParameter("containerObject", typeRef(EObject));
							body = '''
								super();
								this.containedObject = containedObject;
								this.containerObject = containerObject;
							'''
						]
						members += toField("containedObject", typeRef(affectedElementClass.instanceClass));
						members += toField("containerObject", typeRef(EObject));
						members += affectedElementClass.instanceClass.methods.map[method |
							toMethod(affectedElementClass, method.name, typeRef(method.returnType)) [
								visibility = JvmVisibility.PUBLIC;
								exceptions += method.exceptionTypes.map[typeRef()];
								parameters += method.parameters.map[affectedElementClass.toParameter(name, typeRef(type))];
								if (simpleName.equals("eContainer")) {
									body = '''
										return containerObject;
									'''
								} else if (simpleName.equals("eResource")) {
									body = '''
										return containerObject.eResource();
									'''
								} else {
									body = '''
										«IF !method.returnType.equals(Void.TYPE)»return «ENDIF»containedObject.«simpleName»(«FOR param : parameters SEPARATOR ', '»«param.name»«ENDFOR»);
									'''
								}
							]
						]
					]	
				}
			}
		}
		return result;
		
	}
	
	/**
	 * Generates method: applyChange
	 * 
	 * <p>Applies the given change to the specified response. Executes the response if all preconditions are fulfilled.
	 * 
	 * <p>Method parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 * <li>2. blackboard: the {@link Blackboard} containing the {@link CorrespondenceInstance} 
	 * 
	 */
	protected def generateMethodApplyChange() {
		val methodName = RESPONSE_APPLY_METHOD_NAME;

		/* TODO HK Fix this after the new change MM is implemented:
		 * Delete objects (except root one) are removed from the model and now contained in the ChangeDescription,
		 * so correspondences cannot be resolved
					 */
		val checkPreconditionMethod = generateInterfacePreconditionMethod();
		return getOrGenerateMethod(methodName, typeRef(TransformationResult)) [
			visibility = JvmVisibility.PUBLIC;
			val changeParameter = generateUntypedChangeParameter();
			val blackboardParameter = generateBlackboardParameter();
			parameters += changeParameter;
			parameters += blackboardParameter;
			val typedChangeName = "typedChange";
			body = '''
				LOGGER.debug("Called response " + this.getClass().getName() + " with event " + «changeParameter.name»);
				
				// Check if the event matches the trigger of the response
				if (!«checkPreconditionMethod.simpleName»(«changeParameter.name»)) {
					return new «TransformationResult»();
				}
				
				«typedChangeString» «typedChangeName» = («typedChangeString»)«changeParameter.name»;
				«TransformationResult» transformationResult = new «TransformationResult»();
				try {
					«/* TODO HK Fix this after the new change MM is implemented:
					 * Delete objects (except root one) are removed from the model and now contained in the ChangeDescription,
					 * so correspondences cannot be resolved
					 */»
					«IF !(response.trigger instanceof ConcreteModelElementChange && DeleteRootEObject.equals(change))»
					«IF change.methods.exists[it.name == "setOldValue"]»
					«val affectedElementClass = (response.trigger as AtomicFeatureChange).changedFeature.feature.EType»
					«IF EObject.isAssignableFrom(affectedElementClass.instanceClass)»
					final «affectedElementClass.instanceClass» oldValue = «typedChangeName».getOldValue();
					if (oldValue != null) {
						«typedChangeName».setOldValue(new «affectedElementClass.instanceClass.name + "ContainerMock"»(oldValue, «typedChangeName».getOldAffectedEObject()));
					}
					«/*typedChangeName».setOldValue(new «affectedElementClass»() {
						public «EObject» eContainer() { 
							return «typedChangeName».getOldAffectedEObject();
						}
						«FOR method : affectedElementClass.methods»
						«IF method.name != "eContainer"»
						public «method.returnType» «method.name»(«FOR param : method.parameters SEPARATOR ', '»«param.type» «param.name»«ENDFOR») «FOR exept: method.exceptionTypes BEFORE 'throws ' SEPARATOR ', '»«exept»«ENDFOR» {
							«IF !method.returnType.equals(Void.TYPE)»return «ENDIF»oldValue.«method.name»(«FOR param : method.parameters SEPARATOR ', '»«param.name»«ENDFOR»);
						}
						«ENDIF»
						«ENDFOR»
					});*/»
					«ENDIF»
					«ENDIF»
					«ENDIF»
					new «implicitEffectClass»(userInteracting, «blackboardParameter.name», transformationResult).execute(«typedChangeName»);
				} catch («Exception» exception) {
					// If an error occured during execution, avoid an application shutdown and print the error.
					LOGGER.error(exception.getClass().getSimpleName() + " (" + exception.getMessage() + ") during execution of response: " + this.getClass().getName());
				}
				
				return transformationResult;'''
		];
	}
	
	private def StringConcatenationClient getTypedChangeString() '''
		«val trigger = response.trigger
		»«change»«IF trigger instanceof ConcreteModelElementChange»<«getGenericTypeParameterOfChange(trigger)»>«ENDIF»'''
		
	
		
	protected def generateMethodGetTrigger() {
		val methodName = "getTrigger";
		return getOrGenerateMethod(methodName, typeRef(Class, wildcardExtends(typeRef(EChange)))) [
			static = true;
			body = '''return «change».class;'''
		];
	} 
	
	/** 
	 * Generated method: checkPrecondition : boolean
	 * 
	 * <p>Evaluates the precondition specified in the response
	 * 
	 * <p>Methods parameters are: 
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 * 
	 * <p>Precondition: precondition code block must exist.
	 */
	protected def generateInterfacePreconditionMethod() {
		val methodName = PRECONDITION_METHOD_NAME;
		val operationsToCallBeforeCast = getPreconditionMethodsBeforeCast();
		val operationsToCallAfterCast = getPreconditionMethodsAfterCast();
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			val changeParameter = generateUntypedChangeParameter(response);
			val typedChangeClass = change;
			visibility = JvmVisibility.PUBLIC;
			parameters += changeParameter
			body = '''
				«FOR operation : operationsToCallBeforeCast»
					if (!«operation.simpleName»(«changeParameter.name»)) {
						return false;
					}
				«ENDFOR»
				«typedChangeClass» typedChange = («typedChangeClass»)«changeParameter.name»;
				«FOR operation : operationsToCallAfterCast»
					if (!«operation.simpleName»(typedChange)) {
						return false;
					}
				«ENDFOR»
				LOGGER.debug("Passed precondition check of response " + this.getClass().getName());
				return true;
				'''
		];
	}
	
	protected def generateMethodCheckChangeType() {
		val methodName = "checkChangeType";
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			val changeParameter = generateUntypedChangeParameter;
			visibility = JvmVisibility.PRIVATE;
			parameters += changeParameter;
			val changeType = if (!change.equals(EChange)) {
				typeRef(change, wildcard);
			} else {
				typeRef(change);
			}
			body = '''return «changeParameter.name» instanceof «changeType»;''';
		];	
	}
	
	/** 
	 * Generated method: checkPrecondition : boolean
	 * 
	 * <p>Evaluates the precondition specified in the response
	 * 
	 * <p>Methods parameters are: 
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 * 
	 * <p>Precondition: precondition code block must exist.
	 */
	protected def JvmOperation generateUserDefinedPreconditionMethod(PreconditionCodeBlock preconditionBlock) {
		val methodName = TRIGGER_PRECONDITION_METHOD_NAME;
		
		return preconditionBlock.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateChangeParameter(preconditionBlock);
			body = preconditionBlock.code;
		];		
	}
	
	protected def Iterable<JvmOperation> getPreconditionMethodsBeforeCast() {
		val methods = <JvmOperation>newArrayList();
		methods += generateMethodCheckChangeType();
		if (response.trigger instanceof ConcreteModelElementChange) {
			methods += generateMethodCheckChangedObject();
		}
		return methods;
	}
	
	protected def Iterable<JvmOperation> getPreconditionMethodsAfterCast() {
		val methods = <JvmOperation>newArrayList();
		if (hasPreconditionBlock) {
			methods += generateUserDefinedPreconditionMethod(response.trigger.precondition);
		}
		return methods;
	}
	
	/**
	 * Generates method: checkChangedObject : boolean
	 * 
	 * <p>Checks if the currently changed object equals the one specified in the response.
	 * 
	 * <p>Methods parameters are:
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 */
	protected def generateMethodCheckChangedObject() {
		val methodName = "checkChangedObject";
		
		if (!(response.trigger instanceof ConcreteModelElementChange)) {
			throw new IllegalStateException();
		}
		
		val changeEvent = response.trigger;
		val changedElement = response.trigger.changedModelElementClass;
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			val changeParameter = generateUntypedChangeParameter();
			parameters += changeParameter;
			val typedChangeName = "typedChange";
			val typedChangeClassGenericString = if (!change.equals(EChange)) "<?>" else ""
			body = '''
				«change»«typedChangeClassGenericString» «typedChangeName» = («change»«typedChangeClassGenericString»)«changeParameter.name»;
				«EObject» changedElement = «typedChangeName».get«changeEvent.EChangeFeatureNameOfChangedObject.toFirstUpper»();
				«IF changeEvent instanceof AtomicFeatureChange»
					«/* TODO HK We could compare something more safe like <MM>PackageImpl.eINSTANCE.<ELEMENT>_<FEATURE>.*/»
					if (!«typedChangeName».getAffectedFeature().getName().equals("«changeEvent.changedFeature.feature.name»")) {
						return false;
					}
				«ENDIF»
				if (changedElement instanceof «changedElement.instanceClass») {
					return true;
				}
				return false;
			'''
		];
	}
}