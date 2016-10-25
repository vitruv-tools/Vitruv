package tools.vitruv.dsls.response.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope

import static tools.vitruv.dsls.mirbase.mirBase.MirBasePackage.Literals.*;

import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.dsls.response.responseLanguage.AtomicMultiValuedFeatureChange
import tools.vitruv.dsls.response.responseLanguage.AtomicSingleValuedFeatureChange
import tools.vitruv.dsls.mirbase.scoping.MirBaseScopeProviderDelegate
import org.eclipse.emf.ecore.EcorePackage
import tools.vitruv.dsls.response.responseLanguage.inputTypes.InputTypesPackage
import tools.vitruv.dsls.response.responseLanguage.RoutineInput
import tools.vitruv.dsls.response.responseLanguage.CreateModelElement
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport

class ResponseLanguageScopeProviderDelegate extends MirBaseScopeProviderDelegate {
	override getScope(EObject context, EReference reference) {
		// context differs during content assist: 
		// * if no input is provided yet, the container is the context as the element is not known yet
		// * if some input is already provided, the element is the context
		if (reference.equals(METACLASS_FEATURE_REFERENCE__FEATURE))
			return createEStructuralFeatureScope(context as MetaclassFeatureReference)
		else if (reference.equals(METACLASS_REFERENCE__METACLASS)) {
			val contextContainer = context.eContainer();
			if (context instanceof CreateModelElement) {
				return createQualifiedEClassScopeWithoutAbstract(context.metamodel);
			} else if (contextContainer instanceof CreateModelElement) {
				return createQualifiedEClassScopeWithoutAbstract(contextContainer.metamodel);
			} else if (contextContainer instanceof RoutineInput) {
				val inputElement = context as MetaclassReference;
				return createQualifiedEClassScopeWithSpecialInputTypes(inputElement.metamodel);
			} else if (context instanceof RoutineInput) {
				return createQualifiedEClassScopeWithSpecialInputTypes(null);
			} else if (context instanceof MetaclassReference) {
				return createQualifiedEClassScopeWithEObject(context.metamodel)
			} else if (contextContainer instanceof MetaclassReference) {
				return createQualifiedEClassScopeWithEObject(contextContainer.metamodel)
			}
		}
		super.getScope(context, reference)
	}
	
	def createEStructuralFeatureScope(MetaclassFeatureReference featureReference) {
		if (featureReference?.metaclass != null) {
			val changeType = featureReference.eContainer;
			val filterFunction = if (changeType instanceof AtomicMultiValuedFeatureChange) {
				[EStructuralFeature feat | feat.many];
			} else if (changeType instanceof AtomicSingleValuedFeatureChange) {
				[EStructuralFeature feat | !feat.many];
			} else {
				[EStructuralFeature feat | true];
			}
			createScope(IScope.NULLSCOPE, featureReference.metaclass.EAllStructuralFeatures.filter(filterFunction).iterator, [
				EObjectDescription.create(it.name, it)
			])
		} else {
			return IScope.NULLSCOPE
		}
	}

	def createQualifiedEClassScopeWithSpecialInputTypes(MetamodelImport metamodelImport) {
		val classifierDescriptions = 
			if (metamodelImport == null || metamodelImport.package == null) {
				#[createEObjectDescription(EcorePackage.Literals.EOBJECT, true),
					createEObjectDescription(InputTypesPackage.Literals.STRING, true),
					createEObjectDescription(InputTypesPackage.Literals.INT, true)
				];
			} else {
				collectObjectDescriptions(metamodelImport.package, true, true, metamodelImport.useSimpleNames)		
			}

		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions)
		return resultScope
	}
}
