package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import tools.vitruv.dsls.reactions.codegen.classgenerators.ClassGenerator
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsImportsHelper.*;

class ExecutorClassGenerator extends ClassGenerator {
	final ReactionsSegment reactionsSegment;
	var JvmGenericType generatedClass;
	
	new(ReactionsSegment reactionsSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider)
		if (!reactionsSegment.isComplete) {
			throw new IllegalArgumentException("incomplete");
		}
		this.reactionsSegment = reactionsSegment;
	}
	
	override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(reactionsSegment.executorClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.DEFAULT;
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

			// create routines facades provider:
			members += reactionsSegment.toMethod("createRoutinesFacadesProvider", typeRef(RoutinesFacadesProvider)) [
				visibility = JvmVisibility.PROTECTED;
				body = '''
					return new «reactionsSegment.routinesFacadesProviderClassNameGenerator.qualifiedName»();
				'''
			]

			// register all reactions, including imported and overridden reactions:
			members += reactionsSegment.toMethod("setup", typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PROTECTED;
				body = '''
					«FOR reactionEntry : reactionsSegment.includedReactions.entrySet»
						«val reaction = reactionEntry.key»
						«val reactionsImportPath = reactionEntry.value»
						«val reactionsNameGenerator = reaction.reactionClassNameGenerator»
						this.addReaction(new «reactionsNameGenerator.qualifiedName»(this.getRoutinesFacadesProvider().getRoutinesFacade(«
							»«ReactionsImportPath».fromPathString("«reactionsImportPath.pathString»"))));
					«ENDFOR»
				'''
			]
		]
	}
}