package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.elements.DomainProvider
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class MetaclassScope implements IScope {

	@Inject DomainProvider domainProvider
	@Inject IEObjectDescriptionProvider descriptionProvider

	override getAllElements() {
		domainProvider.allDomains.flatMap[metaclasses].map(descriptionProvider)
	}

	override getElements(QualifiedName qName) {
		if (qName.segmentCount != 2) return #[]
		domainProvider.getDomainByName(qName.firstSegment)
			?.metaclasses
			?.filter[name == qName.getSegment(1)]
			?.map(descriptionProvider)
		?: #[]
	}

	override getElements(EObject object) {
		throw new UnsupportedOperationException("I don’t know what to do here!")
	}

	override getSingleElement(QualifiedName name) {
		getElements(name).findFirst[true]
	}

	override getSingleElement(EObject object) {
		getElements(object).findFirst[true]
	}
}
