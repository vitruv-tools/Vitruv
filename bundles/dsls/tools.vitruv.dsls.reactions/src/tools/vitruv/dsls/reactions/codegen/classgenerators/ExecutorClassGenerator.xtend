package tools.vitruv.dsls.reactions.codegen.classgenerators

import tools.vitruv.dsls.reactions.codegen.classgenerators.ClassGenerator
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;

class ExecutorClassGenerator extends ClassGenerator {
	private final ReactionsSegment reactionsSegment;
	
	new(ReactionsSegment reactionsSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider)
		this.reactionsSegment = reactionsSegment;
	}
	
	override generateEmptyClass() {
		val executorNameGenerator = reactionsSegment.executorClassNameGenerator;
		reactionsSegment.toClass(executorNameGenerator.qualifiedName) [
			superTypes += typeRef(AbstractReactionsExecutor);
			members += toConstructor() [
				body = '''
				super(new «reactionsSegment.fromDomain.domainProviderForReference.canonicalNameForReference»().getDomain(), 
					new «reactionsSegment.toDomain.domainProviderForReference.canonicalNameForReference»().getDomain());'''
			]
			members += toMethod("setup", typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PROTECTED;
				body = '''
					«FOR reaction : reactionsSegment.reactions»
						«val reactionsNameGenerator = reaction.reactionClassNameGenerator»
						this.addReaction(new «reactionsNameGenerator.qualifiedName»());
					«ENDFOR»
				'''
			]
		]
	}
	
}