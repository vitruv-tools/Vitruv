package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.CommonalityFile

@Utility class GeneratorConstants {
	static val GENERATED_PACKAGE_PREFIX = "tools.vitruv.commonalities"
	
	def static getIntermediateModelInstanceClassName(CommonalityFile commonalityFile) {
		GENERATED_PACKAGE_PREFIX + '.' + commonalityFile.concept.name.toLowerCase + '.' +
			commonalityFile.commonality.name
	}
	
	def static getIntermediateModelClassName(CommonalityFile commonalityFile) {
		commonalityFile.commonality.name
	}
	
	def static getConceptPackageSimpleName(String conceptName) {
		return conceptName.toLowerCase
	}
	
	def static getConceptPackagePathPart(String conceptName) {
		GENERATED_PACKAGE_PREFIX
	}
	
	def static getConceptPackageFullName(String conceptName) {
		conceptName.conceptPackagePathPart + '.' + conceptName.conceptPackageSimpleName
	}
	
	def static getConceptDomainSimpleName(String conceptName) {
		conceptName
	}
	
	def static getConceptDomainProviderSimpleName(String conceptName) {
		conceptName + "DomainProvider"
	}
	
	def static getConceptDomainProviderCanonicalName(String conceptName) {
		conceptName.conceptPackageFullName + '.' + conceptName.conceptDomainProviderSimpleName
	}
}