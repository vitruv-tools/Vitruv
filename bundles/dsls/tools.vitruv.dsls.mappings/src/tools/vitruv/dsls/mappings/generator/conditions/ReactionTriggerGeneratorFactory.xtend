package tools.vitruv.dsls.mappings.generator.conditions

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.ElementReplacedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mappings.generator.reactions.DeletedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.InsertedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.RemovedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.ConflictingTriggerCheck
import tools.vitruv.dsls.common.elements.NamedMetaclassReference

class ReactionTriggerGeneratorFactory {

	static val Logger logger = Logger.getLogger(ReactionTriggerGeneratorFactory)
	var List<SingleSidedCondition> conditions
	var List<AbstractReactionTriggerGenerator> generators = new ArrayList<AbstractReactionTriggerGenerator>()
	var List<MappingParameter> fromParameters
	var List<MappingParameter> toParameters
	var String mappingName

	new(String mappingName, List<SingleSidedCondition> conditions) {
		this.mappingName = mappingName
		this.conditions = conditions;
	}

	/**
	 * Creates ReactionTriggerGenerators from a mapping specification
	 */
	def List<AbstractReactionTriggerGenerator> constructGenerators(List<MappingParameter> fromParameters,
		List<MappingParameter> toParameters) {
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
				logger.error('''No single sided condition generator found for condition «condition»''')
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
		fromParameters.map[it.value].distinctMetaclasses.forEach [
			val defaultInsertGenerator = new InsertedReactionTriggerGenerator(it)
			defaultInsertGenerator.initGenerator
			val defaultDeleteGenerator = new DeletedReactionTriggerGenerator(it)
			defaultDeleteGenerator.initGenerator
			// make it subordinate to other inserted generators so it will be replaced 
			defaultInsertGenerator.conflictingTriggerCheck = conflictingInsertionGeneratorsCheck
			// make it subordinate to other delete generators so it will be replaced
			defaultDeleteGenerator.conflictingTriggerCheck = conflictingDeletionGeneratorsCheck
			generators.add(defaultInsertGenerator)
			generators.add(defaultDeleteGenerator)
		]
	}

	// conflict with inserte or replace triggers
	private def ConflictingTriggerCheck conflictingInsertionGeneratorsCheck() {
		[ generator |
			if (generator instanceof InsertedReactionTriggerGenerator) {
				return true
			}
			if (generator instanceof ElementReplacedReactionTriggerGenerator) {
				return true
			}
			false
		]
	}

	// conflict with remove, delete or replace triggers
	private def ConflictingTriggerCheck conflictingDeletionGeneratorsCheck() {
		[ generator |
			if (generator instanceof RemovedReactionTriggerGenerator) {
				return true
			}
			if (generator instanceof DeletedReactionTriggerGenerator) {
				return true
			}
			if (generator instanceof ElementReplacedReactionTriggerGenerator) {
				return true
			}
			false
		]
	}

	private def tryInsertTrigger(AbstractReactionTriggerGenerator generator) {
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

	private def initGenerator(AbstractReactionTriggerGenerator generator) {
		generator.init(mappingName, fromParameters, toParameters)
	}

	private def boolean isDuplicateOfExistingTrigger(AbstractReactionTriggerGenerator generator) {
		generators.contains(generator)
	}

}
