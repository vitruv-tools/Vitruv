package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext
import tools.vitruv.dsls.mappings.generator.MappingScenarioType
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference

class RemovedReactionTriggerGenerator extends AbstractContainingReactionTriggerGenerator {

	private MetaclassFeatureReference removeTarget = null

	new(MetaclassReference element) {
		super(element.metaclass, MappingScenarioType.DELETE)
	}

	new(MetaclassReference element, MetaclassFeatureReference insertedIn) {
		this(element)
		this.removeTarget = insertedIn
	}

	override generateTrigger(MappingGeneratorContext context) {
		if (removeTarget !== null) {
			this.usesNewValue = true
			this.reactionName = '''«metaclass.parameterName»RemovedFrom«removeTarget.metaclassName»'''
			return context.create.reaction(reactionName()).afterElement(metaclass).removedFrom(
				removeTarget.feature as EReference)
		} else {
			this.reactionName = '''«metaclass.parameterName»RemovedAsRoot'''
			return context.create.reaction(reactionName()).afterElement(metaclass).removedAsRoot
		}
	}

	override toString() '''
	«metaclass.parameterName» removed «IF removeTarget!==null»from «removeTarget.metaclassFeatureName»«ELSE»as root«ENDIF»'''

	override equals(Object obj) {
		if (obj instanceof RemovedReactionTriggerGenerator) {
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
