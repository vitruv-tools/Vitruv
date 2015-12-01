package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Import
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping

class MappingLanguageGeneratorNameProvider {
	new(String pkgName) {
		this.pkgName = pkgName
	}

	private var anonMappingIndex = 0
	private final String pkgName

	public def getMappingName(Mapping mapping) {
		if ((mapping.name == null) || (mapping.name.empty)) {
			mapping.name = mapping.nextAnonymousMappingName.toString
		}

		return mapping.name
	}

	public def nextAnonymousMappingName(Mapping mapping) {
		'''Mapping«anonMappingIndex++»'''.toString
	}

	public def getMappedCorrespondenceName(Mapping mapping) {
		'''«pkgName».«mapping.name»_MappedCorrespondence'''.toString
	}

	public def getHelperClassName(Mapping mapping) {
		'''«pkgName».«mapping.name»_Helper'''.toString
	}

	public def getWrapperName(Mapping mapping, Import imp) {
		'''«pkgName».«mapping.mappingName»_Wrapper_«imp.name»'''.toString
	}

	public def getCandidateClassName(Mapping mapping, Import imp) {
		'''«pkgName».«mapping.mappingName»_Candidate_«imp.name»'''.toString
	}

	public def getChange2CommandTransformingClassName() {
		'''«pkgName».MappingsChange2CommandTransforming'''.toString
	}

	public def getMappingClassName(Mapping mapping) {
		'''«pkgName».«mapping.name»_Mapping'''.toString
	}

	public def getConstantsClassName() {
		'''«pkgName».EMFWrapper'''.toString
	}

	public def getTestClassName() {
		'''«pkgName».test.TestBase'''.toString
	}

	public def getWrapperFieldName(Import imp) {
		'''«imp.name.toFirstLower»'''.toString
	}

	public def toFirstUpperName(Import imp) {
		'''«imp.name.toFirstUpper»'''.toString
	}

	public def toStateName(Import imp) {
		'''«imp.name.toUpperCase»'''.toString
	}
}