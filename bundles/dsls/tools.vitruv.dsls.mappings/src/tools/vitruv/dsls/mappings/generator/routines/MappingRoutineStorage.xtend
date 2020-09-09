package tools.vitruv.dsls.mappings.generator.routines

import java.util.HashMap
import java.util.List
import java.util.Map
import org.apache.log4j.Logger
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.AbstractSingleSidedCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter

class MappingRoutineStorage {

	static val Logger logger = Logger.getLogger(MappingRoutineStorage)

	val Map<Class<? extends AbstractMappingRoutineGenerator>, AbstractMappingRoutineGenerator> routineGenerators = new HashMap
	var List<MappingParameter> fromParameters
	var List<MappingParameter> toParameters
	var String mappingName
	var MappingGeneratorContext context
	var List<AbstractSingleSidedCondition<?>> singleSidedConditions
	var List<AbstractBidirectionalCondition> bidirectionalConditions
	var List<AbstractSingleSidedCondition<?>> correspondingSingleSidedConditions

	new(List<MappingParameter> fromParameters, List<MappingParameter> toParameters) {
		this.fromParameters = fromParameters
		this.toParameters = toParameters
	}
	
	/**
	 * Initializes the fields 
	 */
	def init(String mappingName, MappingGeneratorContext context,
		List<AbstractSingleSidedCondition<?>> singleSidedConditions,
		List<AbstractSingleSidedCondition<?>> correspondingSingleSidedConditions,
		List<AbstractBidirectionalCondition> bidirectionalConditions) {
		this.mappingName = mappingName
		this.context = context
		this.singleSidedConditions = singleSidedConditions
		this.correspondingSingleSidedConditions = correspondingSingleSidedConditions
		this.bidirectionalConditions = bidirectionalConditions
	}
	
	/**
	 * Generates a routine from a routine generator
	 */
	def generateRoutine(AbstractMappingRoutineGenerator generator) {
		logger.info('''=> generate routine: «generator.toString»''')
		generator.init(mappingName, fromParameters, toParameters)
		generator.prepareGenerator(singleSidedConditions, correspondingSingleSidedConditions, bidirectionalConditions,
			this)
		val routine = generator.generate(context.create)
		context.segmentBuilder += routine
		routineGenerators.put(generator.class, generator)
	}
	
	/**
	 * Returns a routine generator by the class
	 */
	def getRoutine(Class<? extends AbstractMappingRoutineGenerator> key) {
		routineGenerators.get(key)
	}

	/**
	 * Returns a FluentRoutineBuilder used within a routine generator by the class
	 */
	def getRoutineBuilder(Class<? extends AbstractMappingRoutineGenerator> key) {
		routineGenerators.get(key).routine
	}
}
