package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.elements.Domain
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

class DomainMetaclassesScope implements IScope {
	
	@Inject extension IEObjectDescriptionProvider descriptionProvider
	Domain domain
	
	def forDomain(Domain domain) {
		this.domain = domain
		return this
	}
	
	override getAllElements() {
		domain?.metaclasses?.map(descriptionProvider) ?: #[]
	}
	
	override getElements(QualifiedName qName) {
		#[getSingleElement(qName)].filterNull
	}
	
	override getElements(EObject object) {
		#[getSingleElement(object)].filterNull
	}
	
	override getSingleElement(QualifiedName qName) {
		if (domain === null) return null
		
		val classPart = if (qName.segmentCount > 1) {
			if (qName.getSegment(0) != domain.name) return null
			qName.getSegment(1)
		} else {
			// this scope can also be used with unqualified class references.
			// In this case, the first part of the qName is not a domain but a
			// class.
			qName.getSegment(0)
		}
		
		domain.metaclasses.findFirst [name == classPart]?.describe()
	}
	
	override getSingleElement(EObject object) {
		throw new UnsupportedOperationException("I don’t know what to do here!")
	}
	
}