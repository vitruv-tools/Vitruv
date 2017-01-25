package tools.vitruv.dsls.reactions.jvmmodel.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import static tools.vitruv.dsls.reactions.api.generator.ReactionsLanguageGeneratorConstants.*;
import static extension tools.vitruv.dsls.reactions.helper.EChangeHelper.*;
import tools.vitruv.dsls.reactions.reactionsLanguage.PreconditionCodeBlock
import tools.vitruv.dsls.reactions.helper.ClassNamesGenerators.ClassNameGenerator
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.root.RootEChange
import static tools.vitruv.dsls.reactions.helper.ReactionsLanguageConstants.*;
import tools.vitruv.dsls.reactions.jvmmodel.classgenerators.UserExecutionClassGenerator.AccessibleElement
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import static extension tools.vitruv.dsls.reactions.helper.ClassNamesGenerators.*
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelChange
import tools.vitruv.dsls.reactions.reactionsLanguage.ConcreteModelChange
import tools.vitruv.dsls.reactions.helper.EChangeHelper.ChangeTypeRepresentation
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelElementChange
import org.eclipse.xtend2.lib.StringConcatenationClient
import org.eclipse.emf.ecore.EClass
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeChange
import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange
import tools.vitruv.dsls.reactions.helper.EChangeHelper.AtomicChangeTypeRepresentation
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.dsls.reactions.helper.EChangeHelper.CompoundChangeTypRepresentation
import tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot

class ReactionClassGenerator extends ClassGenerator {
	protected final Reaction reaction;
	protected final ChangeTypeRepresentation change;
	protected final boolean hasPreconditionBlock;
	private final ClassNameGenerator reactionClassNameGenerator;
	private final UserExecutionClassGenerator userExecutionClassGenerator;
	private final ClassNameGenerator routinesFacadeClassNameGenerator;
	
	new(Reaction reaction, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		if (reaction?.trigger == null || reaction?.callRoutine == null) {
			throw new IllegalArgumentException();
		}
		this.reaction = reaction;
		this.hasPreconditionBlock = reaction.trigger.precondition != null;
		this.change = reaction.trigger.generateEChange();
		this.reactionClassNameGenerator = reaction.reactionClassNameGenerator;
		this.routinesFacadeClassNameGenerator = reaction.reactionsSegment.routinesFacadeClassNameGenerator;
		this.userExecutionClassGenerator = new UserExecutionClassGenerator(typesBuilderExtensionProvider, reaction, 
			reactionClassNameGenerator.qualifiedName + "." + EFFECT_USER_EXECUTION_SIMPLE_NAME);
	}
		
	public override JvmGenericType generateClass() {
		generateMethodGetExpectedChangeType();
		generateMethodCheckPrecondition();
		generateMethodExecuteReaction();
				
		reaction.toClass(reactionClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.DEFAULT;
			superTypes += typeRef(AbstractReactionRealization);
			addConstructor(it);
			members += generatedMethods;
			members += userExecutionClassGenerator.generateClass();
		];
	}
	
	protected def void addConstructor(JvmGenericType clazz) {
		clazz.members += clazz.toConstructor [
			visibility = JvmVisibility.PUBLIC;
			val userInteractingParameter = generateUserInteractingParameter();
			parameters += userInteractingParameter
			body = '''super(«userInteractingParameter.name»);'''
		]
	}
	
	protected def generateMethodGetExpectedChangeType() {
		val methodName = "getExpectedChangeType";
		return getOrGenerateMethod(methodName, typeRef(Class, wildcardExtends(typeRef(EChange)))) [
			static = true;
			body = '''return «change.changeType».class;'''
		];
	}
	
