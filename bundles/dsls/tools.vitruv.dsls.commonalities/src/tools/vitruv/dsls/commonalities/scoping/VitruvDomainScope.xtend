package tools.vitruv.dsls.commonalities.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.scoping.IScope;
import tools.vitruv.dsls.commonalities.language.elements.Domain
import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.language.elements.DomainProvider
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.emf.ecore.EcoreFactory

class VitruvDomainScope implements IScope {

	@Inject DomainProvider domainProvider

	def describe(Domain domain) {
		EObjectDescription.create(domain.name, domain)
	}

	override getSingleElement(QualifiedName name) {
		domainProvider.getDomainByName(name.toString)?.describe
	}

	override getElements(QualifiedName name) {
		#[getSingleElement(name)].filter [it !== null]
	}

	override getSingleElement(EObject object) {
		throw new AssertionError('I don’t know what this method should do!')
	}

	override getElements(EObject object) {
		#[getSingleElement(object)].filter [it !== null]
	}

	override getAllElements() {
		domainProvider.allDomains.map[describe] + testDomains
	}
	
	
	// TODO: only for testing purposes!
	def private testDomains() {
		#['Java', 'UML', 'PCM'].map[domainName | EObjectDescription.create(domainName, EcoreFactory.eINSTANCE.createEObject)]
	}

}
