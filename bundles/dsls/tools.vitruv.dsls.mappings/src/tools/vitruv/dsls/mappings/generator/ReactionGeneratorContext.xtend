package tools.vitruv.dsls.mappings.generator

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.builder.FluentReactionsFileBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsFile
import tools.vitruv.dsls.mappings.generator.integration.IReactionIntegrateable

class ReactionGeneratorContext implements IReactionIntegrateable{
	
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	var FluentReactionsFileBuilder file
	@Accessors(PUBLIC_GETTER)
	var FluentReactionsSegmentBuilder segmentBuilder
	@Accessors(PUBLIC_GETTER)
	var MappingsSegment segment
	@Accessors(PUBLIC_GETTER)
	var MappingsFile mappingsFile
	
	new(FluentReactionsFileBuilder file, FluentReactionsSegmentBuilder segmentBuilder, MappingsSegment segment, MappingsFile mappingsFile){
		this.file = file;
		this.segmentBuilder  =segmentBuilder;
		this.segment = segment;
		this.mappingsFile = mappingsFile;
	}
	
	override integrate(Reaction reaction) {
		segmentBuilder+=reaction
	}
	
	override integrate(Routine routine) {
		segmentBuilder+=routine
	}
	
}