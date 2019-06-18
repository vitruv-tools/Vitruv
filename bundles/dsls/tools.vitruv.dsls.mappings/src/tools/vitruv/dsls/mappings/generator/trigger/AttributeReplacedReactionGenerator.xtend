package tools.vitruv.dsls.mappings.generator.trigger

import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference

class AttributeReplacedReactionGenerator extends AbstractReactionTypeGenerator{
	
	private MetaclassFeatureReference attribute
	
	new(MetaclassFeatureReference attribute) {
		super(attribute.metaclass)
		this.attribute = attribute
	}
	
	override generateTrigger(ReactionGeneratorContext context) {
		return context.create.reaction('''on«attribute.getAttributeName»ReplacedAt«metaclass.parameterName»''')
			.afterAttributeReplacedAt(attribute.feature as EAttribute)
	}
	
	override equals(Object obj) {
		if(obj instanceof AttributeReplacedReactionGenerator){
			return attribute.feature == obj.attribute.feature && metaclass == obj.metaclass
		}
		false
	}
	
}
