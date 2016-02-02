package edu.kit.ipd.sdq.vitruvius.dsls.response.scoping

import java.util.Iterator
import java.util.function.Function
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.xbase.scoping.XImportSectionNamespaceScopeProvider

import static edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage.Literals.*

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.FeatureOfElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage
import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelImport
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseFile
import org.eclipse.emf.ecore.EStructuralFeature
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicMultiValuedFeatureChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicSingleValuedFeatureChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelRootChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelRootCreate
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelRootDelete

/**
 * Copy of edu.kit.ipd.sdq.vitruvius.dsls.mapping.scoping.MappingLanguageScopeProviderDelegate by Dominik Werle
 */
// TODO HK refactor to only one implementation
class ResponseLanguageScopeProviderDelegate extends XImportSectionNamespaceScopeProvider {
	private static val LOGGER = Logger.getLogger(ResponseLanguageScopeProviderDelegate)
	
	def <T> IScope createScope(IScope parentScope, Iterator<T> elements,
		Function<T, IEObjectDescription> descriptionCreation) {
		new SimpleScope(parentScope, elements.map [descriptionCreation.apply(it)].filterNull.toList);
	}
	
	override getScope(EObject context, EReference reference) {
		if (reference.equals(FEATURE_OF_ELEMENT__FEATURE))
			return createEStructuralFeatureScope(context as FeatureOfElement)
		else if (reference.equals(FEATURE_OF_ELEMENT__ELEMENT)
			|| reference.equals(MODEL_ELEMENT__ELEMENT)) {
			if (context instanceof ConcreteTargetModelRootCreate
				|| context instanceof ConcreteTargetModelRootDelete) {
				return createQualifiedConcreteEClassScope(context.eResource);
			} else {
				return createQualifiedEClassScope(context.eResource)
			}
		} else if (reference.equals(METAMODEL_REFERENCE__MODEL)) {
			return createImportsScope(context.eResource);
		}
		super.getScope(context, reference)
	}
	
	def createImportsScope(Resource resource) {
		val file = resource.contents.get(0) as ResponseFile;
		createScope(IScope.NULLSCOPE, file.metamodelImports.iterator, [EObjectDescription.create(it.name, it)])		
	}
	
	def hasQualifiedName(EObject eObject) {
		val qn = qualifiedNameProvider.getFullyQualifiedName(eObject);
		return ((qn != null) && (!qn.empty));
	}
	
	def createEStructuralFeatureScope(FeatureOfElement variable) {
		if (variable?.element != null) {
			val changeType = variable.eContainer;
			val filterFunction = if (changeType instanceof AtomicMultiValuedFeatureChange) {
				[EStructuralFeature feat | feat.upperBound != 1];
			} else if (changeType instanceof AtomicSingleValuedFeatureChange) {
				[EStructuralFeature feat | feat.upperBound == 1];
			} else {
				[EStructuralFeature feat | true];
			}
			createScope(IScope.NULLSCOPE, variable.element.EAllStructuralFeatures.filter(filterFunction).iterator, [
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
	def getMetamodelImports(Resource res) {
		var contents = res.getAllContentsOfEClass(ResponseLanguagePackage.eINSTANCE.getMetamodelImport, true).toList
		val validImports = contents.filter(MetamodelImport).filter[package != null].map[it.name = it.name ?: it.package.name; it]

		return validImports
	}


	/**
	 * Create an {@link IScope} that represents all non-abstract {@link EClass}es
	 * that are referencable inside the {@link Resource} via {@link Import}s
	 * by a fully qualified name.
	 * 
	 * @see MIRScopeProviderDelegate#createQualifiedEClassifierScope(Resource)
	 */
	def createQualifiedConcreteEClassScope(Resource res) {
		val classifierDescriptions = res.metamodelImports.map[
			import | collectObjectDescriptions(import.package, true, false, false, import.name)
		].flatten

		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions)
		return resultScope
	}
	
	/**
	 * Create an {@link IScope} that represents all {@link EClass}es
	 * that are referencable inside the {@link Resource} via {@link Import}s
	 * by a fully qualified name.
	 * 
	 * @see MIRScopeProviderDelegate#createQualifiedEClassifierScope(Resource)
	 */
	def createQualifiedEClassScope(Resource res) {
		val classifierDescriptions = res.metamodelImports.map[
			import | collectObjectDescriptions(import.package, true, true, false, import.name)
		].flatten

		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions)
		return resultScope
	}
	
	private def Iterable<IEObjectDescription> collectObjectDescriptions(EPackage pckg, 
		boolean includeSubpackages, boolean includeAbstract, boolean useSimpleNames, String packagePrefix) {
		var classes = collectEClasses(pckg, includeSubpackages);
		val result = classes.filter[includeAbstract || !abstract].map[it.createEObjectDescription(useSimpleNames, packagePrefix)];
		return result;
	}
	
	private def Iterable<EClass> collectEClasses(EPackage pckg, boolean includeSubpackages) {
		var recursiveResult = <EClass>newArrayList();
		if (includeSubpackages) {
			recursiveResult += pckg.ESubpackages.map[it | collectEClasses(it, includeSubpackages)].flatten
		}
		val result = pckg.EClassifiers.filter(EClass);
		return recursiveResult + result;
	}
	
	/**
	 * Creates and returns a {@link EObjectDescription} with simple name
	 * or in case of a qualified name with the given package prefix.
	 */
	private def IEObjectDescription createEObjectDescription(EClassifier classifier, boolean useSimpleName, String packagePrefix) {
		if (useSimpleName) {
			return EObjectDescription.create(classifier.name, classifier);
		} else {
			var qualifiedName = qualifiedNameProvider.getFullyQualifiedName(classifier).skipFirst(1);
			if (packagePrefix != null) {
				qualifiedName = QualifiedName.create(packagePrefix).append(qualifiedName);
			}
			return EObjectDescription.create(qualifiedName, classifier);
		}
	}
}
