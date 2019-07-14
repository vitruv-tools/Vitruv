package tools.vitruv.dsls.mappings.generator.conditions

import java.util.ArrayList
import java.util.List
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.reactions.DeletedReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.mappings.generator.reactions.InsertedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.RemovedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.ElementReplacedReactionGenerator

class ReactionTypeFactory {

	private List<SingleSidedCondition> conditions
	private List<AbstractReactionTypeGenerator> generators = new ArrayList<AbstractReactionTypeGenerator>()
	private List<NamedMetaclassReference> fromParameters
	private List<NamedMetaclassReference> toParameters

	new(List<SingleSidedCondition> conditions) {
		this.conditions = conditions;
	}

	def List<AbstractReactionTypeGenerator> constructGenerators(List<NamedMetaclassReference> fromParameters,
		List<NamedMetaclassReference> toParameters) {
		generators.clear
		this.fromParameters = fromParameters
		this.toParameters = toParameters
		insertDefaultTriggers
		conditions.forEach [ condition |
			val conditionGenerator = SingleSidedConditionFactory.construct(condition)
			if (conditionGenerator !== null) {
				conditionGenerator.constructReactionTriggers.forEach [ generator |
					tryInsertTrigger(generator)
				]
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

	private def insertDefaultTriggers() {
		// add insert as root and delete trigger for all distinct metaclasses
		fromParameters.distinctMetaclasses.forEach [
			val defaultInsertGenerator = new InsertedReactionGenerator(it)
			defaultInsertGenerator.initGenerator
			val defaultDeleteGenerator = new DeletedReactionGenerator(it)
			defaultDeleteGenerator.initGenerator
			// make it subordinate to other inserted generators so it will be replaced 
			defaultInsertGenerator.conflictingTriggerCheck = [ generator |
				if (generator instanceof InsertedReactionGenerator) {
					return true
				}
				if(generator instanceof ElementReplacedReactionGenerator){
					return true
				}
				false
			]
			// make it subordinate to other delete generators so it will be replaced
			defaultDeleteGenerator.conflictingTriggerCheck = [ generator |
				if (generator instanceof RemovedReactionGenerator) {
					return true
				}
				if (generator instanceof DeletedReactionGenerator) {
					return true
				}
				if(generator instanceof ElementReplacedReactionGenerator){
					return true
				}
				false
			]
			generators.add(defaultInsertGenerator)
			generators.add(defaultDeleteGenerator)
		]
	}

	private def tryInsertTrigger(AbstractReactionTypeGenerator generator) {
		generator.initGenerator
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

	private def initGenerator(AbstractReactionTypeGenerator generator) {
		generator.init(fromParameters, toParameters)
	}

	private def boolean isDuplicateOfExistingTrigger(AbstractReactionTypeGenerator generator) {
		generators.contains(generator)
	}

}
