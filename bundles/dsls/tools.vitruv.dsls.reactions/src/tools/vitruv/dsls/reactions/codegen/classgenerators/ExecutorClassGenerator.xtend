package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.TypesFactory
import tools.vitruv.dsls.reactions.codegen.classgenerators.ClassGenerator
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade

class ExecutorClassGenerator extends ClassGenerator {
	private final ReactionsSegment reactionsSegment;
	var JvmGenericType generatedClass;
	
	new(ReactionsSegment reactionsSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider)
		this.reactionsSegment = reactionsSegment;
	}
	
	override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(reactionsSegment.executorClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
		];
	}

	override generateBody() {
		generatedClass => [
			superTypes += typeRef(AbstractReactionsExecutor);
			members += reactionsSegment.toConstructor() [
				body = '''
				super(new «reactionsSegment.fromDomain.domainProviderForReference.canonicalNameForReference»().getDomain(), 
					new «reactionsSegment.toDomain.domainProviderForReference.canonicalNameForReference»().getDomain());'''
			]

			// register all reactions, including imported and overridden reactions:
			members += reactionsSegment.toMethod("setup", typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PROTECTED;
				body = '''
					«FOR reaction : reactionsSegment.allReactions.values.flatten»
						«val reactionsNameGenerator = reaction.reactionClassNameGenerator»
						this.addReaction(new «reactionsNameGenerator.qualifiedName»(this));
					«ENDFOR»
				'''
			]

			// generate routine facade factory method:
			val routineFacadeTypeParam = TypesFactory.eINSTANCE.createJvmTypeParameter => [
				name = "T";
				constraints += TypesFactory.eINSTANCE.createJvmUpperBound => [
					typeReference = typeRef(AbstractRepairRoutinesFacade);
				]
			]
			members += reactionsSegment.toMethod(EXECUTOR_ROUTINES_FACADE_FACTORY_METHOD_NAME, typeRef(routineFacadeTypeParam)) [
				visibility = JvmVisibility.PUBLIC;
				typeParameters += routineFacadeTypeParam;
				val reactionsSegmentNameParameter = generateParameter("reactionsSegmentName", typeRef(String));
				val executionStateParameter = generateReactionExecutionStateParameter();
				val calledByParameter = generateParameter("calledBy", typeRef(CallHierarchyHaving));
				parameters += reactionsSegmentNameParameter;
				parameters += executionStateParameter;
				parameters += calledByParameter;
				body = '''
					switch(«reactionsSegmentNameParameter.name») {
					«val routinesFacadeClassNameGenerator = reactionsSegment.routinesFacadeClassNameGenerator»
						case "«reactionsSegment.name»": {
							return (T) new «routinesFacadeClassNameGenerator.qualifiedName»(this, «executionStateParameter.name», «calledByParameter.name»);
						}
					«FOR importedReactionsSegment : reactionsSegment.allImportedReactionsSegments»
						«val importedRoutinesFacadeClassNameGenerator = reactionsSegment.getImportedRoutinesFacadeActualClassNameGenerator(importedReactionsSegment)»
							case "«importedReactionsSegment.name»": {
								return (T) new «importedRoutinesFacadeClassNameGenerator.qualifiedName»(this, «executionStateParameter.name», «calledByParameter.name»);
							}
					«ENDFOR»
						default: {
							throw new IllegalArgumentException("Unexpected reactions segment name: " + «reactionsSegmentNameParameter.name»);
						}
					}
				'''
			]
		]
	}
}