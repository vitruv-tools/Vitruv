package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.CommonalityDeclaration
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static com.google.common.base.Preconditions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class ParticipationAttributesScope implements IScope {

	@Inject IEObjectDescriptionProvider descriptionProvider
	var CommonalityDeclaration commonality

	def forCommonality(CommonalityDeclaration commonality) {
		this.commonality = checkNotNull(commonality)
		this			
	}
	
	def private checkCommonalitySet() {
		checkState(this.commonality !== null, "No commonality to get attributes from was set!")
	}
	
	override getAllElements() {
		checkCommonalitySet()
		
		commonality.participations.flatMap [classes].flatMap [attributes].map(descriptionProvider)
	}

	override getElements(QualifiedName qName) {
		checkCommonalitySet()
		
		if (qName.segmentCount != 3) return #[]
		commonality.participations
			.filter [name == qName.getSegment(0)]
			.flatMap [classes]
			.filter [name == qName.getSegment(1)]
			.flatMap [attributes]
			.filter [name == qName.getSegment(2)]
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
