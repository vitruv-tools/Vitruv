package tools.vitruv.dsls.commonalities.generator.domain

import org.eclipse.emf.ecore.EPackage
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateVitruvDomain
import tools.vitruv.framework.domains.VitruvDomain

import static extension tools.vitruv.dsls.commonalities.generator.domain.ConceptDomainConstants.*
import static extension tools.vitruv.dsls.commonalities.generator.intermediatemodel.IntermediateModelConstants.*

/**
 * Dummy {@link VitruvDomain} which represents the concept domain during the
 * generation process (mainly during the generation of reactions).
 * <p>
 * This is required because the generated concept domain cannot be used
 * until after the compilation of the generated code.
 */
class ConceptDomain extends IntermediateVitruvDomain {
	new(String conceptName, EPackage conceptPackage) {
		super(conceptName.conceptDomainName, conceptPackage,
			conceptName.intermediateModelFileExtension)
	}
}
