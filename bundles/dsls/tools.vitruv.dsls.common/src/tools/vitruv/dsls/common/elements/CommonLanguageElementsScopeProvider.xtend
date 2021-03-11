package tools.vitruv.dsls.common.elements

import java.util.Iterator
import java.util.function.Function
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

import static tools.vitruv.dsls.common.elements.ElementsPackage.Literals.*
import tools.vitruv.dsls.common.elements.MetamodelImport
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcorePackage
import tools.vitruv.dsls.common.elements.MetaclassReference
import tools.vitruv.dsls.common.elements.MetaclassFeatureReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.dsls.common.elements.MetaclassEAttributeReference
import tools.vitruv.dsls.common.elements.MetaclassEReferenceReference
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import com.google.inject.Provider

class CommonLanguageElementsScopeProvider {
	@Inject IQualifiedNameProvider qualifiedNameProvider
	@Inject Provider<EPackageRegistryScope> packagesScope

	def <T> IScope createScope(IScope parentScope, Iterator<? extends T> elements,
		Function<T, IEObjectDescription> descriptionCreation) {
		new SimpleScope(parentScope, elements.map[descriptionCreation.apply(it)].filterNull.toList);
	}

	def getScope(EObject context, EReference reference) {
		if (reference.equals(METAMODEL_IMPORT__PACKAGE)) {
			return packagesScope.get()
		} else if (reference.equals(METACLASS_FEATURE_REFERENCE__FEATURE)) {
			return createEStructuralFeatureScope((context as MetaclassFeatureReference)?.metaclass)
		} else if (reference.equals(METACLASS_EATTRIBUTE_REFERENCE__FEATURE)) {
			return createEAttributeScope((context as MetaclassEAttributeReference)?.metaclass)
		} else if (reference.equals(METACLASS_EREFERENCE_REFERENCE__FEATURE)) {
			return createEReferenceScope((context as MetaclassEReferenceReference)?.metaclass)
		} else if (reference.equals(METACLASS_REFERENCE__METAMODEL)) {
			return createImportsScope(context.eResource)
		} else if (reference.equals(METACLASS_REFERENCE__METACLASS)) {
			val potentialMetaclassReference = if(context instanceof MetaclassReference) context
			return createQualifiedEClassScope(potentialMetaclassReference?.metamodel)
		}
		return null
	}
	
	private def createImportsScope(Resource resource) {
		if (resource === null) {
			return IScope.NULLSCOPE
		}
		createScope(IScope.NULLSCOPE, resource.metamodelImports.iterator, [EObjectDescription.create(it.name, it)])
	}
	
	/**
	 * Returns all packages that have been imported by import statements
	 * in the given resource.
	 */
	private def getMetamodelImports(Resource res) {
		var contents = res.getAllContentsOfEClass(ElementsPackage.eINSTANCE.getMetamodelImport, true).toList
		val validImports = contents.filter(MetamodelImport).filter[package !== null].map [
			it.name = it.name ?: it.package.name;
			it
		]

		return validImports
	}

	private def createEStructuralFeatureScope(EClass eClass) {
		return createEStructuralFeatureScope(eClass?.EAllStructuralFeatures.iterator);
	}

	private def createEAttributeScope(EClass eClass) {
		return createEStructuralFeatureScope(eClass?.EAllAttributes.iterator);
	}

	private def createEReferenceScope(EClass eClass) {
		return createEStructuralFeatureScope(eClass?.EAllReferences.iterator);
	}

