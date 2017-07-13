package tools.vitruv.dsls.mapping.helpers

import tools.vitruv.dsls.mapping.helpers.JavaGeneratorHelper.ImportHelper
import tools.vitruv.dsls.mapping.mappingLanguage.ConstraintBlock
import tools.vitruv.dsls.mapping.mappingLanguage.ContextVariable
import tools.vitruv.dsls.mapping.mappingLanguage.DefaultContainExpression
import tools.vitruv.dsls.mapping.mappingLanguage.EqualsLiteralExpression
import tools.vitruv.dsls.mapping.mappingLanguage.FeatureOfContextVariable
import tools.vitruv.dsls.mapping.mappingLanguage.InExpression
import tools.vitruv.dsls.mapping.mappingLanguage.Mapping
import tools.vitruv.dsls.mapping.mappingLanguage.MappingFile
import tools.vitruv.dsls.mapping.mappingLanguage.RequiredMapping
import tools.vitruv.dsls.mapping.mappingLanguage.RequiredMappingPathBase
import tools.vitruv.dsls.mapping.mappingLanguage.RequiredMappingPathTail
import tools.vitruv.dsls.mapping.mappingLanguage.Signature
import edu.kit.ipd.sdq.commons.util.java.Pair
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage

import static extension tools.vitruv.dsls.mapping.helpers.EMFHelper.*
import static extension tools.vitruv.framework.util.bridges.JavaHelper.*
import static extension java.util.Objects.*
import tools.vitruv.dsls.mapping.mappingLanguage.SignatureConstraintBlock
import tools.vitruv.dsls.mapping.mappingLanguage.ConstraintExpression
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference

class MappingLanguageHelper {
	public static def inferPackagesForSignaturesAndConstraints(Mapping mapping) {
		// mappings and constraint blocks must have the same packages
		// they can also be null.
		val signatures = mapping.signatures.map[it?.package]
		val constraints = mapping.constraints.map[it?.package]

		constraints.zipAny(signatures).map [
			claim[first == second || first === null || second === null]
			first ?: second
		]
	}

