package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.elements.DomainProvider
import tools.vitruv.dsls.commonalities.language.elements.Metaclass
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

@Singleton
class AllMetaclassesScope implements IScope {

	@Inject DomainProvider domainProvider
	@Inject IEObjectDescriptionProvider descriptionProvider

	override getAllElements() {
		domainProvider.allDomains.flatMap [metaclasses].map(descriptionProvider)
	}

	override getElements(QualifiedName qName) {
		var Iterable<Metaclass> elements = domainProvider.getDomainByName(qName.getSegment(0))
			?.metaclasses
			
		if (qName.segmentCount > 1) {
			elements = elements?.filter[name == qName.getSegment(1)]
		}
		elements?.map(descriptionProvider) ?: #[]
	}

	override getElements(EObject object) {
		throw new UnsupportedOperationException("I don’t know what to do here!")
	}

	override getSingleElement(QualifiedName name) {
		getElements(name).head
	}

	override getSingleElement(EObject object) {
		getElements(object).head
	}
}
