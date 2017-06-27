package tools.vitruv.dsls.reactions.codegen.classgenerators

import tools.vitruv.dsls.reactions.codegen.classgenerators.ClassGenerator
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.framework.userinteraction.UserInteracting
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
	
	override generateClass() {
		val executorNameGenerator = reactionsSegment.executorClassNameGenerator;
		reactionsSegment.toClass(executorNameGenerator.qualifiedName) [
			superTypes += typeRef(AbstractReactionsExecutor);
			members += toConstructor() [
				body = '''
				super(new «reactionsSegment.fromDomain.domainProviderForReference.class»().getDomain(), 
					new «reactionsSegment.toDomain.domainProviderForReference.class»().getDomain());'''
			]
			members += toMethod("setup", typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PROTECTED;
				body = '''
					«UserInteracting.name» userInteracting = getUserInteracting();
					«FOR reaction : reactionsSegment.reactions»
						«val reactionsNameGenerator = reaction.reactionClassNameGenerator»
						this.addReaction(«reactionsNameGenerator.qualifiedName».getExpectedChangeType(), new «reactionsNameGenerator.qualifiedName»(userInteracting));
					«ENDFOR»
				'''
			]
		]
	}
	
}