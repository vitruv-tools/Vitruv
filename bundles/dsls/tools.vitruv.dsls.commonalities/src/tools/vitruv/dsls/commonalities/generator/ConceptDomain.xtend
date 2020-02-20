package tools.vitruv.dsls.commonalities.generator

import org.eclipse.emf.ecore.EPackage
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateVitruvDomain
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.VitruvDomainProvider
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver

import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*

/**
 * Dummy {@link VitruvDomain} used by the {@link ReactionsGenerator}.
 */
package class ConceptDomain extends IntermediateVitruvDomain {

	val Provider provider

	package new(String conceptName, EPackage conceptPackage) {
		super(conceptName.conceptDomainName, conceptPackage,
			new AttributeTuidCalculatorAndResolver(conceptPackage.nsURI,
				IntermediateModelBasePackage.eINSTANCE.intermediate_IntermediateId.name),
			conceptName.intermediateModelFileExtension)
		provider = new Provider(this, conceptName.conceptDomainProviderClassName.qualifiedName)
	}

	def getProvider() {
		this.provider
	}

	private static class Provider implements VitruvDomainProvider<ConceptDomain> {

		val ConceptDomain domain
		val String canonicalName

		private new(ConceptDomain domain, String canonicalName) {
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