	private def createEStructuralFeatureScope(Iterator<? extends EStructuralFeature> featuresIterator) {
		if (featuresIterator !== null) {
			createScope(IScope.NULLSCOPE, featuresIterator, [
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
	 * Creates an {@link IScope} that represents all {@link EClass}es
	 * that are provided by the metamodel of the given {@link MetamodelImport}
	 * by a fully qualified name.
	 * 
	 * @param metamodelImport - the metamodel to provide all classes of
	 */
	def createQualifiedEClassScope(MetamodelImport metamodelImport) {
		return createQualifiedEClassScope(metamodelImport, false, null);
	}

	/**
	 * Creates an {@link IScope} that represents all {@link EClass}es
	 * that are provided by the metamodel of the given {@link MetamodelImport}
	 * by a fully qualified name, and {@link EObject} if the given import does not
	 * reference a proper metamodel.
	 * 
	 * @param metamodelImport - the metamodel to provide the classes of
	 */
	def createQualifiedEClassScopeWithEObject(MetamodelImport metamodelImport) {
		return createQualifiedEClassScope(metamodelImport, true, null);
	}

	/**
	 * Creates an {@link IScope} that represents all non-abstract {@link EClass}es
	 * that are provided by the metamodel of the given {@link MetamodelImport}
	 * by a fully qualified name.
	 * 
	 * @param metamodelImport - the metamodel to provide the non-abstract classes of
	 */
	def createQualifiedEClassScopeWithoutAbstract(MetamodelImport metamodelImport) {
		return createQualifiedEClassScope(metamodelImport, false, [
			!abstract
		]);
	}

	/**
	 * Creates an {@link IScope} that represents all abstract {@link EClass}es
	 * that are provided by the metamodel of the given {@link MetamodelImport}
	 * by a fully qualified name.
	 * 
	 * @param metamodelImport - the metamodel to provide the abstract classes of
	 */
	def createQualifiedEClassScopeOnlyAbstract(MetamodelImport metamodelImport) {
		return createQualifiedEClassScope(metamodelImport, true, [
			abstract
		]);
	}

	/**
	 * Creates an {@link IScope} that represents all {@link EClass}es
	 * that are provided by the metamodel of the given {@link MetamodelImport} and that
	 * are either the given type or one of its subtypes by a fully qualified name.
	 * 
	 * @param metamodelImport - the metamodel to provide the abstract classes of
	 * @param superType - the type to provide the subtypes of
	 */
	def createQualifiedEClassScopeOfSuperTypeChildren(MetamodelImport metamodelImport, EClass superType) {
		return createQualifiedEClassScope(metamodelImport, true, [
			!it.abstract && (it.EAllSuperTypes.contains(superType) || it.EAllGenericSuperTypes.contains(superType))
		]);
	}

	/**
	 * Create an {@link IScope} that represents all {@link EClass}es
	 * that are referencable inside the {@link Resource} via {@link Import}s
	 * by a fully qualified name.
	 * 
	 * @see #createQualifiedEClassifierScope(Resource)
	 */
	private def createQualifiedEClassScope(MetamodelImport metamodelImport, boolean includeEObject,
		Function<EClass, Boolean> filter) {
		val classifierDescriptions = if (metamodelImport === null || metamodelImport.package === null) {
				if (includeEObject) {
					#[createEObjectDescription(EcorePackage.Literals.EOBJECT, false)];
				} else {
					#[];
				}
			} else {
				collectObjectDescriptions(metamodelImport.package, true, metamodelImport.useQualifiedNames, filter)
			}

		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions)
		return resultScope
	}
	
	/**
	 * Creates and returns a {@link EObjectDescription} with simple name
	 * or in case of a qualified name with the given package prefix.
	 */
	private def IEObjectDescription createEObjectDescription(EClassifier classifier, boolean useQualifiedNames) {
		var QualifiedName qualifiedName;
		if (useQualifiedNames) {
			qualifiedName = qualifiedNameProvider.getFullyQualifiedName(classifier).skipFirst(1);
		} else {
			qualifiedName = QualifiedName.create(classifier.name);
		}
		return EObjectDescription.create(qualifiedName, classifier);
	}
	
	private def Iterable<IEObjectDescription> collectObjectDescriptions(EPackage pckg, boolean includeSubpackages,
		boolean useQualifiedNames, Function<EClass, Boolean> filter) {
		var classes = collectEClasses(pckg, includeSubpackages)
		if (filter !== null) {
			classes = classes.filter(filter)
		}
		classes.map[it.createEObjectDescription(useQualifiedNames)]
	}

	private def Iterable<EClass> collectEClasses(EPackage pckg, boolean includeSubpackages) {
		var recursiveResult = <EClass>newArrayList();
		if (includeSubpackages) {
			recursiveResult += pckg.ESubpackages.map[it|collectEClasses(it, includeSubpackages)].flatten
		}
		val result = pckg.EClassifiers.filter(EClass);
		return recursiveResult + result;
	}

}
