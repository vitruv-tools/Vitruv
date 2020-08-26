package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*

class ParticipationClassesScope implements IScope {

	@Inject IEObjectDescriptionProvider descriptionProvider
	var Commonality commonality

	def forCommonality(Commonality commonality) {
		this.commonality = checkNotNull(commonality)
		this
	}

	private def checkCommonalitySet() {
		checkState(commonality !== null, "No commonality to get participation classes from was set!")
	}

	override getAllElements() {
		checkCommonalitySet()
		commonality.participations.flatMap[classes].map(descriptionProvider)
	}

	override getElements(QualifiedName qName) {
		checkCommonalitySet()
		val domainName = qName.domainName
		if (domainName === null) return #[]
		val className = qName.className
		if (className === null) return #[]

		return commonality.participations
			.filter[name == domainName]
			.flatMap[classes]
			.filter[name == className]
			.map(descriptionProvider)
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
