package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext

class ElementReplacedReactionGenerator extends AbstractContainingReactionTypeGenerator {

	private MetaclassFeatureReference reference
	private MetaclassReference targetElement

	new(MetaclassFeatureReference reference) {
		super(reference.metaclass, ReactionTriggerType.UPDATE)
		this.reference = reference
	}

	new(MetaclassFeatureReference attribute, MetaclassReference targetElement) {
		this(attribute)
		this.targetElement = targetElement
	}

	override generateTrigger(MappingGeneratorContext context) {
		if (targetElement !== null) {
			this.usesNewValue = true
			this.reactionName = '''«targetElement.parameterName»ReplacedAt«reference.parameterName»'''
			return context.create.reaction(reactionName()).afterElement(targetElement.metaclass).replacedAt(
				reference.feature as EReference)
		} else {
			this.reactionName = '''ElementReplacedAt«reference.parameterName»'''
			return context.create.reaction(reactionName()).afterElement.replacedAt(reference.feature as EReference)
		}
	}

	override toString() '''
	«IF targetElement!==null»«targetElement.parameterName»«ELSE»element«ENDIF» replaced at «targetElement.parameterName»'''

	override equals(Object obj) {
		if (obj instanceof ElementReplacedReactionGenerator) {
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
