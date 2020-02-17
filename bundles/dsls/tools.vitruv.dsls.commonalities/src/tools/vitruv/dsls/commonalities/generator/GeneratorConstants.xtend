package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.common.util.URI
import tools.vitruv.dsls.common.helper.GenericClassNameGenerator
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Concept

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Utility
class GeneratorConstants {

	static val GENERATED_PACKAGE_PREFIX = "tools.vitruv.commonalities"
	public static val MODEL_OUTPUT_FILE_EXTENSION = ".ecore"
	static val DOMAIN_TYPES_BASE_URI = URI.createURI('synthetic:/domainTypes')

	// CONCEPT DOMAIN

	@Pure
	def static getConceptDomainName(Concept concept) {
		concept.name.conceptDomainName
	}

	@Pure
	def static getConceptDomainName(String conceptName) {
		conceptName
	}

	@Pure
	def static getConceptDomainClassName(Concept concept) {
		concept.name.conceptDomainClassName
	}

	@Pure
	def static getConceptDomainClassName(String conceptName) {
		new GenericClassNameGenerator(conceptName.intermediateModelPackageName,
			conceptName.conceptDomainName.toFirstUpper + "Domain")
	}

	@Pure
	def static getConceptDomainProviderClassName(Concept concept) {
		concept.name.conceptDomainProviderClassName
	}

	@Pure
	def static getConceptDomainProviderClassName(String conceptName) {
		new GenericClassNameGenerator(conceptName.intermediateModelPackageName,
			conceptName.conceptDomainName.toFirstUpper + "DomainProvider")
	}

	@Pure
	def static getDomainTypeUri(String className) {
		DOMAIN_TYPES_BASE_URI.appendSegment(className + '.java')
	}

	// INTERMEDIATE MODEL

	@Pure
	def static getIntermediateModelFileExtension(Concept concept) {
		concept.name.intermediateModelFileExtension
	}

	@Pure
	def static getIntermediateModelFileExtension(String conceptName) {
		conceptName.toFirstLower
	}

	@Pure
	def static getIntermediateModelPackagePrefix() {
		GENERATED_PACKAGE_PREFIX
	}

	@Pure
	def static getIntermediateModelPackageSimpleName(Concept concept) {
		concept.name.intermediateModelPackageSimpleName
	}

	@Pure
	def static getIntermediateModelPackageSimpleName(String conceptName) {
		conceptName.toLowerCase
	}

	@Pure
	def static getIntermediateModelPackageName(Concept concept) {
		concept.name.intermediateModelPackageName
	}

	@Pure
	def static getIntermediateModelPackageName(String conceptName) {
		intermediateModelPackagePrefix + '.' + conceptName.intermediateModelPackageSimpleName
	}

	@Pure
	def static getIntermediateModelClassesPrefix(String conceptName) {
		conceptName.toFirstUpper
	}

	@Pure
	def static getIntermediateModelRootClassName(Concept concept) {
		concept.name.intermediateModelRootClassName
	}

	@Pure
	def static getIntermediateModelRootClassName(String conceptName) {
		new GenericClassNameGenerator(conceptName.intermediateModelPackageName,
			conceptName.intermediateModelClassesPrefix + "Root")
	}

	@Pure
	def static getIntermediateModelClassName(Commonality commonality) {
		new GenericClassNameGenerator(commonality.concept.intermediateModelPackageName, commonality.name)
	}

	@Pure
	def static getIntermediateModelFactoryClassName(String conceptName) {
		new GenericClassNameGenerator(conceptName.intermediateModelPackageName,
			conceptName.intermediateModelClassesPrefix + "Factory")
	}

	@Pure
	def static getIntermediateModelPackageClassName(Concept concept) {
		val conceptName = concept.name
		new GenericClassNameGenerator(conceptName.intermediateModelPackageName,
			conceptName.intermediateModelClassesPrefix + "Package")
	}
}
