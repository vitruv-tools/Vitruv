package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext
import tools.vitruv.dsls.mappings.generator.MappingScenarioType
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference

class InsertedReactionTriggerGenerator extends AbstractContainingReactionTriggerGenerator {

	private MetaclassFeatureReference insertTarget = null

	new(MetaclassReference element) {
		super(element.metaclass, MappingScenarioType.CREATE)
	}

	new(MetaclassReference element, MetaclassFeatureReference insertedIn) {
		this(element)
		this.insertTarget = insertedIn
	}

	override generateTrigger(MappingGeneratorContext context) {
		if (insertTarget !== null) {
			this.usesNewValue = true
			this.reactionName = '''«metaclass.parameterName»InsertedIn«insertTarget.metaclassName»'''
			return context.create.reaction(reactionName()).afterElement(metaclass).insertedIn(
				insertTarget.feature as EReference)
		} else {
			this.reactionName = '''«metaclass.parameterName»InsertedAsRoot'''
			return context.create.reaction(reactionName()).afterElement(metaclass).created
		}
	}

	override toString() '''
	«metaclass.parameterName» inserted «IF insertTarget!==null»in «insertTarget.metaclassName»«ELSE»as root«ENDIF»'''

	override equals(Object obj) {
		if (obj instanceof InsertedReactionTriggerGenerator) {
			if (metaclass == obj.metaclass) {
				if (insertTarget === null) {
					return obj.insertTarget === null
				} else {
					if (obj.insertTarget !== null) {
						return insertTarget.feature == obj.insertTarget.feature
					}
				}
			}
		}
		false
	}

}
