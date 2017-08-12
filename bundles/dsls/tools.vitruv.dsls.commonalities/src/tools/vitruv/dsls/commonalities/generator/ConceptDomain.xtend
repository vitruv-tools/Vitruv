package tools.vitruv.dsls.commonalities.generator

import java.util.Collections
import java.util.Set
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.framework.domains.AbstractVitruvDomain
import tools.vitruv.framework.domains.VitruvDomainProvider
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver

import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*

package class ConceptDomain extends AbstractVitruvDomain {
	val Provider provider
	
	private new(String conceptName, EPackage mainPackage, Set<EPackage> furtherPackages) {
		super(conceptName.conceptDomainName, mainPackage, furtherPackages,
			new AttributeTuidCalculatorAndResolver('', #[])) // TODO
		provider = new Provider(this, conceptName.conceptDomainProvider.qualifiedName)
	}

	package new(String conceptName, Iterable<EPackage> packages) {
		this(conceptName, packages.head, packages.drop(1).toSet)
	}

	package new(String conceptName, EPackage ePackage) {
		this(conceptName, ePackage, Collections.emptySet)
	}

	override getBuilderApplicator() {
		return new VitruviusProjectBuilderApplicator("what is this?")
	}

	def getProvider() { this.provider }
	
	private static class Provider implements VitruvDomainProvider<ConceptDomain> {
		val ConceptDomain domain
		val String canonicalName
		
		private new (ConceptDomain domain, String canonicalName) {
			this.domain = domain
			this.canonicalName = canonicalName
		}
		
		override getDomain() {
			domain
		}
		
		override getCanonicalNameForReference() {
			canonicalName
		}
		
	}

}
