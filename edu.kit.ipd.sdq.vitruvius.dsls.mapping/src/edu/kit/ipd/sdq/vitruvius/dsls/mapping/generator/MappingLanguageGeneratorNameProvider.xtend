package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.StringHelper
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MetamodelImport

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

	public def getPkgName(Mapping mapping) {
		'''«pkgName».mappings.«mapping.mappingName.toLowerCase»'''.toString
	}

	public def nextAnonymousMappingName(Mapping mapping) {
		'''Mapping«anonMappingIndex++»'''.toString
	}

	public def getCorrespondenceWrapperClassName(Mapping mapping) {
		'''«mapping.pkgName».«mapping.mappingName.toFirstUpper»_Correspondence_Wrapper'''.toString
	}
	
	public def getWrapperClassName(Mapping mapping, MetamodelImport imp) {
		'''«mapping.pkgName».«mapping.mappingName.toFirstUpper»_Wrapper_«imp.name.toUpperCase»'''.toString
	}

	public def getChange2CommandTransformingClassName() {
		'''«pkgName».MappingsChange2CommandTransforming'''.toString
	}

	public def getMappingClassName(Mapping mapping) {
		'''«mapping.pkgName».«mapping.mappingName.toFirstUpper»_Mapping'''.toString
	}

	public def getConstantsClassName() {
		'''«pkgName».EMFWrapper'''.toString
	}

	public def getTestClassName() {
		'''«pkgName».test.TestBase'''.toString
	}

	public def toVarName(MetamodelImport imp) {
		'''«imp.name.toFirstLower»'''.toString
	}

	public def toFirstUpperName(MetamodelImport imp) {
		'''«imp.name.toFirstUpper»'''.toString
	}

	public def toStateName(MetamodelImport imp) {
		'''«imp.name.toUpperCase»'''.toString
	}
	
	public def toVarName(String className) {
		val simpleName = JavaHelper.toSimpleName(className)
		if (className.equals(simpleName.toFirstLower)) {
			return '''___«simpleName»'''
//			throw new IllegalArgumentException('''className "«className»" should start with uppercase letter''')
		}
		
		return simpleName.toFirstLower
	}
}