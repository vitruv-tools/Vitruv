package tools.vitruvius.dsls.mirbase.scoping

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

import static tools.vitruvius.dsls.mirbase.mirBase.MirBasePackage.Literals.*
import tools.vitruvius.dsls.mirbase.mirBase.FeatureOfElement
import tools.vitruvius.dsls.mirbase.mirBase.MirBasePackage
import tools.vitruvius.dsls.mirbase.mirBase.MetamodelImport
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcorePackage

class MirBaseScopeProviderDelegate extends XImportSectionNamespaceScopeProvider {
	private static val LOGGER = Logger.getLogger(MirBaseScopeProviderDelegate)
	
	def <T> IScope createScope(IScope parentScope, Iterator<T> elements,
		Function<T, IEObjectDescription> descriptionCreation) {
		new SimpleScope(parentScope, elements.map [descriptionCreation.apply(it)].filterNull.toList);
	}
	
	override getScope(EObject context, EReference reference) {
		if (reference.equals(FEATURE_OF_ELEMENT__FEATURE))
			return createEStructuralFeatureScope((context as FeatureOfElement)?.element)
		else if (reference.equals(FEATURE_OF_ELEMENT__ELEMENT)
			|| reference.equals(MODEL_ELEMENT__ELEMENT))
			return createQualifiedEClassScope(context.eResource)
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
		return ((qn != null) && (!qn.empty));
	}
	
	def createEStructuralFeatureScope(EClass eClass) {
		if (eClass != null) {
			createScope(IScope.NULLSCOPE, eClass.EAllStructuralFeatures.iterator, [
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
		var contents = res.getAllContentsOfEClass(MirBasePackage.eINSTANCE.getMetamodelImport, true).toList
		val validImports = contents.filter(MetamodelImport).filter[package != null].map[it.name = it.name ?: it.package.name; it]

		return validImports
	}


	/**
	 * Create an {@link IScope} that represents all {@link EClass}es
	 * that are referencable inside the {@link Resource} via {@link Import}s
	 * by a fully qualified name.
	 * 
	 * @see MIRScopeProviderDelegate#createQualifiedEClassifierScope(Resource)
	 */
	private def createQualifiedEClassScope(Resource res, boolean includeAbstract, boolean includeEObject) {
		val classifierDescriptions = res.metamodelImports.map[
			import | collectObjectDescriptions(import.package, true, includeAbstract, import.useSimpleNames, import.name)
		].flatten +
			if (includeEObject) {
				#[createEObjectDescription(EcorePackage.Literals.EOBJECT, true, null)];	
			} else {
				#[];
			}

		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions)
		return resultScope
	}
	
	def createQualifiedEClassScopeWithoutAbstract(Resource res) {
		return createQualifiedEClassScope(res, true, false);
	}
	
	def createQualifiedEClassScope(Resource res) {
		return createQualifiedEClassScope(res, false, false);
	}
	
	def createQualifiedEClassScopeWithEObject(Resource res) {
		return createQualifiedEClassScope(res, true, true);
	}
	
	protected def Iterable<IEObjectDescription> collectObjectDescriptions(EPackage pckg, 
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
	protected def IEObjectDescription createEObjectDescription(EClassifier classifier, boolean useSimpleName, String packagePrefix) {
		var QualifiedName qualifiedName;
		if (useSimpleName) {
			qualifiedName = QualifiedName.create(classifier.name);
		} else {
			qualifiedName = qualifiedNameProvider.getFullyQualifiedName(classifier).skipFirst(1);
		}
		if (packagePrefix != null) {
			qualifiedName = QualifiedName.create(packagePrefix).append(qualifiedName);
		}
		return EObjectDescription.create(qualifiedName, classifier);
	}
}