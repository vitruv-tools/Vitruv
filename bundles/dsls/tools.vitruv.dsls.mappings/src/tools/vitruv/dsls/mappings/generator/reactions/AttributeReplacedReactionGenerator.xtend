package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import org.eclipse.emf.ecore.EReference

class AttributeReplacedReactionGenerator extends AbstractReactionTypeGenerator{
	
	private MetaclassFeatureReference attribute
	
	new(MetaclassFeatureReference attribute) {
		super(attribute.metaclass)
		this.attribute = attribute
	}
	
	override generateTrigger(ReactionGeneratorContext context) {
		return context.create.reaction('''on«attribute.getAttributeName»ReplacedAt«metaclass.parameterName»''')
			.afterAttributeReplacedAt(extractAttribute)
	}
	
	private def extractAttribute(){
		val ref = attribute.feature as EReference
		val type = ref.EReferenceType
		type.EAllStructuralFeatures.findFirst[it.name.equals(attribute.feature.name)] as EAttribute
	}
	
	override equals(Object obj) {
		if(obj instanceof AttributeReplacedReactionGenerator){
			return attribute.feature == obj.attribute.feature && metaclass == obj.metaclass
		}
		false
	}
	
	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}
