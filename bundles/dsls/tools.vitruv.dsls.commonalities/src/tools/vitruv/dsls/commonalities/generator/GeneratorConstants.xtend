package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.common.util.URI
import tools.vitruv.dsls.common.helper.GenericClassNameGenerator
import tools.vitruv.dsls.commonalities.language.CommonalityFile

@Utility
class GeneratorConstants {
	static val GENERATED_PACKAGE_PREFIX = "tools.vitruv.commonalities"
	public static val MODEL_OUTPUT_FILE_EXTENSION = ".ecore"
	static val DOMAIN_TYPES_BASE_URI = URI.createURI('synthetic:/domainTypes')

	// These must be updated in CommonalitiesConstanst, too!	
	static val INTERMEDIATE_MODEL_ROOT_CLASS = 'IntermediateModelRoot'
	public static val INTERMEDIATE_MODEL_ROOT_CLASS_CONTAINER_NAME = 'children'
	public static val INTERMEDIATE_MODEL_ROOT_CLASS_ID_COUNTER = 'uuidCounter'
	static val INTERMEDIATE_MODEL_NONROOT_CLASS = 'IntermediateModelNonRootObject'
	public static val INTERMEDIATE_MODEL_ID_ATTRIBUTE = 'intermediateModelUuid'

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
	def static getIntermediateModelRootClass(CommonalityFile commonalityFile) {
		commonalityFile.concept.name.intermediateModelRootClass
	}

	@Pure
	def static getIntermediateModelRootClass(String conceptName) {
		new GenericClassNameGenerator(conceptName.conceptPackageFullName, INTERMEDIATE_MODEL_ROOT_CLASS)
	}

	@Pure
	def static getIntermediateModelNonRootClass(String conceptName) {
		new GenericClassNameGenerator(conceptName.conceptPackageFullName, INTERMEDIATE_MODEL_NONROOT_CLASS)
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
	def static getIntermediateModelFileExtension(String conceptName) {
		conceptName.toFirstLower
	}

	@Pure
	def static getIntermediateModelFileExtension(CommonalityFile commonalityFile) {
		commonalityFile.concept.name.intermediateModelFileExtension
	}

	@Pure
	def static getIntermediateModelClassesPrefix(String conceptName) {
		conceptName.toFirstUpper
	}

	@Pure
	def static getConceptDomainName(CommonalityFile commonalityFile) {
		commonalityFile.concept.name.conceptDomainName
	}

	@Pure
	def static getConceptDomainName(String conceptName) {
		conceptName
	}

	@Pure
	def static getConceptDomainClass(String conceptName) {
		new GenericClassNameGenerator(conceptName.conceptPackageFullName,
			conceptName.conceptDomainName.toFirstUpper + "Domain")
	}

	@Pure
	def static getConceptDomainProvider(CommonalityFile commonalityFile) {
		commonalityFile.concept.name.conceptDomainProvider
	}

	@Pure
	def static getConceptDomainProvider(String conceptName) {
		new GenericClassNameGenerator(conceptName.conceptPackageFullName,
			conceptName.conceptDomainName.toFirstUpper + "DomainProvider")
	}

	@Pure
	def static getConceptIntermediateModelPackageFactory(String conceptName) {
		new GenericClassNameGenerator(conceptName.conceptPackageFullName,
			conceptName.intermediateModelClassesPrefix + "Factory")
	}

	@Pure
	def static getDomainTypeUri(String className) {
		DOMAIN_TYPES_BASE_URI.appendSegment(className + '.java')
	}
}
