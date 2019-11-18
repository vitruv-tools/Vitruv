package tools.vitruv.dsls.mappings.generator.integration

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory

class StandaloneReactionIntegration implements IReactionIntegrateable{
	
	@Accessors(PUBLIC_GETTER)
	private ReactionsSegment reactionsSegment

    new(MappingsSegment segment, boolean l2r){
    	reactionsSegment = ReactionsLanguageFactory.eINSTANCE.createReactionsSegment
		reactionsSegment.name = segment.name+'Integration'
		reactionsSegment.fromDomain = getDomain(segment, l2r)
		reactionsSegment.toDomain = getDomain(segment, !l2r)
    }
    
    private def getDomain(MappingsSegment segment, boolean l2r){
    	if(l2r){
    		return segment.leftDomain
    	}else {
    		return segment.rightDomain
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