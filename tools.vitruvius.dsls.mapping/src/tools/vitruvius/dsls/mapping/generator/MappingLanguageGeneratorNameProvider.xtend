package tools.vitruvius.dsls.mapping.generator

import com.google.inject.Inject
import com.google.inject.name.Named
import tools.vitruvius.dsls.mapping.mappingLanguage.ConstraintExpression
import tools.vitruvius.dsls.mapping.mappingLanguage.Mapping
import tools.vitruvius.dsls.mapping.mappingLanguage.XbaseBodyConstraintExpression
import tools.vitruvius.dsls.mapping.mappingLanguage.XbaseSignatureConstraintExpression
import tools.vitruvius.dsls.mirbase.mirBase.MetamodelImport
import tools.vitruvius.framework.util.bridges.JavaHelper
import java.util.Map

import static extension tools.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*
import com.google.inject.Singleton
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruvius.dsls.mapping.mappingLanguage.ContextVariable

@Singleton
class MappingLanguageGeneratorNameProvider {
	private int anonMappingIndex
	private Map<ConstraintExpression, Integer> constraintExpression2Index
	
	@Inject @Named(MappingLanguageGenerator.PACKAGE_NAME_FIELD)
	private String pkgName
	
	public def initialize() {
		anonMappingIndex = 0
		constraintExpression2Index = newHashMap
	}
	
	public new() {
		initialize
	}

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
	
	public def getMappingConstraintsClassName(Mapping mapping) {
		'''«mapping.pkgName».«mapping.mappingName.toFirstUpper»_Mapping_Constraints'''.toString
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
	
	public def toVarName(Mapping mapping) {
		'''«mapping.name.toFirstLower»'''.toString
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
	
	def getOrSetIndex(ConstraintExpression constraintExpression) {
		if (!constraintExpression2Index.containsKey(constraintExpression)) {
			constraintExpression2Index.put(constraintExpression, constraintExpression2Index.size)
		}
		
		return constraintExpression2Index.get(constraintExpression)
	}
	
	public def String getCheckMethodName(XbaseSignatureConstraintExpression expression) {
		'''checkConstraint«getOrSetIndex(expression)»'''
	}
	
	public def String getEnforceMethodName(XbaseSignatureConstraintExpression expression) {
		'''enforceConstraint«getOrSetIndex(expression)»'''
	}
	
	public def String getPropagateMethodName(XbaseBodyConstraintExpression expression) {
		'''propagateConstraint«getOrSetIndex(expression)»From«expression.metamodel?.model?.toFirstUpperName ?: ''»'''
	}
	
	public def String getResponseName(MetamodelImport imp, Resource resource) {
		'''ResponseFrom«imp.toFirstUpperName»And«resource.URI.trimFileExtension.lastSegment.toFirstUpper»'''
	}
	
	public def String getContextVariableName(ContextVariable contextVariable) {
		(contextVariable?.requiredMappingPath
			?.collectRecursive ?: #[])
			.map[it.name.toFirstUpper]
			.join
			+ contextVariable.targetClass.name.toFirstUpper
	}
}