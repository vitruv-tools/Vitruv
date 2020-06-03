package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*

class CommonalityAttributesScope implements IScope {

	@Inject IEObjectDescriptionProvider descriptionProvider
	var Commonality commonality

	def forCommonality(Commonality commonality) {
		this.commonality = checkNotNull(commonality)
		this
	}

	private def checkCommonalitySet() {
		checkState(commonality !== null, "No commonality to get attributes from was set!")
	}

	private def allAttributes() {
		checkCommonalitySet()
		commonality.attributes
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
		checkCommonalitySet()
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
