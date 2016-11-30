package tools.vitruv.dsls.reactions.jvmmodel.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import static tools.vitruv.dsls.reactions.api.generator.ReactionsLanguageGeneratorConstants.*;
import tools.vitruv.dsls.reactions.reactionsLanguage.ConcreteModelElementChange
import static extension tools.vitruv.dsls.reactions.helper.EChangeHelper.*;
import org.eclipse.xtend2.lib.StringConcatenationClient
import static extension tools.vitruv.dsls.reactions.helper.EChangeHelper.*;
import tools.vitruv.dsls.reactions.reactionsLanguage.AtomicFeatureChange
import static extension tools.vitruv.dsls.reactions.helper.ReactionsLanguageHelper.*;
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

class ReactionClassGenerator extends ClassGenerator {
	protected final Reaction reaction;
	protected final Class<? extends EChange> change;
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
		this.change = reaction.trigger.generateEChangeInstanceClass();
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
			body = '''return «change».class;'''
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
			val typedChangeString = typedChangeString;
			body = '''
				«typedChangeString» «typedChangeName» = («typedChangeString»)«changeParameter.name»;
				«routinesFacadeClassNameGenerator.qualifiedName» routinesFacade = new «routinesFacadeClassNameGenerator.qualifiedName»(this.executionState, this);
				«userExecutionClassGenerator.qualifiedClassName» userExecution = new «userExecutionClassGenerator.qualifiedClassName»(this.executionState, this);
				userExecution.«callRoutineMethod.simpleName»(«typedChangeName», routinesFacade);
			'''
		];
	}
			
	private def StringConcatenationClient getTypedChangeString() '''
		«val trigger = reaction.trigger
		»«change»«IF trigger instanceof ConcreteModelElementChange»<«FOR typeParam : getGenericTypeParameterFQNsOfChange(trigger) SEPARATOR ', '»«typeParam»«ENDFOR»>«ENDIF»'''
		
		
	protected def generateMethodCheckPrecondition() {
		val methodName = PRECONDITION_METHOD_NAME;
		val preconditionMethods = getPreconditionMethods();
		// FIXME HK Use method in MM
		val changeType = if (FeatureEChange.isAssignableFrom(change)) {
				typeRef(change, wildcard, wildcard);
			} else if (RootEChange.isAssignableFrom(change)) {
				typeRef(change, wildcard);
			} else {
				typeRef(change);
			}
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			val changeParameter = generateUntypedChangeParameter(reaction);
			val typedChangeClass = change;
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
		if (reaction.trigger instanceof ConcreteModelElementChange) {
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
		
		if (!(reaction.trigger instanceof ConcreteModelElementChange)) {
			throw new IllegalStateException();
		}
		
		val changeEvent = reaction.trigger;
		val changedElement = reaction.trigger.changedModelElementClass;
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			val changeParameter = generateChangeParameter(reaction.trigger);
			parameters += changeParameter;
//			val typedChangeName = "typedChange";
//			val typedChangeClassGenericString = if (!change.equals(EChange)) "<?>" else ""
			body = '''
«««				«change»«typedChangeClassGenericString» «typedChangeName» = («change»«typedChangeClassGenericString»)«changeParameter.name»;
				«EObject» changedElement = «changeParameter.name».get«changeEvent.EChangeFeatureNameOfChangedObject.toFirstUpper»();
				// Check model element type
				if (!(changedElement instanceof «changedElement.instanceClass»)) {
					return false;
				}
				
				«IF changeEvent instanceof AtomicFeatureChange»
					// Check feature
					if (!«changeParameter.name».getAffectedFeature().getName().equals("«changeEvent.changedFeature.feature.name»")) {
						return false;
					}
				«ENDIF»
				return true;
			'''
		];
	}
}