package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext
import tools.vitruv.dsls.mappings.generator.MappingScenarioType
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEAttributeReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory

class AttributeReplacedReactionTriggerGenerator extends AbstractReactionTriggerGenerator {

	var MetaclassEAttributeReference attribute

	new(MetaclassFeatureReference reference) {
		super(reference.metaclass, MappingScenarioType.CREATE_OR_DELETE)
		val refFeature = reference.feature
		if (refFeature instanceof EAttribute) {
			this.attribute = MirBaseFactory.eINSTANCE.createMetaclassEAttributeReference => [
				metaclass = reference.metaclass
				feature = refFeature
			]
		} else {
			throw new RuntimeException('''Cant create an attributereplaced reaction trigger with the reference «reference» because it doesnt contain an EAttribute feature!''')
		}
	}

	new(MetaclassEAttributeReference attribute) {
		super(attribute.metaclass, MappingScenarioType.CREATE_OR_DELETE)
		this.attribute = attribute
	}

	override generateTrigger(MappingGeneratorContext context) {
		this.reactionName = '''«attribute.getAttributeName.toFirstUpper»ReplacedAt«metaclass.parameterName»_«attribute.feature.name»'''
		context.create.reaction(reactionName()).afterAttributeReplacedAt(metaclass, extractAttribute)
	}

	override toString() '''
	«attribute.attributeName» replaced at «metaclass.parameterName»'''

	private def extractAttribute() {
		attribute.feature
	}

	override equals(Object obj) {
		if (obj instanceof AttributeReplacedReactionTriggerGenerator) {
			return attribute.feature == obj.attribute.feature && metaclass == obj.metaclass
		}
		false
	}

}
