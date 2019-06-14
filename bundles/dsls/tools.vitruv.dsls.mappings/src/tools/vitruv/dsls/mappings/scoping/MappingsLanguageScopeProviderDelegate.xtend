package tools.vitruv.dsls.mappings.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mirbase.scoping.MirBaseScopeProviderDelegate
import static tools.vitruv.dsls.mirbase.mirBase.MirBasePackage.Literals.*;
import static tools.vitruv.dsls.mappings.mappingsLanguage.MappingsLanguagePackage.Literals.*
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.EnforceableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableExpression
import org.eclipse.xtext.scoping.Scopes
import java.util.ArrayList

class MappingsLanguageScopeProviderDelegate extends MirBaseScopeProviderDelegate {

	override getScope(EObject context, EReference reference) {

		// context differs during content assist: 
		// * if no input is provided yet, the container is the context as the element is not known yet
		// * if some input is already provided, the element is the context
		if (reference.equals(METACLASS_FEATURE_REFERENCE__FEATURE))
			return createEStructuralFeatureScope(context as MetaclassFeatureReference)
		else if (reference.equals(METACLASS_REFERENCE__METACLASS)) {
			val contextContainer = context.eContainer();
			if (contextContainer instanceof FeatureCondition) {
				val enforcableCondition = contextContainer.eContainer as EnforceableCondition;
				val singleSidedCondition = enforcableCondition.eContainer as SingleSidedCondition;
				val mapping = singleSidedCondition.eContainer as Mapping;
				if (mapping.leftConditions.contains(singleSidedCondition)) {
					// left side
					return createMappingScope(mapping,true,false);
				} else {
					// right side			
					return createMappingScope(mapping,false,true);					
				}
			}
			else if(contextContainer instanceof BidirectionalizableCondition){
				val mapping = contextContainer.eContainer as Mapping;		
				//both sides
				return createMappingScope(mapping,true,true);			
			}
			else if(contextContainer instanceof BidirectionalizableExpression){
				val bidirectionalizableCondition = contextContainer.eContainer;
				val mapping = bidirectionalizableCondition.eContainer as Mapping;		
				//both sides
				return createMappingScope(mapping,true,true);	
			}
		}
		super.getScope(context, reference)
	}
	
	def createMappingScope(Mapping mapping, boolean leftSide, boolean rightSide){
		val featureList = new ArrayList();
		if(leftSide){
			featureList.addAll(mapping.leftParameters);
		}
		if(rightSide){
			featureList.addAll(mapping.rightParameters);			
		}
		return createScope(IScope.NULLSCOPE, featureList.iterator, [
						EObjectDescription.create(it.name, it.metaclass)
		]);
	}

	def createEStructuralFeatureScope(MetaclassFeatureReference featureReference) {
		if (featureReference?.metaclass !== null) {
			/*val changeType = featureReference.eContainer;
			 * val multiplicityFilterFunction = if (changeType instanceof ElementReplacementChangeType) {
			 * 	[EStructuralFeature feat | !feat.many];
			 * } else {
			 * 	[EStructuralFeature feat | true];
			 * }
			 * val typeFilterFunction = if (changeType instanceof ModelAttributeChange) {
			 * 	[EStructuralFeature feat | feat instanceof EAttribute];
			 * } else if (changeType instanceof ElementChangeType) {
			 * 	[EStructuralFeature feat | feat instanceof EReference];
			 * } else {
			 * 	throw new IllegalStateException();
			 * }
			 * createScope(IScope.NULLSCOPE, featureReference.metaclass.EAllStructuralFeatures.
			 * 	filter(multiplicityFilterFunction).filter(typeFilterFunction).iterator, [
			 * 	EObjectDescription.create(it.name, it)
			 ])*/
			createScope(IScope.NULLSCOPE, featureReference.metaclass.EAllStructuralFeatures.iterator, [
				EObjectDescription.create(it.name, it)
			])
		} else {
			return IScope.NULLSCOPE
		}
	}
}
