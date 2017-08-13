package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.common.helper.GenericClassNameGenerator
import tools.vitruv.dsls.commonalities.language.CommonalityFile

@Utility
class GeneratorConstants {
	static val GENERATED_PACKAGE_PREFIX = "tools.vitruv.commonalities"
	public static val MODEL_OUTPUT_FILE_EXTENSION = ".ecore"

	@Pure
	def static getIntermediateModelInstanceClassName(CommonalityFile commonalityFile) {
		GENERATED_PACKAGE_PREFIX + '.' + commonalityFile.concept.name.toLowerCase + '.' +
			commonalityFile.commonality.name
	}

	@Pure
	def static getIntermediateModelClass(CommonalityFile commonalityFile) {
		new GenericClassNameGenerator(commonalityFile.concept.name.conceptPackageFullName,
			commonalityFile.commonality.name)
	}

	@Pure
	def static getConceptPackageSimpleName(String conceptName) {
		return conceptName.toLowerCase
	}

	@Pure
	def static getConceptPackagePathPart(String conceptName) {
		GENERATED_PACKAGE_PREFIX
	}

	@Pure
	def static getConceptPackageFullName(String conceptName) {
		conceptName.conceptPackagePathPart + '.' + conceptName.conceptPackageSimpleName
	}

	@Pure
	def static getIntermediateModelClassesPrefix(String conceptName) {
		conceptName
	}

	@Pure
	def static getConceptDomainName(String conceptName) {
		conceptName
	}

	@Pure
	def static getConceptDomainClass(String conceptName) {
		new GenericClassNameGenerator(conceptName.conceptPackageFullName, conceptName.conceptDomainName + "Domain")
	}

	@Pure
	def static getConceptDomainProvider(String conceptName) {
		new GenericClassNameGenerator(conceptName.conceptPackageFullName,
			conceptName.conceptDomainName + "DomainProvider")
	}

	@Pure
	def static getConceptIntermediateModelPackageFactory(String conceptName) {
		new GenericClassNameGenerator(conceptName.conceptPackageFullName,
			conceptName.intermediateModelClassesPrefix + "Factory")
	}
}
