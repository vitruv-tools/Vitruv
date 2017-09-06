package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.dsls.commonalities.language.elements.impl.EFeatureAttributeImpl

import static com.google.common.base.Preconditions.*

class EFeatureAdapter extends EFeatureAttributeImpl implements Wrapper<EStructuralFeature> {

	EStructuralFeature wrappedEFeature
	Classifier adaptedType
	ClassifierProvider classifierProvider
	Metaclass containingMetaclass

	override withClassifierProvider(ClassifierProvider classifierProvider) {
		this.classifierProvider = checkNotNull(classifierProvider)
		if (wrappedEFeature !== null && containingMetaclass !== null) readAdaptedType()
		return this
	}

	def private checkAdaptedTypeRead() {
		if (adaptedType === null) {
			checkState(adaptedType !== null, "No classifier provider was set on this element!")
			checkState(containingMetaclass !== null, "No containing metaclass was set on this attribute!")
		}
	}

	def private checkEFeatureSet() {
		checkState(wrappedEFeature !== null, "No EStructualFeature was set on this adapter!")
	}
	
	def private checkMetaclassSet() {
		checkState(containingMetaclass !== null, "No metaclass was set on this attribute!")
	}

	def private readAdaptedType() {
		adaptedType = classifierProvider.toClassifier(wrappedEFeature.EType, containingMetaclass.domain)
		classifierProvider = null
	}

	override forEFeature(EStructuralFeature eFeature) {
		this.wrappedEFeature = checkNotNull(eFeature)
		if (classifierProvider !== null && containingMetaclass !== null) readAdaptedType()
		return this
	}
	
	override fromMetaclass(Metaclass metaclass) {
		this.containingMetaclass = checkNotNull(metaclass)
		if (wrappedEFeature !== null && classifierProvider !== null) readAdaptedType()
		return this
	}
	
	override basicGetClassLikeContainer() {
		if (eIsProxy) return null
		checkMetaclassSet()
		containingMetaclass
	}

	override getName() {
		if (eIsProxy) return null
		checkEFeatureSet()
		wrappedEFeature.name
	}

	override getType() {
		if (eIsProxy) return null
		checkAdaptedTypeRead()
		adaptedType
	}

	override getWrapped() {
		wrappedEFeature
	}

	override isMultiValued() {
		if (eIsProxy) return false
		checkEFeatureSet()
		wrappedEFeature.many
	}
	
	override toString() {
		'''{{«wrappedEFeature?.name»}}'''
	}

}
