package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static com.google.common.base.Preconditions.*

class ParticipationAttributesScope implements IScope {

	@Inject IEObjectDescriptionProvider descriptionProvider
	var ParticipationClass participationClass

	def forParticipationClass(ParticipationClass participationClass) {
		this.participationClass = checkNotNull(participationClass)
		this			
	}
	
	def private checkParticipationClassSet() {
		checkState(participationClass !== null, "No participation class to get attributes from was set!")
	}
	
	def private allAttributes() {
		checkParticipationClassSet()
		
		participationClass.superMetaclass?.attributes ?: #[]
	}
	
	override getAllElements() {
		allAttributes.map(descriptionProvider)
	}

	override getElements(QualifiedName qName) {
		if (qName.segmentCount > 1) return #[]
		allAttributes.filter [name == qName.firstSegment].map(descriptionProvider)
	}

	override getElements(EObject object) {
		checkParticipationClassSet()
		
		throw new UnsupportedOperationException("I don’t know what to do here!")
	}

	override getSingleElement(QualifiedName name) {
		getElements(name).head
	}

	override getSingleElement(EObject object) {
		getElements(object).head
	}

}
