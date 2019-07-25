package tools.vitruv.dsls.mappings.generator.routines

import java.util.HashMap
import java.util.List
import java.util.Map
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.AbstractSingleSidedCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter

class MappingRoutineStorage {

	private Map<Class<? extends AbstractMappingRoutineGenerator>, AbstractMappingRoutineGenerator> routineGenerators = new HashMap
	private List<MappingParameter> fromParameters
	private List<MappingParameter> toParameters
	private String mappingName
	private ReactionGeneratorContext context
	private List<AbstractSingleSidedCondition> singleSidedConditions
	private List<AbstractBidirectionalCondition> bidirectionalConditions
	private List<AbstractSingleSidedCondition> correspondingSingleSidedConditions

	new(List<MappingParameter> fromParameters, List<MappingParameter> toParameters) {
		this.fromParameters = fromParameters
		this.toParameters = toParameters
	}

	public def init(String mappingName, ReactionGeneratorContext context,
		List<AbstractSingleSidedCondition> singleSidedConditions,
		List<AbstractSingleSidedCondition> correspondingSingleSidedConditions,
		List<AbstractBidirectionalCondition> bidirectionalConditions) {
		this.mappingName = mappingName
		this.context = context
		this.singleSidedConditions = singleSidedConditions
		this.correspondingSingleSidedConditions = correspondingSingleSidedConditions
		this.bidirectionalConditions = bidirectionalConditions
	}

	public def generateRoutine(AbstractMappingRoutineGenerator generator) {
		generator.init(mappingName, fromParameters, toParameters)
		generator.prepareGenerator(singleSidedConditions, correspondingSingleSidedConditions, bidirectionalConditions,
			this)
		val routine = generator.generate(context.create)
		context.segmentBuilder += routine
		routineGenerators.put(generator.class, generator)
	}

	public def getRoutine(Class<? extends AbstractMappingRoutineGenerator> key) {
		routineGenerators.get(key)
	}

	public def getRoutineBuilder(Class<? extends AbstractMappingRoutineGenerator> key) {
		routineGenerators.get(key).routine
	}
}
