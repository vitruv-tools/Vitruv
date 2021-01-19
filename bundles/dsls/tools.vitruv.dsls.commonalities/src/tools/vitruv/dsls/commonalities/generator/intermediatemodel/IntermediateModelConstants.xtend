package tools.vitruv.dsls.commonalities.generator.intermediatemodel

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.common.GenericClassNameGenerator
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Concept

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Utility
class IntermediateModelConstants {
	static val INTERMEDIATE_METAMODEL_PACKAGE_PREFIX = 'tools.vitruv.commonalities'
	static val METAMODEL_FILE_EXTENSION = '.ecore'

	@Pure
	static def getIntermediateModelFileExtension(Concept concept) {
		concept.name.intermediateModelFileExtension
	}

	@Pure
	static def getIntermediateModelFileExtension(String conceptName) {
		conceptName.toFirstLower
	}

	@Pure
	static def getIntermediateMetamodelFilePath(Concept concept) {
		concept.name.intermediateMetamodelFilePath
	}

	@Pure
	static def getIntermediateMetamodelFilePath(String conceptName) {
		conceptName + METAMODEL_FILE_EXTENSION
	}

	@Pure
	static def getIntermediateMetamodelPackagePrefix() {
		INTERMEDIATE_METAMODEL_PACKAGE_PREFIX
	}

	@Pure
	static def getIntermediateMetamodelPackageSimpleName(Concept concept) {
		concept.name.intermediateMetamodelPackageSimpleName
	}

	@Pure
	static def getIntermediateMetamodelPackageSimpleName(String conceptName) {
		conceptName.toLowerCase
	}

	@Pure
	static def getIntermediateMetamodelPackageName(Concept concept) {
		concept.name.intermediateMetamodelPackageName
	}

	@Pure
	static def getIntermediateMetamodelPackageName(String conceptName) {
		intermediateMetamodelPackagePrefix + '.' + conceptName.intermediateMetamodelPackageSimpleName
	}

	@Pure
	static def getIntermediateMetamodelClassesPrefix(String conceptName) {
		conceptName.toFirstUpper
	}

	@Pure
	static def getIntermediateMetamodelRootClassName(Concept concept) {
		concept.name.intermediateMetamodelRootClassName
	}

	@Pure
	static def getIntermediateMetamodelRootClassName(String conceptName) {
		new GenericClassNameGenerator(conceptName.intermediateMetamodelPackageName,
			conceptName.intermediateMetamodelClassesPrefix + 'Root')
	}

	@Pure
	static def getIntermediateMetamodelClassName(Commonality commonality) {
		new GenericClassNameGenerator(commonality.concept.intermediateMetamodelPackageName, commonality.name)
	}

	@Pure
	static def getIntermediateMetamodelFactoryClassName(String conceptName) {
		new GenericClassNameGenerator(conceptName.intermediateMetamodelPackageName,
			conceptName.intermediateMetamodelClassesPrefix + 'Factory')
	}

	@Pure
	static def getIntermediateMetamodelPackageClassName(Concept concept) {
		concept.name.intermediateMetamodelPackageClassName
	}

	@Pure
	static def getIntermediateMetamodelPackageClassName(String conceptName) {
		new GenericClassNameGenerator(conceptName.intermediateMetamodelPackageName,
			conceptName.intermediateMetamodelClassesPrefix + 'Package')
	}
}
