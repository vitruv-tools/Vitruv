package edu.kit.ipd.sdq.vitruvius.dsls.mapping.scoping

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintBlock
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.FeatureOfContextVariable
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Import
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.NamedEClass
import java.util.Iterator
import java.util.function.Function
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.mwe2.language.scoping.QualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.xbase.scoping.XImportSectionNamespaceScopeProvider

import static edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage.Literals.*

import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.EMFHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*

/**
 * @author Dominik Werle
 */
class MappingLanguageScopeProviderDelegate extends XImportSectionNamespaceScopeProvider {
	@Inject
	QualifiedNameProvider qualifiedNameProvider;

	def <T extends EObject> IScope createScope(IScope parentScope, Iterator<T> elements) {
		createScope(parentScope, elements.filter[hasQualifiedName], [
			val qn = qualifiedNameProvider.getFullyQualifiedName(it)
			if ((qn != null) && (!qn.isEmpty))
				EObjectDescription.create(qn, it)
			else
				null
		])
	}

	def <T extends EObject> IScope createScope(IScope parentScope, Iterator<T> elements,
		Function<T, IEObjectDescription> descriptionCreation) {
		new SimpleScope(
			parentScope,
			elements.map [
				descriptionCreation.apply(it)
			].filterNull.toList
		)
	}

	override getScope(EObject context, EReference reference) {
		if (reference.EType.equals(EcorePackage.Literals.ECLASS))
			return createQualifiedEClassScope(context.eResource)
		else if ((reference.equals(FEATURE_OF_CONTEXT_VARIABLE__FEATURE)) &&
			(context instanceof FeatureOfContextVariable))
			return createEStructuralFeatureScope(context as FeatureOfContextVariable)
		else if (reference.equals(CONTEXT_VARIABLE__TARGET))
			return createContextVariableScope(context)

		super.getScope(context, reference)
	}

	def IScope createMappingBaseScope(IScope parentScope, Mapping mapping) {
		var baseScope = parentScope
		for (it : mapping.requires) {
			baseScope = recursiveCreateMappingBaseScope(baseScope, it)
		}
		baseScope
	}

	def IScope recursiveCreateMappingBaseScope(IScope parentScope, Mapping mapping) {
		var baseScope = parentScope
		for (it : mapping.requires) {
			baseScope = recursiveCreateMappingBaseScope(baseScope, it)
		}

		for (it : #[mapping.signature0, mapping.signature1].filterNull) {
			baseScope = createScope(baseScope, it.eContents.validNamedEClasses.iterator)
		}

		baseScope
	}

	def <T> Iterable<NamedEClass> validNamedEClasses(Iterable<T> iterable) {
		iterable.filter(NamedEClass).filter[hasQualifiedName]
	}

	def hasQualifiedName(EObject eObject) {
		val qn = qualifiedNameProvider.getFullyQualifiedName(eObject)
		((qn != null) && (!qn.empty)
		)
	}

	def createContextVariableScope(EObject object) {
		val constraintBlock = object.getContainerOfType(ConstraintBlock)

		val mapping = constraintBlock.eContainer.requireType(Mapping)

		val requiresScope = createMappingBaseScope(IScope.NULLSCOPE, mapping)

		switch (constraintBlock.eContainingFeature) {
			case MAPPING__CONSTRAINTS0:
				return createScope(requiresScope, mapping.signature0.eContents.validNamedEClasses.iterator)
			case MAPPING__CONSTRAINTS1:
				return createScope(requiresScope, mapping.signature1.eContents.validNamedEClasses.iterator)
			case MAPPING__CONSTRAINTS_BODY:
				return recursiveCreateMappingBaseScope(IScope.NULLSCOPE, mapping)
		}
		
		return IScope.NULLSCOPE
	}

	def createEStructuralFeatureScope(FeatureOfContextVariable variable) {
		if (variable?.context?.target?.type != null) {
			createScope(IScope.NULLSCOPE, variable.context.target.type.EAllStructuralFeatures.iterator, [
					EObjectDescription.create(it.name, it)
				])
		} else {
			return IScope.NULLSCOPE
		}
	}

	/**
	 * Returns all elements with the given EClass inside the Resource res.
	 */
	def getAllContentsOfEClass(Resource res, EClass namedParent, boolean allContents) {
		var contents = if (allContents)
				res.allContents.toList
			else
				res.contents

		return contents.filter[eClass.equals(namedParent)]

	}

	/**
	 * Returns all packages that have been imported by import statements
	 * in the given resource.
	 */
	def getImports(Resource res) {
		var contents = res.getAllContentsOfEClass(MappingLanguagePackage.eINSTANCE.getImport, true).toList
		val validImports = contents.filter(Import).filter[package != null].map[it.name = it.name ?: it.package.name; it]

		return validImports
	}

	/**
	 * Creates and returns a {@link EObjectDescription} with a
	 * qualified name that also includes the name of the given
	 * {@link Import}.
	 */
	def createEObjectDescription(EClassifier classifier, Import imp) {
		if (classifier == null) {
			return null
		}

		return EObjectDescription.create(
			QualifiedName.create(imp.name).append(qualifiedNameProvider.getFullyQualifiedName(classifier).skipFirst(1)),
			classifier
		)
	}

	/**
	 * Create an {@link IScope} that represents all {@link EClass}es
	 * that are referencable inside the {@link Resource} via {@link Import}s
	 * by a fully qualified name.
	 * 
	 * @see MIRScopeProviderDelegate#createQualifiedEClassifierScope(Resource)
	 */
	def createQualifiedEClassScope(Resource res) {
		val classifierDescriptions = res.imports.map [ import |
			import.package.EClassifiers.filter(EClass).map[it.createEObjectDescription(import)]
		].flatten

		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions)
		return resultScope
	}
}