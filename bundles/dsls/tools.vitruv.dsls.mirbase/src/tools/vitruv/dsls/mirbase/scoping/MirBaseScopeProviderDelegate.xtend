package tools.vitruv.dsls.mirbase.scoping

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
import org.eclipse.xtext.xbase.scoping.XImportSectionNamespaceScopeProvider

import static tools.vitruv.dsls.mirbase.mirBase.MirBasePackage.Literals.*
import tools.vitruv.dsls.mirbase.mirBase.MirBasePackage
import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcorePackage
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEAttributeReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEReferenceReference

class MirBaseScopeProviderDelegate extends XImportSectionNamespaceScopeProvider {
	def <T> IScope createScope(IScope parentScope, Iterator<? extends T> elements,
		Function<T, IEObjectDescription> descriptionCreation) {
		new SimpleScope(parentScope, elements.map[descriptionCreation.apply(it)].filterNull.toList);
	}

	override getScope(EObject context, EReference reference) {
		if (reference.equals(METACLASS_FEATURE_REFERENCE__FEATURE))
			return createEStructuralFeatureScope((context as MetaclassFeatureReference)?.metaclass)
		else if (reference.equals(METACLASS_EATTRIBUTE_REFERENCE__FEATURE))
			return createEAttributeScope((context as MetaclassEAttributeReference)?.metaclass)
		else if (reference.equals(METACLASS_EREFERENCE_REFERENCE__FEATURE))
			return createEReferenceScope((context as MetaclassEReferenceReference)?.metaclass)
		else if (reference.equals(METACLASS_REFERENCE__METACLASS))
			return createQualifiedEClassScope((context as MetaclassReference).metamodel)
		else if (reference.equals(METAMODEL_REFERENCE__MODEL)) {
			return createImportsScope(context.eResource);
		}
		super.getScope(context, reference)
	}

	def createImportsScope(Resource resource) {
		createScope(IScope.NULLSCOPE, resource.metamodelImports.iterator, [EObjectDescription.create(it.name, it)])
	}

	def hasQualifiedName(EObject eObject) {
		val qn = qualifiedNameProvider.getFullyQualifiedName(eObject);
		return ((qn !== null) && (!qn.empty));
	}

	def createEStructuralFeatureScope(Iterator<? extends EStructuralFeature> featuresIterator) {
		if (featuresIterator !== null) {
			createScope(IScope.NULLSCOPE, featuresIterator, [
				EObjectDescription.create(it.name, it)
			])
		} else {
			return IScope.NULLSCOPE
		}
	}

	def createEStructuralFeatureScope(EClass eClass) {
		return createEStructuralFeatureScope(eClass?.EAllStructuralFeatures.iterator);
	}

	def createEAttributeScope(EClass eClass) {
		return createEStructuralFeatureScope(eClass?.EAllAttributes.iterator);
	}

	def createEReferenceScope(EClass eClass) {
		return createEStructuralFeatureScope(eClass?.EAllReferences.iterator);
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
		var contents = res.getAllContentsOfEClass(MirBasePackage.eINSTANCE.getMetamodelImport, true).toList
		val validImports = contents.filter(MetamodelImport).filter[package !== null].map [
			it.name = it.name ?: it.package.name;
			it
		]

		return validImports
	}

	/**
	 * Create an {@link IScope} that represents all {@link EClass}es
	 * that are referencable inside the {@link Resource} via {@link Import}s
	 * by a fully qualified name.
	 * 
	 * @see MIRScopeProviderDelegate#createQualifiedEClassifierScope(Resource)
	 */
	private def createQualifiedEClassScope(Resource res, boolean includeEObject, Function<EClass, Boolean> filter) {
		val classifierDescriptions = res.metamodelImports.map [ import |
			collectObjectDescriptions(import.package, true, import.useQualifiedNames, filter)
		].flatten + if (includeEObject) {
			#[createEObjectDescription(EcorePackage.Literals.EOBJECT, false)];
		} else {
			#[];
		}

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

	def createQualifiedEClassScope(Resource res) {
		return createQualifiedEClassScope(res, false, null);
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

	protected def Iterable<IEObjectDescription> collectObjectDescriptions(EPackage pckg, boolean includeSubpackages,
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

	/**
	 * Creates and returns a {@link EObjectDescription} with simple name
	 * or in case of a qualified name with the given package prefix.
	 */
	protected def IEObjectDescription createEObjectDescription(EClassifier classifier, boolean useQualifiedNames) {
		var QualifiedName qualifiedName;
		if (useQualifiedNames) {
			qualifiedName = qualifiedNameProvider.getFullyQualifiedName(classifier).skipFirst(1);
		} else {
			qualifiedName = QualifiedName.create(classifier.name);
		}
		return EObjectDescription.create(qualifiedName, classifier);
	}
}
