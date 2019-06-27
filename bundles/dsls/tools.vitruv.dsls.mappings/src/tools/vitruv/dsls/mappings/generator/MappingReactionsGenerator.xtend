package tools.vitruv.dsls.mappings.generator

import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import org.eclipse.emf.common.util.EList
import java.util.ArrayList

class MappingReactionsGenerator extends MappingsReactionsFileGenerator {
	val Mapping mapping

	new(String basePackage, MappingsSegment segment, boolean left2right, IReactionsGenerator generator,
		FluentReactionsLanguageBuilder create, Mapping mapping) {
		super(basePackage, segment, left2right, generator, create, null)
		this.mapping = mapping
	}

	def generateReactionsAndRoutines(ReactionGeneratorContext context) {
		segment.mappings.forEach [	
			val from = fromParameters
			val to = toParameters
			val fromConditions = fromConditions
			val mappingsConditions = it.bidirectionalizableConditions
			val mappingRoutines = it.bidirectionalizableRoutines
			val reactionsBuilder = new ReactionsBuilder(from, to)
			reactionsBuilder.generate(context, fromConditions, mappingsConditions, mappingRoutines)
		]
	}
}
