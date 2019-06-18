package tools.vitruv.dsls.mappings.generator.conditions

import java.util.ArrayList
import java.util.List
import tools.vitruv.dsls.mappings.generator.trigger.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.trigger.DeletedReactionGenerator
import tools.vitruv.dsls.mappings.generator.trigger.InsertedInReactionGenerator
import tools.vitruv.dsls.mappings.generator.trigger.RemovedFromReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference

class ReactionTypeFactory {

	private List<SingleSidedCondition> conditions
	private List<AbstractReactionTypeGenerator> generators = new ArrayList<AbstractReactionTypeGenerator>()

	new(List<SingleSidedCondition> conditions) {
		this.conditions = conditions;
	}

	def List<AbstractReactionTypeGenerator> constructGenerators(List<NamedMetaclassReference> fromAttributes) {
		generators.clear
		insertDefaultTriggers(fromAttributes.distinctMetaclasses)
		conditions.forEach [ condition |
			val generator = SingleSidedConditionFactory.construct(condition)
			if (generator !== null) {
				generator.constructReactionTriggers.forEach[tryInsertTrigger]
			} else {
				System.err.println('''No single sided condition generator found for condition «condition»''')
			}
		]
		generators
	}

	private def getDistinctMetaclasses(List<NamedMetaclassReference> references) {
		val distinctList = new ArrayList<NamedMetaclassReference>()
		references.forEach [ reference |
			if (!distinctList.exists [
				it.metaclass == reference.metaclass
			]) {
				distinctList.add(reference)
			}
		]
		return distinctList
	}

	private def insertDefaultTriggers(List<NamedMetaclassReference> fromAttributes) {
		// add insert as root and delete trigger for all distinct metaclasses
		fromAttributes.forEach [
			val defaultInsertGenerator = new InsertedInReactionGenerator(it)
			val defaultDeleteGenerator = new DeletedReactionGenerator(it)
			// make it subordinate to other inserted generators so it will be replaced 
			defaultInsertGenerator.conflictingTriggerCheck = [ generator |
				if (generator instanceof InsertedInReactionGenerator) {
					return true
				}
				false
			]
			// make it subordinate to other delete generators so it will be replaced
			defaultDeleteGenerator.conflictingTriggerCheck = [ generator |
				if (generator instanceof RemovedFromReactionGenerator) {
					return true
				}
				if (generator instanceof DeletedReactionGenerator) {
					return true
				}
				false
			]
			generators.add(defaultInsertGenerator)
			generators.add(defaultDeleteGenerator)
		]
	}

	private def tryInsertTrigger(AbstractReactionTypeGenerator generator) {
		// check if the same trigger already exists
		if (isDuplicateOfExistingTrigger(generator)) {
			// nothing to do
			return
		}
		// check if it should overwrite a matching subordinate trigger
		var generatorAdded = false
		val iterator = generators.iterator
		while (generatorAdded == false && iterator.hasNext) {
			val existingGenerator = iterator.next
			if (existingGenerator.isSubordinateToTrigger(generator)) {
				// overwrite the subordinate trigger
				generatorAdded = true
				generators.remove(existingGenerator)
				generators.add(generator)
			}
		}
		if (!generatorAdded) {
			// no overwriting, just add
			generators.add(generator)
		}
	}

	private def boolean isDuplicateOfExistingTrigger(AbstractReactionTypeGenerator generator) {
		generators.contains(generator)
	}

}
