package edu.kit.ipd.sdq.vitruvius.dsls.response.scoping

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope

import static edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MirBasePackage.Literals.*;

import org.eclipse.emf.ecore.EStructuralFeature
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicMultiValuedFeatureChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicSingleValuedFeatureChange
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.scoping.MirBaseScopeProviderDelegate
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.FeatureOfElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelCreate
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelDelete

class ResponseLanguageScopeProviderDelegate extends MirBaseScopeProviderDelegate {
	override getScope(EObject context, EReference reference) {
		if (reference.equals(FEATURE_OF_ELEMENT__FEATURE))
			return createEStructuralFeatureScope(context as FeatureOfElement)
		else if (reference.equals(FEATURE_OF_ELEMENT__ELEMENT)
			|| reference.equals(MODEL_ELEMENT__ELEMENT)) {
			if (context instanceof ConcreteTargetModelCreate
				|| context instanceof ConcreteTargetModelDelete) {
				return createQualifiedConcreteEClassScope(context.eResource);
			} else {
				return createQualifiedEClassScope(context.eResource)
			}
		}
		super.getScope(context, reference)
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
	
}
