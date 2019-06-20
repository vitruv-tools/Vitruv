package tools.vitruv.dsls.mappings.generator

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.builder.FluentReactionsFileBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsFile
import tools.vitruv.dsls.mappings.generator.integration.IReactionIntegrateable
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder

class ReactionGeneratorContext implements IReactionIntegrateable{
	
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	var FluentReactionsFileBuilder file
	@Accessors(PUBLIC_GETTER)
	var FluentReactionsSegmentBuilder segmentBuilder
	@Accessors(PUBLIC_GETTER)
	var MappingsSegment segment
	@Accessors(PUBLIC_GETTER)
	var MappingsFile mappingsFile
	@Accessors(PUBLIC_GETTER)
	val FluentReactionsLanguageBuilder create
	@Accessors(PUBLIC_GETTER)	
	var boolean left2right
	
	new(FluentReactionsFileBuilder file, FluentReactionsSegmentBuilder segmentBuilder, MappingsSegment segment, MappingsFile mappingsFile, FluentReactionsLanguageBuilder create, boolean left2right){
		this.file = file;
		this.segmentBuilder  =segmentBuilder;
		this.segment = segment;
		this.mappingsFile = mappingsFile;
		this.create = create;
		this.left2right = left2right
	}
	
	override integrate(Reaction reaction) {
		segmentBuilder+=reaction
	}
	
	override integrate(Routine routine) {
		segmentBuilder+=routine
	}
	
}