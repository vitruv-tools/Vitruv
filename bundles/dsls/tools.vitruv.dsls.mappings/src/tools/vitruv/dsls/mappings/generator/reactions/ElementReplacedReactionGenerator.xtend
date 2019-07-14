package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEAttributeReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import org.eclipse.emf.ecore.EReference

class ElementReplacedReactionGenerator extends AbstractReactionTypeGenerator {

	private MetaclassFeatureReference reference
	private MetaclassReference targetElement

	new(MetaclassFeatureReference reference) {
		super(reference.metaclass)
		this.reference = reference
	}

	new(MetaclassFeatureReference attribute, MetaclassReference targetElement) {
		this(attribute)
		this.targetElement = targetElement
	}

	override generateTrigger(ReactionGeneratorContext context) {
		if (targetElement !== null) {
			return context.create.reaction(reactionName('''«targetElement.parameterName»ReplacedAt«reference.parameterName»''')).afterElement(targetElement.metaclass).replacedAt(
				reference.feature as EReference)			
		} else {
			return context.create.reaction(reactionName('''ElementReplacedAt«reference.parameterName»''')).afterElement.replacedAt(
				reference.feature as EReference)
		}
	}
	
	override toString()'''
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

	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		// nothing to do, only single sided conditions create matchers
	}

	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		// nothing to do, only bidirectional conditions create actions
	}

}
