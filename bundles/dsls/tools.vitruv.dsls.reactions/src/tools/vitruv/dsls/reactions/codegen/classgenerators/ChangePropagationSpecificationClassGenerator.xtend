package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsSegment
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification
import tools.vitruv.framework.propagation.ChangePropagationSpecification

import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsElementsCompletionChecker.isReferenceable

class ChangePropagationSpecificationClassGenerator extends ClassGenerator {
	final ReactionsSegment reactionsSegment;
	var JvmGenericType generatedClass;
	
	new(ReactionsSegment reactionsSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider)
		if (!reactionsSegment.isReferenceable) {
			throw new IllegalArgumentException("incomplete");
		}
		this.reactionsSegment = reactionsSegment;
	}
	
	override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(reactionsSegment.changePropagationSpecificationClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
		];
	}

	override generateBody() {
		generatedClass => [
			superTypes += typeRef(AbstractReactionsChangePropagationSpecification);
			superTypes += typeRef(ChangePropagationSpecification);
			members += reactionsSegment.toConstructor() [
				body = '''
				super(new «reactionsSegment.fromDomain.domainProviderForReference.canonicalNameForReference»().getDomain(), 
					new «reactionsSegment.toDomain.domainProviderForReference.canonicalNameForReference»().getDomain());'''
			]

			// register executor as change processor:
			members += reactionsSegment.toMethod("setup", typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PROTECTED;
				body = '''
					this.addChangeMainprocessor(new «reactionsSegment.executorClassNameGenerator.qualifiedName»());
				'''
			]
		]
	}
}