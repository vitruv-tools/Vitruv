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

	private static val Logger logger = Logger.getLogger(MappingRoutineStorage)

	private Map<Class<? extends AbstractMappingRoutineGenerator>, AbstractMappingRoutineGenerator> routineGenerators = new HashMap
	private List<MappingParameter> fromParameters
	private List<MappingParameter> toParameters
	private String mappingName
	private MappingGeneratorContext context
	private List<AbstractSingleSidedCondition> singleSidedConditions
	private List<AbstractBidirectionalCondition> bidirectionalConditions
	private List<AbstractSingleSidedCondition> correspondingSingleSidedConditions

	new(List<MappingParameter> fromParameters, List<MappingParameter> toParameters) {
		this.fromParameters = fromParameters
		this.toParameters = toParameters
	}
	
	/**
	 * Initializes the fields 
	 */
	public def init(String mappingName, MappingGeneratorContext context,
		List<AbstractSingleSidedCondition> singleSidedConditions,
		List<AbstractSingleSidedCondition> correspondingSingleSidedConditions,
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
	public def generateRoutine(AbstractMappingRoutineGenerator generator) {
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
	public def getRoutine(Class<? extends AbstractMappingRoutineGenerator> key) {
		routineGenerators.get(key)
	}

	/**
	 * Returns a FluentRoutineBuilder used within a routine generator by the class
	 */
	public def getRoutineBuilder(Class<? extends AbstractMappingRoutineGenerator> key) {
		routineGenerators.get(key).routine
	}
}
