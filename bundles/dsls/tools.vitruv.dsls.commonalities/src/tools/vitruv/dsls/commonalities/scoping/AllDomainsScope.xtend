package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.elements.DomainProvider
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

class AllDomainsScope implements IScope{
	@Inject DomainProvider domainProvider
	@Inject extension IEObjectDescriptionProvider descriptionProvider
	
	
	override getAllElements() {
		domainProvider.allDomains.map(descriptionProvider)
	}
	
	override getElements(QualifiedName name) {
		#[getSingleElement(name)].filterNull
	}
	
	override getElements(EObject object) {
		#[getSingleElement(object)].filterNull
	}
	
	override getSingleElement(QualifiedName name) {
		domainProvider.getDomainByName(name.getSegment(0))?.describe()
	}
	
	override getSingleElement(EObject object) {
		throw new UnsupportedOperationException("I don’t know what to do here!")
	}
	
}