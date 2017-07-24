package tools.vitruv.dsls.commonalities.language.elements

import tools.vitruv.dsls.commonalities.language.elements.impl.ParticipationReferenceImpl
import static com.google.common.base.Preconditions.*

class ParticipationReferenceI extends ParticipationReferenceImpl implements Wrapper<Reference> {

	Reference wrappedReference

	override basicGetClassLikeContainer() {
		participationClass
	}

	override wrapReference(Reference reference) {
		checkState(wrappedReference === null, "This object already has a Reference!")
		this.wrappedReference = checkNotNull(reference)
	}
	
	override getName() {
		wrappedReference.name
	}
	
	override basicGetType() {
		wrappedReference.type
	}

	override getWrapped() {
		wrappedReference
	}

}
