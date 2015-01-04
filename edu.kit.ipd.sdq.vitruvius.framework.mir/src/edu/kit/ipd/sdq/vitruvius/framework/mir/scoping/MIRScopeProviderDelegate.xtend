package edu.kit.ipd.sdq.vitruvius.framework.mir.scoping

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.EcoreHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef
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
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.xbase.scoping.XImportSectionNamespaceScopeProvider

import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper.*
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass

/**
 * @author Dominik Werle
 */
class MIRScopeProviderDelegate extends XImportSectionNamespaceScopeProvider {
	@Inject
	QualifiedNameProvider qualifiedNameProvider;
	
	override getScope(EObject context, EReference reference) {
		if (reference.getEType.equals(EcorePackage.eINSTANCE.getEClass))
			return createQualifiedEClassScope(context.eResource)
		else if (reference.getEType.equals(EcorePackage.eINSTANCE.getEClassifier))
			return createQualifiedEClassifierScope(context.eResource)
		else if (reference.equals(MIRPackage.eINSTANCE.featureCall_Tail))
			return createFeatureCallTailScope(context as FeatureCall)
		else if (reference.equals(MIRPackage.eINSTANCE.typedElementRef_Ref))
			return createFeatureCallRootElementScope(context)
		
		super.getScope(context, reference)
	}
	
	/**
	 * Returns all elements with the given EClass inside the Resource res.
	 */
	def getAllContentsOfEClass(Resource res, EClass namedParent, boolean allContents) {
		var contents =
			if (allContents)
				res.allContents.toList
			else
				res.contents
				
		return contents
			.filter[eClass.equals(namedParent)]
		
	}
	
	/**
	 * Returns all packages that have been imported by import statements
	 * in the given resource.
	 */
	def getImports(Resource res) {
		var contents = res.getAllContentsOfEClass(MIRPackage.eINSTANCE.getImport, true).toList
		return contents.map [
			if (it != null) {
				val import = it as Import
				if (import.name != null)
					return import
				else
					return null
			} else
				return null
		].filterNull
	}
	
	/**
	 * Creates and returns a {@link EObjectDescription} with a
	 * qualified name that also includes the name of the given
	 * {@link Import}.
	 */
	def createEObjectDescription(EClassifier classifier, Import imp) {
		return
			if (classifier == null)
				null 
			else
				EObjectDescription.create(
					QualifiedName
						.create(imp.name)
						.append(qualifiedNameProvider.getFullyQualifiedName(classifier).skipFirst(1)),
					classifier
				)
	}
	
	/**
	 * Create an {@link IScope} that represents all {@link EClassifier}s
	 * that are referencable inside the {@link Resource} via {@link Import}s
	 * by a fully qualified name.
	 */
	def createQualifiedEClassifierScope(Resource res) {
		val classifierDescriptions = res.imports.map [ import |
			import.package.EClassifiers
				.map [ it.createEObjectDescription(import) ]
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
		val classifierDescriptions = res.imports.map [ import |
			import.package.EClassifiers
				.filter(EClass)
				.map [ it.createEObjectDescription(import) ]
		].flatten
		
		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions)
		return resultScope
	}
	
	
	/**
	 * Creates an {@link IScope} that includes all features that are referenceable
	 * in the given context, where context is a {@link NamedFeature} that already has
	 * a defined referenced {@link EStructuralFeature} or {@link EClass}.
	 * 
	 * @see NamedFeature#getContainingNamedFeature()
	 * @see NamedFeature#getContainingNamedEClass()
	 */
	def createFeatureCallTailScope(FeatureCall call) {
		val head = call.ref
		
		val headType = head?.getTypeRecursive
		val featureDescriptions = if ((headType != null) && (headType instanceof EClass)) {
			(headType as EClass).createFeatureDescriptions
		}
		
		val resultScope = new SimpleScope(IScope.NULLSCOPE, featureDescriptions ?: #[])
		return resultScope
	}
	
	
	// TODO: refactor Feature/ClassMapping, pull up mappedElements to supertype
	def createFeatureCallRootElementScope(EObject context) {
		val elementsInScope = EcoreHelper.getContainerHierarchy(context, true)
			.filter[it instanceof FeatureMapping || it instanceof ClassMapping]
		
		return new SimpleScope(IScope.NULLSCOPE,
			elementsInScope.map [
				switch it {
					FeatureMapping:
						mappedElements
							.filter(FeatureCall)
							.filter[it.name != null]
							.map[EObjectDescription.create(it.name, it)]
					ClassMapping:
						mappedElements
							.filter(NamedEClass)
							.filter[it.name != null]
							.map[EObjectDescription.create(it.name, it)]
				}
			].flatten)
	}
	
	/**
	 * Creates {@link EObjectDescription}s for every feature
	 * of the class.
	 */
	def createFeatureDescriptions(EClass eClass) {
		return eClass.getEAllStructuralFeatures.map [
			val qualifiedName =
				QualifiedName.create(it.name)
			if (qualifiedName != null)
				EObjectDescription.create(qualifiedName, it)
		]
	}
	
	def getContainingNamedEClassScope(FeatureCall feature) {
		var resultScope = IScope.NULLSCOPE
		for (var container = feature.eContainer; container != null; container = container.eContainer) {
			if (container instanceof ClassMapping) {
				resultScope = new SimpleScope(resultScope,
					container.mappedElements
						.filter(NamedEClass)
						.filter[name != null]
						.map [ namedClass |
						EObjectDescription.create(namedClass.name, namedClass)
					])
			}	
		}
		return resultScope
	}
}