package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEAttributeReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class AttributeReplacedReactionGenerator extends AbstractReactionTypeGenerator {

	private MetaclassEAttributeReference attribute

	new(MetaclassFeatureReference reference) {
		super(reference.metaclass)
		val refFeature = reference.feature
		if (refFeature instanceof EAttribute) {
			this.attribute = MirBaseFactory.eINSTANCE.createMetaclassEAttributeReference => [
				metaclass = reference.metaclass
				feature = refFeature
			]
		} else {
			throw new RuntimeException('''Cant create an attributereplaced reaction with the reference «reference» because it doesnt contain an EAttribute feature!''')
		}
	}

	new(MetaclassEAttributeReference attribute) {
		super(attribute.metaclass)
		this.attribute = attribute
	}

	override generateTrigger(ReactionGeneratorContext context) {
		this.reactionName = '''«attribute.getAttributeName.toFirstUpper»ReplacedAt«metaclass.parameterName»'''
		val trigger = context.create.reaction(reactionName()).afterAttributeReplacedAt(metaclass, extractAttribute)
	/* 	trigger.with[typeProvider | 
			
		]*/
		trigger
	}

	override toString() '''
	«attribute.attributeName» replaced at «metaclass.parameterName»'''

	private def extractAttribute() {
		attribute.feature as EAttribute
	}

	override equals(Object obj) {
		if (obj instanceof AttributeReplacedReactionGenerator) {
			return attribute.feature == obj.attribute.feature && metaclass == obj.metaclass
		}
		false
	}

	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		// nothing to do, only single sided conditions create matchers
	}

	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		// nothing to do, only bidirectional conditions create actions
	}

}
