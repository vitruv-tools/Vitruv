package tools.vitruv.dsls.commonalities.generator.domain

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.common.util.URI
import tools.vitruv.dsls.common.helper.GenericClassNameGenerator
import tools.vitruv.dsls.commonalities.language.Concept

import static extension tools.vitruv.dsls.commonalities.generator.intermediatemodel.IntermediateModelConstants.*

@Utility
class ConceptDomainConstants {

	static val DOMAIN_TYPES_BASE_URI = URI.createURI('synthetic:/commonalities/domainTypes')

	@Pure
	static def getConceptDomainName(Concept concept) {
		concept.name.conceptDomainName
	}

	@Pure
	static def getConceptDomainName(String conceptName) {
		conceptName
	}

	@Pure
	static def getConceptDomainClassName(Concept concept) {
		concept.name.conceptDomainClassName
	}

	@Pure
	static def getConceptDomainClassName(String conceptName) {
		new GenericClassNameGenerator(conceptName.intermediateMetamodelPackageName,
			conceptName.conceptDomainName.toFirstUpper + 'Domain')
	}

	@Pure
	static def getConceptDomainProviderClassName(Concept concept) {
		concept.name.conceptDomainProviderClassName
	}

	@Pure
	static def getConceptDomainProviderClassName(String conceptName) {
		new GenericClassNameGenerator(conceptName.intermediateMetamodelPackageName,
			conceptName.conceptDomainName.toFirstUpper + 'DomainProvider')
	}

	@Pure
	static def getDomainTypeUri(String qualifiedClassName) {
		DOMAIN_TYPES_BASE_URI.appendSegment(qualifiedClassName + '.java')
	}
}
