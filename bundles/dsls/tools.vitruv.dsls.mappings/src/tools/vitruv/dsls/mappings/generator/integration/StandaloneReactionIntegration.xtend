package tools.vitruv.dsls.mappings.generator.integration

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.language.toplevelelements.Reaction
import tools.vitruv.dsls.reactions.language.toplevelelements.Routine
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsSegment
import tools.vitruv.dsls.reactions.language.toplevelelements.TopLevelElementsFactory

class StandaloneReactionIntegration implements IReactionIntegrateable{
	
	@Accessors(PUBLIC_GETTER)
	var ReactionsSegment reactionsSegment

    new(MappingsSegment segment, boolean l2r){
    	reactionsSegment = TopLevelElementsFactory.eINSTANCE.createReactionsSegment
		reactionsSegment.name = segment.name+'Integration'
		reactionsSegment.fromMetamodels += getMetamodels(segment, l2r)
		reactionsSegment.toMetamodels += getMetamodels(segment, !l2r)
    }
    
    private def getMetamodels(MappingsSegment segment, boolean l2r){
    	if(l2r){
    		return segment.leftMetamodels
    	}else {
    		return segment.rightMetamodels
    	}
    }

	def boolean isFilled(){
		 reactionsSegment.reactions.size>0
	}
	
	override integrate(Reaction reaction) {
		 reactionsSegment.reactions += reaction
	}
	
	override integrate(Routine routine) {
		 reactionsSegment.routines += routine
	}
	
}