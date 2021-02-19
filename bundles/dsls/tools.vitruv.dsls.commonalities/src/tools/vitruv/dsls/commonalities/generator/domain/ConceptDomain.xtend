package tools.vitruv.dsls.commonalities.generator.domain

import org.eclipse.emf.ecore.EPackage
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateVitruvDomain
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.VitruvDomainProvider

import static extension tools.vitruv.dsls.commonalities.generator.domain.ConceptDomainConstants.*
import static extension tools.vitruv.dsls.commonalities.generator.intermediatemodel.IntermediateModelConstants.*
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Dummy {@link VitruvDomain} which represents the concept domain during the
 * generation process (mainly during the generation of reactions).
 * <p>
 * This is required because the generated concept domain cannot be used
 * until after the compilation of the generated code.
 */
class ConceptDomain extends IntermediateVitruvDomain {
	@Accessors
	val Provider provider

	new(String conceptName, EPackage conceptPackage) {
		super(conceptName.conceptDomainName, conceptPackage,
			conceptName.intermediateModelFileExtension)
		provider = new Provider(this, conceptName.conceptDomainProviderClassName.qualifiedName)
	}

	@VitruvDomainProviderRegistry.IgnoreInStandalone
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
