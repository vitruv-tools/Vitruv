package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext
import tools.vitruv.dsls.mappings.generator.MappingScenarioType
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference

class ElementReplacedReactionTriggerGenerator extends AbstractContainingReactionTriggerGenerator {

	private MetaclassFeatureReference reference
	private MetaclassReference targetElement

	new(MetaclassFeatureReference reference) {
		super(reference.metaclass, MappingScenarioType.CREATE_OR_DELETE)
		this.reference = reference
	}

	new(MetaclassFeatureReference attribute, MetaclassReference targetElement) {
		this(attribute)
		this.targetElement = targetElement
	}

	override generateTrigger(MappingGeneratorContext context) {
		if (targetElement !== null) {
			this.usesNewValue = true
			this.reactionName = '''«targetElement.metaclassName»ReplacedAt«reference.metaclassName»_«reference.feature.name»'''
			return context.create.reaction(reactionName()).afterElement(targetElement.metaclass).replacedAt(
				reference.feature as EReference)
		} else {
			this.reactionName = '''ElementReplacedAt«reference.metaclassName»_«reference.feature.name»'''
			return context.create.reaction(reactionName()).afterElement.replacedAt(reference.feature as EReference)
		}
	}

	override toString() '''
	«IF targetElement!==null»«targetElement.metaclassName»«ELSE»element«ENDIF» replaced at «reference.metaclassFeatureName»'''

	override equals(Object obj) {
		if (obj instanceof ElementReplacedReactionTriggerGenerator) {
			if (metaclass == obj.metaclass) {
				if (reference.feature == obj.reference.feature) {
					if (targetElement === null) {
						return obj.targetElement === null
					} else {
						if (obj.targetElement !== null) {
							return targetElement.metaclass == obj.targetElement.metaclass
						}
					}
				}
			}
		}
		false
	}

}
