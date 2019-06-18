package tools.vitruv.dsls.mappings.generator.trigger

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference

class InsertedInReactionGenerator extends AbstractReactionTypeGenerator {

	private MetaclassFeatureReference insertTarget = null

	new(MetaclassReference element) {
		super(element.metaclass)
	}

	new(MetaclassReference element, MetaclassFeatureReference insertedIn) {
		this(element)
		this.insertTarget = insertedIn
	}

	override generateTrigger(ReactionGeneratorContext context) {
		if (insertTarget !== null) {
			return context.create.reaction('''on«metaclass.parameterName»InsertedIn«insertTarget.parameterName»''').
				afterElement(metaclass).insertedIn(insertTarget.feature as EReference)
		} else {
			return context.create.reaction('''on«metaclass.parameterName»InsertedAsRoot''').afterElement(metaclass).
				insertedAsRoot
		}
	}

	override equals(Object obj) {
		if (obj instanceof InsertedInReactionGenerator) {
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
