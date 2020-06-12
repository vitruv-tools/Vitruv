package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.elements.Attribute
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*

class ParticipationAttributesScope implements IScope {

	@Inject IEObjectDescriptionProvider descriptionProvider
	var ParticipationClass participationClass

	def forParticipationClass(ParticipationClass participationClass) {
		this.participationClass = checkNotNull(participationClass)
		this
	}

	private def checkParticipationClassSet() {
		checkState(participationClass !== null, "No participation class to get attributes from was set!")
	}

	private def allAttributes() {
		checkParticipationClassSet()
		val metaclass = participationClass.superMetaclass
		if (metaclass === null) return #[]
		// We iterate over all members here instead of just the attributes returned by #getAttributes, because for
		// commonality participations #getAttributes only includes the commonality's CommonalityAttributes but not its
		// CommonalityReferences (which are referenceable attributes as well).
		return metaclass.allMembers.filter(Attribute)
	}

	override getAllElements() {
		allAttributes.map(descriptionProvider)
	}

	override getElements(QualifiedName qName) {
		val memberName = qName.memberName
		if (memberName === null) return #[]
		return allAttributes.filter[name == memberName].map(descriptionProvider)
	}

	override getElements(EObject object) {
		checkParticipationClassSet()
		val objectURI = EcoreUtil2.getURI(object)
		return allElements.filter[it.EObjectOrProxy === object || it.EObjectURI == objectURI]
	}

	override getSingleElement(QualifiedName name) {
		getElements(name).head
	}

	override getSingleElement(EObject object) {
		getElements(object).head
	}
}
