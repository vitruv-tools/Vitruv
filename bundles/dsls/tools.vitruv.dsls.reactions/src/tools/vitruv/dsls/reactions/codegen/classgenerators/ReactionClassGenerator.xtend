package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import static tools.vitruv.dsls.reactions.api.generator.ReactionsLanguageGeneratorConstants.*;
import tools.vitruv.dsls.reactions.reactionsLanguage.PreconditionCodeBlock
import tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.ClassNameGenerator
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization
import tools.vitruv.framework.change.echange.EChange
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*;
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelChange
import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange
import static extension tools.vitruv.dsls.reactions.codegen.changetyperepresentation.ChangeTypeRepresentationExtractor.*
import tools.vitruv.dsls.reactions.codegen.changetyperepresentation.ChangeTypeRepresentation
import tools.vitruv.dsls.reactions.codegen.changetyperepresentation.AtomicChangeTypeRepresentation
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider

class ReactionClassGenerator extends ClassGenerator {
	protected final Reaction reaction;
	protected final ChangeTypeRepresentation changeTypeRepresentation;
	protected final AtomicChangeTypeRepresentation relevantAtomicChangeTypeRepresentation;
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
		this.changeTypeRepresentation = reaction.trigger.extractChangeTypeRepresentation;
		this.relevantAtomicChangeTypeRepresentation = changeTypeRepresentation.relevantAtomicChangeTypeRepresentation;
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
			body = '''return «changeTypeRepresentation.changeType».class;'''
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
		val changeParameterList = relevantAtomicChangeTypeRepresentation.generatePropertiesParameterList;
		val callRoutineMethod = userExecutionClassGenerator.generateMethodCallRoutine(reaction.callRoutine, 
			changeParameterList, typeRef(routinesFacadeClassNameGenerator.qualifiedName));
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			val changeParameter = generateUntypedChangeParameter();
			parameters += changeParameter;
			val typedChangeName = "typedChange";
			body = '''
				«changeTypeRepresentation.getRelevantChangeAssignmentCode(changeParameter.name, typedChangeName)»
				«relevantAtomicChangeTypeRepresentation.generatePropertiesAssignmentCode(typedChangeName)»
				«routinesFacadeClassNameGenerator.qualifiedName» routinesFacade = new «routinesFacadeClassNameGenerator.qualifiedName»(this.executionState, this);
				«userExecutionClassGenerator.qualifiedClassName» userExecution = new «userExecutionClassGenerator.qualifiedClassName»(this.executionState, this);
				userExecution.«callRoutineMethod.simpleName»(«
					FOR parameter : changeParameterList SEPARATOR ", " AFTER ", "»«parameter.name»«ENDFOR»routinesFacade);
			'''
		];
	}
	
	
	protected def generateMethodCheckPrecondition() {
		val methodName = PRECONDITION_METHOD_NAME;
		val changePropertiesCheckMethod = generateMethodCheckChangeProperties();
		val userDefinedPreconditionMethod = if (hasPreconditionBlock) {
			generateMethodCheckUserDefinedPrecondition(reaction.trigger.precondition);	
		}
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			val changeParameter = generateUntypedChangeParameter(reaction);
			visibility = JvmVisibility.PUBLIC;
			parameters += changeParameter
			val typedChangeVariableName = "typedChange";
			body = '''
				if (!(«changeParameter.name» instanceof «changeTypeRepresentation.changeType»)) {
					return false;
				}
				getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
				if (!«changePropertiesCheckMethod.simpleName»(«changeParameter.name»)) {
					return false;
				}
				getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
				«IF hasPreconditionBlock»
					«changeTypeRepresentation.getRelevantChangeAssignmentCode(changeParameter.name, typedChangeVariableName)»
					«relevantAtomicChangeTypeRepresentation.generatePropertiesAssignmentCode(typedChangeVariableName)»
					if (!«userDefinedPreconditionMethod.simpleName»(«
						FOR parameter : relevantAtomicChangeTypeRepresentation.generatePropertiesParameterList SEPARATOR ", "»«parameter.name»«ENDFOR»)) {
						return false;
					}
				«ENDIF»
				getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
				return true;
				'''
		];
	}

	protected def JvmOperation generateMethodCheckUserDefinedPrecondition(PreconditionCodeBlock preconditionBlock) {
		val methodName = USER_DEFINED_TRIGGER_PRECONDITION_METHOD_NAME;
		return preconditionBlock.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateAccessibleElementsParameters(relevantAtomicChangeTypeRepresentation.generatePropertiesParameterList);
			body = preconditionBlock.code;
		];		
	}
	
	/**
	 * Generates method: checkChangedObject : boolean
	 * 
	 * <p>Checks if the currently changed object equals the one specified in the reaction.
	 * 
	 * <p>Methods parameters are:
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 */
	protected def generateMethodCheckChangeProperties() {
		val methodName = "checkChangeProperties";
		
		if (!(reaction.trigger instanceof ModelChange)) {
			throw new IllegalStateException();
		}
		
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			val changeParameter = generateUntypedChangeParameter();
			parameters += changeParameter;
			val relevantChangeParamterName = "relevantChange";
			body = '''
				«changeTypeRepresentation.getRelevantChangeAssignmentCode(changeParameter.name, relevantChangeParamterName)»
				«generateElementChecks(relevantAtomicChangeTypeRepresentation, relevantChangeParamterName)»
				return true;
			'''
		];
	}
	
	private def StringConcatenationClient generateElementChecks(AtomicChangeTypeRepresentation change, String changeParameterName) '''
		«IF relevantAtomicChangeTypeRepresentation.hasAffectedElement»
			if (!(«changeParameterName».getAffectedEObject() instanceof «change.affectedElementClass»)) {
				return false;
			}
		«ENDIF»
		«IF relevantAtomicChangeTypeRepresentation.hasAffectedFeature»
			if (!«changeParameterName».getAffectedFeature().getName().equals("«change.affectedFeature.name»")) {
				return false;
			}
		«ENDIF»
		«IF relevantAtomicChangeTypeRepresentation.hasOldValue»
			if («IF ReplaceSingleValuedFeatureEChange.isAssignableFrom(change.changeType)»«changeParameterName».isFromNonDefaultValue() && «
				ENDIF»!(«changeParameterName».getOldValue() instanceof «change.affectedValueClass»)) {
				return false;
			}
		«ENDIF»
		«IF relevantAtomicChangeTypeRepresentation.hasNewValue»
			if («IF ReplaceSingleValuedFeatureEChange.isAssignableFrom(change.changeType)»«changeParameterName».isToNonDefaultValue() && «
				ENDIF»!(«changeParameterName».getNewValue() instanceof «change.affectedValueClass»)) {
				return false;
			}
		«ENDIF»
	'''
	
}