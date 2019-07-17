package tools.vitruv.dsls.mappings.generator

import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder

class MappingReactionsGenerator extends MappingsReactionsFileGenerator {

	new(String basePackage, MappingsSegment segment, boolean left2right, IReactionsGenerator generator,
		FluentReactionsLanguageBuilder create) {
		super(basePackage, segment, left2right, generator, create, null)
	}

	def generateReactionsAndRoutines(ReactionGeneratorContext context) {
				val f= XExpressionParser.parseExpression("4 == 4");
		println(f)
		segment.mappings.forEach [	
			val from = fromParameters
			val to = toParameters
			val fromConditions = fromConditions
			val mappingsConditions = it.bidirectionalizableConditions
			val mappingRoutines = it.bidirectionalizableRoutines
			val mappingAttributes = it.observeAttributes
			val reactionsBuilder = new DirectionalMappingReactionGenerator(from, to, it)
			println('''generate mapping «it.name» «IF(context.left2right)»left to right«ELSE»right to left«ENDIF»:''')
			reactionsBuilder.generate(context, fromConditions, mappingsConditions, mappingRoutines, mappingAttributes)
		]
	}
}
