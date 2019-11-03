package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.mappings.generator.MappingScenarioType
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.CorrespondenceElementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RetrieveModelElementMatcherStatementCorrespondenceElementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.TaggedWithBuilder

abstract class AbstractContainingReactionTriggerGenerator extends AbstractReactionTriggerGenerator {

	
	new(EClass metaclass, MappingScenarioType triggerType) {
		super(metaclass, triggerType)
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
