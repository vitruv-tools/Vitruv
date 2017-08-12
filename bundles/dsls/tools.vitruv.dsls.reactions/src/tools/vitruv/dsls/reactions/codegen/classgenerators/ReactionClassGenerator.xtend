package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import static tools.vitruv.dsls.reactions.api.generator.ReactionsLanguageGeneratorConstants.*;
import tools.vitruv.dsls.reactions.reactionsLanguage.PreconditionCodeBlock
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization
import tools.vitruv.framework.change.echange.EChange
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*;
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.reactions.codegen.changetyperepresentation.ChangeTypeRepresentationExtractor.*
import tools.vitruv.dsls.reactions.codegen.changetyperepresentation.AtomicChangeTypeRepresentation
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import tools.vitruv.dsls.reactions.codegen.helper.AccessibleElement
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.dsls.reactions.codegen.changetyperepresentation.ChangeSequenceRepresentation
import tools.vitruv.dsls.common.helper.ClassNameGenerator

class ReactionClassGenerator extends ClassGenerator {
	private static val String CHANCE_COUNTER_VARIABLE = "currentlyMatchedChange";
	protected final Reaction reaction
	protected final ChangeSequenceRepresentation changeSequenceRepresentation
	protected final boolean hasPreconditionBlock
	private final ClassNameGenerator reactionClassNameGenerator
	private final UserExecutionClassGenerator userExecutionClassGenerator
	private final ClassNameGenerator routinesFacadeClassNameGenerator
	private var JvmGenericType generatedClass
	
	new(Reaction reaction, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		if (reaction?.trigger === null || reaction?.callRoutine === null) {
			throw new IllegalArgumentException();
		}
		this.reaction = reaction;
		this.hasPreconditionBlock = reaction.trigger.precondition !== null;
		this.changeSequenceRepresentation = reaction.trigger.extractChangeSequenceRepresentation;
		this.reactionClassNameGenerator = reaction.reactionClassNameGenerator;
		this.routinesFacadeClassNameGenerator = reaction.reactionsSegment.routinesFacadeClassNameGenerator;
		this.userExecutionClassGenerator = new UserExecutionClassGenerator(typesBuilderExtensionProvider, reaction, 
			reactionClassNameGenerator.qualifiedName + "." + EFFECT_USER_EXECUTION_SIMPLE_NAME);
	}
		
	public override JvmGenericType generateEmptyClass() {
		userExecutionClassGenerator.generateEmptyClass()
		generatedClass = reaction.toClass(reactionClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.DEFAULT
		]
	}
	
	override generateBody() {
		generateMethodExecuteReaction()
		
		generatedClass => [
			documentation = getCommentWithoutMarkers(reaction.documentation)
			superTypes += typeRef(AbstractReactionRealization)
			members += changeSequenceRepresentation.fields.map[reaction.toField(name, it.generateTypeRef(_typeReferenceBuilder))]
			members += reaction.toField(CHANCE_COUNTER_VARIABLE, typeRef(Integer.TYPE))
			members += generatedMethods
			members += userExecutionClassGenerator.generateBody()
		]
	}
	
	protected def generateMatchChangeMethod(AtomicChangeTypeRepresentation change) {
		val methodName = "match" + change.name.toFirstUpper
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE
			val changeParameter = generateUntypedChangeParameter;
			parameters += changeParameter
			body = change.generateCheckMethodBody(changeParameter.name)
		]
	}
	
	protected def generateResetChangesMethod() {
		val methodName = "resetChanges"
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PRIVATE
			body = '''
				«changeSequenceRepresentation.resetFieldsCode»
				«CHANCE_COUNTER_VARIABLE» = 0;
			'''
		]
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
		val matchChangesMethod = generateMatchChangesMethod
		val resetChangesMethod = generateResetChangesMethod;
		val accessibleElementList = changeSequenceRepresentation.generatePropertiesParameterList();
		val callRoutineMethod = userExecutionClassGenerator.generateMethodCallRoutine(reaction.callRoutine, 
			accessibleElementList, typeRef(routinesFacadeClassNameGenerator.qualifiedName));
		val userDefinedPreconditionMethod = if (hasPreconditionBlock) {
			generateMethodCheckUserDefinedPrecondition(reaction.trigger.precondition);	
		}
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			val changeParameter = generateUntypedChangeParameter();
			parameters += changeParameter;
			body = '''
				if (!«matchChangesMethod.simpleName»(«changeParameter.name»)) {
					return;
				}
				«changeSequenceRepresentation.generatePropertiesAssignmentCode()»
								
				«IF hasPreconditionBlock»
					getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
					if (!«userDefinedPreconditionMethod.simpleName»(«
						FOR argument : accessibleElementList.generateArgumentsForAccesibleElements SEPARATOR ", "»«argument»«ENDFOR»)) {
						«resetChangesMethod.simpleName»();
						return;
					}
				«ENDIF»
				getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
								
				«routinesFacadeClassNameGenerator.qualifiedName» routinesFacade = new «routinesFacadeClassNameGenerator.qualifiedName»(this.executionState, this);
				«userExecutionClassGenerator.qualifiedClassName» userExecution = new «userExecutionClassGenerator.qualifiedClassName»(this.executionState, this);
				userExecution.«callRoutineMethod.simpleName»(«
					FOR argument : accessibleElementList.generateArgumentsForAccesibleElements SEPARATOR ", " AFTER ", "»«argument»«ENDFOR»routinesFacade);
				
				«resetChangesMethod.simpleName»();
			'''
		];
	}
	
	public def Iterable<String> generateArgumentsForAccesibleElements(Iterable<AccessibleElement> elements) {
		elements.map[name];
	}
	
	// FIXME HK Use better change matching that using a counter, e.g. with state handling matcher classes
	protected def generateMatchChangesMethod() {
		val methodName = MATCH_METHOD_NAME;
		val matchMethods = changeSequenceRepresentation.atomicChanges.mapFixed[generateMatchChangeMethod]
		val resetChangesMethod = generateResetChangesMethod;
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			val changeParameter = generateUntypedChangeParameter(reaction);
			visibility = JvmVisibility.PUBLIC;
			parameters += changeParameter
			body = '''
				«var counter = 0»
				«FOR atomicChange : changeSequenceRepresentation.atomicChanges»
					if («CHANCE_COUNTER_VARIABLE» == «counter») {
						if (!«matchMethods.get(counter).simpleName»(«changeParameter.name»)) {
							«resetChangesMethod.simpleName»();
							«IF counter !== 0»
								«methodName»(«changeParameter.name»); // Reexecute to potentially register this as first change
							«ENDIF»
							return false;
						} else {
							«CHANCE_COUNTER_VARIABLE»++;
						}
						«IF counter++ < changeSequenceRepresentation.numberOfChanges - 1»
							return false; // Only proceed on the last of the expected changes
						«ENDIF»
					}
				«ENDFOR»
				
				return true;
				'''
		];
	}

	protected def JvmOperation generateMethodCheckUserDefinedPrecondition(PreconditionCodeBlock preconditionBlock) {
		val methodName = USER_DEFINED_TRIGGER_PRECONDITION_METHOD_NAME;
		return preconditionBlock.code.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateAccessibleElementsParameters(changeSequenceRepresentation.generatePropertiesParameterList());
			body = preconditionBlock.code;
		];		
	}
	
}