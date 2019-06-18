package tools.vitruv.dsls.mappings.generator.trigger

import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference

abstract class AbstractReactionTypeGenerator {
	
	@Accessors(PUBLIC_GETTER)
	protected EClass metaclass
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)	
	private ConflictingTriggerCheck conflictingTriggerCheck
	
	new(EClass metaclass){
		this.metaclass =  metaclass
	}
	
	def boolean isSubordinateToTrigger(AbstractReactionTypeGenerator generator){
		if(generator.metaclass == metaclass && conflictingTriggerCheck !==null){
			return conflictingTriggerCheck.isSubordinateTrigger(generator)
		}
		false
	}
	
	def protected getAttributeName(MetaclassFeatureReference parameter){
		parameter.feature.name
	}
	
	def protected getParameterName(MetaclassReference parameter){
		parameter.metaclass.getParameterName
	}
	
	def protected getParameterName(EClass clazz){
		clazz.name
	}
	
	def abstract PreconditionOrRoutineCallBuilder generateTrigger(ReactionGeneratorContext context)

}
