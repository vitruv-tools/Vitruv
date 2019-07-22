package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class RemovedReactionGenerator extends AbstractReactionTypeGenerator {

	private MetaclassFeatureReference removeTarget = null

	new(MetaclassReference element) {
		super(element.metaclass)
	}

	new(MetaclassReference element, MetaclassFeatureReference insertedIn) {
		this(element)
		this.removeTarget = insertedIn
	}

	override generateTrigger(ReactionGeneratorContext context) {
		if (removeTarget !== null) {
			this.reactionName = '''«metaclass.parameterName»RemovedFrom«removeTarget.parameterName»'''
			return context.create.reaction(reactionName()).afterElement(metaclass).removedFrom(
				removeTarget.feature as EReference)
		} else {
			this.reactionName = '''«metaclass.parameterName»RemovedAsRoot'''
			return context.create.reaction(reactionName()).afterElement(metaclass).removedAsRoot
		}
	}

	override toString() '''
	«metaclass.parameterName» removed «IF removeTarget!==null»from «removeTarget.parameterName»«ELSE»as root«ENDIF»'''

	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder, MappingParameter parameter) {
		iterateParameters [ reactionParameter, correspondingParameter |
			val newElement = getParameterName(reactionParameter, correspondingParameter)
			builder.vall(newElement).retrieve(correspondingParameter.value.metaclass).correspondingTo.affectedEObject.
				taggedWith(reactionParameter, correspondingParameter)
		]
	}

	override generateCorrespondenceActions(ActionStatementBuilder builder, MappingParameter parameter) {
		// delete 
		iterateParameters [ reactionParameter, correspondingParameter |
			val element = getParameterName(reactionParameter, correspondingParameter)
			builder.removeCorrespondenceBetween(element).and.affectedEObject.taggedWith(reactionParameter,
				correspondingParameter)
			builder.delete(element)
		]
	}

	override equals(Object obj) {
		if (obj instanceof RemovedReactionGenerator) {
			if (metaclass == obj.metaclass) {
				if (removeTarget === null) {
					return obj.removeTarget === null
				} else {
					if (obj.removeTarget !== null) {
						return removeTarget.feature == obj.removeTarget.feature
					}
				}
			}
		}
		false
	}

}
