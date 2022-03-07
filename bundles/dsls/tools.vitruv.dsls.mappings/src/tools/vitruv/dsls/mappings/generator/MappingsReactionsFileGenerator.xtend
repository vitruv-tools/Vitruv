package tools.vitruv.dsls.mappings.generator

import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsFile

class MappingsReactionsFileGenerator {
	@Accessors(PROTECTED_GETTER)
	val FluentReactionsLanguageBuilder create
	val MappingsFile mappingsFile;
	var MappingGeneratorContext context
	var boolean left2right
	var MappingsSegment segment

	new(MappingsSegment segment, boolean left2right, FluentReactionsLanguageBuilder create,
		MappingsFile file) {
		this.segment = segment
		this.left2right = left2right
		this.create = create
		this.mappingsFile = file
	}

	/**
	 * Generates reaction files from a mapping specification in a single direction.
	 * Creates the generator context and iterates over all mappings contained in the specification.
	 */
	def generate() {
		createAndInitializeFile
		generateReactionsAndRoutines
		context
	}

	private def createAndInitializeFile() {
		val reactionsFile = create.reactionsFile(directedSegmentName)
		val segmentBuilder = create.reactionsSegment(directedSegmentName).inReactionToChangesIn(fromMetamodels.map[package].toSet).
			executeActionsIn(toMetamodels.map[package].toSet)
		reactionsFile += segmentBuilder
		context = new MappingGeneratorContext(reactionsFile, segmentBuilder, segment, mappingsFile, create, left2right)
	}

	private def generateReactionsAndRoutines() {
		val extractor = new MappingParameterExtractor(fromMetamodels.map[package].toSet)
		segment.mappings.forEach [
			extractor.extract(it)
			val from = extractor.fromParameters
			val to = extractor.toParameters
			val fromConditions = extractor.fromConditions
			val toConditions = extractor.toConditions
			val mappingsConditions = extractor.bidirectionalizableConditions
			val mappingRoutines = extractor.bidirectionalizableRoutines
			val mappingAttributes = extractor.observeChanges
			val reactionsBuilder = new DirectionalMappingReactionGenerator(from, to, it)
			reactionsBuilder.generate(context, fromConditions, toConditions, mappingsConditions, mappingRoutines,
				mappingAttributes)
		]
	}

	private def getFromMetamodels() {
		if(left2right) segment.leftMetamodels else segment.rightMetamodels
	}

	private def getToMetamodels() {
		if(left2right) segment.rightMetamodels else segment.leftMetamodels
	}

	private def String getDirectedSegmentName() '''«segment.name»«directionSuffix»'''

	private def String getDirectionSuffix() '''«IF left2right »_L2R«ELSE»_R2L«ENDIF»'''

}
