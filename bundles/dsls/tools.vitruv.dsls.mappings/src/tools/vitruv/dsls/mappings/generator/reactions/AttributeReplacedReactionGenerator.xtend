package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import org.eclipse.emf.ecore.EReference
import static tools.vitruv.dsls.mappings.generator.XExpressionParser.*


class AttributeReplacedReactionGenerator extends AbstractReactionTypeGenerator{
	
	private MetaclassFeatureReference attribute
	
	new(MetaclassFeatureReference attribute) {
		super(attribute.metaclass)
		this.attribute = attribute
	}
	
	override generateTrigger(ReactionGeneratorContext context) {
		return context.create.reaction('''On«attribute.getAttributeName.toFirstUpper»ReplacedAt«metaclass.parameterName»''')
		.afterAttributeReplacedAt(attribute.metaclass,extractAttribute)
	}
	
	private def extractAttribute(){
		attribute.feature as EAttribute
	}
	
	override equals(Object obj) {
		if(obj instanceof AttributeReplacedReactionGenerator){
			return attribute.feature == obj.attribute.feature && metaclass == obj.metaclass
		}
		false
	}
	
	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		//nothing to do, only single sided conditions create matchers
	}
	
	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		//nothing to do, only bidirectional conditions create actions
	}
	
	
	
}
