package tools.vitruv.dsls.mappings.generator

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.reactions.builder.FluentReactionsFileBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder

class ReactionGeneratorContext {
	
	@Accessors(PUBLIC_GETTER)
	var FluentReactionsFileBuilder file
	@Accessors(PUBLIC_GETTER)
	var FluentReactionsSegmentBuilder segment
	
	new(FluentReactionsFileBuilder file, FluentReactionsSegmentBuilder segment){
		this.file = file;
		this.segment  =segment;
	}
}