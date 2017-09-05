package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static com.google.common.base.Preconditions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ParticipationClassesScope implements IScope {

	@Inject IEObjectDescriptionProvider descriptionProvider
	var Commonality commonality

	def forCommonality(Commonality commonality) {
		this.commonality = checkNotNull(commonality)
		this
	}

	def private checkCommonalitySet() {
		checkState(commonality !== null, "No commonality to get participation classes from was set!")
	}
	
	
	override getAllElements() {
		checkCommonalitySet()
		
		commonality.participations.flatMap [classes].map(descriptionProvider)
	}
	
	override getElements(QualifiedName qName) {
		checkCommonalitySet()
		
		if (qName.segmentCount !== 2) return #[]
		
		commonality.participations
			.filter [
				name == qName.getSegment(0)
			]
			.flatMap [
				classes
			]
			.filter [name == qName.getSegment(1)]
			.map(descriptionProvider)
	}
	
	override getElements(EObject object) {
		checkCommonalitySet()
		
		throw new UnsupportedOperationException("I don’t know what to do here!")
	}
	
	override getSingleElement(QualifiedName name) {
		getElements(name).head
	}
	
	override getSingleElement(EObject object) {
		getElements(object).head
	}
	
}