	public static def getAllSignatureElements(Mapping mapping) {
		(mapping?.signatures?.map[elements]?.flatten ?: #[])
	}

	public static def getAllConstraintExpressions(Mapping mapping) {
		(mapping?.constraints?.map[expressions]?.flatten ?: #[]) + (mapping?.constraintsBody?.expressions ?: #[])
	}

	public static def getAllSignatureConstraints(Mapping mapping) {
		mapping?.allConstraintExpressions.filter[getPackage !== null]
			.groupBy[getPackage(it)]
	}

	public static def getAllRequires(Mapping mapping) {
		if (mapping.^default)
			#[]
		else
			(mapping?.requires ?: #[]) + (mapping.getContainerOfType(MappingFile)?.defaultRequirements ?: #[])
	}

	public static def defaultMappings(EObject context) {
		return context.getContainerOfType(MappingFile)?.mappings?.filter[^default] ?: #[]
	}

	public static def <T extends EObject> claimExactlyOneInPackage(Iterable<T> elements, EPackage pkg) {
		elements.filterWithPackage(pkg).claimIdenticalElements
	}

	public static def <T extends EObject> getOneInPackage(Iterable<T> elements, EPackage pkg) {
		elements.filterWithPackage(pkg).getIdenticalElement
	}

	public static def <T extends EObject> filterWithPackage(Iterable<T> elements, EPackage pkg) {
		elements.filter[(getPackage ?: pkg).equals(pkg)]
	}

	public static def dispatch EPackage getPackage(DefaultContainExpression defaultContainExpression) {
		getPackage(defaultContainExpression.target.targetClass)
	}

	public static def dispatch EPackage getPackage(InExpression inExpression) {
		getPackage(inExpression.target.targetClass)
	}
	
	/**
	 * Tries to find the fitting import for a constraint expression.
	 * This is only possible if there is an associated not empty signature.
	 */
	public static def dispatch EPackage getPackage(ConstraintExpression expression) {
		val signatureConstraintBlock = expression.getContainerOfType(SignatureConstraintBlock)
		if (signatureConstraintBlock === null)
			return null

		val mapping = signatureConstraintBlock.eContainer as Mapping
		val blockIndex = indexOf(mapping.constraints, signatureConstraintBlock)
		return mapping?.signatures?.get(blockIndex)?.package
	}

	public static def dispatch EPackage getPackage(EqualsLiteralExpression equalsLiteralExpression) {
		getPackage(equalsLiteralExpression.target.context.targetClass)
	}

	public static def dispatch EPackage getPackage(MetaclassReference modelElement) {
		modelElement?.metaclass?.EPackage
	}

	public static def dispatch EPackage getPackage(FeatureOfContextVariable feat) {
		getPackage(feat.context.targetClass)
	}

	public static def dispatch EPackage getPackage(Signature signature) {
		signature?.declaredPackage?.model?.package ?: signature.elements.map[metaclass.EPackage].getIdenticalElement
	}

	public static def dispatch EPackage getPackage(ConstraintBlock constraintBlock) {
		constraintBlock.eAllContents.filter(ContextVariable).map[targetClass.metaclass.EPackage].getIdenticalElement
	}

	public static def getImport(EObject eObject) {
		val imports = eObject.getContainerOfType(MappingFile).requireNonNull.imports
		val index = imports.map[package].indexOf(eObject.getPackage).claim [
			it != -1
		]
		return imports.get(index)
	}

	public static def getTypesAndNames(ImportHelper ih, List<NamedMetaclassReference> elements) {
		elements?.map[new Pair(ih.typeRef(metaclass.instanceTypeName), name.toFirstLower)] ?: #[]
	}

	public static def RequiredMapping getLastMapping(RequiredMappingPathBase mappingPath) {
		if (mappingPath.tail !== null)
			return mappingPath.tail.lastMapping
		else
			return mappingPath.requiredMapping
	}

	public static def RequiredMapping getLastMapping(RequiredMappingPathTail mappingPath) {
		if (mappingPath.tail !== null)
			return mappingPath.tail.lastMapping
		else
			return mappingPath.requiredMapping
	}

	public static def List<RequiredMapping> collectRecursive(RequiredMappingPathBase mappingPath) {
		(#[mappingPath.requiredMapping] + (mappingPath.tail?.collectRecursive ?: #[])).toList
	}

	public static def List<RequiredMapping> collectRecursive(RequiredMappingPathTail mappingPath) {
		(#[mappingPath.requiredMapping] + (mappingPath.tail?.collectRecursive ?: #[])).toList
	}

	public static def toSensibleDefaultName(EObject eObject) {
		val baseName = eObject.getBaseName.toFirstLower

		val containment = eObject.eContainmentFeature
		val container = eObject.eContainer

		if (container === null || !containment.many)
			return baseName

		val siblingsWithSameName = container.eGet(containment)?.requireCollectionType(EObject)?.filterNull.filter [
			baseName.equals(it.baseName?.toFirstLower)
		]
		if ((siblingsWithSameName === null) || (siblingsWithSameName.size == 1))
			return baseName

		val index = siblingsWithSameName.indexOf(eObject)

		if (index === null)
			throw new IllegalArgumentException

		return '''«baseName»_«index.toString»'''
	}

	public static def dispatch getBaseName(Object object) {
		object.class.simpleName ?: "object"
	}

	public static def dispatch getBaseName(EObject object) {
		object.eClass.name ?: "eObject"
	}

	public static def dispatch getBaseName(EClass object) {
		object.name ?: "eClass"
	}

	public static def dispatch getBaseName(MetaclassReference object) {
		object.metaclass?.name ?: "eClass"
	}

	public static def dispatch getBaseName(RequiredMapping object) {
		object.mapping?.name ?: "requiredMapping"
	}

	public static def dispatch getBaseName(Mapping object) {
		object.name ?: "mapping"
	}
}