	/**
	 * Generates method: applyChange
	 * 
	 * <p>Applies the given change to the specified reaction. Executes the reaction if all preconditions are fulfilled.
	 * 
	 * <p>Method parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 * <li>2. blackboard: the {@link Blackboard} containing the {@link CorrespondenceModel} 
	 * 
	 */
	protected def generateMethodExecuteReaction() {
		val methodName = "executeReaction";
		val callRoutineMethod = userExecutionClassGenerator.generateMethodCallRoutine(reaction.callRoutine, 
			#[new AccessibleElement("change", reaction.generateChangeParameter(reaction.trigger).parameterType)], 
			typeRef(routinesFacadeClassNameGenerator.qualifiedName));
			
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			val changeParameter = generateUntypedChangeParameter();
			parameters += changeParameter;
			val typedChangeName = "typedChange";
			val typedChangeString = change.typedChangeTypeRepresentation;
			body = '''
				«typedChangeString» «typedChangeName» = («typedChangeString»)«changeParameter.name»;
				«routinesFacadeClassNameGenerator.qualifiedName» routinesFacade = new «routinesFacadeClassNameGenerator.qualifiedName»(this.executionState, this);
				«userExecutionClassGenerator.qualifiedClassName» userExecution = new «userExecutionClassGenerator.qualifiedClassName»(this.executionState, this);
				userExecution.«callRoutineMethod.simpleName»(«typedChangeName», routinesFacade);
			'''
		];
	}
			
	protected def generateMethodCheckPrecondition() {
		val methodName = PRECONDITION_METHOD_NAME;
		val preconditionMethods = getPreconditionMethods();
		// FIXME HK Use method in MM
		val changeType = if (FeatureEChange.isAssignableFrom(change.changeType)) {
				typeRef(change.changeType, wildcard, wildcard);
			} else if (RootEChange.isAssignableFrom(change.changeType)) {
				typeRef(change.changeType, wildcard);
			} else {
				typeRef(change.changeType);
			}
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			val changeParameter = generateUntypedChangeParameter(reaction);
			val typedChangeClass = change.typedChangeTypeRepresentation;
			visibility = JvmVisibility.PUBLIC;
			parameters += changeParameter
			body = '''
				if (!(«changeParameter.name» instanceof «changeType»)) {
					return false;
				}
				«typedChangeClass» typedChange = («typedChangeClass»)«changeParameter.name»;
				«FOR operation : preconditionMethods»
					if (!«operation.simpleName»(typedChange)) {
						return false;
					}
				«ENDFOR»
				getLogger().debug("Passed precondition check of reaction " + this.getClass().getName());
				return true;
				'''
		];
	}

	protected def JvmOperation generateMethodCheckUserDefinedPrecondition(PreconditionCodeBlock preconditionBlock) {
		val methodName = TRIGGER_PRECONDITION_METHOD_NAME;
		
		return preconditionBlock.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += preconditionBlock.generateChangeParameter(reaction.trigger);
			body = preconditionBlock.code;
		];		
	}
	
	protected def Iterable<JvmOperation> getPreconditionMethods() {
		val methods = <JvmOperation>newArrayList();
		if (reaction.trigger instanceof ConcreteModelChange) {
			methods += generateMethodCheckChangedObject();
		}
		if (hasPreconditionBlock) {
			methods += generateMethodCheckUserDefinedPrecondition(reaction.trigger.precondition);	
		}
		return methods;
	}
	
	/**
	 * Generates method: checkChangedObject : boolean
	 * 
	 * <p>Checks if the currently changed object equals the one specified in the reaction.
	 * 
	 * <p>Methods parameters are:
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 */
	protected def generateMethodCheckChangedObject() {
		val methodName = "checkChangeProperties";
		
		if (!(reaction.trigger instanceof ModelChange)) {
			throw new IllegalStateException();
		}
		
		val changeEvent = reaction.trigger as ModelChange;
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			val changeParameter = generateChangeParameter(reaction.trigger);
			parameters += changeParameter;
			body = '''
				«IF changeEvent instanceof ModelElementChange»
					«generateElementChecks(change, changeEvent.elementType?.metaclass, changeParameter.name)»
				«ELSEIF changeEvent instanceof ModelAttributeChange»
					«generateAttributeChecks(changeEvent as ModelAttributeChange, changeParameter.name)»
				«ENDIF»

				return true;
			'''
		];
	}
	
	private def StringConcatenationClient generateAttributeChecks(ModelAttributeChange change, String changeParameterName) '''
		// Check affected object
		if (!(«changeParameterName».getAffectedEObject() instanceof «change.feature.metaclass.instanceClass»)) {
			return false;
		}
			
		// Check feature
		if (!«changeParameterName».getAffectedFeature().getName().equals("«change.feature.feature.name»")) {
			return false;
		}
	'''
	
	private def dispatch StringConcatenationClient generateElementChecks(AtomicChangeTypeRepresentation change, EClass element, String changeParameterName) '''
		«generateExistenceCheck(change, element, changeParameterName)»
		«generateUsageCheck(change, element, changeParameterName)»
	'''
	
	private def dispatch StringConcatenationClient generateElementChecks(CompoundChangeTypRepresentation change, EClass element, String changeParameterName) '''
		«IF CreateAndInsertEObject.isAssignableFrom(change.changeType)»
			«generateElementChecks(change.existenceChange, element, changeParameterName + ".getCreateChange()")»
			«generateElementChecks(change.usageChange, element, changeParameterName + ".getInsertChange()")»
		«ENDIF»
		«IF RemoveAndDeleteEObject.isAssignableFrom(change.changeType)»
			«generateElementChecks(change.existenceChange, element, changeParameterName + ".getDeleteChange()")»
			«generateElementChecks(change.usageChange, element, changeParameterName + ".getRemoveChange()")»
		«ENDIF»
		«IF CreateAndReplaceAndDeleteNonRoot.isAssignableFrom(change.changeType)»
			«generateElementChecks(change.existenceChange, element, changeParameterName + ".getDeleteChange()")»
			«generateElementChecks(change.existenceChange, element, changeParameterName + ".getCreateChange()")»
			«generateElementChecks(change.usageChange, element, changeParameterName + ".getReplaceChange()")»
		«ENDIF»
	'''
	
	private def StringConcatenationClient generateUsageCheck(AtomicChangeTypeRepresentation change, EClass element, String changeParameterName) '''
		«IF FeatureEChange.isAssignableFrom(change.changeType)»
			// Check affected object
			if (!(«changeParameterName».getAffectedEObject() instanceof «change.affectedElementClass»)) {
				return false;
			}
			// Check feature
			if (!«changeParameterName».getAffectedFeature().getName().equals("«change.affectedFeature.name»")) {
				return false;
			}
		«ENDIF»
		«generateAdditiveSubtractiveCheck(change, element, changeParameterName)»
	'''
	
	private def StringConcatenationClient generateAdditiveSubtractiveCheck(AtomicChangeTypeRepresentation change, EClass element, String changeParameterName) '''
		«IF EObjectSubtractedEChange.isAssignableFrom(change.changeType)»
			if («IF ReplaceSingleValuedFeatureEChange.isAssignableFrom(change.changeType)»«changeParameterName».isFromNonDefaultValue() && «
				ENDIF»!(«changeParameterName».getOldValue() instanceof «change.affectedValueClass»)
			) {
				return false;
			}
		«ENDIF»
		«IF EObjectAddedEChange.isAssignableFrom(change.changeType)»
			if («IF ReplaceSingleValuedFeatureEChange.isAssignableFrom(change.changeType)»«changeParameterName».isToNonDefaultValue() && «
				ENDIF»!(«changeParameterName».getNewValue() instanceof «change.affectedValueClass»)) {
				return false;
			}
		«ENDIF»
	'''
	
	private def StringConcatenationClient generateExistenceCheck(AtomicChangeTypeRepresentation change, EClass element, String changeParameterName) '''
		«IF EObjectExistenceEChange.isAssignableFrom(change.changeType)»
			if (!(«changeParameterName».getAffectedEObject() instanceof «change.affectedElementClass»)) {
				return false;
			}
		«ENDIF»
	'''
	
}