package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.CorrespondenceElementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RetrieveModelElementMatcherStatementCorrespondenceElementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.TaggedWithBuilder

abstract class AbstractContainingReactionTypeGenerator extends AbstractReactionTypeGenerator {

	new(EClass metaclass) {
		super(metaclass)
	}

	def matchingElement(RetrieveModelElementMatcherStatementCorrespondenceElementBuilder builder) {
		if (usesNewValue) {
			builder.newValue
		} else {
			builder.affectedEObject
		}
	}

	def matchingElement(CorrespondenceElementBuilder<TaggedWithBuilder> builder) {
		if (usesNewValue) {
			builder.newValue
		} else {
			builder.affectedEObject
		}
	}

}
