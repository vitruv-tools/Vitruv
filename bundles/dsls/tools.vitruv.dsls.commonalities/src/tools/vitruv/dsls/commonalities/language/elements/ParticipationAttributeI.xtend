package tools.vitruv.dsls.commonalities.language.elements

import tools.vitruv.dsls.commonalities.language.elements.impl.ParticipationAttributeImpl

import static com.google.common.base.Preconditions.*

class ParticipationAttributeI extends ParticipationAttributeImpl implements Wrapper<Attribute> {
	
	Attribute wrappedAttribute
	
	override basicGetClassLikeContainer() {
		participationClass
	}
	
	override wrapAttribute(Attribute attribute) {
		checkState(wrappedAttribute === null, "This object already has an Attribute!")
		this.wrappedAttribute = checkNotNull(attribute)
	}
	
	override getName() {
		wrappedAttribute.name
	}
	
	override basicGetType() {
		wrappedAttribute.type
	}
	
	override getWrapped() {
		wrappedAttribute
	}
}